package Controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dao.NewUserDao;
import Dao.ShopMemberDao;
import Pojo.NewUser;
import Pojo.ShopMember;
import Securitys.MD5Util;
import redis.clients.jedis.Jedis;
@Service
public class Handler {

	@Autowired
	ShopMemberDao shopMemberDao;
	@Autowired
	NewUserDao newUserDao;
	
	@Transactional
	public ShopMember loginByMobile(String mobile,String password)
	{
		ShopMember shopMember=shopMemberDao.findShopMember(mobile);
		return shopMember;
	}
	
	
	@Transactional
	public  String register(String mobile,String pwd,String code)
	{
		if(shopMemberDao.findShopMember(mobile)!=null)
		{
			return "{\"status\":\"0\",\"msg\":\"该手机号已被注册，请直接前往登录页登录\"}";
		}
		Integer createtime = Integer.valueOf(String.valueOf(new Date().getTime() / 1000));
		ShopMember shopMember = new ShopMember();
		shopMember.setMobile(mobile);
		shopMember.setPwd(MD5Util.encode(pwd));
		shopMember.setCreatetime(createtime);
		Jedis jedis = new Jedis(Pojo.Jedis.jedis_url);
		if (jedis.exists(mobile) && jedis.get(mobile).equals(code)) {
			System.out.println(shopMemberDao.insertShopMember(shopMember));
			jedis.close();
			return "{\"status\":\"1\"}";
		} else {
			jedis.close();
			return "{\"status\":\"0\",\"msg\":\"验证码错误或过期！\"}";
		}
	}
	
	
	@Transactional
	public String forget(String mobile,String pwd,String code)
	{
		 if(shopMemberDao.findShopMember(mobile)==null)
			{
				return "{\"status\":\"0\",\"msg\":\"该电话号码为注册，请先注册\"}";
			}
			else
			{
				Jedis jedis=new Jedis(Pojo.Jedis.jedis_url);
				if (jedis.exists(mobile) && jedis.get(mobile).equals(code)) {
				ShopMember shopMember=new ShopMember();
				shopMember.setMobile(mobile);
				shopMember.setPwd(MD5Util.encode(pwd));
				shopMemberDao.updateShopMember(shopMember);
				jedis.close();
				return "{\"status\":\"1\"}";
				}
				else
				{
					jedis.close();
					return "{\"status\":\"0\",\"msg\":\"验证码过期或错误\"}";
				}
			}
	}
	
	
public void alipay(String buyphone,HttpServletRequest request) throws IOException {
		

	    
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String oldphone = userDetails.getUsername();
		NewUser newuser = new NewUser();
		newuser.setBuyphone(buyphone);
		newuser.setOldphone(oldphone);
		Object recommendidobject = request.getSession().getAttribute("recommendid");
		// 如果有推荐人
		if (recommendidobject != null) {
			Integer recommendid = Integer.valueOf((String) recommendidobject);
			// 查找推荐人信息
			NewUser recommendNewUser = newUserDao.findById(recommendid);
			int grade = recommendNewUser.getGrade();
			if (grade == 1) {
				int count = recommendNewUser.getCount();
				if (count < 30)
					recommendNewUser.setMoney(recommendNewUser.getMoney() + new Float(40));
				else
					recommendNewUser.setMoney(recommendNewUser.getMoney() + new Float(60));
				newUserDao.updateMoney(recommendNewUser);
			} else if (grade == 2) {
				int firstrecommendid = recommendNewUser.getRecommendid();
				NewUser firstrecommendNewUser = newUserDao.findById(firstrecommendid);
				int count = recommendNewUser.getCount();
				if (count < 30) {
					recommendNewUser.setMoney(recommendNewUser.getMoney() + new Float(30));
					firstrecommendNewUser.setMoney(firstrecommendNewUser.getMoney() + new Float(10));
				} else {
					recommendNewUser.setMoney(recommendNewUser.getMoney() + new Float(45));
					firstrecommendNewUser.setMoney(firstrecommendNewUser.getMoney() + new Float(15));
				}
				newUserDao.updateMoney(recommendNewUser);
				newUserDao.updateMoney(firstrecommendNewUser);
			}
			newuser.setRecommendid(recommendid);
		}
		newUserDao.insertNewUser(newuser);

	}
}
