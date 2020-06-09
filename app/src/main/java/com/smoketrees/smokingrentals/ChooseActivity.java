package com.smoketrees.smokingrentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class ChooseActivity extends AppCompatActivity {

    Button rentee, renter;
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        rentee=findViewById(R.id.rentee);
        renter=findViewById(R.id.renter);
        heading=findViewById(R.id.Heading);

        final Animation animation1=AnimationUtils.loadAnimation(this, R.anim.lefttoright);
        final Animation animation2=AnimationUtils.loadAnimation(this, R.anim.lefttoright);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                heading.setAnimation(animation2);
                heading.setVisibility(View.VISIBLE);
            }
        }, 200);

        Handler handler1=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rentee.setAnimation(animation1);
                renter.setAnimation(animation1);

                rentee.setVisibility(View.VISIBLE);
                renter.setVisibility(View.VISIBLE);
            }
        }, 200);

        renter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChooseActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }
}
