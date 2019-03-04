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
package com.gsoft.framework.core.dataobj.tree;

import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;

import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.util.PropertyUtils;
import com.gsoft.framework.util.StringUtils;

/**
 * html树节点
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class HtmlTreeNode extends AbstractTreeNode implements TreeNode {

	/**
	 * @Fields TREE_NODE_CHECK : 节点选中
	 */
	public static final String TREE_NODE_CHECK="check";
	
	/**
	 * @Fields TREE_NODE_ROOT : 根节点root
	 */
	public static final String TREE_NODE_ROOT="root";
	
	public static final String TREE_NODE_BLANK="_blank";
	public static final String TREE_NODE_SUBPAGE="subpage";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1879870009832140690L;

	/**
	 * @Fields src : 树加载路径
	 */
	private String src;

	/**
	 * @Fields href : 链接路径
	 */
	private String href;

	/**
	 * @Fields tooltips : tooltips
	 */
	private String tooltips;

	/**
	 * @Fields check : 复选标识check checked parchecked
	 */
	private String check;

	/**
	 * @Fields domain : 树节点映射的实体对象
	 */
	private Domain domain;

	/**
	 * @Fields expanded : 自动展开
	 */
	private boolean expanded;

	private String icon;
	
	private String target;

	public HtmlTreeNode() {
		super(null, null);
	}
	/**
	 * 构造函数
	 * 
	 * @param id
	 * @param text
	 */
	public HtmlTreeNode(String id, String text) {
		super(id, text);
	}

	/**
	 * 构造函数
	 * 
	 * @param id
	 * @param text
	 */
	public HtmlTreeNode(String id, String text, TreeNodeConfig treeNodeConfig) {
		super(id, text);
		if (treeNodeConfig != null) {
			treeNodeConfig.render(this);
		}
	}

	/**
	 * 自动解析Domain的TreeAttribute的构造函数
	 * 
	 * @param domain
	 */
	public HtmlTreeNode(Domain domain) {
		this(domain, null);
	}

	/**
	 * @param domain
	 * @param treeNodeConfig
	 */
	public HtmlTreeNode(Domain domain, TreeNodeConfig treeNodeConfig) {
		super(null, null);
		Assert.notNull(domain, "domain对象不能为空！");
		// 根据Domain的注释自动映射
		injectFromDomain(domain);
		this.domain = domain;
		if (treeNodeConfig != null) {
			treeNodeConfig.render(this);
		}
	}

	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * @param src
	 *            the src to set
	 */
	@Override
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * @return the tooltips
	 */
	public String getTooltips() {
		return tooltips;
	}

	/**
	 * @param tooltips
	 *            the tooltips to set
	 */
	public void setTooltips(String tooltips) {
		this.tooltips = tooltips;
	}

	/**
	 * @return the check
	 */
	public String getCheck() {
		return check;
	}

	/**
	 * @param check
	 *            the check to set
	 */
	@Override
	public void setCheck(String check) {
		this.check = check;
	}

	@Override
	public String getHref() {
		return href;
	}

	@Override
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * 值注入
	 * 
	 * @param domain
	 */
	private void injectFromDomain(Domain domain) {
		Map<String, Object> values = TreeUtils.getValueMapFromDomain(domain);
		// 如果没有找到id属性映射，使用domain的toString
		if (!values.containsKey(TreeAttribute.TREE_ATTR_ID)) {
			values.put(TreeAttribute.TREE_ATTR_ID, domain.toString());
		}
		// Assert.isTrue(values.containsKey("id"),
		// domain.getClass().getName()+": 必须有get方法注释为（TreeAttribute(\"id\"),并且具有返回值！）");
		Set<String> attributes = values.keySet();
		for (String attribute : attributes) {
			this.setTreeAttributeValue(attribute, values.get(attribute));
		}
	}

	/**
	 * @param attribute
	 * @param value
	 */
	private void setTreeAttributeValue(String attribute, Object value) {
		if (value == null) {
			value = "";
		}
		// 树节点id
		if (attribute.equals(TreeAttribute.TREE_ATTR_ID)) {
			setId(value.toString());
		} else if (attribute.equals(TreeAttribute.TREE_ATTR_PARENT)) {
			// 树节点parent对象
			if (value != null && value instanceof Domain) {
				Map<String, Object> parentValues = TreeUtils.getValueMapFromDomain((Domain) value);
				Object parentIdObject = parentValues.get(TreeAttribute.TREE_ATTR_ID);
				if (parentIdObject != null) {
					setParentId(parentIdObject.toString());
				}
			}
		} else if (attribute.equals(TreeAttribute.TREE_ATTR_PID)) {
			// 树节点parentId
			setParentId(value.toString());
		} else if (attribute.equals(TreeAttribute.TREE_ATTR_CODE)) {
			// 树节点编码
			setCode(value.toString());
		} else if (attribute.equals(TreeAttribute.TREE_ATTR_TEXT)) {
			// 树节点文本
			setText(value.toString());
		} else if (attribute.equals(TreeAttribute.TREE_ATTR_GOURP)) {
			// 树节点组
			setGroup(value.toString());
		} else if (attribute.equals(TreeAttribute.TREE_ATTR_SRC)) {
			// 树节点src
			setSrc(value.toString());
		} else if (attribute.equals(TreeAttribute.TREE_ATTR_HREF)) {
			// 树节点链接
			setHref(value.toString());
		}  else if (attribute.equals(TreeAttribute.TREE_ATTR_TARGET)) {
			// 树节点target
			setTarget(value.toString());
		} else if (attribute.equals(TreeAttribute.TREE_ATTR_NUM)) {
			// 树节点序号
			if (value instanceof Integer) {
				this.setNum((Integer) value);
			}
		} else {
			// skip
		}
	}

	/**
	 * 使用模版生成html 返回html树节点的html文本
	 * 
	 * @return
	 */
	public String toHtml(boolean isLast, boolean isCheck) {
		StringBuffer htmls = new StringBuffer();
		if (id == null) {
			return "";
		}
		// html文本
		String htmlText = text == null ? id : text;
		// html的tooltips
		String htmlTooltips = tooltips == null ? htmlText : tooltips;
		// html树的节点样式
		StringBuffer treeNodeClasses = new StringBuffer("treeNode");
		String treeTriggerHtml = "";
		String nodeHref = href;

		// 节点分组标识
		if (group != null) {
			// 树节点分组
			treeNodeClasses.append(" " + group);
		}

		if (this.icon != null) {
			treeNodeClasses.append(" use-icon");
		}

		if (isCheck) {
			check = "check";
		}

		if (StringUtils.isEmpty(href)) {
			href = "javascript:void(0)";
		}

		// 复选标识
		if (check != null) {
			treeNodeClasses.append(" check");
			if (!check.equals(TREE_NODE_CHECK)) {
				treeNodeClasses.append(" " + check);
			}
		}
		// 末尾节点标识
		if (isLast) {
			treeNodeClasses.append(" last");
		}
		// 标识为可展开
		if (children.size() > 0 || src != null) {
			treeNodeClasses.append(" expandable");
			if (isLast) {
				// ie6样式
				treeNodeClasses.append(" lastExpandable");
			}
			if (!TREE_NODE_ROOT.equals(group)) {
				treeTriggerHtml = "<div class=\"tree-trigger\"></div>";
			}
			if (expanded) {
				treeNodeClasses.append(" expanded");
			}
		}

		htmls.append("<li ");
		if (src != null) {
			htmls.append(" src=\"" + src + "\" ");
		}
		if (code != null) {
			htmls.append(" code=\"" + code + "\" ");
		}
		htmls.append(" class=\"" + treeNodeClasses.toString() + "\" id=\"" + id + "\">");
		htmls.append(treeTriggerHtml);

		htmls.append("<span");
		if (StringUtils.isNotEmpty(code)) {
			htmls.append(" id=\"" + code + "\"");
		}
		htmls.append(" title=\"" + htmlTooltips + "\" class=\"tree-span " + treeNodeClasses.toString() + "\">");

		if (this.icon != null) {
			htmls.append("<span class=\"youi-icon icon-" + this.icon + "\"></span>");
		}

		htmls.append("<a");

		if (getDomain() != null && StringUtils.isNotEmpty(href)) {
			Object target = PropertyUtils.getSimplePropertyValue(getDomain(), "target");
			if ((TREE_NODE_BLANK.equals(target)) || (TREE_NODE_SUBPAGE.equals(target))) {
				htmls.append(" target=\"" + target + "\"");
			}
		}

		if (StringUtils.isNotEmpty(nodeHref)) {
			htmls.append(" href=\"" + nodeHref + "\"");
		}

		htmls.append(" class=\"tree-a page-link\" >");
		// html树节点的显示文本
		htmls.append(htmlText);
		htmls.append("</a></span>");
		// 子节点 处理子节点
		htmls.append(childrenHtml(isCheck));
		htmls.append("</li>");
		return htmls.toString();
	}

	/**
	 * 生成子节点的html
	 * 
	 * @return
	 */
	private String childrenHtml(boolean isCheck) {
		StringBuffer htmls = new StringBuffer();
		if (children != null && children.size() > 0) {
			boolean isLastChild;
			htmls.append("<ul ");
			if (!expanded) {
				htmls.append(" style=\"display:none;\" ");
			}
			htmls.append(">");
			int index = 0;
			for (TreeNode child : children) {
				isLastChild = ++index == children.size();
				htmls.append(((HtmlTreeNode) child).toHtml(isLastChild, isCheck));
			}
			htmls.append("</ul>");
		}
		return htmls.toString();
	}

	/**
	 * @return the domain
	 */
	@Override
	public Domain getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	@Override
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return toHtml(true, false);
	}

	@Override
	public boolean getExpanded() {
		return expanded;
	}

	@Override
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * @return the target
	 */
	@Override
	public String getTarget() {
		return target;
	}
	/**
	 * @param target the target to set
	 */
	@Override
	public void setTarget(String target) {
		this.target = target;
	}

}
