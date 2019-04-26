/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.gsoft.framework.security.authc;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import com.gsoft.framework.core.web.view.Message;
import com.gsoft.framework.security.AppResConstants;
import com.gsoft.framework.security.util.ResJsonUtils;

/**
 * 权限验证
 * 
 * @author liupantao
 *
 */
public class ShiroPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		AppResContext resContext = new AppResContext();
		Subject subject = getSubject(request, response);
		if (subject.isAuthenticated()) {
			resContext.setMessage(new Message(AppResConstants.UN_AUTHED, "无权操作[perms]"));
			ResJsonUtils.resJson(request, response, resContext);
		} else {
			ResJsonUtils.resTokenError(request, response);
		}
		return false;
	}
}
