package Interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

public class PermisstionInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
	request.getCookies();
		response.setCharacterEncoding("utf-8");
		if (path.contains("getData1")) {
			Cookie[] cookie=request.getCookies();
			if(cookie==null)
			{
				request.getRequestDispatcher("/admin/adminlogin").forward(request, response);  
				return false;
				
				}
			String username=null;
			String password=null;
			for (Cookie cookie2 : cookie) {
				if(cookie2.getName().equals("username"))
				{
				username=cookie2.getValue();	
				}
				else if(cookie2.getName().equals("admin"))
				{
				password=cookie2.getValue();	
				}
			}
			if(username==null||password==null)
				{
				request.getRequestDispatcher("/admin/adminlogin").forward(request, response);  
				return false;
				
				}
			else
			{
			Jedis jedis = new Jedis(Pojo.Jedis.jedis_url);
			if(jedis.exists(username)&&jedis.get(username).equals(password))
			{
				jedis.expire(username,60*20);
				jedis.close();
				return true;
			}else
			{
				jedis.close();
				request.getRequestDispatcher("/admin/adminlogin").forward(request, response);  
				return false;
			}
			}
			
		}
		return true;
	}

}
