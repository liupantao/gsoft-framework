package com.gsoft.framework.core.dataobj.tree;

import java.util.ArrayList;
import java.util.List;


/**
 * <p></p>
 * @author 
 * @version 1.0.0
 * @see    
 * @since 
 */
public abstract class AbstractTreeNode implements TreeNode {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9047174823228858025L;

	/**
	 * @Fields parent : 父节点
	 */
	protected TreeNode parent;
	
	/**
	 * @Fields parentId : 父节点id，用于list生成树结构
	 */
	protected String parentId;
	
	/**
	 * @Fields children : 子节点集合
	 */
	protected List<TreeNode> children = new ArrayList<TreeNode>();
	
	/**
	 * @Fields id : 唯一标识
	 */
	protected String id;
	
	/**
	 * @Fields code : 编码
	 */
	protected String code;
	
	/**
	 * @Fields text : 显示文本
	 */
	protected String text;
	
	/**
	 * @Fields group : 节点分组
	 */
	protected String group;
	
	/**
	 * @Fields num : 序号
	 */
	private int num;
	
	/**
	 * @Fields level : level
	 */
	private int level;
	
	public AbstractTreeNode(String id,String text){
		this.id = id;
		this.text = text;
	}

	@Override
	public void addChild(TreeNode treeNode) {
		if(treeNode!=null&&!children.contains(treeNode)){
			children.add(treeNode);
		}
	}

	/**
	 * @see oTreeNode#removeChild(oTreeNode)
	 */
	@Override
	public void removeChild(TreeNode treeNode) {
		if(treeNode!=null){
			children.remove(treeNode);
		}
	}

	/**
	 * @return the parent
	 */
	@Override
	public TreeNode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	@Override
	public List<TreeNode> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	/**
	 * @return the id
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	@Override
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the text
	 */
	@Override
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	@Override
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	@Override
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the parentId
	 */
	@Override
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the level
	 */
	@Override
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	@Override
	public void setLevel(int level) {
		this.level = level;
	}

}
