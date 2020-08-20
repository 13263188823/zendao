package org.project.modules.message.job;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.project.common.util.DateUtils;
import org.project.common.util.RedisUtil;
import org.project.modules.message.entity.*;
import org.project.modules.message.service.ISysMessageService;
import org.project.modules.message.service.IZentaoQService;
import org.project.modules.message.service.IZentaoService;
import org.project.modules.message.util.*;
import org.project.modules.zentao.entity.ZtWorkhistory;
import org.project.modules.zentao.service.IZtWorkhistoryService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 信息同步任务
 */

@Slf4j
public class DingInformationTransferjob implements Job {

	@Resource
	private IZtWorkhistoryService ztWorkhistoryService;

	@Resource
	private RedisUtil redisUtil;

	@Resource
	private IZentaoService izentaoService;
	@SneakyThrows
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

	    log.info(String.format(" 钉钉定时 禅道信息同步 ! 时间:" + DateUtils.getTimestamp()));
		log.info(String.format(" ---------------------------------->"));

		int day =SomeDateUtils.getWeekStr(new Date());
		if(day!=0&&day!=6&&day!=7){
			//逻辑层
			List<UserEntity> userList= new ArrayList<UserEntity>();
			//获取头天工时不足人员列表
				userList = getUserToday();

			if(null!=userList&&userList.size()>0){
				//界面变动 实体会调整,需注意  未完成
				for(UserEntity ue:userList){
					String uuid =genRandomNum();
					izentaoService.insertZHkEntity(parWHEntity(ue,uuid));
					ztWorkhistoryService.save(parEntity(ue,uuid));
				}
			}else{
				//获取人员为空,无操作
			}
		}else{

		}
		log.info(String.format(" ---------------------------------->"));
		log.info(String.format(" -----------------定时任务结束！----------------->"));
	}

	//暂时处理,对象转换 工时原因未反射,类型可能调整
	private ZtWorkhistory parEntity(UserEntity ue,String uid){
		ZtWorkhistory zhs = new ZtWorkhistory();
		if(null!=ue.getRealname()&&!"".equals(ue.getRealname())){
			zhs.setName(ue.getRealname());
			//zhs.setModifiedman(ue.getRealname()+","+SomeDateUtils.getYesterday()+","+ue.getConsumed());
			//zhs.setMemo(uid+","+ue.getConsumed()+","+ue.getRealname()+","+SomeDateUtils.getYesterday());
			zhs.setModifiedman(ue.getConsumed()+"");
			zhs.setMemo(ue.getConsumed()/8*100+"%");

		}
		zhs.setCreatedate(SomeDateUtils.getYesterdayDate());
		zhs.setUpdatedate(SomeDateUtils.getYesterdayDate());
		zhs.setModifieddate(SomeDateUtils.getYesterdayDate());
		return zhs;
	}

	//暂时处理,对象转换 工时原因未反射,类型可能调整
	private ZtWorkHistoryEntity parWHEntity(UserEntity ue,String rid){
		ZtWorkHistoryEntity whe = new ZtWorkHistoryEntity();
		if(null!=ue.getRealname()&&!"".equals(ue.getRealname())){
			whe.setName(ue.getRealname());
			whe.setModifiedMan(ue.getRealname());
		}
		whe.setStatus("1");
        whe.setMemo(rid+","+ue.getConsumed()+","+ue.getRealname()+","+SomeDateUtils.getYesterday());
		whe.setCreateDate(SomeDateUtils.getYesterdayDate());
		whe.setUpdateDate(SomeDateUtils.getYesterdayDate());
		whe.setModifiedDate(SomeDateUtils.getYesterdayDate());
		return whe;
	}

	public String genRandomNum(){
		int  maxNum = 36;
		int i;
		int count = 0;
		char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
				'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ,'=','+',};
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while(count < 8){
			i = Math.abs(r.nextInt(maxNum));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count ++;
			}
		}
		return pwd.toString();
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
	 * double 相减
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sum(double d1,double d2){
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.add(bd2).doubleValue();
	}

	/**
	 * double 减法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sub(double d1,double d2){
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.subtract(bd2).doubleValue();
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
	 * 获取昨天统计的前日未补录用户信息
	 * @return
	 */
	private List<UserEntity> getUserYesterdayAgo(){
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
}
