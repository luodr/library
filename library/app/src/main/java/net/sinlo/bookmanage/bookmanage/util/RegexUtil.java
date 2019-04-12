package net.sinlo.bookmanage.bookmanage.util;

import android.app.Activity;
import android.widget.Toast;

import net.sinlo.bookmanage.bookmanage.ModifyActivity;

/**
 * Created by hello on 2018/6/28.
 */
//因为有多处用到同一正则表达式所以我封装起来
public class RegexUtil {

    public static boolean  userInfoRegex(String user, String password, String userName, String mainl, String phone, Activity activity){
        if(!UserInfoRegexUtil.user(user)){
            Toast.makeText(activity,"用户名长度应该是4-18",Toast.LENGTH_SHORT).show();
            return false;
        }else  if(!UserInfoRegexUtil.userPassword(password)){
            Toast.makeText(activity,"密码长度应该是6-18",Toast.LENGTH_SHORT).show();
            return false;
        }else  if(!UserInfoRegexUtil.userName(userName)){
            Toast.makeText(activity,"请输入正确的姓名",Toast.LENGTH_SHORT).show();
            return false;
        }else  if(!UserInfoRegexUtil.Emial(mainl)){
            Toast.makeText(activity,"请输入正确的邮箱",Toast.LENGTH_SHORT).show();
            return false;
        }else  if(!UserInfoRegexUtil.phone(phone)){
            Toast.makeText(activity,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static  boolean bookInfoRegex(String bookNama,String ISBN,String date,String zz,String number,String jj,Activity activity){
   if(!BookRegexUtil.bookName(bookNama)){
       Toast.makeText(activity,"请输入正确的书名",Toast.LENGTH_SHORT).show();
       return false;
   }else if(!BookRegexUtil.ISBN(ISBN)){
       Toast.makeText(activity,"ISBN应该是10位数的纯数字",Toast.LENGTH_SHORT).show();
       return false;
   }else if(!BookRegexUtil.bookZZ(zz)){
       Toast.makeText(activity,"请输入书本作者",Toast.LENGTH_SHORT).show();
       return false;
   }else if(!BookRegexUtil.bookDate(date)){
       Toast.makeText(activity,"日期格式应该如:2018-06-28",Toast.LENGTH_SHORT).show();
       return false;
   }else if(!BookRegexUtil.booknumber(number)){
       Toast.makeText(activity,"请输入书本数量",Toast.LENGTH_SHORT).show();
       return false;
   }else if(!BookRegexUtil.bookjj(jj)){
       Toast.makeText(activity,"请输入书本简介",Toast.LENGTH_SHORT).show();
       return false;
   }


        return  true;
    }
    public static  boolean userAndBookInfoRegex(String user,String userName,String bookNama,String ISBN,Activity activity){
        if(!UserInfoRegexUtil.user(user)){
            Toast.makeText(activity,"请输入正确的用户名(4-18)",Toast.LENGTH_SHORT).show();
            return false;
        }else  if(!UserInfoRegexUtil.userName(userName)){
            Toast.makeText(activity,"请输入正确的姓名",Toast.LENGTH_SHORT).show();
            return false;
        }   if(!BookRegexUtil.bookName(bookNama)){
            Toast.makeText(activity,"请输入正确的书名",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!BookRegexUtil.ISBN(ISBN)){
            Toast.makeText(activity,"ISBN应该是10位数的纯数字",Toast.LENGTH_SHORT).show();
            return false;
        }

        return  true;
    }
}
