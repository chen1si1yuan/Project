package Securitys;

import java.security.MessageDigest;

public class MD5Util {

    private static final String SALT = "fX0xXNXP2hMG4950";

    public static String encode(String password) {
    	
            try{
                 MessageDigest md = MessageDigest.getInstance("MD5");
                 md.update((password+SALT).getBytes("utf-8"));    //问题主要出在这里，Java的字符串是unicode编码，不受源码文件的编码影响；而PHP的编码是和源码文件的编码一致，受源码编码影响。
                 StringBuffer buf=new StringBuffer();            
                 for(byte b:md.digest()){
                      buf.append(String.format("%02x", b&0xff));        
                 }
                return  buf.toString();
              }catch( Exception e ){
                  e.printStackTrace(); 

                  return null;
               } 
     
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.encode("abel"));


    }
}