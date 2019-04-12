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
import android.widget.Spinner;
import android.widget.Toast;

import net.sinlo.bookmanage.bookmanage.database.MySQLiteOpenHelper;
import net.sinlo.bookmanage.bookmanage.util.ActivityUtil;
import net.sinlo.bookmanage.bookmanage.util.RegexUtil;
import net.sinlo.bookmanage.bookmanage.util.UserInfoRegexUtil;


public class ModifyActivity extends AppCompatActivity {
	private EditText modify_nameEdit;
	private EditText passEdit;
	private EditText userNameEdit;
	private EditText modify_userEmail;
	private EditText modify_userContact;
   private Spinner spinner;

	private Button modify_fanhui;
	private Button modify_registration;
	private Button deleteBtn;
	private  SQLiteDatabase database;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activit_modify);
		//显示顶部返回键
		ActivityUtil.showActionBar(this);
		//禁止旋转屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		findByid();
		MySQLiteOpenHelper sql=new MySQLiteOpenHelper(this,"ldrbook1",null,1);
		database=sql.getWritableDatabase();
String id=getIntent().getStringExtra("id");
/*List list=new ArrayList();
		list.add("图书管理员");
		list.add("系统管理员");*/

		Cursor co = database.query("correctionuser", null, "id=?", new String[]{id}, null, null, null);
       while (co.moveToNext()){
       	//获取用户的所有信息
		   modify_nameEdit.setText(co.getString(co.getColumnIndex("user")));
		   passEdit.setText(co.getString(co.getColumnIndex("password")));
		   userNameEdit.setText(co.getString(co.getColumnIndex("userName")));
		   modify_userEmail.setText(co.getString(co.getColumnIndex("email")));
		   modify_userContact.setText(co.getString(co.getColumnIndex("contact")));
		   //这个是用户的权限,让其首先显示的是用户当前的权限,否则可能会造成误改
		   spinner.setSelection(Integer.parseInt(co.getString(co.getColumnIndex("manage"))));
	   }

		deleteBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(!getIntent().getStringExtra("id").trim().equals("1")&&!getIntent().getStringExtra("id").trim().equals("2")) {
					database.delete("correctionuser", "id=?", new String[]{getIntent().getStringExtra("id")});
					Toast.makeText(ModifyActivity.this, "删除成功！跳回到用户列表", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(ModifyActivity.this, IndexActivity.class);
					startActivity(intent);
					ModifyActivity.this.finish();
				}else {
					Toast.makeText(ModifyActivity.this,"admin用户和book用户不可删除哦,你可以修改它",Toast.LENGTH_LONG).show();
				}
			}
		});

		modify_registration.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
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

			 ContentValues values = new ContentValues();
                  String   user=modify_nameEdit.getText().toString().trim();
                  String password=passEdit.getText().toString().trim();
                  String userName=userNameEdit.getText().toString().trim();
                  String email=modify_userEmail.getText().toString().trim();
                  String contact=modify_userContact.getText().toString().trim();
                  //因为有多处使用同一正则表达式所以我封装起来了
		if(RegexUtil.userInfoRegex(user,password,userName,email,contact,ModifyActivity.this)) {
			values.put("user", user);
			values.put("password", password);
			values.put("userName", userName);
			values.put("email", email);
			values.put("contact", contact);
			values.put("manage", i);

			database.update("correctionuser", values, "id=?", new String[]{getIntent().getStringExtra("id")});
			Intent intent = new Intent(ModifyActivity.this, IndexActivity.class);
			startActivity(intent);
			ModifyActivity.this.finish();
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
		deleteBtn=findViewById(R.id.modify_delete);
		modify_nameEdit=findViewById(R.id.modify_nameEdit);
		passEdit=findViewById(R.id.modify_AgeEdit);
		userNameEdit=findViewById(R.id.modify_userEdit);
		modify_userEmail=findViewById(R.id.modify_userEmail);
		modify_userContact=findViewById(R.id.modify_userContact);
		modify_registration=findViewById(R.id.modify_registration);
		spinner=findViewById(R.id.modify_userSpinner);

	}
	public void onBackPressed() {
		Intent intent=new Intent(ModifyActivity.this,IndexActivity.class);
		startActivity(intent);
		ModifyActivity.this.finish();
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

