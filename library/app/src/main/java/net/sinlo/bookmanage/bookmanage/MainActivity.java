package net.sinlo.bookmanage.bookmanage;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.sinlo.bookmanage.bookmanage.database.MySQLiteOpenHelper;

public class MainActivity extends Activity {
	private EditText nameEdit;
	private EditText AgeEdit;
	private Button loginBtn;
	private TextView registrationBtn;
	private CheckBox checkBox;
	private  SQLiteDatabase database;
	private Editor editor;
	private TextView zhmm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//禁止旋转屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.activity_main);
		findByid();
		//禁止旋转屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		SharedPreferences sb= getSharedPreferences("info", MODE_PRIVATE);
		editor=sb.edit();
		//初始化 用户名和密码，复选框
		nameEdit.setText(sb.getString("user", ""));
		AgeEdit.setText(sb.getString("password", ""));
		checkBox.setChecked(sb.getBoolean("checkBox1", false));

		MySQLiteOpenHelper sql=new MySQLiteOpenHelper(MainActivity.this,"ldrbook1",null,1);

		database=sql.getWritableDatabase();
		Cursor curso=database.query("correctionuser",null,null,null,null,null,null);
		//如果没有数据就初始化
		if(!curso.moveToNext()){
			//添加一个系统管理员
			database.execSQL("insert into correctionuser(user,password,manage,userName) values('"+"admin"+"','"+"admin"+"','"+"2"+"','admin')");
			//和两个图书管理员
			database.execSQL("insert into correctionuser(user,password,manage,userName) values('"+"book"+"','"+"book"+"','"+"1"+"','book')");
			database.execSQL("insert into correctionuser(user,password,manage,userName) values('"+"luodongrong"+"','"+"luodongrong "+"','"+"1"+"','ldr')");
			//初始化几门书
			database.execSQL("insert into book(bookname,ISBN,brief,number,dateofpublication,authorb) values('安卓基础','1234567891','带你入门安卓',10,'2018-5-5','罗东荣')");
			database.execSQL("insert into book(bookname,ISBN,brief,number,dateofpublication,authorb) values('java基础','1234567821','带你入门java',10,'2015-2-5','罗东荣')");
			database.execSQL("insert into book(bookname,ISBN,brief,number,dateofpublication,authorb) values('mysql基础','1234567391','带你入门mysql',10,'2012-3-5','罗东荣')");
			database.execSQL("insert into book(bookname,ISBN,brief,number,dateofpublication,authorb) values('webgl基础','1234567851','带你入门webgl',10,'2014-2-1','罗东荣')");
			database.execSQL("insert into book(bookname,ISBN,brief,number,dateofpublication,authorb) values('webgl','1234267851','精通webgl',10,'2014-2-1','罗东荣')");

			Intent intent=new Intent(MainActivity.this,InitActivity.class);
			startActivity(intent);
			MainActivity.this.finish();
		}

		loginBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String user=nameEdit.getText().toString().trim();
				String password=AgeEdit.getText().toString().trim();

				String sql="select * from correctionuser where user='"+user+"' and password='"+password+"'";

				Cursor cursor=database.rawQuery(sql,null);
				if(cursor.moveToNext()){//只要查询到数据证明用户名，密码正确   ，并且只会有一条数据 因为user是唯一的

					//向本地保存用户名，密码，复选框
					if(checkBox.isChecked())
					{
						editor.putString("user",user);
						editor.putString("password",password);
						editor.putBoolean("checkBox1",true);
						editor.commit();
					}else{
						//清空
						editor.clear();
						editor.commit();
					}

			try{
				Integer i=	cursor.getInt(cursor.getColumnIndex("manage"));

				if(i==2){
					Intent intent=new Intent(MainActivity.this,IndexActivity.class);
					startActivity(intent);
					MainActivity.this.finish();
				}else if(i==1){
					Intent intent=new Intent(MainActivity.this,BookIndex.class);
					startActivity(intent);
					MainActivity.this.finish();
				}else {
					Toast.makeText(MainActivity.this, "普通用户不能登录！", Toast.LENGTH_SHORT).show();
				}

			}catch (Exception e){
				//Toast.makeText(MainActivity.this, "普通用户不能登录！", Toast.LENGTH_SHORT).show();

			}

				}else{
					Toast.makeText(MainActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
				}
			}

		});

		//如果点击注册
		registrationBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
         Toast.makeText(MainActivity.this,"暂时不支持用户注册,请联系管理员注册!",Toast.LENGTH_LONG).show();
			}
		});
		zhmm.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this,"暂时不支持用户修改密码,请联系管理员修改!",Toast.LENGTH_LONG).show();
			}
		});
	}



	//查找组件的Id
	public void findByid(){
		zhmm=findViewById(R.id.zhmm);
		nameEdit=(EditText) findViewById(R.id.main_nameEdit);
		AgeEdit=(EditText) findViewById(R.id.main_AgeEdit);
		loginBtn=(Button)findViewById(R.id.mian_login);
		registrationBtn=(TextView)findViewById(R.id.mian_registration);
		checkBox=(CheckBox)findViewById(R.id.mina_check);
	}


	/*AlertDialog.Builder builder  = new Builder(MainActivity.this);
; */

	//onDestroy 关闭数据库
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//	MainActivity.this.finish();
		database.close();

	}

}
