package net.sinlo.bookmanage.bookmanage;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
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

public class BookManage extends AppCompatActivity {
	private ListView ListView;
	private SearchView searchView;
	private Button addBook;

	private SQLiteDatabase database;
	private ArrayList<String> arrayList;
	private ArrayList<String> arrayListID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.bookmanage);
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);// 标题栏的布局
		findId();
		//显示顶部返回键
		ActivityUtil.showActionBar(this);
		//禁止旋转屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		MySQLiteOpenHelper sql=new MySQLiteOpenHelper(BookManage.this,"ldrbook1",null,1);
		database=sql.getWritableDatabase();
		findBookInfo(null,null,null);
		//点击
		ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

					String id=arrayListID.get(i);
					Cursor co = database.query("book", null, "id=?", new String[]{id}, null, null, null);
					if (co.moveToNext()) {
						//Toast.makeText(IndexActivity.this, indexSearchView.getText().toString().trim(), Toast.LENGTH_SHORT).show();
						Intent intent=new Intent(BookManage.this,ModifyBook.class);
						intent.putExtra("id", id);
						startActivity(intent);
						BookManage.this.finish();

				}
			}
		});


		addBook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(BookManage.this,AddBook.class);
				startActivity(intent);
				BookManage.this.finish();
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//使用菜单填充器获取menu下的菜单资源文件
		getMenuInflater().inflate(R.menu.menu,menu);
		//获取搜索的菜单组件
		MenuItem menuItem = menu.findItem(R.id.search);
		searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
		//设置搜索的事件
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
                        //搜索包含  书名|ISBN|作者/简介
				findBookInfo(null,"id like ? or bookname like ? or ISBN like ? or brief like ? or authorb like ?",new String[]{
						"%"+query+"%","%"+query+"%","%"+query+"%","%"+query+"%","%"+query+"%"
				});
				return false;
			}
			@Override
			public boolean onQueryTextChange(String newText) {
				//搜索框把搜索信息清空时,显示所有图书 
				if(newText.trim().equals("")){

					boolean  isExist= findBookInfo(null,null,null);
				}
				return false;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}
	//顶部返回键
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//跳转
		ActivityUtil.ActionBarInt(item,this,BookIndex.class);
		return super.onOptionsItemSelected(item);
	}
	//查询book表图书信息并适配到ListView
	public boolean findBookInfo(String[] colums,String  selection,String [] selectionArgs){
   //l临时存放,避免查询不到信息有清空了List
	ArrayList<String> arrayBookInfo=new ArrayList<String>();
		ArrayList arrayID=new ArrayList<String>();
		//查询所有的图书信息
		Cursor cursor= database.query("book",colums,selection,selectionArgs,null,null,null);
		while(cursor.moveToNext()){
			arrayID.add(cursor.getString(cursor.getColumnIndex("id")));
			arrayBookInfo.add("id:"+cursor.getString(cursor.getColumnIndex("id"))+"\n书名:"+cursor.getString(cursor.getColumnIndex("bookname"))+"\n作者:" +
					""+cursor.getString(cursor.getColumnIndex("authorb"))+"\n"+"ISBN:"+cursor.getString(cursor.getColumnIndex("ISBN"))+"\n剩余数量:"+cursor.getString(cursor.getColumnIndex("number"))+"\n简介:"+cursor.getString(cursor.getColumnIndex("brief")));
		}
	if(arrayID.size()>0){
		//把查询得到的图书信息适配到ArrayAdapter
		this.arrayList=arrayBookInfo;
		this.arrayListID=arrayID;
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(BookManage.this, R.layout.layout_tv,arrayList);
		ListView.setAdapter(adapter);
		return true;
	}
		return false;
	}
	public void findId(){
		ListView=(ListView)findViewById(R.id.book_lv);
		addBook=findViewById(R.id.addBook);
	}
	public void onBackPressed() {
		Intent intent=new Intent(BookManage.this,BookIndex.class);
		startActivity(intent);
		BookManage.this.finish();
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

