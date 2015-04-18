package com.weige.player;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.widget.Toast;

public class MainUI extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//移除标题
        setContentView(R.layout.main);
        
        Toast.makeText(this, "我是主界面", 0).show();
    }
    
}
