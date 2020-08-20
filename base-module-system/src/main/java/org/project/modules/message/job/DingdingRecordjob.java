package org.project.modules.message.job;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.project.common.util.RedisUtil;
import org.project.modules.message.entity.UserEntity;
import org.project.modules.message.entity.ZtTaskestimateEntity;
import org.project.modules.message.service.*;
import org.project.modules.message.util.*;
import org.project.modules.zentao.entity.ZtWorkhistory;
import org.project.modules.zentao.service.IZtWorkhistoryService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 补录昨日任务
 */

@Slf4j
public class DingdingRecordjob implements Job {

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

	@Resource
	private RedisUtil redisUtil;

	@Resource
	private IZentaoService izentaoService;

	@Resource
	private IZentaoQService izentaoQService;

	@Resource
	private IZentaoYesterDayService iZentaoYesterDayService;

	@Resource
	private IZtWorkhistoryService ztWorkhistoryService;

	@Resource
	private IZentaoYesterDayDeptService iZentaoYesterDayDeptService;

	@Resource
	private IZentaoTaskestimateService iZentaoTaskestimateService;

	@SneakyThrows
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		int day =SomeDateUtils.getWeekStr(new Date());
		if(day!=0&&day!=7&&day!=1){
			//获取前天未填写列表,查询用户工时信息,工时大于8小时 补录,否则不处理
			List<UserEntity> userList= new ArrayList<UserEntity>();
			//获取当天工时不足人员列表
			if(day==2){
				userList = getUserToday();
			}else if(day==3){
				userList = getUserYesterday();
			}else{
				userList = getUserThreeDaysAgo();
			}


			if(null!=userList&&userList.size()>0){
				List<ZtTaskestimateEntity> list = new ArrayList<ZtTaskestimateEntity>();
				//未优化,十分不推荐这么写 , IN user
				List<UserEntity>listUser =iZentaoYesterDayService.findUserAll();
				ZtTaskestimateEntity ztte = new ZtTaskestimateEntity();
	//			List<ZtWorkhistory> listZWH =ztWorkhistoryService.findByDate(SomeDateUtils.getThreeDaysAgo(),SomeDateUtils.getYesterday());
				StringBuffer strB = new StringBuffer();

				String dateBegin ="";
				String dateEnd ="";

				if(day!=2){
					dateBegin =SomeDateUtils.getThreeDaysAgo();
					dateEnd = SomeDateUtils.getYesterday();
				}else{
					dateBegin = SomeDateUtils.getYesterday();
					dateEnd = SomeDateUtils.getToday();
				}

	//			if(listZWH!=null){   这个是正确的完成后恢复
				if(null!=null){      //这个删掉调节页面对应方法暂时加上,页面调整完成删除
					//待修改,UserLit
					for(UserEntity ue:userList){
						for(UserEntity ues:listUser){
							if(ue.getRealname().equals(ues.getRealname())){
								//性能很差工时不足,待优化
								List<ZtTaskestimateEntity> ztteList =iZentaoTaskestimateService.findTaskestimateByUser(ues.getAccount(),dateBegin,dateEnd);
								Double sumConsumed = new Double(0);
								for(ZtTaskestimateEntity zt :ztteList){
									sumConsumed =add(sumConsumed,zt.getConsumed());
								}

								if(ues.getAccount().contains("wangzichen")){
									System.out.println("111");
								}
								//if(ztte.getConsumed() >=8){
								if(sumConsumed >=8){
									if(strB.length()>0){
										strB.append(","+ues.getRealname());
									}else{
										strB.append(ues.getRealname());
									}
								}
							}
						}
					}
				}else{
					System.out.println("-------------->");
				}
				System.out.println(strB.toString());
				//开始根据用户名补录
				//赶工期完成后优化
				String[] strList =strB.toString().split(",");
				//更新三天前状态为核销
				//删除框架信息
				//System.out.println(SomeDateUtils.getThreeDaysAgo());
				//if(null!=strList && strList.length>0){
				//	for(String s: strList){
				//		izentaoService.updateEntityByNameAndDate(s,SomeDateUtils.getThreeDaysAgo());
				//		ztWorkhistoryService.deleteByUserNameAndcreateDate(s,SomeDateUtils.getThreeDaysAgo(),SomeDateUtils.getYesterday());
				//	}
				//}

				//待优化
				if(day!=2){
					if(null!=strList && strList.length>0){
						for(String s: strList){
							izentaoService.updateEntityByNameAndDate(s,SomeDateUtils.getThreeDaysAgo());
	//						ztWorkhistoryService.deleteByUserNameAndcreateDate(s,SomeDateUtils.getThreeDaysAgo(),SomeDateUtils.getYesterday());
						}
					}
				}else{
					if(null!=strList && strList.length>0){
						for(String s: strList){
							izentaoService.updateEntityByNameAndDate(s,SomeDateUtils.getYesterday());
	//						ztWorkhistoryService.deleteByUserNameAndcreateDate(s,SomeDateUtils.getYesterday(),SomeDateUtils.getToday());
						}
					}
				}

			}else{
				//未找到未填人员//无操作
			}
		}
	}

	/**
	 * 获取RedisObjectString
	 * @param str
	 * @return
	 */
	private String getRedisInfoByString(String str){
		Object obj =redisUtil.get(str);
		if(null!=obj) {
			return obj.toString();
		}
		return new String("");
	}

	/**
	 * 获取昨天未补录用户信息
	 * @return
	 */
	private List<UserEntity> getUserThreeDaysAgo(){
		String unStr =getRedisInfoByString(SomeDateUtils.getThreeDaysAgo()+"_unStr");
		//String[] strNormaliList =strNormali.replace("\n","").split("%");
		String[] unStrList =unStr.replace("\n","").split("%");
		List<UserEntity> userList = new ArrayList<UserEntity>();
		if(unStr.length()>0){
			//用户组装
			userList = parUser(userList,unStrList);
			return parUser(userList,unStrList);
		}else{ return userList; }
	}
	/**
	 * 用户整理
	 * @return
	 */
	private List<UserEntity> parUser(List<UserEntity> userList ,String[] unStrList ){
		String strSu =null;
		String strSuCount =null;
		Double dbSu = null;
		UserEntity ue = null;
		for(String sU: unStrList){
			strSu =parSubString(sU,0);
			strSuCount =parSubString(sU,1);
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
	 * 获取今日统计昨日的未补录用户信息
	 * @return
	 */
	private List<UserEntity> getUserToday(){
		String unStr =getRedisInfoByString(SomeDateUtils.getToday()+"_unStr");
		//String[] strNormaliList =strNormali.replace("\n","").split("%");
		String[] unStrList =unStr.replace("\n","").split("%");
		List<UserEntity> userList = new ArrayList<UserEntity>();
		if(unStr.length()>0){
			//用户组装
			return parUser(userList,unStrList);
		}else{
			return userList;
		}
	}

	/**
	 * 获取今日统计昨日的未补录用户信息
	 * @return
	 */
	private List<UserEntity> getUserYesterday(){
		String unStr =getRedisInfoByString(SomeDateUtils.getYesterday()+"_unStr");
		//String[] strNormaliList =strNormali.replace("\n","").split("%");
		String[] unStrList =unStr.replace("\n","").split("%");
		List<UserEntity> userList = new ArrayList<UserEntity>();
		if(unStr.length()>0){
			//用户组装
			return parUser(userList,unStrList);
		}else{
			return userList;
		}
	}

	/**
	 * 截取工时信息
	 * @param des
	 * @param flag
	 * @return
	 */
	private String parSubString (String des,int flag){

		//用户名截取
		if(0 == flag) {
			return des.substring(des.indexOf("姓名:")+3,des.indexOf("工作时长:"));
		}
		//工时截取
		if(1 == flag) {
			return des.substring(des.indexOf("工作时长:")+5,des.indexOf("工作饱和度:"));
		}

		return "";
	}

	/**
	 * * 两个Double数相加 *
	 *
	 * @param v1 *
	 * @param v2 *
	 * @return Double
	 */
	public static Double add(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.add(b2).doubleValue());
	}
}
