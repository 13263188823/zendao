package org.project.modules.message.job;

import com.sun.org.apache.xml.internal.utils.StringBufferPool;
//import com.sun.xml.internal.bind.v2.TODO;
import jdk.internal.instrumentation.Logger;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.project.common.util.DateUtils;
import org.project.common.util.RedisUtil;
import org.project.modules.message.entity.*;
import org.project.modules.message.service.ISysMessageService;
import org.project.modules.message.service.IZentaoQService;
import org.project.modules.message.service.IZentaoService;
import org.project.modules.message.util.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 发送消息任务
 */

@Slf4j
public class Dingdingjob implements Job {

	@Autowired
	private ISysMessageService sysMessageService;

	//返回参数
	private static String resString;
	//钉钉机器access_token
	private static String dingUrl;
	//统计起始时间
	private static String startTime = "0";
	//统计结束时间
	private static String endTime = "9";
	//钉钉URL
	private static String url = "jdbc:mysql://localhost:6611/zentao?autoReconnect=true&zeroDateTimeBehavior=convertToNull";

	//SSH的HOST
	private static String sshHost = "172.16.1.103";
	//SSH的User
	private static String sshUser = "root";
	//SSH的password
	private static String sshPassword = "qiyi123!@#";
	//Des目标数据库用户名
	private static String desUser = "project";
	//Des目标数据库密码
	private static String desPassword = "123456";
	//过滤人员名单
	private static String userVip = "李仑,李嘉,李玉洁";
	@Resource
	private RedisUtil redisUtil;

	@Resource
	private IZentaoService izentaoService;

	@Resource
	private IZentaoQService izentaoQService;

	@SneakyThrows
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		//String dingUrl = "https://oapi.dingtalk.com/robot/send?access_token=8f03162faa65c17d67fd6c3e7298f04b197e310dd3342b428417ff356dbf9c6c";


		log.info(String.format(" 钉钉定时 定时任务对当前钉钉用户进行统计 !  时间:" + DateUtils.getTimestamp()));
		log.info(String.format(" ---------------------------------->"));
		log.info(String.format(" ---------------------------------->"));
		//获取当前星期几，之后根据不同星期数来进行操作
		int day = SomeDateUtils.getWeekStr(new Date());
		//调用接口，确定今天是否是节假日，工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2
		String isFlag = HolidayCopy.HolidayGet(SomeDateUtils.getTodaytoYYYYMMDD());

		if (day != 0) {
			//禅道数据准备
			BaseSSHUtils.startSSH(sshUser, sshPassword, sshHost);

			//获取禅道用户信息
			String ztUserSql = "select * from zt_user";
			List<Map<String, Object>> list = BaseSSHUtils.runSSH(url, desUser, desPassword, ztUserSql);
			//获取禅道部门信息
			String ztDeptSql = "select * from zt_dept";
			List<Map<String, Object>> listDempt = BaseSSHUtils.runSSH(url, desUser, desPassword, ztDeptSql);
			//用户处理
			List<UserEntity> listUe = new ArrayList<UserEntity>();
			//现在的list里存储的是从数据库中得到的信息，listUe为空，要把list中的数据存储到listUe中
			userProcess(list, listUe);

			//部门对照过滤并回填
			listUe = parDeptAnduser(listUe);

			StringBuffer userListStr = null;
			//把userListStr中添加每个符合要求的用户，并使用“，”分隔开，这个符合sql语句的查询格式
			for (UserEntity ue : listUe) {
				//getDeleted暂时没有意义，之后可以添加update语句将数据库中的部门字段更新
				if (!ue.getDeleted().equalsIgnoreCase("1")) {
					if (null != userListStr) {
						userListStr.append("," + "'" + ue.getAccount() + "'");
					} else {
						userListStr = new StringBuffer();
						StringBuffer append = userListStr.append("'" + ue.getAccount() + "'");
					}
				}
			}

			//整体未优化,SQL后期可配置位置动态获取
			//获取填充工时
			//根据sql语句查询出对应用户的工时

			String ztTaskSQL = "select * from zt_taskestimate zt where zt.account in (" + userListStr + ")\n" +
					"and date>= '" + SomeDateUtils.getYesterday() + "'\n" +
					"and date<'" + SomeDateUtils.getToday() + "'";

			//根据用户查询工时
			List<Map<String, Object>> listTask = BaseSSHUtils.runSSH(url, desUser, desPassword, ztTaskSQL);
			List<Object> listObj = new ArrayList<Object>();
			//在ZtTaskestimateeEntity类中的存在的属性才会在listObj中有属性
			for (Map<String, Object> map : listTask) {
				listObj.add(RefiectBeanUtils.map2JavaBean(ZtTaskestimateeEntity.class, map));
			}
			//建了一个listZte在存储ZtTaskestimateeEntity类的对象
			List<ZtTaskestimateeEntity> listZte = new ArrayList<ZtTaskestimateeEntity>();

			ZtTaskestimateeEntity zt = null;
			//找listObj也就是查询结果中的每一个对象，把结果加入到listZte中，目的是为了得到ZtTaskestimateEntity类型的对象
			for (Object obj : listObj) {
				//把查询到的每一个对象obj转换成ZtTaskestimateEntity类型
				zt = (ZtTaskestimateeEntity) obj;
				//把查到的ZtTaskestimateEntity添加到listZte中，里面存储了工时信息
				listZte.add(zt);
			}

			//listUe中存储着过滤了部门之后的人员，是一个个UserEntity对象，遍历其中的每一个人员
			for (UserEntity ue : listUe) {
				//对每个人员的工时信息进行遍历
				for (ZtTaskestimateeEntity zte : listZte) {
					//对可能传入的非法数据进行处理
					if (null == ue.getConsumed()) {
						ue.setConsumed(0.0);
					}
					//将同一个人的工时信息进行累加
					if (ue.getAccount().equalsIgnoreCase(zte.getAccount())) {
						//把每一条listZte中的信息都加和到ue中
						ue.setConsumed(ue.getConsumed() + zte.getConsumed());
					}
				}
			}

			//System.out.println(listUe.toString());

			//工时组装
			//calculationOfWorkingHours(listUe,historyList,historyEndList);
			//定义了未达标的输出字符串
			StringBuffer unStr = getStringBuffer();
			//定义了达标的输出字符串
			StringBuffer strNormali = getStringBuffer();

			//用户工时处理
			// 对白名单中的成员进行处理

			String[] vips = userVip.split(",");
			List<String> test = new ArrayList<String>();
			for(String test1:vips){
				test.add(test1);
			}

			for (UserEntity ue : listUe) {
				//工时逻辑，得到的总和工时会小于8的
				if (ue.getConsumed() < 8) {
					//人员过滤，过滤白名单中的人，并设置标志位，会加入到工时满的人名单
						if (!test.contains(ue.getRealname())){
							prossceUserString(unStr, ue);
					    }
				}
				else {
					prossceUserString(strNormali, ue);
				}
			}

			//按照日期发送到Redis
			//redis中存储的表单信息日期是今天的，实际统计的应该是昨天的信息
			//redis中会得到完整的信息，但会在信息同步的时候做处理
			sendRedes(SomeDateUtils.getToday(), strNormali, unStr);



			//历史禅道信息
			//izentaoQService.insertEntity();
			//List<ZtUserQuickQueryEntity> listZqqe =izentaoQService.findEntityAll();
			//筛选逻辑,用来展示本日工时不足的累计信息
			//组装反馈统计信息
			//判断是非节假日，才会进入星期的判断
			if (!isFlag.equalsIgnoreCase("2")) {
				switch (day) {
					//周一
					case 1:
						//发送非历史逻辑信息

						StringBuffer strNormaliOne = new StringBuffer();
						StringBuffer unStrOne = new StringBuffer(getUserFourDaysAgo());

						StringBuffer invMsgOne = new StringBuffer();
						invMsgOne.append("");
						StringBuffer headMsgOne = new StringBuffer();
						headMsgOne.append("【" + SomeDateUtils.getFourDaysAgo() + "】 \n 工作<8小时人员名单如下，请进行工时补录，如在下班前仍未补录，部门负责人将和你一对一约谈。");
						process(jobExecutionContext.getMergedJobDataMap().get("parameter").toString(), resMsg(headMsgOne, strNormaliOne, invMsgOne.toString(), unStrOne));
						break;
						//周六
					case 6:

						StringBuffer invMsgSix = new StringBuffer();
						invMsgSix.append("星期六,好好休息!");
						break;
						//不发送process(jobExecutionContext.getMergedJobDataMap().get("parameter").toString(),resMsg(null,null,invMsgSix.toString(),null));
 					//周日
					case 7:

						StringBuffer invMsgSeven = new StringBuffer();
						invMsgSeven.append("星期日,好好休息!");
						break;
						//不需要发送

					default:
						StringBuffer invMsg = new StringBuffer();
//						invMsg(listZhe, invMsg);
						//System.out.println(invMsg);
						//执行发送处理
						StringBuffer headMsg = new StringBuffer();
						headMsg.append("【" + SomeDateUtils.getYesterday() + "】 \n 工作<8小时人员名单如下，请进行工时补录，如在下班前仍未补录，部门负责人将和你一对一约谈。");
						process(jobExecutionContext.getMergedJobDataMap().get("parameter").toString(), resMsg(headMsg, strNormali, invMsg.toString(), unStr));
				}
			}
		}
		log.info(String.format(" ---------------------------------->"));
		log.info(String.format(" -----------------定时任务结束！----------------->"));
	}

	//StringBuffer
	private StringBuffer getStringBuffer() {
		return new StringBuffer();
	}

	/**
	 * 缓存存储 strNormali 正常
	 * 缓存存储 unStr 未达标
	 *
	 * @param strno
	 * @param strur
	 */
	private void sendRedes(String dateString, StringBuffer strno, StringBuffer strur) {
		redisUtil.set(dateString + "_strNormali", strno);
		redisUtil.set(dateString + "_unStr", strur);
	}

	

	/**
	 * 开始对Task工时从新装填,符合每日工时总量  这里需要优化,工时原因暂未优化
	 *
	 * @param taskList
	 * @param historyList
	 * @param historyEndList
	 * @return
	 */
	//待优化,工时不足,完成后优化
	private List<UserEntity> calculationOfWorkingHours(List<UserEntity> uel, List<ZtHistoryEntity> historyList, List<ZtHistoryEntity> historyEndList) {
		for (UserEntity ue : uel) {
			for (ZtHistoryEntity ze : historyList) {
				if (ue.getAccount().equals(ze.getNewInfo())) {
					for (ZtHistoryEntity zeHl : historyEndList) {
						if (ze.getAction() == zeHl.getAction()) {
							if (null != ue.getConsumed() || zeHl.getCpCount() != 0.0) {
								if (ue.getConsumed() == null) {
									ue.setConsumed(0.0);
								}
								ue.setConsumed(ue.getConsumed() + zeHl.getCpCount());
							} else {
								Double db = new Double("0");
								ue.setConsumed(db);
							}
						}
					}
				}
			}
		}
		return uel;
	}

	/**
	 * 反馈信息组装
	 *
	 * @param strNormali 正常用户
	 * @param unStr      工时不足用户
	 * @return
	 */
	private String resMsg(StringBuffer headMsg, StringBuffer strNormali, String invMsg, StringBuffer unStr) {
		//这里可以获取规则,用于组装反馈
		//信息处理
		return new String(rolePar(headMsg, strNormali, unStr, invMsg, ""));
	}

	/**
	 * 信息组装规则(发送模板)
	 */
	private String rolePar(StringBuffer headMsg, StringBuffer strNormali, StringBuffer unStr, String invMsg, String role) {
		//暂时未编写
		// 反馈规则,
		// 反馈暂时采用 msg(NoJson)
		StringBuffer strB = new StringBuffer();
		//strB.append("昨日工时正常人员名单:");
		//strB.append("\n");
		//strB.append(strNormali);
		//strB.append("\n");
		//反馈信息头
		strB.append("@所有人:");
		strB.append(headMsg);
		strB.append("\n");
		//补录功能暂不使用 invMsg方法
		//strB.append(invMsg);
		//strB.append("工时未达标名单:");
		strB.append(unStr);
		return strB.toString();
	}

	/**
	 * 用户信息处理(分组)
	 *
	 * @param strB
	 * @param ue
	 * @return
	 */
	private StringBuffer prossceUserString(StringBuffer strB, UserEntity ue) {
		if (strB != null && strB.length() > 0) {
			accountAndRealName(strB, ue);
		} else {
			accountAndRealName(strB, ue);
		}
		return strB;
	}

	/**
	 * 访问名处理
	 *
	 * @param strB
	 * @return
	 */
	private StringBuffer accountAndRealName(StringBuffer strB, UserEntity ue) {
		//strB.append("用户访问名:"+ue.getAccount());
		strB.append("\n");
		strB.append("姓名:"+ue.getRealname());
		strB.append("\n");
		//工时负数校验
		if (ue.getConsumed() < 0) {
			ue.setConsumed(new Double(0));
		}
		strB.append("工作时长:" + formatToNumber(new BigDecimal(ue.getConsumed())));
		strB.append("\n");
		strB.append("工作饱和度:" + formatToNumber(new BigDecimal(ue.getConsumed()/ 8.0 * 100) ) + "%");
		strB.append("\n");
		return strB;
	}

	/**
	 * 处理
	 *
	 * @param url     请求路径
	 * @param sendMsg 请求信息
	 */
	private void process(String url, String sendMsg) {
		dingUrl = url;
		if (null != dingUrl && !"".equals(dingUrl)) {
			resString = DingTalkUtil.process(dingUrl, sendMsg);
			if (null != resString) {
				log.info(String.format(" ------发送时间->" + DateUtils.getTimestamp()));
			}
		} else {
			log.info(String.format(" ------------钉钉机器人路径异常!,参数为空!"));
		}
	}


	//用户处理，要把list中的数据存储到listUe中
	private void userProcess(List<Map<String, Object>> list, List<UserEntity> listUe) throws Exception {
		//新建一个反射的处理中间表
		List<Object> listObj = new ArrayList<Object>();
		//把list中的内容，通过反射，根据构建好的UserEntity中的set方法对应的属性
		for (Map<String, Object> map : list) {
			//在listObj中添加符合要求，也就是存在于UserEntity中的属性
			listObj.add(RefiectBeanUtils.map2JavaBean(UserEntity.class, map));
		}
		//用户信息处理
		//新建一个UserEntity的对象
		UserEntity ue = null;
		for (Object obj : listObj) {
			//把listObj中的存储的每个属性都转型成UserEntity类型，是按照其中的类型存储的
			ue = (UserEntity) obj;
			//把listObj中的每个属性都添加到listUe中
			listUe.add(ue);
		}
	}

	//部门处理
	private void deptProcess(List<Map<String, Object>> list, List<DeptEntity> listDe) throws Exception {
		List<Object> listObj = new ArrayList<Object>();
		for (Map<String, Object> map : list) {
			listObj.add(RefiectBeanUtils.map2JavaBean(DeptEntity.class, map));
		}
		//用户信息处理//后续转换动态类型,现阶段意义不大
		DeptEntity de = null;

		for (Object obj : listObj) {
			//处理用户信息符合要求信息过滤
			de = (DeptEntity) obj;
			listDe.add(de);
		}
	}

	//任务处理
	private void taskProcess(List<Map<String, Object>> list, List<TaskEntity> listTE) throws Exception {
		List<Object> listObj = new ArrayList<Object>();
		for (Map<String, Object> map : list) {
			listObj.add(RefiectBeanUtils.map2JavaBean(TaskEntity.class, map));
		}
		//用户信息处理//后续转换动态类型,现阶段意义不大
		TaskEntity te = null;
		for (Object obj : listObj) {
			//处理用户信息符合要求信息过滤
			te = (TaskEntity) obj;
			listTE.add(te);
		}
	}






	/**
	 * 对照Demp-user
	 *
	 * @param listUser
	 * @param listDe
	 * @return
	 */
	private List<UserEntity> parDeptAnduser(List<UserEntity> listUser) {
		//List<DeptEntity> listDe部门详情暂不需要
		//新建一个listU，来存放处理后的数据
		List<UserEntity> listU = new ArrayList<UserEntity>();
		if (null != listUser) {
			//把dept属性赋值为“”
			String dept = "";
			//暂时未优化,完成后优化- -
			for (UserEntity user : listUser) {
				//部门过滤条件暂未调整,之后可以做成参数形式定时任务中填写即可
				//通过对照ConstantEnum中的部门，将这些部门中的人排除后，再写入listU中
				if (flagState(user.getDept() + "")) {
				} else {
					listU.add(user);
				}
			}
			return listU;
		} else {
			return null;
		}
	}

	/**
	 * 部门过滤
	 * 如果人员对应的部门存在于ConstantEnum中，会返回true
	 * @param dept
	 * @return
	 */
	private boolean flagState(String dept) {
		return ConstantEnum.getFlag(dept);
		//if(dept.equals(ConstantEnum.PARENT)||dept.equals(ConstantEnum.WOMAI)||dept.equals(ConstantEnum.PRODUCT)||dept.equals(ConstantEnum.DEVELOPMENT)||dept.equals(ConstantEnum.TEST)||dept.equals(ConstantEnum.OPERATION_AND_MAINTENANCE)||dept.equals(ConstantEnum.FRONT_END)||dept.equals(ConstantEnum.STAFF_LEAVING)){
		//	return true;
		//}else{
		//	return false;
		//}
	}

	private List<UserEntity> getUserThreeDaysAgo() {
		String unStr = getRedisInfoByString(SomeDateUtils.getThreeDaysAgo() + "_unStr");

		//String[] strNormaliList =strNormali.replace("\n","").split("%");
		String[] unStrList = unStr.replace("\n", "").split("%");

		//System.out.println(strNormaliList);
		System.out.println(unStrList);

		List<UserEntity> userList = new ArrayList<UserEntity>();
		//用户组装
		return parUser(userList, unStrList);
	}

	public String getUserFourDaysAgo() {
		String unStr = getRedisInfoByString(SomeDateUtils.getFourDaysAgo() + "_unStr");

		//String[] strNormaliList =strNormali.replace("\n","").split("%");
		String[] unStrList = unStr.replace("\n", "").split("%");

		//System.out.println(strNormaliList);
		System.out.println(unStrList);

		List<UserEntity> userList = new ArrayList<UserEntity>();
		//用户组装
		return unStr;
	}

	/**
	 * 获取RedisObjectString
	 *
	 * @param str
	 * @return
	 */
	private String getRedisInfoByString(String str) {
		Object obj = redisUtil.get(str);
		if (null != obj) {
			return obj.toString();
		}
		return new String("");
	}

	/**
	 * 用户整理
	 *
	 * @return
	 */
	private List<UserEntity> parUser(List<UserEntity> userList, String[] unStrList) {
		//
		String strSu = null;
		String strSuCount = null;

		//使用的工时
		Double dbSu = null;
		//用户
		UserEntity ue = null;
		for (String sU : unStrList) {
			//截取出用户名
			strSu = parSubString(sU, 0);
			//截取出工时信息
			strSuCount = parSubString(sU, 1);
			dbSu = new Double(strSuCount);
			//开始组装
			ue = new UserEntity();
			ue.setConsumed(dbSu);
			ue.setRealname(strSu);
			userList.add(ue);
		}
		return userList;
	}

	/**
	 * 截取工时信息
	 *
	 * @param des
	 * @param flag
	 * @return
	 */
	private String parSubString(String des, int flag) {

		//用户名截取
		if (0 == flag) {
			return des.substring(des.indexOf("姓名:") + 3, des.indexOf("工作时长:"));
		}
		//工时截取
		if (1 == flag) {
			return des.substring(des.indexOf("工作时长:") + 5, des.indexOf("工作饱和度:"));
		}
		return "";
	}

	/**
	 * 格式化输出小数
	 * @param obj
	 * @return
	 */
	private static String formatToNumber(BigDecimal obj) {
		DecimalFormat df = new DecimalFormat("#.0");
		if (obj.compareTo(BigDecimal.ZERO) == 0) {
			return "0.0";
		} else if (obj.compareTo(BigDecimal.ZERO) > 0 && obj.compareTo(new BigDecimal(1)) < 0) {
			return "0" + df.format(obj).toString();
		} else {
			return df.format(obj).toString();
		}
	}

	/**
	 * double 相减
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sum(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.add(bd2).doubleValue();
	}

	/**
	 * double 减法
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sub(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.subtract(bd2).doubleValue();
	}

//以下为暂时没有用到的功能
	/**
	 * 本星期补录人员提示
	 *
	 * @param listZhe
	 * @param invMsg
	 * @return
	 */
	private String invMsg(List<ZtWorkHistoryEntity> listZhe, StringBuffer invMsg) {
		if (listZhe.size() >= 0) {
			for (ZtWorkHistoryEntity zhe : listZhe) {
				invMsg.append("\n");
				invMsg.append("[姓名]" + zhe.getCountName() + ",累计:" + zhe.getCount() + "次" + ",补录" + zhe.getRecordingCount() + "次");
			}
			invMsg.append("\n");
			return invMsg.toString();
		} else {
			invMsg.append("\n");
			return invMsg.append("本星期无补录信息").toString();
		}
	}


	//行为处理 ,之后会抽取
	private void ActionProcess(List<Map<String, Object>> list, List<ActionEntity> listTE) throws Exception {
		List<Object> listObj = new ArrayList<Object>();
		for (Map<String, Object> map : list) {
			listObj.add(RefiectBeanUtils.map2JavaBean(ActionEntity.class, map));
		}
		//用户信息处理//后续转换动态类型,现阶段意义不大
		ActionEntity ae = null;

		for (Object obj : listObj) {
			//处理用户信息符合要求信息过滤
			ae = (ActionEntity) obj;
			//if(ue.getRealname().contains("我买")){ }else{ listUe.add(ue); }
			listTE.add(ae);
		}
	}

	//历史信息处理
	private void historyProcess(List<Map<String, Object>> list, List<ZtHistoryEntity> listZe) throws Exception {
		List<Object> listObj = new ArrayList<Object>();
		for (Map<String, Object> map : list) {
			listObj.add(RefiectBeanUtils.map2JavaBeanIncludeKeywords(ZtHistoryEntity.class, map));
		}
		//用户信息处理//后续转换动态类型,现阶段意义不大
		ZtHistoryEntity ze = null;
		for (Object obj : listObj) {
			//处理用户信息符合要求信息过滤
			ze = (ZtHistoryEntity) obj;
			//if(ue.getRealname().contains("我买")){ }else{ listUe.add(ue); }
			listZe.add(ze);
		}
	}

//	private void dayOffProcess(){
//		//sql的意思是要在工时未满8小时的人再查出存在于请假名单的人，userListStr是要替换的字段
//		//查询条件应该随着表的构建来完成
//		StringBuffer userListStr = new StringBuffer("");
//		String dayOffsql = "select account,beforedate,afterdate from dayOff df where df.account in (" + userListStr + ")\n" +
//				"and date>= '" + SomeDateUtils.getYesterday() + "'\n" +
//				"and date<'" + SomeDateUtils.getToday() + "'";
//		//把查出的结果存储出来，其实只需要查找的是姓名，所以可以把存储的结构改变
//		Map<String, Object> listDayOff = BaseSSHUtils.runSSH(url, desUser, desPassword, dayOffsql);
//
//
//	}

}


