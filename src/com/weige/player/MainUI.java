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
        requestWindowFeature(Window.FEATURE_NO_TITLE);//�Ƴ�����
        setContentView(R.layout.main);//����������
        
        Toast.makeText(this, "������", 0).show();
        
        //initFragment();
    }

//	private void initFragment() {
//		FragmentManager fm = getSupportFragmentManager();//��ȡFragment����������
//		FragmentTransaction ft = fm.beginTransaction();//�������񣬻�ȡ�������������
//		ft.replace(R.id.fl_main, new HomeFragment());//��������Ĳ�����ӵ�fl_main
//		ft.commit();//�ύ����
//	}
    
}
