package Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;


import Dao.AdminDao;
import Dao.NewUserDao;
import Dao.PhoneDao;
import Dao.ShopMemberDao;
import Pojo.Admin;
import Pojo.AliPay;
import Pojo.NewUser;
import Pojo.Phone;
import Securitys.MD5Util;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Controller
public class AdminController {

	@Autowired
	AdminDao adminDao;
	@Autowired
	NewUserDao newUserDao;
	@Autowired
	ShopMemberDao shopMemberDao;
	@Autowired
	PhoneDao phoneDao;
	@Autowired
	Handler handler;
	
	static final Log logger=LogFactory.getLog(AdminController.class);
	
	
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView admin(ModelAndView mv,HttpServletRequest request) {
		mv.setViewName("admin/index");
		return mv;
	} 
	
	
	@RequestMapping(value = "/adminlogin", method = RequestMethod.GET)
	public ModelAndView loginget(ModelAndView mv,HttpServletRequest request) {
		mv.setViewName("admin/login");
		return mv;
	} 
	
	@RequestMapping(value = "/adminlogin", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public String loginpost(HttpServletRequest request,HttpServletResponse response) {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		Admin admin=adminDao.findAdmin(username);
		if(admin!=null&&admin.getPassword().equals(MD5Util.encode(password)))
		{
			Jedis jedis=new Jedis(Pojo.Jedis.jedis_url);
			Cookie cookie = new Cookie("admin",admin.getPassword());
			cookie.setMaxAge(-1);
			response.addCookie(cookie);
			Cookie cookie1 = new Cookie("username",username);
			cookie.setMaxAge(-1);
			response.addCookie(cookie1);
			jedis.set(username, admin.getPassword());
			jedis.expire(username, 60*20);
			jedis.close();
			return "{\"status\":\"1\"}";
		}
		else
			return "{\"status\":\"0\",\"msg\":\"账号或密码错误\"}";
	}
	
	
	
	
	@RequestMapping(value = "/getData", method = RequestMethod.GET)
	@Transactional
	@ResponseBody
	public JSONObject getData()
	{
		Integer firstgrade=newUserDao.gradeCount(1);
		Integer seccendgrage=newUserDao.gradeCount(2);
		Date date=new Date();
		int time=(int) (date.getTime()/1000- (date.getTime()/1000)%(60*60*24));
		Integer todayregister= shopMemberDao.todayRegister(time);
		Integer sellphone=phoneDao.sellPhoneCount();
		JSONObject json=new JSONObject();
		json.put("firstgrade", firstgrade);
		json.put("seccendgrage", seccendgrage);
		json.put("todayregister", todayregister);
		json.put("sellphone", sellphone);
		return json;
	}
	
	
	@RequestMapping(value="payphone",method=RequestMethod.POST)
	@ResponseBody
	public void payphone(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		
		response.setCharacterEncoding("UTF-8");
		String buyphone=request.getParameter("buyphone");
		System.out.println(buyphone+"1");
		if (buyphone == null||buyphone.equals("")) {
			response.getWriter().println("{\"status\":\"请选择需要购买的手机号\"}");
			return;
		}
		String out_trade_no=new Date().getTime()+"";  //订单号
		request.getSession().setAttribute("out_trade_no", out_trade_no);
		request.getSession().setAttribute("buyphone", buyphone);
		String oldphone=null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			response.getWriter().println("{\"status\":\"您还没有进行登录\"}"); 
			return;
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			 oldphone = userDetails.getUsername();
		}
	
		List<NewUser> users=newUserDao.findByOldphone(oldphone);
	
		if(users==null||users.size()>0)
		{
			response.getWriter().println("{\"status\":\"一个用户只能购买一次活动优惠卡\"}"); 
			return;
		}
		
	
		Jedis jedis=new Jedis(Pojo.Jedis.jedis_url);
	
		if(jedis.exists(buyphone)||(phoneDao.phoneExit(buyphone)==1)||phoneDao.phoneExit(buyphone)==null)
		{
			jedis.close();
			response.getWriter().println("{\"status\":\"该手机号以被其他用户选定或正在付款，请刷新购买其他手机号\"}"); 	
			return;
		}
		response.getWriter().println("{\"status\":\"1\"}"); 	
		
	}
	
	@RequestMapping(value="surePhone",method=RequestMethod.GET)
	public ModelAndView surePhone(ModelAndView mv,HttpServletRequest request,HttpServletResponse response)
	{
		String out_trade_no=(String) request.getSession().getAttribute("out_trade_no");
		String buyphone= (String) request.getSession().getAttribute("buyphone");
		if(out_trade_no!=null&&buyphone!=null&&!out_trade_no.equals("")&&!buyphone.equals(""))
		{
			if((phoneDao.phoneExit(buyphone)==1)||phoneDao.phoneExit(buyphone)==null)
				mv.addObject("message", "卡号异常");
			else {
			mv.addObject("out_trade_no", out_trade_no);
			mv.addObject("buyphone", buyphone);
			mv.addObject("subject", "联通购卡");
			mv.addObject("total_amount", "0.1");
			}
		}else
		{
			mv.addObject("message", "订单号或手机号异常");
		}
		mv.setViewName("SurePay");
			
		return mv;
	}
	
	
	@RequestMapping(value="aliyunpay")
	@Transactional
	public void  alipay(HttpServletRequest request,HttpServletResponse response) throws IOException, AlipayApiException
	{
		response.setCharacterEncoding("UTF-8");
		String out_trade_no=(String) request.getSession().getAttribute("out_trade_no");
		String buyphone= (String) request.getSession().getAttribute("buyphone");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		 String oldphone = userDetails.getUsername();
		

		if(out_trade_no==null||out_trade_no.equals(""))
		{
			response.getWriter().println("{\"status\":\"订单号不存在，请不要尝试非法访问！！！\"}"); 
			return;
		}
		Jedis jedis=new Jedis(Pojo.Jedis.jedis_url);
		if(jedis.exists(buyphone)||(phoneDao.phoneExit(buyphone)==1)||phoneDao.phoneExit(buyphone)==null)
		{
			jedis.close();
			response.getWriter().println("{\"status\":\"该手机号以被其他用户选定或正在付款，请刷新购买其他手机号\"}"); 	
			return;
		}
		jedis.set(buyphone, "false");
		jedis.expire(buyphone, 60*5);
		jedis.lpush(out_trade_no, oldphone);
		jedis.lpush(out_trade_no, buyphone);
		jedis.lpush(out_trade_no, "false");
		jedis.expire(out_trade_no, 60*60*3);
		
		jedis.close();
		
		
		
		// 订单名称，必填
	    String subject = "联通购卡";
		System.out.println(subject);
	    // 付款金额，必填
	    String total_amount="0.1";
	   	    // 超时时间 可空
	   String timeout_express="2m";
	    // 销售产品码 必填
	    String product_code="QUICK_WAP_WAY";
		//转到支付页面
        //初始化客户端 只需要实例化一次（线程安全）
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do"
                , AliPay.APP_ID, AliPay.APP_PRIVATE_KEY, "json", "UTF-8", AliPay.ALIPAY_PUBLIC_KEY, "RSA2");
        //在公共参数中设置回跳和通知地址
      
        AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
        
        // 封装请求支付信息
        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(total_amount);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(AliPay.notify_url);
        // 设置同步地址
        alipay_request.setReturnUrl(AliPay.return_url);   
        
        // form表单生产
        String form = "";
    	try {
    		// 调用SDK生成表单
    		form = alipayClient.pageExecute(alipay_request).getBody();
    		response.setContentType("text/html;charset=UTF-8"); 
    	    response.getWriter().write(form);//直接将完整的表单html输出到页面 
    	    response.getWriter().flush(); 
    	    response.getWriter().close();
    	} catch (AlipayApiException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} 
		 
	}
	
	
	@RequestMapping("/notifyurl")
	@Transactional
	public String NotifyUrl1(HttpServletRequest request) throws IOException
	{
		String out_trade_no=request.getParameter("out_trade_no");
		Jedis jedis =new Jedis(Pojo.Jedis.jedis_url);
		if(out_trade_no==null||out_trade_no.equals("")||!jedis.exists(out_trade_no))  //购买失败
		{
			logger.info("订单号不存在");
			jedis.close();
			return "home/main";
		}else
		{
			 // 获取存储的数据并输出
	        List<String> list = jedis.lrange(out_trade_no, 0, 2);
	       
	        if(list.get(0).equals("false"))  //返回通知支付不成功
	        {
	        	logger.info(list.get(2)+"的订单号："+out_trade_no+"支付不成功");
	        	 jedis.close();
	        	return "home/main";
	        }
	        else
	        {
	        	NewUser newuser=new NewUser();
	        	newuser.setOldphone(list.get(2));
	        	newuser.setBuyphone(list.get(1));
	        	handler.alipay(list.get(1), request);
	        	if(phoneDao.updatePhoneExit(list.get(1))==1)
	        		logger.info(list.get(2)+"的订单号："+out_trade_no+"支付成功");
	        	else
	        	logger.info(list.get(2)+"的订单号："+out_trade_no+"支付成功,号码存贮失败");
	        	jedis.del(out_trade_no);
	        	request.getSession().setAttribute(out_trade_no, null);
	        	jedis.close();
	        	return "home/main";
	        }
			
		}
		
	}
	
	@RequestMapping("/notifyurl1")
	@Transactional
	public void NotifyUrl(HttpServletRequest request,HttpServletResponse response) throws IOException, AlipayApiException
	{
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号

			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝账号
			String buyer_id=new String(request.getParameter("buyer_id").getBytes("ISO-8859-1"),"UTF-8");
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			//计算得出通知验证结果
			//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
			boolean verify_result = AlipaySignature.rsaCheckV1(params, AliPay.ALIPAY_PUBLIC_KEY, "UTF-8", "RSA2");
			
			if(verify_result){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码

				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				
				if(trade_status.equals("TRADE_FINISHED")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
						//如果有做过处理，不执行商户的业务程序
					logger.info("支付成功，不可退款");
					logger.info("支付宝用户："+buyer_id+"付款横成功");
					Jedis jedis=new Jedis(Pojo.Jedis.jedis_url);				
					jedis.lset(out_trade_no,0 ,"true");
					jedis.close();
					//注意：
					//如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
					//如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
				} else if (trade_status.equals("TRADE_SUCCESS")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
						//如果有做过处理，不执行商户的业务程序
						logger.info("支付成功");
						Jedis jedis=new Jedis(Pojo.Jedis.jedis_url);					
						jedis.lset(out_trade_no,0 ,"true");
						jedis.close();
					//注意：
					//如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
				}

				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				response.getWriter().println("success");	//请不要修改或删除
			

				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//验证失败
				logger.info("验签失败");
				response.getWriter().println("fail");
			}
		
	}
	
	@RequestMapping("/returnurl")
	public ModelAndView returnurl(ModelAndView mv,HttpServletRequest request)
	{
		mv.setViewName("forget");
		return mv;
	}
	
	
	
	
	@RequestMapping(value = "/firstgradeinfo", method = RequestMethod.GET)
	@ResponseBody
	public Object first(HttpServletRequest request) {
		
		List<NewUser> newUsers = newUserDao.gradeInfo(1);
		
		return newUsers;
	}
	
	@RequestMapping(value = "/secondgradeinfo", method = RequestMethod.GET)
	@ResponseBody
	public Object seconduserinfo(HttpServletRequest request) {
		
		List<NewUser> newUsers = newUserDao.gradeInfo(2);
		
		return newUsers;
	}
	
	@RequestMapping(value="addfirstsellperson", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String addsellperson(HttpServletRequest request)
	{
		String oldphone=request.getParameter("oldphone");
		String pwd=request.getParameter("pwd");
		pwd=MD5Util.encode(pwd);
		List<NewUser> newusers=newUserDao.findByOldphone(oldphone);
		if(newusers.size()>0)
		for (NewUser newUser : newusers) {
			newUser.setGrade(1);
			
			newUserDao.updategrade(newUser);
		}
		else
		{
			NewUser newUser=new NewUser();
			newUser.setOldphone(oldphone);
			newUser.setGrade(1);
			newUser.setCount(0);
			newUserDao.insertNewUser(newUser);
		}
		return "{\"status\":\"添加分销商成功\"}";
		
	}

	
	@RequestMapping(value="addsecondsellperson", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String addsecondsellperson(HttpServletRequest request)
	{
		String oldphone=request.getParameter("oldphone");
		Object recommendid = request.getSession().getAttribute("recommendid");
		
		List<NewUser> newusers=newUserDao.findByOldphone(oldphone);
		if(newusers.size()>0)
		for (NewUser newUser : newusers) {
			newUser.setGrade(2);
			if(newUser.getRecommendid()!=null)
			{
				NewUser newUserrecom=newUserDao.findById(newUser.getRecommendid());
				if(newUserrecom==null)
					return "{\"status\":\"该分销商不存在\"}";
				else if(newUserrecom.getGrade()==1)
				newUserDao.updategrade(newUser);
				else
					return "{\"status\":\"您不是由一级分销商推荐，无法成为二级分销商\"}";
			}
			else if(recommendid!=null)
			{
				newUser.setRecommendid(Integer.valueOf(recommendid.toString()));
				NewUser newUserrecom=newUserDao.findById(newUser.getRecommendid());
				if(newUserrecom==null)
					return "{\"status\":\"该分销商不存在\"}";
				else if(newUserrecom.getGrade()==1)
				newUserDao.updategrade(newUser);
				else
					return "{\"status\":\"您不是由一级分销商推荐，无法成为二级分销商\"}";
			}
			else
				return "{\"status\":\"您没有推荐人,无法成为分销商\"}";
			
		}
		else
		{
			NewUser newUser=new NewUser();
			newUser.setOldphone(oldphone);
			newUser.setGrade(2);
			newUser.setCount(0);
			if(recommendid!=null)
			{
			newUser.setRecommendid(Integer.valueOf(recommendid.toString()));
			newUserDao.updategrade(newUser);
			}
			else
				return "{\"status\":\"您没有推荐人,无法成为分销商\"}";
			
		}
		return "{\"status\":\"添加分销商成功\"}";
		
	}
	
	
}
