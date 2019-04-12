package net.sinlo.bookmanage.bookmanage.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookRegexUtil {

    //出版日期  2018-6-28
    public  static boolean bookDate(String date){
        Pattern r =Pattern.compile("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$");

        Matcher matcher=r.matcher(date);

        return   matcher.matches();
    }
    //ISBN是10位数的数字
   public  static boolean ISBN(String isbn){
       Pattern r =Pattern.compile("^[0-9]{10}$");

       Matcher matcher=r.matcher(isbn);

       return   matcher.matches();
   }
    //数量是纯数字
    public  static boolean booknumber(String booknumber){
        Pattern r =Pattern.compile("^[0-9]+$");

        Matcher matcher=r.matcher(booknumber);

        return   matcher.matches();
    }
   //书本作者
    public static boolean bookZZ(String user){
        if(user.length()>0){
            return true;
        }
        return false;
    }
    //书本简介
    public static boolean bookjj(String user){
        if(user.length()>0){
            return true;
        }
        return false;
    }
    public static boolean bookName(String user){
        if(user.length()>0){
            return true;
        }
        return false;
    }
   public static  void  main(String [] args){
       System.out.println(booknumber("123s"));
   }
}
