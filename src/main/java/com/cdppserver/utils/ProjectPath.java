package com.cdppserver.utils;

public class ProjectPath {
	public static String getProjectPath(){
	String path=System.getProperty("user.dir");
	String tmp;
	//说明是部署环境
	if(path.contains("bin")){
	 tmp=path.substring(0,path.length()-4);
	//说明是本地运行环境
	}else{
		tmp=path;
	}
	return tmp;
	}
	
	public static void main(String[] args) {
		System.out.println(ProjectPath.getProjectPath());
	}
}
