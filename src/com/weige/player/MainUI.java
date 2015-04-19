package com.weige.player;

import com.weige.player.fragment.HomeFragment;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.Window;
import android.widget.Toast;

public class MainUI extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//移除标题
        setContentView(R.layout.main);//设置主界面
        
        Toast.makeText(this, "主界面", 0).show();
        
        //initFragment();
    }

//	private void initFragment() {
//		FragmentManager fm = getSupportFragmentManager();//获取Fragment管理器对象
//		FragmentTransaction ft = fm.beginTransaction();//开启事务，获取事务管理器对象
//		ft.replace(R.id.fl_main, new HomeFragment());//把主界面的布局添加到fl_main
//		ft.commit();//提交事务
//	}
    
}
