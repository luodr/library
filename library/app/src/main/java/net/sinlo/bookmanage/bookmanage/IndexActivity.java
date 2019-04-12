package net.sinlo.bookmanage.bookmanage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import net.sinlo.bookmanage.bookmanage.database.MySQLiteOpenHelper;
import net.sinlo.bookmanage.bookmanage.util.ActivityUtil;

import java.util.ArrayList;

public class IndexActivity extends AppCompatActivity {
	private ListView ListView;

	private Button index_addUser;
	private SearchView searchView;
	private SQLiteDatabase database;
	private ArrayList<String> arrayList;
	private ArrayList<String> arrayListID;
  private  	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//显示顶部返回键
		ActivityUtil.showActionBar(this);
		setContentView(R.layout.activity_index);
		//禁止旋转屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	findId();
		arrayList = new ArrayList<String>();
		arrayListID = new ArrayList<String>();


		MySQLiteOpenHelper sql = new MySQLiteOpenHelper(IndexActivity.this, "ldrbook1", null, 1);
		database = sql.getWritableDatabase();
		//查询所有用户
		findAllUser(null,null,null);

		//点击事件   点击用户跳转到修改用户页面
		ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

					String id = arrayListID.get(i);
					Cursor co = database.query("correctionuser", null, "id=?", new String[]{id}, null, null, null);
					if (co.moveToNext()) {
						//Toast.makeText(IndexActivity.this, indexSearchView.getText().toString().trim(), Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(IndexActivity.this, ModifyActivity.class);
						intent.putExtra("id", id);
						startActivity(intent);
						IndexActivity.this.finish();
						//	IndexActivity.this.finish();
					}

			}
		});
		//ListView.setOnLongClickListener();
		index_addUser.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(IndexActivity.this, AddUserActivity.class);
				startActivity(intent);
				IndexActivity.this.finish();
			}
		});

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		//获取搜索的菜单组件
		MenuItem menuItem = menu.findItem(R.id.search);

		searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

		//设置搜索的事件
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				boolean  isExist= findAllUser(null,"id like " +
						"? or user like ? or userName like ?", new String[]{"%"+query+"%","%"+query+"%","%"+query+"%"});
			//如果没有查询到用户信息....
			if(!isExist){
				Toast t = Toast.makeText(IndexActivity.this, "没有查询到用户!", Toast.LENGTH_SHORT);
				t.setGravity(Gravity.TOP, 0, 0);
				t.show();
			}
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
                //搜索框把搜索信息清空时,显示所有用户
				if(newText.trim().equals("")){

					boolean  isExist= findAllUser(null,null,null);
				}

				return false;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}
      //查询用户并配置到listview上
	public boolean findAllUser(String[] columns, String selection, String[] selectionArge){
		//定义两个临时的ArrayList避免没有查询结果也清空listview,因为我会提示没有查询到结果...
		ArrayList<String> arrayinfo=new ArrayList<String>();
		ArrayList<String>  arrayID=new ArrayList<String>();

		Cursor cursor = database.query("correctionuser", columns, selection, selectionArge, null, null, null);
	//	arrayinfo.add("id \t 用户名  \t 密码");
		while (cursor.moveToNext()) {
			arrayinfo.add(" I   D  :   "+cursor.getInt(0) + "  \n 用户名:" + cursor.getString(1) + "  \n 密    码:" + cursor.getString(2)+"\n 姓    名:"+cursor.getString(3));
			arrayID.add(cursor.getInt(0) + "");

		}
		if(arrayID.size()>0){
			//如果有查询到用户信息就重新适配ArrayAdapter
			this.arrayList=arrayinfo;
			this.arrayListID=arrayID;  //存放Id的点击用
			adapter = new ArrayAdapter<String>(IndexActivity.this, R.layout.layout_tv, this.arrayList);
			ListView.setAdapter(adapter);
			return true;
		}
		return false;
	}
public  void findId(){
	ListView = (ListView) findViewById(R.id.index_lv);

	index_addUser = findViewById(R.id.index_addUser);
}
   //重写返回键
	public void onBackPressed() {
		//Intent intent=new Intent(IndexActivity.this,BorrowBooks.class);
	//	startActivity(intent);
		//IndexActivity.this.finish();
		AlertDialog.Builder builder = new AlertDialog.Builder(IndexActivity.this);
		builder.setTitle("退出" ) ;
		builder.setMessage("你确定要退出APP吗？" ) ;
		builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
IndexActivity.this.finish();
			}
		});
		builder.setNegativeButton("否", null);

		builder.show();


	}
	//顶部返回键
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				AlertDialog.Builder builder = new AlertDialog.Builder(IndexActivity.this);
				builder.setTitle("退出" ) ;
				builder.setMessage("你确定要退出APP吗？" ) ;
				builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						IndexActivity.this.finish();
					}
				});
				builder.setNegativeButton("否", null);

				builder.show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
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

