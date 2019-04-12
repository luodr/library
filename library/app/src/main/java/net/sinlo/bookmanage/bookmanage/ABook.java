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

public class ABook extends AppCompatActivity {
	private ListView ListView;




	private SQLiteDatabase database;
	private ArrayList<String> arrayList;
	private ArrayList<String> arrayListID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.abook);
		ListView=(ListView)findViewById(R.id.abook_lv);
		//显示顶部返回键
		ActivityUtil.showActionBar(this);
		//禁止旋转屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		MySQLiteOpenHelper sql=new MySQLiteOpenHelper(ABook.this,"ldrbook1",null,1);
		database=sql.getWritableDatabase();
		arrayList=new ArrayList<String>();
		arrayListID=new ArrayList<String>();
		//db.execSQL("CREATE TABLE IF NOT EXISTS Borrowbooks(id integer  primary key autoincrement,useid
		// int,username varchar(10),bookname varchar(100),ISBN varchar(10),Borrowdata Datetime,ReturnData  Datetime,  state varchar(10))");

		Cursor cursor= database.query("Borrowbooks",null,"state=?",new String[]{"借出"},null,null,null);


		arrayList.add("(单击已借出的书进行还书)");
		while(cursor.moveToNext()){
			arrayListID.add(cursor.getString(cursor.getColumnIndex("id")));
			arrayList.add("id:"+cursor.getString(cursor.getColumnIndex("id"))+"\n书名:"+cursor.getString(cursor.getColumnIndex("bookname"))+
					"\nISBN:"+cursor.getString(cursor.getColumnIndex("ISBN"))+"\n借书用户:" +
					""+cursor.getString(cursor.getColumnIndex("username"))+"\n用户id:"+cursor.getString(cursor.getColumnIndex("user")));
		}
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(ABook.this, R.layout.layout_tv,arrayList);
		ListView.setAdapter(adapter);
		//ListView.setOnLongClickListener();
		ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				if(i!=0){
					String id=arrayListID.get(i-1);
					Cursor co = database.query("Borrowbooks", null, "id=?", new String[]{id}, null, null, null);
					Intent intent=new Intent(ABook.this,AlsoBook.class);
					intent.putExtra("bookid", id);
					startActivity(intent);
					ABook.this.finish();
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
	@Override
	public void onBackPressed() {
Intent intent=new Intent(ABook.this,AlsoBook.class);
		startActivity(intent);
		ABook.this.finish();
	}
	//顶部返回键
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		////点击顶部的返回键后跳转
		ActivityUtil.ActionBarInt(item,this,AlsoBook.class);
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

