package net.sinlo.bookmanage.bookmanage;

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
import android.widget.Spinner;
import android.widget.Toast;

import net.sinlo.bookmanage.bookmanage.database.MySQLiteOpenHelper;
import net.sinlo.bookmanage.bookmanage.util.ActivityUtil;
import net.sinlo.bookmanage.bookmanage.util.RegexUtil;
import net.sinlo.bookmanage.bookmanage.util.UserInfoRegexUtil;


public class AddUserActivity extends AppCompatActivity {
	private EditText nameEdit;
	private EditText passEdit;
	private EditText userNameEdit;
	private EditText userEmail;
	private EditText userContact;
	private Button registrationBtn;
	private  SQLiteDatabase database;
	private Spinner spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		findByid();
		//显示顶部返回键
		ActivityUtil.showActionBar(this);
		//禁止旋转屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		MySQLiteOpenHelper sql=new MySQLiteOpenHelper(this,"ldrbook1",null,1);
		database=sql.getWritableDatabase();
		//如果点击了registrationBtn就跳转到登陆界面
		registrationBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				int i=0;
				switch (spinner.getSelectedItem().toString()){
					case "图书管理员":
						i=1;
					break;
					case "系统管理员":
						i=2;
						break;
					case "普通用户":
						i=0;
						break;
				}
				String name=nameEdit.getText().toString().trim();
				String pwd=passEdit.getText().toString().trim();
				String userName=userNameEdit.getText().toString().trim();
				String email=userEmail.getText().toString().trim();
				String contact=userContact.getText().toString().trim();
				String manage=i+"";

				//防止用户多个用户名相同或者用户名、密码为空
				if(name.equals("")||database.rawQuery("select * from correctionuser where user='"+name+"'",null).moveToNext()){
					Toast.makeText(AddUserActivity.this, "账号为空或用户已存在！", Toast.LENGTH_SHORT).show();
				}else{
                        //使用封装起来的正则判断
					if(RegexUtil.userInfoRegex(name,pwd,userName,email,contact,AddUserActivity.this)) {
				database.execSQL("insert into correctionuser(user,password,userName,email,contact,manage) values('"+name+"','"+pwd+"','"+userName+"','"+email+"','"+contact+"','"+manage+"')");
					Intent intent=new Intent(AddUserActivity.this,IndexActivity.class);

					startActivity(intent);
					Toast.makeText(AddUserActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
					AddUserActivity.this.finish();
					}
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
		setContentView(R.layout.activit_adduser);
		nameEdit=(EditText) findViewById(R.id.add_nameEdit);
		passEdit=(EditText) findViewById(R.id.add_AgeEdit);
		registrationBtn=(Button)findViewById(R.id.add_registration);
		userNameEdit=(EditText) findViewById(R.id.add_userEdit);
		spinner=findViewById(R.id.add_userSpinner);
		userEmail=findViewById(R.id.add_userEmail);
		userContact=findViewById(R.id.add_userContact);
	}
	public void onBackPressed() {
		Intent intent=new Intent(AddUserActivity.this,IndexActivity.class);
		startActivity(intent);
		AddUserActivity.this.finish();
	}
	//顶部返回键
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		////点击顶部的返回键后跳转
		ActivityUtil.ActionBarInt(item,this,IndexActivity.class);
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

