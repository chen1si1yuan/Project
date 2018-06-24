import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.taobao.api.ApiException;

import SMS.Message;
import Securitys.MD5Util;
import redis.clients.jedis.Jedis;

public class Test {

	public static void main(String[] args) throws ClientProtocolException, IOException, ApiException {
		Jedis jedis=new Jedis(Pojo.Jedis.jedis_url);
		jedis.lpush("key", "3");
		jedis.lpush("key", "2");
		jedis.lpush("key", "false");
		jedis.expire("key", 60);
		jedis.lset("key", 0, "true");
	
		 // 获取存储的数据并输出
        List<String> list = jedis.lrange("key", 0, 2);
      
            System.out.println("列表项为: "+list.get(0));
          //  System.out.println(jedis.get("key"));
		jedis.close();
	}
}
