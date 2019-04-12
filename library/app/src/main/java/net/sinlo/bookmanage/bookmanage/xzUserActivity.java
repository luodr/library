package net.sinlo.bookmanage.bookmanage;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import net.sinlo.bookmanage.bookmanage.database.MySQLiteOpenHelper;
import net.sinlo.bookmanage.bookmanage.util.ActivityUtil;

import java.util.ArrayList;

public class xzUserActivity extends AppCompatActivity {
	private ListView ListView;

	private Button index_addUser;

	private SQLiteDatabase database;
	private ArrayList<String> arrayList;
	private ArrayList<String> arrayListID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xzuser);
		ListView=(ListView)findViewById(R.id.xzuser_lv);
		//显示顶部返回键
		ActivityUtil.showActionBar(this);
		//禁止旋转屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	//	index_addUser=findViewById(R.id.index_addUser);
		MySQLiteOpenHelper sql=new MySQLiteOpenHelper(xzUserActivity.this,"ldrbook1",null,1);
		database=sql.getWritableDatabase();
		arrayList=new ArrayList<String>();
		arrayListID=new ArrayList<String>();
		Cursor cursor= database.query("correctionuser",null,null,null,null,null,null);
		arrayList.add("(单击用户进行借书哦！)");
		while(cursor.moveToNext()){
			arrayList.add("id:"+cursor.getInt(0)+"\n"+"用户名："+cursor.getString(1)+"\n姓名"+cursor.getString(3));
			arrayListID.add(cursor.getInt(0)+"");
		}
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(xzUserActivity.this, android.R.layout.simple_expandable_list_item_1,arrayList);
		ListView.setAdapter(adapter);
		ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				if(i!=0){
				String id=	arrayListID.get(i-1);
					Cursor co = database.query("correctionuser", null, "id=?", new String[]{id}, null, null, null);
					if (co.moveToNext()) {
						//Toast.makeText(IndexActivity.this, indexSearchView.getText().toString().trim(), Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(xzUserActivity.this, BorrowBooks.class);
						intent.putExtra("userid",id);
						intent.putExtra("bookid",getIntent().getStringExtra("bookid"));

						startActivity(intent);
						xzUserActivity.this.finish();
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
	public void onBackPressed() {
		Intent intent=new Intent(xzUserActivity.this,BorrowBooks.class);
		intent.putExtra("bookid",getIntent().getStringExtra("bookid"));
		startActivity(intent);
		xzUserActivity.this.finish();
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

