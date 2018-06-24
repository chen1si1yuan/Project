package Controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.jpay.ext.kit.PaymentKit;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.taobao.api.ApiException;

import Dao.BillDao;
import Dao.NewUserDao;
import Dao.PhoneDao;
import Dao.ShopMemberDao;
import Pojo.AliPay;
import Pojo.Bill;
import Pojo.H5ScencInfo;
import Pojo.H5ScencInfo.H5;
import Pojo.NewUser;
import Pojo.Phone;
import Pojo.ShopMember;
import Pojo.WxPayConfig;
import SMS.Message;
import Securitys.MD5Util;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Controller
public class WebController {

	@Autowired
	Handler handler;
	@Autowired
	PhoneDao phoneDao;
	@Autowired
	ShopMemberDao shopMemberDao;
	@Autowired
	NewUserDao newUserDao;
	@Autowired
	BillDao billDao;

	static final Log logger = LogFactory.getLog(AdminController.class);

	/**
	 * 登录
	 * 
	 * @param error
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginpage(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, ModelAndView mv,
			HttpServletRequest request) {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			mv.setViewName("home/login");
			return mv;
		}

		if (error != null) {
			mv.addObject("error", "layer.msg('账号名密码错误');");
		}
		if (logout != null) {
			mv.addObject("error", "成功退出，请重新登录！");
		}
		mv.setViewName("home/login");
		return mv;
	}

	/**
	 * 显示库存电话号码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/showNodesByFlowNameOption", method = RequestMethod.GET)
	@ResponseBody
	public Object showNodesByFlowNameOption(HttpServletRequest request, HttpServletResponse response) {
		List<Phone> phonelist = phoneDao.findPhone();
		return phonelist;
	}
	
	/**
	 * 显示库存电话号码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/showphone", method = RequestMethod.POST)
	@ResponseBody
	public Object showphone(HttpServletRequest request, HttpServletResponse response) {
		String vaguephone=request.getParameter("vaguephone");
		List<Phone> phones=phoneDao.findPhoneByVague(vaguephone);
		if(phones.size()>0)
			return phones;
		else
			return "{\"status\":\"not found phonenumber\"}";
	}

	/**
	 * 登录成功主界面
	 * 
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv, HttpServletRequest request) {
		String userid = request.getParameter("id");
		if (userid != null) {
			try {
				Integer id = Integer.valueOf(userid);
				request.getSession().setAttribute("recommendid", userid);
			} catch (Exception e) {

			}
		}
		mv.setViewName("home/main");
		return mv;
	}

	@RequestMapping(value = "/recommendstatus", method = RequestMethod.GET)
	public void recommendstatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		Object recommendid = request.getSession().getAttribute("recommendid");
		if (recommendid != null) {
			response.getWriter().println("您的推荐人ID=" + recommendid);
		} else
			response.getWriter().println("您没有推荐人");
	}

	@RequestMapping(value = "/logstatus", method = RequestMethod.GET)
	public void logstatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");

		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			response.getWriter().println("<a href='/HuiChuang/login'>登录</a>");
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			String oldphone = userDetails.getUsername();
			response.getWriter().println("欢迎登录" + oldphone + "  <a href='/HuiChuang/logout'>退出</a>");
		}
	}

	@RequestMapping("/getcode")
	@ResponseBody
	public String getcode(HttpServletRequest request, HttpServletResponse response)
			throws ClientProtocolException, IOException, ApiException {
		response.setCharacterEncoding("utf-8");
		String phone = request.getParameter("mobile");
		Pattern p = Pattern.compile("^1[3578]\\d{9}$");

		Matcher m = p.matcher(phone);
		if (m.matches()) {
			return Message.sendMessage(phone);
		} else
			return "{\"status\":\"请查看手机格式是否正确\"}";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerGet(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("home/register");
		return mv;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public String register(HttpServletRequest request) {

		String mobile = request.getParameter("mobile");
		String pwd = request.getParameter("pwd");
		String code = request.getParameter("code");

		if (mobile == null || mobile.equals("")) {
			return "{\"status\":\"0\",\"msg\":\"手机号为空\"}";
		}
		if (pwd == null || pwd.equals("")) {
			return "{\"status\":\"0\",\"msg\":\"密码为空\"}";
		}
		if (code == null || code.equals("")) {
			return "{\"status\":\"0\",\"msg\":\"验证码为空\"}";
		}
		return handler.register(mobile, pwd, code);

	}

	@RequestMapping(value = "/forget", method = RequestMethod.GET)
	public ModelAndView forgetPwdGet(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("home/forget");
		return mv;
	}

	@RequestMapping(value = "/forget", method = RequestMethod.POST)
	@ResponseBody
	public String forgetPwdPost(HttpServletRequest request) {

		String mobile = request.getParameter("mobile");
		String pwd = request.getParameter("pwd");
		String code = request.getParameter("code");

		if (pwd == null || pwd.equals("") || mobile == null || mobile.equals("") || code == null || code.equals("")) {
			return "{\"status\":\"0\",\"msg\":\"请确认数据格式是否正确\"}";
		}
		return handler.forget(mobile, pwd, code);

	}

	
	
	
	@RequestMapping(value = "/userinfo", method = RequestMethod.GET)
	@ResponseBody
	public Object userinfo(HttpServletRequest request) {
		String oldphone = null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return "{\"status\":\"您还没有进行登录\"}";
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			oldphone = userDetails.getUsername();
		}
		List<NewUser> newUsers = newUserDao.findByOldphone(oldphone);
		for (NewUser newUser : newUsers) {
			if (newUser.getGrade() == null)
				return "{\"oldphone\":\"" + newUser.getOldphone() + "\",\"buyphone\":\"" + newUser.getBuyphone()
						+ "\"}";
			else
				return newUser;
		}
		return "{\"status\":\"未找到相关信息\"}";
	}

	
	
	
	@RequestMapping(value = "salesperson", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	@Transactional
	public String Salesperson(HttpServletRequest request) throws Exception {

		String oldphone = request.getParameter("oldphone");
		String aliaccount = request.getParameter("aliaccount");
		Float money;
		try {
			money = Float.valueOf(request.getParameter("money"));
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return "{\"status\":\"金额格式不正确\"}";

		}
		List<NewUser> newusers = newUserDao.findByOldphone(oldphone);
		for (NewUser newUser : newusers) {
			if (newUser.getMoney() < money) {
				return "{\"status\":\"输入金额超过余额，申请提现失败\"}";
			} else {
				NewUser updateuser = new NewUser();
				updateuser.setId(newUser.getId());
				updateuser.setMoney(newUser.getMoney() - money);
				Integer status = newUserDao.updateMoney(updateuser);
				if (status == 1) {
					Bill bill = new Bill();
					bill.setAliaccount(aliaccount);
					bill.setPhone(oldphone);
					bill.setMoney(money);
					bill.setBillstatus(0);
					status = billDao.insertBill(bill);
					if (status == 1) {
						return "{\"status\":\"提现申请成功，我们将于两个工作日内转账到您的账号，请注意查收\"}";
					} else {
						throw new Exception();
					}
				} else {
					throw new Exception();
				}
			}
		}
		return "{\"status\":\"用户不存在\"}";
	}

	@RequestMapping("Wx_notityurl")
	public void Wx_notityurl() {
		System.out.println("调用成功");
	}

	@RequestMapping("/wapPay")
	public void wapPay(HttpServletRequest request, HttpServletResponse response) {
		String ip = IpKit.getRealIp(request);
		if (com.jpay.ext.kit.StrKit.isBlank(ip)) {
			ip = "127.0.0.1";
		}

		H5ScencInfo sceneInfo = new H5ScencInfo();

		H5 h5_info = new H5();
		h5_info.setType("Wap");
		// 此域名必须在商户平台--"产品中心"--"开发配置"中添加

		h5_info.setWap_url("http://www.xxx.com");
		h5_info.setWap_name("公司官网");
		sceneInfo.setH5_info(h5_info);
		WxPayApiConfig wxPayApiConfig = getApiConfig();
		Map<String, String> params = WxPayApiConfig.New().setAppId(WxPayConfig.APPID).setMchId(WxPayConfig.MCHID)
				.setBody("H5支付测试").setSpbillCreateIp(ip).setTotalFee("1").setTradeType(WxPayApi.TradeType.MWEB)
				.setNotifyUrl(WxPayConfig.NOTIFY_URL).setPaternerKey(WxPayConfig.KEY)
				.setOutTradeNo(String.valueOf(System.currentTimeMillis()))
				.setSceneInfo("{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"wap_url\",\"wap_name\": \"联通售卡\"}}")
				.setAttach("H5支付测试").build();
		String xmlResult = WxPayApi.pushOrder(false, params);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
		// 返回结果
		String return_code = result.get("return_code");
		String return_msg = result.get("return_msg");
		if (!PaymentKit.codeIsOK(return_code)) {
			logger.error("return_code>" + return_code + " return_msg>" + return_msg);
			throw new RuntimeException(return_msg);
		}
		String result_code = result.get("result_code");
		if (!PaymentKit.codeIsOK(result_code)) {
			logger.error("result_code>" + result_code + " return_msg>" + return_msg);
			throw new RuntimeException(return_msg);
		}
		// 以下字段在return_code 和result_code都为SUCCESS的时候有返回

		String prepay_id = result.get("prepay_id");
		String mweb_url = result.get("mweb_url");

		logger.info("prepay_id:" + prepay_id + " mweb_url:" + mweb_url);
		try {
			response.sendRedirect(mweb_url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static WxPayApiConfig getApiConfig() {
		return WxPayApiConfig.New().setAppId(WxPayConfig.APPID).setMchId(WxPayConfig.MCHID)
				.setPaternerKey(WxPayConfig.KEY).setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);
	}

}
