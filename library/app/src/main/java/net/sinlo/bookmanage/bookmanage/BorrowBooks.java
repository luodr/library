package net.sinlo.bookmanage.bookmanage;

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
BorrowBooks extends AppCompatActivity {
    private EditText borrowBooks_UserId;
    private EditText borrowBooks_UserName;
    private EditText borrowBooks_bookISBN;
    private EditText borrowBooks_bookname;
    private Button button;
    private Button buttonXZuser;
    private Button buttonXZBook;
    private MySQLiteOpenHelper sql;
    private SQLiteDatabase database;
    private Intent intentBook=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrowbooks);
         sql=new MySQLiteOpenHelper(BorrowBooks.this,"ldrbook1",null,1);
         database= sql.getWritableDatabase();
        //显示顶部返回键
        ActivityUtil.showActionBar(this);
        //禁止旋转屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        find();
        buttonXZuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BorrowBooks.this,xzUserActivity.class);
                intent.putExtra("bookid",intentBook.getStringExtra("bookid"));
                startActivity(intent);
                BorrowBooks.this.finish();
            }
        });
        buttonXZBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BorrowBooks.this,XZBook.class);
                intent.putExtra("userid",intentBook.getStringExtra("userid"));
                startActivity(intent);

                BorrowBooks.this.finish();
            }
        });
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
                        //使用正则表达式判断输入的格式
                    if(RegexUtil.userAndBookInfoRegex(user,userName,bookName,bookISBN,BorrowBooks.this)){
                    //查询借书表中是否有该用户借该本书的记录(状态是借出的)
                    Cursor cursorBbook= database.query("Borrowbooks",null,"user=? and username=? and bookname=? and ISBN=? and state=?",new String[]{user,userName,bookName,bookISBN,"借出"},null,null,null);
                //如果同一个用户已经借了同一本书就不能继续借
                    if (cursorBbook.moveToNext()){
                        Toast.makeText(BorrowBooks.this,"你已经借了同一本书！请先还书哦！",Toast.LENGTH_LONG).show();
            }    else {    Cursor cursor= database.query("correctionuser",new String[]{"userName","user"},"userName=? and user=?",new String[]{userName,user},null,null,null);
                    boolean isUser=false;
                    boolean isBook=false;
                    if(cursor.moveToNext()){
                        Toast.makeText(BorrowBooks.this,"借书人正确",Toast.LENGTH_LONG).show();
                        isUser=true;
                    }else{
                        Toast.makeText(BorrowBooks.this,"借书人不正确",Toast.LENGTH_LONG).show();
                    }
                    String bookId=null;
                    Cursor cursor1= database.query("book",new String[]{"id,brief","number","authorb","dateofpublication"},"bookname=? and ISBN=?",new String[]{bookName,bookISBN},null,null,null);
                    if (cursor1.moveToNext()){
                        bookId=cursor1.getString(cursor1.getColumnIndex("id"));
                        //  Toast.makeText(BorrowBooks.this,"书正确",Toast.LENGTH_LONG).show();;
                        brief=cursor1.getString(cursor1.getColumnIndex("brief"));
                        number=cursor1.getInt(cursor1.getColumnIndex("number"));
                        authorb=cursor1.getString(cursor1.getColumnIndex("authorb")) ;
                        dateofpublication=cursor1.getString(cursor1.getColumnIndex("dateofpublication"));
                        isBook=true;
                    }else {
                        Toast.makeText(BorrowBooks.this,"书名或者ISBN不正确!",Toast.LENGTH_LONG).show();;
                    }
                    //    Toast.makeText(BorrowBooks.this,number+"",Toast.LENGTH_LONG).show();;
                    if(number>0){
                        if(isUser&&isBook){
                            Intent intent=new Intent(BorrowBooks.this,Confirmthebook.class);
                            intent.putExtra("brief",brief);
                            intent.putExtra("number",number+"");
                            intent.putExtra("authorb",authorb);
                            intent.putExtra("dateofpublication",dateofpublication);
                            intent.putExtra("user",user);
                            intent.putExtra("bookid",bookId);
                            intent.putExtra("userName",userName);
                            intent.putExtra("bookISBN",bookISBN);
                            intent.putExtra("bookName",bookName);
                            startActivity(intent);
                            BorrowBooks.this.finish();
                        }

                  }else {
                      Toast.makeText(BorrowBooks.this,"书本数量已不足!",Toast.LENGTH_LONG).show();
                  }


                    }
                }}
            });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //添加个好看的标题栏
        getMenuInflater().inflate(R.menu.menu1,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public  void find(){
        borrowBooks_UserId=findViewById(R.id.borrowBooks_UserId);
        borrowBooks_UserName=findViewById(R.id.borrowBooks_UserName);
        borrowBooks_bookISBN=findViewById(R.id.borrowBooks_bookISBN);
        borrowBooks_bookname=findViewById(R.id.borrowBooks_bookname);
        button=findViewById(R.id.borrowBooks_Button);
        buttonXZuser=findViewById(R.id.xzuser);
        buttonXZBook=findViewById(R.id.xzbook);
        intentBook=getIntent();
        if(intentBook.getStringExtra("userid")!=null){
            Cursor cursor= database.query("correctionuser",new String[]{"userName","user"}," id=?",new String[]{intentBook.getStringExtra("userid")},null,null,null);
if(cursor.moveToNext()){
    borrowBooks_UserId.setText(cursor.getString(cursor.getColumnIndex("user")));
    borrowBooks_UserName.setText(cursor.getString(cursor.getColumnIndex("userName")));
}

        }
        if(intentBook.getStringExtra("bookid")!=null) {
            Cursor cursor1= database.query("book",new String[]{"bookname","ISBN"},"id=?",new String[]{intentBook.getStringExtra("bookid")},null,null,null);
            if (cursor1.moveToNext()) {
                borrowBooks_bookISBN.setText(cursor1.getString(cursor1.getColumnIndex("ISBN")));
                borrowBooks_bookname.setText(cursor1.getString(cursor1.getColumnIndex("bookname")));
            }
        }
    }
    public void onBackPressed() {
        Intent intent=new Intent(BorrowBooks.this,BookIndex.class);
        startActivity(intent);
        BorrowBooks.this.finish();
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
