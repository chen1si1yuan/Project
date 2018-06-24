package Pojo;
public class WxPayConfig {
	//=======【基本信息设置】=====================================
	//微信公众号身份的唯一标识。审核通过后，在微信发送的邮件中查看
	public static final String APPID = "wx691aaaa2e8c9cf16";//需改
	//受理商ID，身份标识
	public static final String MCHID = "1488109022";//需改
	//商户支付密钥Key。审核通过后，在微信发送的邮件中查看
	public static final String KEY = "Paterner";//需改
	//JSAPI接口中获取openid，审核后在公众平台开启开发模式后可查看
	public static final String APPSECRET = "8b84563b222d2578de595682b69ed9cb";//需改
	
	
	//=======【异步通知url设置】===================================
	//异步通知url，商户根据实际开发过程设定
	
	private static String SERVER_URL = "http://xxx.xxx.com";//需改
	
	public static final String NOTIFY_URL = "https://wechat.aizuoshe.com";

	
	//编码方式
	public static final String ENCODE = "UTF-8";
	
	//微信uri-根据code获取openid
	public static String GET_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
	
	//微信uri-请求预支付接口
	public static String UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}