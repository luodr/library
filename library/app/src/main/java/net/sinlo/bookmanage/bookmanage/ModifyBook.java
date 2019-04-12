package net.sinlo.bookmanage.bookmanage;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.sinlo.bookmanage.bookmanage.database.MySQLiteOpenHelper;
import net.sinlo.bookmanage.bookmanage.util.ActivityUtil;
import net.sinlo.bookmanage.bookmanage.util.RegexUtil;


public class ModifyBook extends AppCompatActivity {
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
		setContentView(R.layout.book_modify);
		//禁止旋转屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//显示顶部返回键
		ActivityUtil.showActionBar(this);
		findByid();
		MySQLiteOpenHelper sql=new MySQLiteOpenHelper(this,"ldrbook1",null,1);
		database=sql.getWritableDatabase();
String id=getIntent().getStringExtra("id");


		Cursor co = database.query("book", null, "id=?", new String[]{id}, null, null, null);
       while (co.moveToNext()){
		   modify_nameEdit.setText(co.getString(co.getColumnIndex("bookname")));
		   passEdit.setText(co.getString(co.getColumnIndex("ISBN")));
		   userNameEdit.setText(co.getString(co.getColumnIndex("dateofpublication")));
		   modify_userEmail.setText(co.getString(co.getColumnIndex("number")));
		   modify_userContact.setText(co.getString(co.getColumnIndex("brief")));
		   modify_bookZZ.setText(co.getString(co.getColumnIndex("authorb")));
	   }

		deleteBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				database.delete("book","id=?",new String[]{getIntent().getStringExtra("id")});
				Toast.makeText(ModifyBook.this, "删除成功！跳回到图书列表", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(ModifyBook.this,BookManage.class);
				startActivity(intent);
				ModifyBook.this.finish();
			}
		});

		modify_registration.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String bookname=modify_nameEdit.getText().toString().trim();
				String ISBN=passEdit.getText().toString().trim();
				String dateofpublication=userNameEdit.getText().toString().trim();
				String number=modify_userEmail.getText().toString().trim();
				String brief=modify_userContact.getText().toString().trim();
				String authorb=modify_bookZZ.getText().toString().trim();

				if(RegexUtil.bookInfoRegex(bookname,ISBN,dateofpublication,authorb,number,brief,ModifyBook.this)) {
					ContentValues values = new ContentValues();
					values.put("bookname", bookname);
					values.put("ISBN", ISBN);
					values.put("dateofpublication", dateofpublication);
					values.put("number", number);
					values.put("brief", brief);
					values.put("authorb", authorb);
					database.update("book", values, "id=?", new String[]{getIntent().getStringExtra("id")});
					Intent intent = new Intent(ModifyBook.this, BookManage.class);
					startActivity(intent);
					ModifyBook.this.finish();
				}
			}
		});
	}



	public void findByid(){
		deleteBtn=findViewById(R.id.modify_bookdelete);
		modify_nameEdit=findViewById(R.id.modify_bookname);
		//modify_bookZZ
		modify_bookZZ=findViewById(R.id.modify_bookZZ);
		passEdit=findViewById(R.id.modify_bookISBN);
		userNameEdit=findViewById(R.id.modify_bookData);
		modify_userEmail=findViewById(R.id.modify_bookumb);
		modify_userContact=findViewById(R.id.modify_bookjj);
		modify_registration=findViewById(R.id.modify_bookxg);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//添加个好看的标题栏
		getMenuInflater().inflate(R.menu.menu1,menu);
		return super.onCreateOptionsMenu(menu);
	}
	public void onBackPressed() {
		Intent intent=new Intent(ModifyBook.this,BookManage.class);
		startActivity(intent);
		ModifyBook.this.finish();
	}
	//顶部返回键
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		////点击顶部的返回键后跳转
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

