package com.example.alarm_test;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스 (서버연동)
    private EditText mEtName, EmtPwd;  // 로그인 입력필드
    private AlarmManager alarmManager;
    private GregorianCalendar mCalender;

    private NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("alarm");

        mEtName = findViewById(R.id.et_email);
        EmtPwd = findViewById(R.id.et_pwd);

        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        mCalender = new GregorianCalendar();
        NotificationCompat.Builder builder;
        Button btn_login  =findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인 요청
                String strEmail = mEtName.getText().toString();
                String strPwd = EmtPwd.getText().toString();
                if (mEtName.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                    mEtName.requestFocus();
                    return;
                }
                if (EmtPwd.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                    mEtName.requestFocus();
                    return;
                }

                mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            PreferencesManager.setData(getApplication(),"email",strEmail);
                            //로그인 성공
                            FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Object hour, min;
                                    String friendId;
                                    for(DataSnapshot ds : snapshot.getChildren()) {

                                        for(DataSnapshot ds2 : ds.getChildren()){
                                            if(ds.getKey().equals("TimeAccount")){
                                                hour = (Object) ds2.child("hour").getValue();
                                                min = (Object) ds2.child("min").getValue();
                                                friendId = (String) ds2.child("frinendID").getValue();
                                                if(friendId.equals(strEmail)){ //친구 등록이면 실행
                                                    getAlram(hour, min);
                                                }

                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Intent intent = new Intent(LoginActivity.this, FriendActivity.class); //화면 이동
                            startActivity(intent);
                            finish(); // 현재 액티비티 파괴
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 버튼 누를시
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getAlram(Object hour, Object min){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int a = Math.toIntExact((long)hour);
        int b = Math.toIntExact((long)min);
        calendar.set(Calendar.HOUR_OF_DAY,a);
        calendar.set(Calendar.MINUTE,b);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        AlarmManager alarmManager=(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);


        }

    }
}