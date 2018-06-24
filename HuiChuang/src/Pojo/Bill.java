package Pojo;

public class Bill {

	Integer id;
	String Phone;
	String aliaccount;
	Float money;
	Integer billstatus;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getAliaccount() {
		return aliaccount;
	}
	public void setAliaccount(String aliaccount) {
		this.aliaccount = aliaccount;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public Integer getBillstatus() {
		return billstatus;
	}
	public void setBillstatus(Integer billstatus) {
		this.billstatus = billstatus;
	}
}
