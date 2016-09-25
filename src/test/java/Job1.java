import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

import com.crt.jobs.api.AbstractTask;

/**
 * 
 */

/**
 * @author UOrder
 *
 */
public class Job1 extends AbstractTask {

	/*
	 * 2016年8月29日 上午10:25:06
	 * 
	 * @see com.crt.jobs.api.ITask#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) {
		// TODO Auto-generated method stub
		// JobDataMap map = context.getMergedJobDataMap();
		long i = 2 * 1000L;
		for (long m = 0; m < i; m++) {
//			System.out.println("m:" + m);
		}
		try {
			System.out
					.print("Job1----"+context.getTrigger().getJobKey().toString()+"----"+context.getScheduler().getTriggerState(context.getTrigger().getKey())+"---------"+context.getNextFireTime()+"-------" + Thread.currentThread().getName() + "-------" + getParameters() + "\r\n");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
