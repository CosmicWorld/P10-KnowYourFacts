package com.example.a15017381.p10_knowyourfacts;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Fragment> al;
    MyFragmentPagerAdapter adapter;
    ViewPager vp;
    Button btnChange, btnRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = (ViewPager) findViewById(R.id.viewpager1);
        btnChange = (Button) findViewById(R.id.btnChange);
        btnRead = (Button) findViewById(R.id.btnRead);

        FragmentManager fm = getSupportFragmentManager();

        al = new ArrayList<Fragment>();
        al.add(new Frag1());
        al.add(new Frag2());
        al.add(new Frag3());

        adapter = new MyFragmentPagerAdapter(fm, al);

        vp.setAdapter(adapter);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int a = random.nextInt(255);
                int b = random.nextInt(255);
                int c = random.nextInt(255);
                vp.setBackgroundColor(Color.rgb(a,b,c));
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 20);

                Intent intent = new Intent(MainActivity.this,
                        TaskBroadcastReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        MainActivity.this, 123,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);

                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);

                Intent returnIntent = new Intent();
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.previous:
                if (vp.getCurrentItem() > 0){
                    int previousPage = vp.getCurrentItem() - 1;
                    vp.setCurrentItem(previousPage, true);
                }
                return true;
            case R.id.random:
                int max = al.size();
                Random random = new Random();
                int a = random.nextInt(max);
                vp.setCurrentItem(a,true);
                return true;
            case R.id.next:
                int max2 = vp.getChildCount();
                if (vp.getCurrentItem() < max2-1){
                    int nextPage = vp.getCurrentItem() + 1;
                    vp.setCurrentItem(nextPage, true);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("frag",vp.getCurrentItem());
        Drawable background = vp.getBackground();
        if (background instanceof ColorDrawable) {
            int a = ((ColorDrawable) background).getColor();
            editor.putInt("color",a);
        }
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        int highScore = sharedPref.getInt("frag",1);
        int color = sharedPref.getInt("color",Color.rgb(211,211,211));
        vp.setCurrentItem(highScore);
        vp.setBackgroundColor(color);
    }
}
