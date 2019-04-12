package net.sinlo.bookmanage.bookmanage.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hello on 2018/6/28.
 */

public class UserInfoRegexUtil {

    public static boolean user(String user){
          Pattern r =Pattern.compile("^[\\w]{4,18}$");

        Matcher matcher=r.matcher(user);

        return matcher.matches();
    }
    //手机号  必须是11位数  ,且必须1字开头
    public static boolean phone(String phone){
        Pattern r =Pattern.compile("^[1][0-9]{10}$");

        Matcher matcher=r.matcher(phone);

        return matcher.matches();
    }
    public static boolean Emial(String email){
        //邮箱正则    xxxx@zzz.yyy(.yyy最多可重复2遍)
        Pattern r =Pattern.compile("^[\\w\\-]+@[a-z A-Z 0-9]+(.[a-z A-Z 0-9]{2,6}){1,2}$");

        Matcher matcher=r.matcher(email);

        return matcher.matches();
    }
    //姓名判断长度即可
    public static boolean userName(String userName){
         if(userName.length()>0&&userName.length()<=10){
     return  true;
         }
        return false;
    }
    //密码除了初始化的以为,都必须长度>6,<18
    public static boolean userPassword(String password){
        if(password.length()>=6&&password.length()<18){
            return  true;
        }
        return false;
    }


}
