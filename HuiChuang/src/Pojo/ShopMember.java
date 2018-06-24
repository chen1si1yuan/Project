package Pojo;

public class ShopMember {

	Integer id;
	String mobile;
	String pwd;
	String salt="fX0xXNXP2hMG4950";
	String datavalue="";
	int updateaddress=0;
	int uniacid=2;
	Integer createtime;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getDatavalue() {
		return datavalue;
	}
	public void setDatavalue(String datavalue) {
		this.datavalue = datavalue;
	}
	public int getUpdateaddress() {
		return updateaddress;
	}
	public void setUpdateaddress(int updateaddress) {
		this.updateaddress = updateaddress;
	}
	public int getUniacid() {
		return uniacid;
	}
	public void setUniacid(int uniacid) {
		this.uniacid = uniacid;
	}
	public Integer getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Integer createtime) {
		this.createtime = createtime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
