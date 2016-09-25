import org.quartz.SchedulerException;

import com.crt.jobs.core.JobService;
import com.crt.jobs.core.ServiceHandler;
import com.crt.jobs.models.TaskJob;

/**
 * 
 */

/**
 * @author UOrder
 *
 */
public class TaskTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobService service = ServiceHandler.getInstance().getJobService();
		TaskJob task = new TaskJob("2221", "t001", "1#2", false, Job1.class.getName(),
				"{\"key1\":\"value1\",\"key2\":\"value2\"}");
		try {
			service.addJob(task);
			
//			 // First we must get a reference to a scheduler
//		    SchedulerFactory sf = new StdSchedulerFactory();
//		    Scheduler sched = sf.getScheduler();
//
//		    sched.start();
//		    // jobs can be scheduled before sched.start() has been called
//
//		    // get a "nice round" time a few seconds in the future...
//		    Date startTime = DateBuilder.nextGivenSecondDate(null, 1);
//
//		    // job1 will only fire once at date/time "ts"
//		    JobDetail job = JobBuilder.newJob(XSimpleJob.class).withIdentity("job1", "group1").build();
//
//		    SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(startTime).build();
//
//		    // schedule it to run!
//		    Date ft = sched.scheduleJob(job, trigger);
//			
			Thread.sleep(5*1000L);
//			sched.shutdown(true);
			// service.triggerJob(task);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
