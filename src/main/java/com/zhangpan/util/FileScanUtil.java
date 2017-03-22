package com.zhangpan.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class FileScanUtil {
	
	public static List<TreeNode> scanFiles(String filePath) {
		List<TreeNode> treeNodes=new ArrayList<TreeNode>();
		List<TreeNode> fileTreeNodes=new ArrayList<TreeNode>();
		File[] files=null;
		File file = new File(filePath);
		if(file.exists()){
			files=file.listFiles();
			for(File f : files){
				if(f.getName().matches("^[.].*|target")){
					continue;
				}
				if(f.isDirectory()){
					TreeNode node=buildTreeNode(f,true);//目录文件夹
					treeNodes.add(node);
				}else{
					TreeNode node=buildTreeNode(f,false);//非目录文件
					fileTreeNodes.add(node);
				}
			}
			treeNodes.addAll(fileTreeNodes);
		}else{
			System.out.println("不存在文件。");
		}
		return treeNodes;
	}
	
	private static TreeNode buildTreeNode(File file,boolean isParent){
		TreeNode treeNode=new TreeNode();
		treeNode.setName(file.getName());
		treeNode.setFilePath(file.getAbsolutePath());
		treeNode.setParent(isParent);
		return treeNode;
	}
	
	public static List<String> readFile(String filePath) throws Exception{
		List<String> list=new ArrayList<String>();
		FileInputStream in=new FileInputStream(filePath);
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		String str="";
		int rownum=0;
		while((str=br.readLine())!=null){
			rownum++;
			System.out.println(rownum+"---->"+str);
			str=str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			list.add(str);
		}
		br.close();
		return list;
	}
	
	public static void main(String[] args) throws Exception {
		String filePath=System.getProperty("user.dir");
		filePath="F:\\Workspaces\\SSHWeb\\pom.xml";
		/*List<TreeNode> list=scanFiles(filePath);
		for(TreeNode node : list){
			System.out.println(JSON.toJSONString(node));
		}*/
		List list=readFile(filePath);
		System.out.println(list);
	}

}
