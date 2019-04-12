package net.sinlo.bookmanage.bookmanage;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import net.sinlo.bookmanage.bookmanage.database.MySQLiteOpenHelper;
import net.sinlo.bookmanage.bookmanage.util.ActivityUtil;
import net.sinlo.bookmanage.bookmanage.util.RegexUtil;


public class AddBook extends AppCompatActivity {
	private EditText modify_nameEdit;
	private EditText passEdit;
	private EditText userNameEdit;
	private EditText modify_userEmail;
	private EditText modify_userContact;
   private EditText modify_bookZZ;
	private Button modify_registration;
	private Button deleteBtn;
	private  SQLiteDatabase database;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addbook);
		//显示顶部返回键
		ActivityUtil.showActionBar(this);
		findByid();
		//禁止旋转屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		MySQLiteOpenHelper sql=new MySQLiteOpenHelper(this,"ldrbook1",null,1);
		database=sql.getWritableDatabase();
		modify_registration.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String bookname=modify_nameEdit.getText().toString().trim();
				String ISBN=passEdit.getText().toString().trim();
				String dateofpublication=userNameEdit.getText().toString().trim();
				String number=modify_userEmail.getText().toString().trim();
				String brief=modify_userContact.getText().toString().trim();
				String authorb=modify_bookZZ.getText().toString().trim();
				//使用封装好的正则表达判断
		if(RegexUtil.bookInfoRegex(bookname,ISBN,dateofpublication,authorb,number,brief,AddBook.this)) {
			ContentValues values = new ContentValues();
			values.put("bookname", bookname);
			values.put("ISBN", ISBN);
			values.put("dateofpublication", dateofpublication);
			values.put("number", number);
			values.put("brief", modify_userContact.getText().toString().trim());
			values.put("authorb", modify_bookZZ.getText().toString().trim());


			database.insert("book", null, values);
			Intent intent = new Intent(AddBook.this, BookManage.class);
			startActivity(intent);
			AddBook.this.finish();
		}
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//添加个好看的标题栏
		getMenuInflater().inflate(R.menu.menu1,menu);
		return super.onCreateOptionsMenu(menu);
	}
	public void findByid(){

		modify_nameEdit=findViewById(R.id.add_bookname);

		modify_bookZZ=findViewById(R.id.add_bookZZ);
		passEdit=findViewById(R.id.add_bookISBN);
		userNameEdit=findViewById(R.id.add_bookData);
		modify_userEmail=findViewById(R.id.add_bookumb);
		modify_userContact=findViewById(R.id.add_bookjj);
		modify_registration=findViewById(R.id.add_bookxg);

	}
	public void onBackPressed() {
		Intent intent=new Intent(AddBook.this,BookManage.class);
		startActivity(intent);
		AddBook.this.finish();
	}
	//顶部返回键
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//点击顶部的返回键后跳转
		ActivityUtil.ActionBarInt(item,this,BookManage.class);
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

