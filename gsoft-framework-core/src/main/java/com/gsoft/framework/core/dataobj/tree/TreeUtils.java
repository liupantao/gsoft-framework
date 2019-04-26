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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.util.ReflectionUtils;
import com.gsoft.framework.util.StringUtils;

/**
 * 树工具类
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class TreeUtils {
	private final static Log logger = LogFactory.getLog(TreeUtils.class);
	
	private static final String METHOD_GET_PREFIX = "get";

	/**
	 * 获得树节点属性的键值对
	 * 
	 * @param domain
	 * @return
	 */
	public static Map<String, Object> getValueMapFromDomain(Domain domain) {
		Map<String, Object> values = new HashMap<>(16);
		TreeAttribute treeAttribute;
		Object value;

		// 使用对象名小写作为默认的group
		values.put("group", domain.getClass().getSimpleName().toLowerCase());
		//
		List<Method> methods = ReflectionUtils.annotationedGetterMethod(domain.getClass(), TreeAttribute.class);
		for (Method method : methods) {
			treeAttribute = method.getAnnotation(TreeAttribute.class);
			if (treeAttribute != null) {
				value = getTreeAttributeMethodValue(domain, method);
				if (value != null) {
					values.put(treeAttribute.value(), value);
				}
			}
		}
		return values;
	}

	/**
	 * 把list对象转换为树结构
	 * 
	 * @param list
	 * @param rootId
	 * @param rootText
	 * @param maxLevel
	 *            树的最大层次
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HtmlTreeNode listToHtmlTree(List list, String rootId, String rootText, int maxLevel) {
		return listToHtmlTree(list, rootId, rootText, maxLevel, null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HtmlTreeNode listToHtmlTree(List list, String rootId, String rootText) {
		return listToHtmlTree(list, rootId, rootText, 0, null);
	}

	/**
	 * @param list
	 * @param treeNodeConfig
	 * @return
	 */
	public static List<TreeNode> listToTreeNodes(List<? extends Domain> list, TreeNodeConfig treeNodeConfig) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		for (Domain domain : list) {
			treeNodes.add(new HtmlTreeNode(domain, treeNodeConfig));
		}
		return treeNodes;
	}

	public static HtmlTreeNode listToHtmlTree(List<? extends Domain> list, String rootId, String rootText,
			TreeNodeConfig treeNodeConfig) {
		return listToHtmlTree(list, rootId, rootText, 0, treeNodeConfig);
	}

	/**
	 * 把list对象转换为树结构
	 * 
	 * @param list
	 * @param rootId
	 * @param rootText
	 * @param treeNodeConfig
	 *            树配置
	 * @return
	 */
	public static HtmlTreeNode listToHtmlTree(List<? extends Domain> list, String rootId, String rootText, int maxLevel,
			TreeNodeConfig treeNodeConfig) {
		HtmlTreeNode root = new HtmlTreeNode(rootId, rootText, treeNodeConfig);
		root.setGroup("root");
		// 组装树
		makeHtmlTree(root, listToTreeNodes(list, treeNodeConfig), 0, maxLevel);
		return root;

	}

	// /**
	// * @param root
	// * @param treeNodes
	// * @param parentId 此参数主要是避免
	// */
	// private static void makeHtmlTree(TreeNode root,
	// List<TreeNode> treeNodes) {
	// String parentId = root.getId();
	// if(treeNodes.size()>0){
	// treeNodes.remove(root);//移除root
	// List<TreeNode> newTreeNodesList = new
	// ArrayList<TreeNode>(treeNodes);//新建一个存放删除了父节点元素的list
	// for(TreeNode treeNode:treeNodes){
	// if(isCurrentRootChild(parentId,treeNode)){
	// root.addChild(treeNode);//如果匹配，则加入树
	// makeHtmlTree(treeNode,newTreeNodesList);//继续需找下级节点
	// }
	// }
	// }
	// }

	/**
	 * @param root
	 * @param treeNodes
	 * @param parentId
	 *            此参数主要是避免
	 */
	private static void makeHtmlTree(TreeNode root, List<TreeNode> treeNodes, int level, int maxLevel) {
		String parentId = root.getId();
		if (treeNodes.size() > 0) {
			// 移除root
			treeNodes.remove(root);
			// 新建一个存放删除了父节点元素的list
			List<TreeNode> newTreeNodesList = new ArrayList<>(treeNodes);
			if (maxLevel > 0 && level >= maxLevel) {
				return;
			}
			for (TreeNode treeNode : treeNodes) {
				if (isCurrentRootChild(parentId, treeNode)) {
					// 如果匹配，则加入树
					root.addChild(treeNode);
					// 继续需找下级节点
					makeHtmlTree(treeNode, newTreeNodesList, level + 1, maxLevel);
				}
			}
		}
	}

	/**
	 * 比较父节点id是否匹配
	 * 
	 * @param parentId
	 *            父节点ID
	 * @param treeNode
	 *            当前比较的节点
	 * @return
	 */
	private static boolean isCurrentRootChild(String parentId, TreeNode treeNode) {
		String nodeParentId = treeNode.getParentId();
		if ("".equals(parentId)) {
			parentId = null;
		}
		return (parentId == null && nodeParentId == null) || (parentId != null && parentId.equals(nodeParentId));
	}

	/**
	 * 从get方法中反射获得当前方法的返回值
	 * 
	 * @return
	 */
	private static Object getTreeAttributeMethodValue(Domain domain, Method method) {
		String methodName = method.getName();
		if (methodName.startsWith(METHOD_GET_PREFIX)) {
			try {
				return method.invoke(domain, new Object[] {});
			} catch (IllegalArgumentException e) {
				logger.error("IllegalArgumentException:" + e.getMessage());
			} catch (IllegalAccessException e) {
				logger.error("IllegalAccessException:" + e.getMessage());
			} catch (InvocationTargetException e) {
				logger.error("InvocationTargetException:" + e.getMessage());
			}
		} else {
			logger.warn("请在get方法上使用TreeAttribute注释！");
		}
		return null;
	}

	public static <T extends Domain> List<TreeNode> buildPathTree(List<String> paths, Map<String, T> domains,
			TreeNodeConfig nodeConfig) {
		if (paths == null) {
			return null;
		}

		if (domains == null) {
			domains = new HashMap<>(16);
		}

		Collections.sort(paths);

		HtmlTreeNode rootTreeNode = new HtmlTreeNode("", "");

		Map<String, HtmlTreeNode> allNodes = new HashMap<>(16);

		HtmlTreeNode pageTreeNode = null;
		for (String path : paths) {
			if (path != null) {
				String[] splits = path.split("/");
				int level = splits.length - 1;
				if (level == 1) {
					pageTreeNode = new HtmlTreeNode(path, splits[level]);
					rootTreeNode.addChild(pageTreeNode);
					allNodes.put(path, pageTreeNode);
				} else {
					HtmlTreeNode parentTreeNode = null;
					HtmlTreeNode levelTreeNode = null;
					for (int i = 1; i <= level; ++i) {
						String parentTreeId = StringUtils.arrayToDelimitedString(org.apache.commons.lang3.ArrayUtils.subarray(splits, 0, i), "/");
						String treeId = StringUtils.arrayToDelimitedString(org.apache.commons.lang3.ArrayUtils.subarray(splits, 0, i + 1), "/");

						if (allNodes.containsKey(parentTreeId))
							parentTreeNode = (HtmlTreeNode) allNodes.get(parentTreeId);
						else {
							parentTreeNode = rootTreeNode;
						}

						if (!allNodes.containsKey(treeId)) {
							levelTreeNode = new HtmlTreeNode(treeId + "/", "");
							if ((i == level) && (domains.containsKey(path))) {
								pageTreeNode = levelTreeNode;
							} else {
								levelTreeNode.setText(splits[i]);
								levelTreeNode.setGroup("folder");
							}

							parentTreeNode.addChild(levelTreeNode);
							allNodes.put(treeId, levelTreeNode);
						}
					}
				}

				Domain domain = (Domain) domains.get(path);

				if ((nodeConfig != null) && (pageTreeNode != null) && (domain != null)) {
					pageTreeNode.setDomain(domain);
					nodeConfig.render(pageTreeNode);
				}
			}
		}
		return rootTreeNode.getChildren();
	}
}
