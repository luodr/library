package net.sinlo.bookmanage.bookmanage;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.sinlo.bookmanage.bookmanage.database.MySQLiteOpenHelper;
import net.sinlo.bookmanage.bookmanage.util.ActivityUtil;
import net.sinlo.bookmanage.bookmanage.util.RegexUtil;

/**
 * Created by 罗东荣 on 2018/6/17.
 */

public class
AlsoBook extends AppCompatActivity {
    private EditText borrowBooks_UserId;
    private EditText borrowBooks_UserName;
    private EditText borrowBooks_bookISBN;
    private EditText borrowBooks_bookname;
    private Button button;
    private Button button1;
    private MySQLiteOpenHelper sql;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alsobook);
        //显示顶部返回键
        ActivityUtil.showActionBar(this);
        //禁止旋转屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
         sql=new MySQLiteOpenHelper(AlsoBook.this,"ldrbook1",null,1);
         database= sql.getWritableDatabase();
        find();
        ss();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=borrowBooks_UserId.getText().toString().trim();
                String userName=borrowBooks_UserName.getText().toString().trim();
                String bookISBN=borrowBooks_bookISBN.getText().toString().trim();
                String bookName=borrowBooks_bookname.getText().toString().trim();
                String brief=null;
                Integer number=0;
                String authorb=null;
                String dateofpublication=null;
               // Toast.makeText(BorrowBooks.this,"userId"+userId+"--userName"+userName+"--bookISBN"+bookISBN+"--bookName"+bookName,Toast.LENGTH_LONG).show();;
              if(RegexUtil.userAndBookInfoRegex(user,userName,bookName,bookISBN,AlsoBook.this)){

                    Cursor cursor= database.query("correctionuser",new String[]{"userName","user"},"userName=? and user=?",new String[]{userName,user},null,null,null);
                    boolean isUser=false;
                    boolean isBook=false;
                    if(cursor.moveToNext()){
                        isUser=true;
                    }else{
                        Toast.makeText(AlsoBook.this,"还书人不正确",Toast.LENGTH_LONG).show();
                    }
               //     db.execSQL("CREATE TABLE IF NOT EXISTS Borrowbooks(id integer  primary key autoincrement,useid int," +
               //             "username varchar(10),bookname varchar(100),ISBN varchar(10),Borrowdata Datetime,ReturnData  Datetime,  state varchar(10))");
                            //查询用户借该本书的信息(处于借出状态的)
                    Cursor cursor1= database.query("Borrowbooks",null,"userName=? and user=? and bookname=? and ISBN=? and state=?",new String[]{userName,user,bookName,bookISBN,"借出"},null,null,null);
           if(cursor1.moveToNext()){
               ContentValues contentValues=new ContentValues();
               Cursor cursor2= database.query("book",null," bookname=? and ISBN=?",new String[]{bookName,bookISBN},null,null,null);
               cursor2.moveToNext();
               //还书后书名数量+宣传宣传小册子自行车才
               contentValues.put("number",(cursor2.getInt(cursor2.getColumnIndex("number"))+1)+"");
               database.update("book",contentValues,"bookname=? and ISBN=? ",new String[]{cursor1.getString(cursor1.getColumnIndex("bookname")),cursor1.getString(cursor1.getColumnIndex("ISBN"))});
              //刚新状态为已还
               ContentValues contentValues1=new ContentValues();
               contentValues1.put("state","已还");
               database.update("Borrowbooks",contentValues1,"id=?",new String[]{cursor1.getString(cursor1.getColumnIndex("id"))});

               Toast.makeText(AlsoBook.this,"还书成功",Toast.LENGTH_LONG).show();
               Intent intent=new Intent(AlsoBook.this,BookIndex.class);
               startActivity(intent);
               AlsoBook.this.finish();
           }else {
               Toast.makeText(AlsoBook.this,"书信息不正确或者该用户没有借这本书",Toast.LENGTH_LONG).show();
           }




                }





            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AlsoBook.this,ABook.class);
                startActivity(intent);
                AlsoBook.this.finish();
            }
        });
    }
    public  void find(){
        borrowBooks_UserId=findViewById(R.id.alsoBooks_UserId);
        borrowBooks_UserName=findViewById(R.id.alsoBooks_UserName);
        borrowBooks_bookISBN=findViewById(R.id.alsoBooks_bookISBN);
        borrowBooks_bookname=findViewById(R.id.alsoBooks_bookname);
        button=findViewById(R.id.alsoBooks_Button);
        button1=findViewById(R.id.toAso);
        //bookid

    }
    public void ss(){
        if(getIntent().getStringExtra("bookid")!=null){
            Cursor cursor1= database.query("Borrowbooks",new String[]{"user","username","bookname","ISBN"},"id=?",new String[]{getIntent().getStringExtra("bookid")},null,null,null);
  if(cursor1.moveToNext()){
      borrowBooks_UserId.setText(cursor1.getString(cursor1.getColumnIndex("user")));
      borrowBooks_UserName.setText(cursor1.getString(cursor1.getColumnIndex("username")));
      borrowBooks_bookISBN.setText(cursor1.getString(cursor1.getColumnIndex("ISBN")));
      borrowBooks_bookname.setText(cursor1.getString(cursor1.getColumnIndex("bookname")));
  }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //添加个好看的标题栏
        getMenuInflater().inflate(R.menu.menu1,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void onBackPressed() {
        Intent intent=new Intent(AlsoBook.this,BookIndex.class);
        startActivity(intent);
        AlsoBook.this.finish();
    }
    //顶部返回键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ////点击顶部的返回键后跳转
        ActivityUtil.ActionBarInt(item,this,BookIndex.class);
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
