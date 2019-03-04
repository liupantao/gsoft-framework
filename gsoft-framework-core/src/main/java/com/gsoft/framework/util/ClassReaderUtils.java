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
package com.gsoft.framework.util;

import java.io.IOException;

import net.sf.cglib.asm.AnnotationVisitor;
import net.sf.cglib.asm.Attribute;
import net.sf.cglib.asm.ClassReader;
import net.sf.cglib.asm.ClassVisitor;
import net.sf.cglib.asm.FieldVisitor;
import net.sf.cglib.asm.MethodVisitor;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.gsoft.framework.core.dataobj.tree.HtmlTreeNode;


/**
 * ClassReaderUtils
 * @author liupantao
 * @date 2017年10月17日
 *  
 */
public class ClassReaderUtils {
	
	
	public static HtmlTreeNode getOutlineTree(String clazzName){
		//
		HtmlTreeNode root =  new HtmlTreeNode("root_"+clazzName.replace(".", "_"),"code struct");
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		try {
			
			Resource resource = resourceLoader.getResource("classpath:"+clazzName.replace(".", "/")+".class");
			
			ClassReader classReader = new ClassReader(resource.getInputStream());
			
			TreeClassVisitor treeVisitor = new TreeClassVisitor();
			classReader.accept(treeVisitor , 0);
			root = treeVisitor.getTree();
			root.setId("root_"+clazzName.replace(".", "_"));
			root.setText(clazzName);
			root.setExpanded(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return root;
	}
	
	public static void main(String args[]){
//		System.out.println(ClassReaderUtils.getOutlineTree(Project.class.getName()));
	}
	
	static class TreeClassVisitor implements ClassVisitor {
		
		private HtmlTreeNode tree = new HtmlTreeNode("root_class","codestruct");

		@Override
		public void visit(int version, int access, String name,
	            String signature, String superName, String[] interfaces) {
			
		}

		@Override
		public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void visitAttribute(Attribute attribute) {

		}

		@Override
		public void visitEnd() {
			// TODO Auto-generated method stub

		}

		@Override
		public FieldVisitor visitField(int access, String name, String desc,
	            String signature, Object value) {
			HtmlTreeNode treeNode = new HtmlTreeNode(name,name);
			treeNode.setGroup("field");
			tree.addChild(treeNode);
			return null;
		}

		@Override
		public void visitInnerClass(String name, String outerName,
	            String innerName, int access) {
	    }

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc,
	            String signature, String[] arg4) {
			HtmlTreeNode treeNode = new HtmlTreeNode(name,name);
			treeNode.setGroup("method");
			tree.addChild(treeNode);
			return null;
		}

		@Override
		 public void visitOuterClass(String owner, String name, String desc) {
			 
	     }

		@Override
	     public void visitSource(String source, String debug) {
			
	     }

		/**
		 * @return the tree
		 */
		public HtmlTreeNode getTree() {
			return tree;
		}

		/**
		 * @param tree the tree to set
		 */
		public void setTree(HtmlTreeNode tree) {
			this.tree = tree;
		}
		
	}
}
