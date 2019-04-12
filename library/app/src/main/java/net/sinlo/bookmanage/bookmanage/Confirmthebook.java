package net.sinlo.bookmanage.bookmanage;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.sinlo.bookmanage.bookmanage.database.MySQLiteOpenHelper;
import net.sinlo.bookmanage.bookmanage.util.ActivityUtil;
import net.sinlo.bookmanage.bookmanage.util.DateUtil;

import java.util.Date;

/**
 * Created by 罗东荣 on 2018/6/17.
 */

public class Confirmthebook extends AppCompatActivity {
    private MySQLiteOpenHelper sql;
    private SQLiteDatabase database;
    private Button confirmtheButton;
    private TextView bookInfo;
   private String brief=null;
    private String authorb=null;
    private String number=null;
    private String dateofpublication=null;
    private   String user=null;
    private  String userName=null;
    private  String bookISBN=null;
    private  String bookName=null;
    private  String bookid=null;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmthebook);
        //显示顶部返回键
        ActivityUtil.showActionBar(this);
        //禁止旋转屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        confirmtheButton=findViewById(R.id.confirmthebookButton);
        bookInfo=findViewById(R.id.bookInfo);
        sql=new MySQLiteOpenHelper(Confirmthebook.this,"ldrbook1",null,1);
        database=sql.getWritableDatabase();

/*
*  intent.putExtra("brief",brief);
                          intent.putExtra("number",number);
                          intent.putExtra("authorb",authorb);
                          intent.putExtra("dateofpublication",dateofpublication);
                          intent.putExtra("userId",userId);
                          intent.putExtra("userName",userName);
                          intent.putExtra("bookISBN",bookISBN);
                          intent.putExtra("bookName",bookName);
*
* */
        Intent intent=getIntent();
         brief=intent.getStringExtra("brief");
         authorb=intent.getStringExtra("authorb");
         number=intent.getStringExtra("number");
         dateofpublication=intent.getStringExtra("dateofpublication");
        user=intent.getStringExtra("user");
         userName=intent.getStringExtra("userName");
         bookISBN=intent.getStringExtra("bookISBN");
         bookName=intent.getStringExtra("bookName");
         bookid=intent.getStringExtra("bookid");
        bookInfo.setText("书名:"+bookName+"\nISBN:"+bookISBN+"\n作者:"+authorb+
                "\n出版时间:"+dateofpublication+"\n借书人:"+userName+
        "\n借书时间:"+ DateUtil.getDataString(new Date(System.currentTimeMillis()))+
                "\n最后还书时间:"+DateUtil.getAl10Day()+"\n书本简介:"+brief);
        confirmtheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues contentValues=new ContentValues();
                contentValues.put("number",(Integer.parseInt(number)-1)+"");
                //借出一本书书数量就-1
                database.update("book",contentValues,"id=?",new String[]{bookid});
                database.execSQL("insert into Borrowbooks(user,username,bookname,ISBN,Borrowdata,state) values('"+user+"','"+userName+"','"+bookName+"','"+bookISBN+"','2018-6-17','借出')");

                Intent integer=new Intent(Confirmthebook.this,BookIndex.class);
                Toast.makeText(Confirmthebook.this,"借书成功!",Toast.LENGTH_LONG).show();
                startActivity(integer);
                Confirmthebook.this.finish();
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //添加个好看的标题栏
        getMenuInflater().inflate(R.menu.menu1,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void onBackPressed() {
        Intent intent=new Intent(Confirmthebook.this,BorrowBooks.class);
        startActivity(intent);
        Confirmthebook.this.finish();
    }
    //顶部返回键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ////点击顶部的返回键后跳转
        ActivityUtil.ActionBarInt(item,this,BorrowBooks.class);
        return super.onOptionsItemSelected(item);
    }
    //onDestroy 关闭数据库
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //	MainActivity.this.finish();
        database.close();

    }
}
