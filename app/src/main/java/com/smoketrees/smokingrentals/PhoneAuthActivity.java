package com.smoketrees.smokingrentals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuthActivity extends AppCompatActivity {

    Button request, verify;
    EditText phone, otp;
    String code, mVerificationId;
    TextView heading;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        request=findViewById(R.id.request_btn);
        verify=findViewById(R.id.verify_btn);
        phone=findViewById(R.id.phone_number);
        otp=findViewById(R.id.otp);
        heading=findViewById(R.id.head);

        animate();

        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                code=phoneAuthCredential.getSmsCode();
                Toast.makeText(PhoneAuthActivity.this, code, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(PhoneAuthActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };

       request.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               PhoneAuthProvider.getInstance().verifyPhoneNumber(
                       phone.getText().toString().trim(),
                       60,
                       TimeUnit.SECONDS,
                       PhoneAuthActivity.this,
                       mCallbacks
               );
           }
       });

       verify.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(otp.getText().toString().trim().equals(code))
                   Toast.makeText(PhoneAuthActivity.this, "Verified", Toast.LENGTH_SHORT).show();
           }
       });

    }

    void animate(){
        final Animation animation= AnimationUtils.loadAnimation(this, R.anim.lefttoright);
        final Animation animation2= AnimationUtils.loadAnimation(this, R.anim.bounce);

        heading.setAnimation(animation2);
        heading.setVisibility(View.VISIBLE);
        phone.setAnimation(animation);
        phone.setVisibility(View.VISIBLE);
        request.setAnimation(animation);
        request.setVisibility(View.VISIBLE);
        otp.setAnimation(animation);
        otp.setVisibility(View.VISIBLE);
        verify.setAnimation(animation);
        verify.setVisibility(View.VISIBLE);

    }
}
