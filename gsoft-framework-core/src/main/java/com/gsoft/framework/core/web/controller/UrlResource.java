/*
 * YOUI框架
 * Copyright 2010 the original author or authors.
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
package com.gsoft.framework.core.web.controller;

import java.util.Arrays;

/**
 * URL资源
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class UrlResource {
	//public 
	
	private String httpMethod;
	
	private String url;
	
	/**
	 * @Fields type : 访问类型 page 或者 data
	 */
	private String type;
	
	/**
	 * @Fields urlId : 唯一编号
	 */
	private String urlId;
	
	/**
	 * @Fields urlText : 描述 
	 */
	private String urlText;
	
	/**
	 * @Fields subPages : 子页面
	 */
	private String[] subPages;
	
	/**
	 * @Fields datas : 交易数据ID
	 */
	private String[] datas;

	public UrlResource(String urlId,String httpMethod, String url) {
		super();
		this.urlId = urlId;
		this.httpMethod = httpMethod;
		this.url = url;
	}

	
	/**
	 * @return the httpMethod
	 */
	public String getHttpMethod() {
		return httpMethod;
	}


	/**
	 * @param httpMethod the httpMethod to set
	 */
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}


	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the urlId
	 */
	public String getUrlId() {
		return urlId;
	}

	/**
	 * @param urlId the urlId to set
	 */
	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	/**
	 * @return the subPages
	 */
	public String[] getSubPages() {
		return subPages;
	}

	/**
	 * @param subPages the subPages to set
	 */
	public void setSubPages(String[] subPages) {
		this.subPages = subPages;
	}

	/**
	 * @return the datas
	 */
	public String[] getDatas() {
		return datas;
	}

	/**
	 * @param datas the datas to set
	 */
	public void setDatas(String[] datas) {
		this.datas = datas;
	}

	/* (非 Javadoc)  
	 * <p>Title: hashCode</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see java.lang.Object#hashCode()  
	 */  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(datas);
		result = prime * result + ((httpMethod == null) ? 0 : httpMethod.hashCode());
		result = prime * result + Arrays.hashCode(subPages);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((urlId == null) ? 0 : urlId.hashCode());
		result = prime * result + ((urlText == null) ? 0 : urlText.hashCode());
		return result;
	}

	
	
	/* (非 Javadoc)  
	 * <p>Title: equals</p>  
	 * <p>Description: </p>  
	 * @param obj
	 * @return  
	 * @see java.lang.Object#equals(java.lang.Object)  
	 */  
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UrlResource other = (UrlResource) obj;
		if (!Arrays.equals(datas, other.datas)) {
			return false;
		}
		if (httpMethod == null) {
			if (other.httpMethod != null) {
				return false;
			}
		} else if (!httpMethod.equals(other.httpMethod)) {
			return false;
		}
		if (!Arrays.equals(subPages, other.subPages)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		if (urlId == null) {
			if (other.urlId != null) {
				return false;
			}
		} else if (!urlId.equals(other.urlId)) {
			return false;
		}
		if (urlText == null) {
			if (other.urlText != null) {
				return false;
			}
		} else if (!urlText.equals(other.urlText)) {
			return false;
		}
		return true;
	}


	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(urlText==null?"":(urlText + " "))
			.append(urlId+" ")
			.append(httpMethod+" ")
			.append(url);
		return buffer.toString();
	}

	public void setUrlText(String urlText) {
		this.urlText = urlText;
	}

	/**
	 * @return the urlText
	 */
	public String getUrlText() {
		return urlText;
	}

	public boolean contains(String httpMethod, String url) {
//		if (this.httpMethod == null) {
//			if (httpMethod != null)
//				return false;
//		} else if (!this.httpMethod.equals(httpMethod))
//			return false;
		if (this.url == null) {
			if (url != null) {
				return false;
			}
		} else if (!this.url.equals(url)) {
			return false;
		}
		return true;
	}
	
}
