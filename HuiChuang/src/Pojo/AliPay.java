package Pojo;

public class AliPay {

	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjokeQ7Jd9Q7rxAgvbpCt/zw6f3PLfnq5gZLzAl3cOTZvJzbSPeQHAmibjO9DrgllA5JRBeDXMhN0H0T7KHPzOMNCyA3uV+nxC8zJQchDU5EiLn4qkQxsO/Dq06CyV8JfHJ7Jj5BPDFc4MIlw+E+VmiLkJ+uTBmsJjfQwR7rIyVVv3bwm06JKfZazVS5VUMjlxF3hhnO5orTQGJxruRrEOzBqzNcUaqzj+IzMAntm4qXgPHdkLXKkhc/OuG8vVN59HRu4IHv+c383V4EwpDLtXwylIq6HCB8SUtzKDZBJw5+SXMRNZO/JqvUU9pe+xckoMpe51JRyPfZmUWEU1NOXSQIDAQAB";
	public static String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDuLPQfV6LCGy6ajJFmDTNGrj8UP23f06LqKylEP3HPhDsqoj3OzgQLeghcIAuskg5CUxZT7qp8f6n+sAUnL6J8sJLPRBbWE0RLIZu0LzatjRaLs4cX7lks2NARvlIfSQJYtFDuU0q428xyp/s1B9Ql4XOvCAwVq+j+ZagVOUmufkTwTpaHien7YqE/QCl1SfYnx/ulK+tvUtZgaTzUvLT7ZFxdx4wippy3Ox3Ew+ykhQ0JnlwlDPKrjCG7kKg8915ZqVt06ImiDat3SpeNBtZ0yp2vrjoglRMHdOtYDiY6yo7St0RtL82//FeeIUUf/JKnpQIEUzP168lXoS5myMX5AgMBAAECggEAHg8AVEBMIzEpBYo+A2IhGYd+IIEMzdW41tLRp3JWW7k1emD1afcoHYauLWpB7uqT8BzqUXdqfsHh42m1jMd/OztRm+Tc+PF/iffY3bmjre2DbegfYY7MJFPMlqCtXpNpP2aqXBmY8ZGT0NiQZJXzWUCFyA488DM8+XY860jKmsHIeJ+It0F9p4/t1MGqRRHfr/URcKyhY4luzRYaECl6VUhVSRQCFYqzdSPM+GplV15l93hA1x42LBwg77E7oZF2K0xFjSjjSdop58QgzGRYAVqVynxy912LMywrk+MOEabEunN1hhuNTH+E3mrSz0YvJGUVSCQ3Gq548ljIURXbsQKBgQD+ceV1rieGDFwQ+EbwokJXs2LWO6HaJKNUOUPBBBqpRisFkl3AKW3pGp6oUbFRwkVGzLTNhbmTvXgwq+eGkky4tg7hJ9jhb+1FQRszB/1QMSCtC1FP12ooH5iKbbwCqOz+DC2frckCj7SCuUaZIkSWRM0ZzLuM8fMVX6n8dp3I/QKBgQDvoZo0+WG+P6LjNMI4ZjMk8bmxgb3BLx0BIYQo4z/CZCQue6RiClgl+4gO7m8ganmM63jaYVQDBmV/WdC1X5s12YHgPfF3JUxzZjCc/XcUnqScoy3qsaTpNk++G8rxr14IlQJqyfQi/xqxZQDEIVgVz3H+ZwaTNbFXHqHnMb+vrQKBgQDOtMadkKrJKpdMrfYrnRXaW4AK3I0DQ8pwLMlPL79dDwT6VMrWxpFk0txbj2pHKlTPTJQBdwao8D6JA85mM8oiMLzry0xU8HHIVjj7fx0aHZ8XFV+sRisWh3yL0NPxMTy+sjQKSctHymjx3gRteTpQ/XnF4N3ohdJd5caZs99BHQKBgQCN5gwCwon2togmMt+vthUjE2D6k/FhrBxoxwgTjj37RoGGZQD6bVW1aDUxYk+7ylEf+XAfIPM2t1rzBqRoQq0OPjaCKywJebTDwQeYbx9MvoSLnLVb/MXCs76UvLB4XXg5bMN5qQ1Ai/+qfDzv5y7Z+3VtSGTEV08kX6dP2E3CIQKBgQCtcxZhp/nJFRUBvNcZmeRPEcNgmHE12C+JkysIzXYAhWFpLaC6FRMSnvl8VtNkS57lEfEELNoNYYQU2f7o/chnzzfP45ODA8AaorT+i0MghJSEAfwcPwSg0ipF5inLBxEiFNhJh33RhOY/zsWwH3b9oewZg/6lP2wogGmCbkMEnw==";
	public static String APP_ID = "2017111009843822";

	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://47.94.206.219:8080/HuiChuang/notifyurl1";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	// 商户可以自定义同步跳转地址
	public static String return_url = "http://47.94.206.219:8080/HuiChuang/notifyurl";
	// 请求网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";

}