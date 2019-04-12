package net.sinlo.bookmanage.bookmanage;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hello on 2018/6/21.
 */

public class InitActivity extends Activity {
    private Button start;
    private TextView qdTxet;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止旋转屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.layoutqd);
        start=findViewById(R.id.start);
        qdTxet=findViewById(R.id.qdTxet);
        qdTxet.setText("相信您是第一次启动这个软件\n" +
                "        我为您初始化了两个用户分别对应不同的模块:\n" +
                "        系统管理员:(用户管理)\n" +
                "                   用户名:admin\n" +
                "                   密码:   admin\n" +
                "         图书管理员:(图书管理、借书、还书)\n" +
                "                   用户名:book\n" +
                "                   密码:     book");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(InitActivity.this,MainActivity.class);
                startActivity(intent);
                InitActivity.this.finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(InitActivity.this,"您必须点我知道了!",Toast.LENGTH_LONG).show();
    }


}
