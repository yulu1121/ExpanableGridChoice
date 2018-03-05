package com.anshi.expanablegridchoice;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.category_drawer_layout);
        getSupportFragmentManager().beginTransaction().add(R.id.category_container, CategoryDrawerFragment.getInstance()).commit();
    }

    public void openDrawer(View view) {
        mDrawerLayout.openDrawer(Gravity.END);
    }
}
