package Dao;

import java.util.List;

import Pojo.NewUser;

public interface NewUserDao {
	public NewUser findById(int id);
	public List<NewUser> findByOldphone(String oldphone);
	public Integer updateMoney(NewUser newuser);
	public void insertNewUser(NewUser newuser);
	public Integer gradeCount(int grade);
	public List<NewUser> gradeInfo(int grade);
	public Integer buyPhone(NewUser newuser);
	public Integer updategrade(NewUser newuser);
	
	
}
