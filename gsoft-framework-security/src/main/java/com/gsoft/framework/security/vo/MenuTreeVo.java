package com.gsoft.framework.security.vo;

import java.io.Serializable;
import java.util.List;

import com.gsoft.framework.core.dataobj.tree.TreeNode;
import com.gsoft.framework.core.web.menu.IMenu;
import com.gsoft.framework.util.StringUtils;

/**
 * 菜单映射对象
 * 
 * @author LiuPeng
 * @date 2018年4月14日
 * 
 */
public class MenuTreeVo implements Serializable {

	private static final long serialVersionUID = 6492342895441269363L;

	private String id;

	private String text;
	
	private String icon = "fa fa-tags";

	private String url;

	private String targetType = "iframe-tab";
	
	private List<MenuTreeVo> children;
	
	/** 
	 * 创建一个新的实例 MenuNavbarVo.  
	 * 
	 */
	public MenuTreeVo() {
	}
	
	public MenuTreeVo(TreeNode treeNode) {
		this.id = treeNode.getId();
		this.text = treeNode.getText();
		if(treeNode.getDomain() instanceof IMenu){
			IMenu menu = (IMenu)treeNode.getDomain();
			if(StringUtils.isNotEmpty(menu.getMenuStyle())){
				this.icon = menu.getMenuStyle();
			}
		}
		this.url = treeNode.getHref();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public List<MenuTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTreeVo> children) {
		this.children = children;
	}
	
//	public static void main(String[] args) throws IOException {
//		
//		StringBuffer b = new StringBuffer();
//		File file = new File("C:/Users/kaifa-01/Desktop/menu.txt");
//		BufferedReader read = new BufferedReader(new FileReader(file));
//		while(read.ready()){
//			b.append(read.readLine());
//		}
//		List<MenuTreeVo> menus = JSON.parseArray(b.toString(), MenuTreeVo.class);
//		
//		StringBuffer xml = new StringBuffer();
//		
//		for(MenuTreeVo menu:menus){
//			xml.append(buildMenuXml(menu));
//		}
//		
//		System.out.println(xml.toString());
//	}
//
//	private static String buildMenuXml(MenuTreeVo menu) {
//		StringBuffer xml = new StringBuffer();
//		menu.getUrl();
//		xml.append("<menu id =\""+menu.getId()+"\" text=\""+menu.getText()+"\" icon=\""+menu.getIcon()+"\" ");
//		if(menu.getChildren()!=null&&menu.getChildren().size()>0){
//			xml.append(" >");
//			for(MenuTreeVo m:menu.getChildren()){
//				xml.append(buildMenuXml(m));
//			}
//		}else{
//			xml.append("href=\""+menu.getUrl()+"\"");
//			xml.append("targetType=\""+menu.getTargetType()+"\"");
//			xml.append(" >");
//		}
//		xml.append("</menu>");
//		return xml.toString();
//	}
}
