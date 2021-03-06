package com.crt.jobs.task;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crt.jobs.api.ITaskService;
import com.crt.jobs.core.JobService;

/**
 * Leader的定时任务类
 * 
 * @version V1.0.0
 */
final class LeaderTask extends com.crt.jobs.api.AbstractTask {

	private static final Logger Logger = LoggerFactory.getLogger(LeaderTask.class);

	private JobService jobService;

	private ITaskService taskService;

	public void setJobService(JobService service) {
		this.jobService = service;
	}
	
	public void setTaskService(ITaskService service){
		this.taskService=service;
	}

	@Override
	public void execute(JobExecutionContext context) {
		// //Leader的任务
		// try {
		// //===================================== 负载均衡 begin
		// =====================================
		// //Leader选举和获取当前Leader
		// ServInfo servInfo = servInfoService.chooseLeader();
		//
		// if(servInfo != null &&
		// servInfo.getServid().equals(Constant.serviceCode())) {
		// //自己为Leader，则分配任务
		// List<TaskJob> jobs = taskJobService.findActive();
		// Map<String, List<TaskJob>> servMap = new HashMap<String,
		// List<TaskJob>>();
		// for (TaskJob job : jobs) {
		// List<TaskJob> servList = servMap.get(job.getServid());
		// if(servList == null) {
		// servList = new ArrayList<TaskJob>();
		// }
		// servList.add(job);
		// servMap.put(job.getServid(), servList);
		// }
		// //获取所有活动的服务
		// List<ServInfo> sis =
		// servInfoService.findByStatus(ServInfoStatus.NORMAL.getCode());
		//
		// //得到服务负载的任务数目
		// int jobNum = jobs.size() / sis.size();
		// if(jobs.size() % sis.size() != 0) {
		// jobNum ++;
		// }
		//
		// for (ServInfo si : sis) {
		// //服务负载均衡计算
		// List<TaskJob> servJobs = servMap.get(si.getServid());
		// int servJobNum = 0;
		// if(servJobs != null) {
		// servJobNum = servJobs.size();
		// }
		// if(servJobs != null && servJobNum > jobNum) {
		// //需要释放任务
		// int destroyNum = servJobNum - jobNum;
		// for (int i = 0; i < destroyNum; i++) {
		// TaskJob tj = servJobs.get(i);
		// //需要释放的任务写入数据库中
		// servEqService.save(si.getServid(), tj.getId());
		// }
		// } else {
		// //需要补充任务，锁定指定数目为服务的任务
		// Integer topnum = jobNum - servJobNum;
		// taskJobService.updateServidByWait(si.getServid(), topnum);
		// }
		// }
		// }
		// } catch (Exception e) {
		// LOGGER.error("负载均衡异常: " + e.getMessage(), e);
		// }
		// //===================================== 负载均衡 end
		// =====================================
		//
		// //将任务执行过期30s和状态不为停止的 改为加入待执行
		// taskJobService.updateWait();
		//
		// //处理需要自己释放的任务
		// List<ServEq> ses = servEqService.findByServid(Constant.serviceCode(),
		// ServEqStatus.WAIT.getCode());
		// for (ServEq se : ses) {
		// try {
		// //删除任务
		// jobService.deleteJob(se.getJobid().toString());
		//
		// //修改job的状态为待添加
		// taskJobService.updateRelease(se.getJobid());
		//
		// //修改为已释放
		// servEqService.updateStatus(se.getId(),
		// ServEqStatus.DESTROY.getCode());
		// LOGGER.info("释放任务-待释放任务 ID【" + se.getJobid() + "】-服务【" +
		// se.getServid() + "】");
		// } catch (SchedulerException e) {
		// LOGGER.error("删除任务异常: " + e.getMessage(), e);
		// }
		// }
	}
}