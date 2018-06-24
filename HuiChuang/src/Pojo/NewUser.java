package Pojo;

public class NewUser {
	
	Integer id;
	Integer grade;
	Float money=new Float(0);
	Integer count=0;
	String buyphone;
	Integer recommendid;
	String oldphone;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getBuyphone() {
		return buyphone;
	}
	public void setBuyphone(String buyphone) {
		this.buyphone = buyphone;
	}
	public Integer getRecommendid() {
		return recommendid;
	}
	public void setRecommendid(Integer recommendid) {
		this.recommendid = recommendid;
	}
	public String getOldphone() {
		return oldphone;
	}
	public void setOldphone(String oldphone) {
		this.oldphone = oldphone;
	}
	@Override
	public String toString() {
		return "NewUser [id=" + id + ", grade=" + grade + ", money=" + money + ", count=" + count + ", buyphone="
				+ buyphone + ", recommendid=" + recommendid + ", oldphone=" + oldphone + "]";
	}

}
