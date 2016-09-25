package com.crt.jobs.task.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 系统配置
 */
public enum Config {
	TASK_MAIN_CRON		("task.main.cron", 		"主线程的时间表达式"),
	LEADER_CRON			("task.leader.cron", 		"Leader的时间表达式"),
	//TASK_WAIT_NUM		("task.wait.num", 		"获取待添加任务的数目"),
	CLEAN_CRON			("task.clean.cron", 			"清空调度记录表达式"),
	JOBLOG_SAVE_DAY		("joblog.save.day", 	"调度记录保存天数"),
	SERV_SAVE_DAY		("stopedservices.save.day", 		"已停止的服务保存天数"),
	LOCK_DESTROY_TIME	("lock.destroy.time", 	"消耗服务和任务的时间[单位:s]"),
	;

	public static final String KEY = "config";
	
	private String code;
	private String name;
	private static Map<String, String> map = new HashMap<String, String>();

	private Config(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		Set<Config> set = EnumSet.allOf(Config.class);
		for(Config e : set){
			map.put(e.getCode(), e.getName());
		}
	}

	/**
	 * 根据Code获取对应的汉字
	 * @param code
	 * @return
	 */
	public static String getText(String code) {
		return map.get(code);
	}

	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}
