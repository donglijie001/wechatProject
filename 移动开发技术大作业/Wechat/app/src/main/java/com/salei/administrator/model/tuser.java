package com.salei.administrator.model;

import java.io.Serializable;

public class tuser implements Serializable{
	private String u_logId;
	private String u_pwd;
	private String u_nickName;
	private String u_signaTure;
	private int u_sex;
	private String u_birthday;
	private String u_telephon;
	private long OnlineTime;
	private int unReadMsgCount = 0;
	private String ip;
	public tuser(tuser bean){
		u_logId=bean.getU_logId();
		u_pwd=bean.getU_pwd();
		u_nickName=bean.getU_nickName();
		u_signaTure=bean.getU_signaTure();
		u_sex=bean.getU_sex();
		u_birthday=bean.getU_birthday();
		u_telephon=bean.getU_telephon();
		unReadMsgCount =bean.getUnReadMsgCount() ;
		OnlineTime=bean.getOnlineTime();
	}
	public tuser(String u_logId, String ip, int unReadMsgCount,long time) {
		super();
		this.u_logId = u_logId;
		this.ip = ip;
		this.unReadMsgCount = unReadMsgCount;
		this.OnlineTime=time;
	}
	public String getU_logId() {
		return u_logId;
	}
	public void setU_logId(String u_logId) {
		this.u_logId = u_logId;
	}
	public String getU_pwd() {
		return u_pwd;
	}
	public void setU_pwd(String u_pwd) {
		this.u_pwd = u_pwd;
	}
	public String getU_nickName() {
		return u_nickName;
	}
	public void setU_nickName(String u_nickName) {
		this.u_nickName = u_nickName;
	}
	public String getU_signaTure() {
		return u_signaTure;
	}
	public void setU_signaTure(String u_signaTure) {
		this.u_signaTure = u_signaTure;
	}
	public int getU_sex() {
		return u_sex;
	}
	public void setU_sex(int u_sex) {
		this.u_sex = u_sex;
	}
	public String getU_birthday() {
		return u_birthday;
	}
	public void setU_birthday(String u_birthday) {
		this.u_birthday = u_birthday;
	}
	public String getU_telephon() {
		return u_telephon;
	}

	public void setU_telephon(String u_telephon) {
		this.u_telephon = u_telephon;
	}

	public long getOnlineTime() {
		return OnlineTime;
	}

	public void setOnlineTime(long onlineTime) {
		OnlineTime = onlineTime;
	}

	public int getUnReadMsgCount() {
		return unReadMsgCount;
	}

	public void setUnReadMsgCount(int unReadMsgCount) {
		this.unReadMsgCount = unReadMsgCount;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public tuser getCopy()
	{
		tuser u=new tuser(this.u_logId,this.ip,this.unReadMsgCount,this.OnlineTime);
		return u;
	}
}
