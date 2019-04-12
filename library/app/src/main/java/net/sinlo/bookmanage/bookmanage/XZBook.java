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
import android.widget.ListView;

import net.sinlo.bookmanage.bookmanage.database.MySQLiteOpenHelper;
import net.sinlo.bookmanage.bookmanage.util.ActivityUtil;

import java.util.ArrayList;

public class XZBook extends AppCompatActivity {
	private ListView ListView;




	private SQLiteDatabase database;
	private ArrayList<String> arrayList;
	private ArrayList<String> arrayListID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xzbook);
		ListView=(ListView)findViewById(R.id.book_xz);
		//显示顶部返回键
		ActivityUtil.showActionBar(this);
		//禁止旋转屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		MySQLiteOpenHelper sql=new MySQLiteOpenHelper(XZBook.this,"ldrbook1",null,1);
		database=sql.getWritableDatabase();
		arrayList=new ArrayList<String>();
		arrayListID=new ArrayList<String>();
		Cursor cursor= database.query("book",null,null,null,null,null,null);


		arrayList.add("(单击图书信息可进行借书哦!)");
		while(cursor.moveToNext()){
			arrayListID.add(cursor.getString(cursor.getColumnIndex("id")));
			arrayList.add("id:"+cursor.getString(cursor.getColumnIndex("id"))+"\n书名:"+cursor.getString(cursor.getColumnIndex("bookname"))+"\n作者:" +
					""+cursor.getString(cursor.getColumnIndex("authorb"))+"\n"+"ISBN:"+cursor.getString(cursor.getColumnIndex("ISBN"))+"\n剩余数量:"+cursor.getString(cursor.getColumnIndex("number"))+"\n简介:"+cursor.getString(cursor.getColumnIndex("brief")));
		}
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(XZBook.this, R.layout.layout_tv,arrayList);
		ListView.setAdapter(adapter);
		//ListView.setOnLongClickListener();
		ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				if(i!=0){
					String id=arrayListID.get(i-1);
					Cursor co = database.query("book", null, "id=?", new String[]{id}, null, null, null);
					if (co.moveToNext()) {
						//Toast.makeText(IndexActivity.this, indexSearchView.getText().toString().trim(), Toast.LENGTH_SHORT).show();
						Intent intent=new Intent(XZBook.this,BorrowBooks.class);
						intent.putExtra("bookid", id);
						intent.putExtra("userid", getIntent().getStringExtra("userid"));
						startActivity(intent);
						XZBook.this.finish();
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
		Intent intent=new Intent(XZBook.this,BorrowBooks.class);
		intent.putExtra("userid", getIntent().getStringExtra("userid"));
		startActivity(intent);
		XZBook.this.finish();
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

