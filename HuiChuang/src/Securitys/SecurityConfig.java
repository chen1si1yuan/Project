package Securitys;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	protected void configure(HttpSecurity http) throws Exception {
		http
		 .formLogin().loginPage("/login")
        .defaultSuccessUrl("/main").failureUrl("/login?error")
        .usernameParameter("username").passwordParameter("password")
        .and()
        .logout().logoutSuccessUrl("/login?logout")
        .and()   
		.authorizeRequests()
		.antMatchers("/aliyunpay").authenticated()
		.antMatchers("/userinfo").authenticated()
		//.anyRequest().permitAll()	
		.and()
       .csrf().disable()
       .httpBasic();
      
	}
	
	@Bean
    CustomUserService customUserService() {
        return new CustomUserService();
    }
	
	@Autowired
	DataSource dataSource;
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserService()).passwordEncoder(new PasswordEncoder(){

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String)rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String)rawPassword));
            }}); //user Details Service验证
	}
	

//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//		.jdbcAuthentication()
//		.dataSource(dataSource)
//		.usersByUsernameQuery("select username,pwd as password,true from user where username=?");
//		//.authoritiesByUsernameQuery("select username, role from user where username=?");
//		
//	}
	
	
}
