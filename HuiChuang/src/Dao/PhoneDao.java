package Dao;

import java.util.List;

import org.springframework.stereotype.Service;

import Pojo.Phone;

@Service
public interface PhoneDao {
	public List<Phone> findPhone();
    public Integer sellPhoneCount();
    public void insertPhone(Phone phone);
    public Integer updatePhoneExit(String phonenumber);
    public Integer phoneExit(String	phonenumber);
    public List<Phone> findPhoneByVague(String vague);
}
