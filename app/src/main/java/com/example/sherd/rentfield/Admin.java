package com.example.sherd.rentfield;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    FloatingActionButton fab,fab1,fab2;
    Animation fab_open,fab_close,fab_rotclock,fab_rotanti;
    boolean isOpen = false;
    List<Field_list> field_list = new ArrayList<Field_list>();
    ListAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        fab = (FloatingActionButton)findViewById(R.id.fab_button);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        fab_rotclock = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        fab_rotanti = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlock);
        fab.setClickable(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){
                    fab1.startAnimation(fab_close);
                    fab2.startAnimation(fab_close);

                    fab.setAnimation(fab_rotanti);
                    fab1.setClickable(false);
                    fab2.setClickable(false);

                    isOpen = false;
                }
                else{
                    fab1.startAnimation(fab_open);
                    fab2.startAnimation(fab_open);

                    fab.setAnimation(fab_rotclock);
                    fab1.setClickable(true);
                    fab2.setClickable(true);

                    isOpen = true;
                }
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.add_field);


            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Profile_user.class);
                startActivity(i);
            }
        });


        ///////////////////////////////////////////////////////listView
        Field_list field1 = new Field_list(1,R.drawable.addphoto,"Maks","5000","87759771615","SDU",158,35);
        Field_list field2 = new Field_list(1,R.drawable.addfield,"Maksat","15000","87759771615","SDU",158,35);
        field_list.add(field1);
        field_list.add(field2);
        field_list.add(field1);
        field_list.add(field2);
        field_list.add(field1);
        field_list.add(field2);
        field_list.add(field2);
        field_list.add(field1);
        field_list.add(field2);
        ListView tab1_list = (ListView)findViewById(R.id.admin_list);
        customAdapter = new ListAdapter(this, R.layout.list_item,field_list);
        tab1_list.setAdapter(customAdapter);
    }
}
