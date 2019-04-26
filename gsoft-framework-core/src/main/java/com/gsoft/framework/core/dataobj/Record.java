/*

* @(#)Record.java  1.0.0 下午08:58:40

* Copyright 2013 gicom, Inc. All rights reserved.

*/
package com.gsoft.framework.core.dataobj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Record
 * 
 * @author liupantao
 * @date 2017年10月16日
 * 
 */
public class Record extends HashMap<String, Object> implements Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -728264324901631474L;

	/**
	 * map 转 Record
	 * 
	 * @param map
	 * @return
	 */
	public static Record map2Record(Map<String, Object> map) {
		Record record = new Record();
		record.putAll(map);
		return record;
	}

	/** 
	 * TODO(描述这个方法的作用) 
	 * @param list
	 * @return 
	 */
	public static List<Record> list2Record(List<Map<String, Object>> list) {
		List<Record> records = new ArrayList<>();
		if (list != null) {
			for (Map<String, Object> map : list) {
				records.add(map2Record(map));
			}
		}
		return records;
	}

}
