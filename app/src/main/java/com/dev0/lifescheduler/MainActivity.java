package com.dev0.lifescheduler;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navbar;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String actionFragmentTag = "action_fragment";
        String taskFragmentTag = "task_fragment";
        String loginFragmentTag = "login_fragment";

        navbar = findViewById(R.id.bottom_navigation);
        navbar.setOnNavigationItemSelectedListener(menuItem -> {
            FragmentManager manager = getSupportFragmentManager();

            if (currentFragment != null)
                manager.beginTransaction().hide(currentFragment).commit();

            switch (menuItem.getItemId()) {
                case R.id.action_menu_task:
                    if (manager.findFragmentByTag(taskFragmentTag) == null) {
                        currentFragment = new TaskListFragment();
                        manager.beginTransaction()
                                .add(R.id.frame_layout, currentFragment, taskFragmentTag).commit();
                    } else {
                        currentFragment = manager.findFragmentByTag(taskFragmentTag);
                        manager.beginTransaction().show(currentFragment).commit();
                    }
                    break;
                case R.id.action_menu_action:
                    if (manager.findFragmentByTag(actionFragmentTag) == null) {
                        currentFragment = new ActionListFragment();
                        manager.beginTransaction()
                                .add(R.id.frame_layout, currentFragment, actionFragmentTag).commit();
                    } else {
                        currentFragment = manager.findFragmentByTag(actionFragmentTag);
                        manager.beginTransaction().show(currentFragment).commit();
                    }
                    break;
                case R.id.action_menu_together:
                    if (manager.findFragmentByTag("login_fragment") == null) {
                        currentFragment = new LoginFragment();
                        manager.beginTransaction()
                                .add(R.id.frame_layout, currentFragment, loginFragmentTag).commit();
                    } else {
                        currentFragment = manager.findFragmentByTag(loginFragmentTag);
                        manager.beginTransaction().show(currentFragment).commit();
                    }
                    break;
            }

            return true;
        });
        if (savedInstanceState != null) {
            int selectedItemId = savedInstanceState.getInt("SelectedItemId");
            navbar.setSelectedItemId(selectedItemId);
        } else
            navbar.setSelectedItemId(R.id.action_menu_task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("SelectedItemId", navbar.getSelectedItemId());
    }
}
