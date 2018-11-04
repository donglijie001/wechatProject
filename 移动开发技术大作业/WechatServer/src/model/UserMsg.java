package model;

public class UserMsg {
	private String uId;
	private String fid;
	private String msg;
	private String u_nickName;
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getU_nickName() {
		return u_nickName;
	}
	public void setU_nickName(String u_nickName) {
		this.u_nickName = u_nickName;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	

}
