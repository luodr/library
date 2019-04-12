package net.sinlo.bookmanage.bookmanage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	public MySQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
 //用户基本信息包括姓名、email、联系方式、四位数字ID号；
		 db.execSQL("CREATE TABLE IF NOT EXISTS correctionuser(id integer  primary key autoincrement,user varchar(20),password varchar(20),userName varchar(10),email varchar(10),contact varchar(10),manage int)");
		 //书名、作者、出版日期，简介，ISBN和库从数量
		db.execSQL("CREATE TABLE IF NOT EXISTS book(id integer  primary key autoincrement,bookname varchar(20),ISBN varchar(10),brief varchar(100),number int,dateofpublication varchar(10),  authorb varchar(10))");

		db.execSQL("CREATE TABLE IF NOT EXISTS Borrowbooks(id integer  primary key autoincrement,user varchar(20),username varchar(10),bookname varchar(100),ISBN varchar(10),Borrowdata Datetime,ReturnData  Datetime,  state varchar(10))");



	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
