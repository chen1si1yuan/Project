import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class AlibabaSMS {
	static String url="http://gw.api.taobao.com/router/rest";
	static String appkey="23496916";
	static String secret="06190baf4dd31ad8386f578ea64df4a7";
	
	public static String sendMessage(String phone) throws ApiException
	{
	TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
	int code=3456;
	AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
	req.setSmsType("normal");
	req.setSmsFreeSignName("慧享校园");
	req.setSmsParamString("{\"code\":\""+code+"\",\"product\":\"alidayu\"}");
	req.setRecNum(phone);
	req.setSmsTemplateCode("SMS_14235743");
	AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
	return rsp.getBody();
	}

}
