package Dao;

import org.springframework.stereotype.Service;

import Pojo.ShopMember;

@Service
public interface ShopMemberDao {
	
	public ShopMember findShopMember(String mobile);
	public Integer insertShopMember(ShopMember shopMember);
	public void updateShopMember(ShopMember shopMember);
	public Integer todayRegister(int time);
}
