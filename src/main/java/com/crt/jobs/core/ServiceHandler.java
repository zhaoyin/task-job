/**
 * 
 */
package com.crt.jobs.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crt.jobs.api.ITaskService;
import com.crt.jobs.task.SysTaskManager;
import com.crt.jobs.utils.JobProperties;

/**
 * @author UOrder
 *
 */
public final class ServiceHandler {

	private static final Logger Logger = LoggerFactory.getLogger(ServiceHandler.class);

	protected final Object lock = new Object();

	private ServiceHandler() {

	}

	private static class ServiceHandlerHolder {
		public static ServiceHandler handler = new ServiceHandler();
	}

	public static ServiceHandler getInstance() {
		return ServiceHandlerHolder.handler;
	}

	private ITaskService service;

	public ITaskService getTaskService() {
		synchronized (lock) {
			if (service == null) {
				// 从配置文件取
				String clazz = JobProperties.getProperty("com.crt.jobs.service");
				if (clazz != null && !clazz.isEmpty()) {
					try {
						service = (ITaskService) Class.forName(clazz).newInstance();
					} catch (InstantiationException e) {
						if (Logger.isErrorEnabled()) {
							Logger.error("Export.getJsonConverter", e);
						}
					} catch (IllegalAccessException e) {
						if (Logger.isErrorEnabled()) {
							Logger.error("Export.getJsonConverter", e);
						}
					} catch (ClassNotFoundException e) {
						if (Logger.isErrorEnabled()) {
							Logger.error("Export.getJsonConverter", e);
						}
					}
				}
			}
			return service;
		}
	}

	private JobService job;

	public JobService getJobService() {
		synchronized (lock) {
			if (job == null) {
				job = new JobService();
				if (service != null) {
					SysTaskManager.init(job);
				}
			}
			return job;
		}
	}

}
