package com.flexible.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.flexible.toolbar.library.FlexibleToolBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlexibleToolBar mToolbar = findViewById(R.id.biz_toolbar);
        mToolbar.setToolBarTitle("FlexibleToolBar");
        mToolbar.setToolBarButton(FlexibleToolBar.GB_LEFT, android.R.drawable.ic_menu_add, 1, 20, 0);
        mToolbar.setToolBarButton(FlexibleToolBar.GB_LEFT, "Add", 2,10,0);
        mToolbar.setToolBarButton(FlexibleToolBar.GB_RIGHT, "Cancel", 1,0,20);

        mToolbar.setOnToolBarClickListener(new FlexibleToolBar.BizToolBarListener() {
            @Override
            public void onToolBarClicked(int groupButton, int id) {
                switch (groupButton) {
                    case FlexibleToolBar.GB_LEFT:
                        if (id == 1) Toast.makeText(MainActivity.this, "Menu Add Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case FlexibleToolBar.GB_RIGHT:
                        if (id == 1) Toast.makeText(MainActivity.this, "Cancel Clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}
