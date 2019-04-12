package net.sinlo.bookmanage.bookmanage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import net.sinlo.bookmanage.bookmanage.util.ActivityUtil;

/**
 * Created by hello on 2018/6/17.
 */

public class BookIndex extends AppCompatActivity {
    private Button borrowBooks;
    private Button alsoBooks;
    private Button bookManageB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_manage);
        //显示返回键
        ActivityUtil.showActionBar(this);

        //禁止旋转屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        find();
        borrowBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookIndex.this, BorrowBooks.class);
                startActivity(intent);
                BookIndex.this.finish();
            }
        });
        alsoBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookIndex.this, AlsoBook.class);
                startActivity(intent);
                BookIndex.this.finish();
            }
        });
        bookManageB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookIndex.this, BookManage.class);
                startActivity(intent);
                BookIndex.this.finish();
            }
        });
    }
    public void find(){
        borrowBooks=findViewById(R.id.BorrowBookBootn);
        alsoBooks=findViewById(R.id.alsoBooks)  ;
        bookManageB=findViewById(R.id.bookManageB);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //添加个好看的标题栏
        getMenuInflater().inflate(R.menu.menu1,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //重写返回键
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(BookIndex.this);
        builder.setTitle("退出" ) ;
        builder.setMessage("你确定要退出APP吗？" ) ;
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BookIndex.this.finish();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(BookIndex.this);
                builder.setTitle("退出" ) ;
                builder.setMessage("你确定要退出APP吗？" ) ;
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BookIndex.this.finish();
                    }
                });
                builder.setNegativeButton("否", null);

                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
