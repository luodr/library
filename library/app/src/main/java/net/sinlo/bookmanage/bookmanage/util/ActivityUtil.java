package net.sinlo.bookmanage.bookmanage.util;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import net.sinlo.bookmanage.bookmanage.BookIndex;
import net.sinlo.bookmanage.bookmanage.BookManage;

/**
 * Created by hello on 2018/6/28.
 */

public class ActivityUtil {
    public static void showActionBar(AppCompatActivity activity){
        //显示顶部返回键
        android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            //Toast.makeText(BookManage.this,"",Toast.LENGTH_LONG).show();

        }
    }
    //顶部返回键跳转
public static void  ActionBarInt(MenuItem item,AppCompatActivity startInten,Class endInten){
    switch (item.getItemId()) {
        case android.R.id.home:
            Intent intent=new Intent(startInten,endInten);
            startInten.startActivity(intent);
            startInten. finish();
          break;
    }
}

}
