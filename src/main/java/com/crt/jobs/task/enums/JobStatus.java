package com.crt.jobs.task.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 定时任务的状态
 * 		0正常、50停止、100待添加、150添加异常、500异常终止
 * @version 1.0.0
 */
public enum JobStatus {
	WAIT		(0, "待添加"),
	NORMAL		(100, "正常"),
	STOP		(50, "停止"),
	ERROR_ADD	(150, "添加异常"),
	ERROR		(500, "异常终止");
	
	public static final String KEY = "job_status";
	
	private Integer code;
	private String name;
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private JobStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		Set<JobStatus> set = EnumSet.allOf(JobStatus.class);
		for(JobStatus e : set){
			map.put(e.getCode(), e.getName());
		}
	}

	/**
	 * 根据Code获取对应的汉字
	 * @param code
	 * @return
	 */
	public static String getText(Integer code) {
		return map.get(code);
	}

	public Integer getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}
