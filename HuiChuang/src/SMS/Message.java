package SMS;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

public class Message {
	static String url = "http://gw.api.taobao.com/router/rest";
	static String appkey = "23496916";
	static String secret = "06190baf4dd31ad8386f578ea64df4a7";

	public static String sendMessage(String phone) throws ApiException {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		int code = smsCode();
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName("慧享校园");
		req.setSmsParamString("{\"code\":\"" + code + "\",\"product\":\"慧享校园\"}");
		req.setRecNum(phone);
		req.setSmsTemplateCode("SMS_14235743");
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		String response= rsp.getBody();
		JSONObject json=JSONObject.fromObject(response);
		if(json.containsKey("alibaba_aliqin_fc_sms_num_send_response"))
		{
			Jedis jedis=new Jedis(Pojo.Jedis.jedis_url);
			jedis.set(phone, code+"");
			jedis.expire(phone, 180);
			return "{\"status\":\"1\"}";
		}
		else
			return "{\"status\":\"0\"}";
	}

	// 创建验证码
	public static Integer smsCode() {
		int random = (int) ((Math.random() * 9 + 1) * 100000);
		return random;
	}

}
