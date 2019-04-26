/*
 * YOUI框架
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gsoft.framework.taglib.convert;

import com.gsoft.framework.core.convert.AbstractConvert;
import com.gsoft.framework.core.dataobj.Record;

/**
 * 
 * @author liupantao
 *
 * @param <T>
 */
public class Convert<T> extends AbstractConvert {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2778871535440021647L;

	public Convert() {
		super();
	}
	
	public Convert(Record record) {
		super(record);
	}
	
	@Override
	protected void initRecord(Record record) {

	}

	public void put(String key, String value) {
		getRecord().put(key, value);
	}

}
