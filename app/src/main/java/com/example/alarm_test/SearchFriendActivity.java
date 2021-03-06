package com.example.alarm_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SearchFriendActivity extends AppCompatActivity {
    Button backBtn,confirmbtn;
    ImageButton searchBtn;
    EditText searchName;
    TextView friend_Tv;
    private String name;
    private DatabaseReference mdbr;
    private Map<String , Object> childUpdates = null;
    private FirebaseUser user;
    private String userID;
    private FirebaseAuth fAuth;
    private DatabaseReference fDatabaseRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

        searchName = findViewById(R.id.search_Tv);
        friend_Tv = findViewById(R.id.friend_Tv);
        friend_Tv.setVisibility(View.INVISIBLE);
        fAuth = FirebaseAuth.getInstance();
        searchBtn = findViewById(R.id.searchBtn);
        backBtn=findViewById(R.id.backBtn);
        confirmbtn = findViewById(R.id.confirmbtn);

        searchName.bringToFront();
        searchBtn.bringToFront();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????? ?????? ?????????
                Intent intent = new Intent(SearchFriendActivity.this, FriendActivity.class);
                startActivity(intent);
            }
        });



        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name =  searchName.getText().toString();
                user = FirebaseAuth.getInstance().getCurrentUser();

                FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                String id = (String) ds.child("ID").getValue();
                                System.out.println("id11111 = " + id);
                                if(id == null){
                                    System.out.println("null ???");
                                }
                                else if(id.equals(name)){
                                    friend_Tv.setText(id);
                                    friend_Tv.setVisibility(View.VISIBLE);
                                    confirmbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(SearchFriendActivity.this);

                                            builder.setTitle("???????????????");            //setTitle -> ????????????
                                            builder.setMessage("???????????????.");         //setMessage -> ????????????
                                            builder.setIcon(R.mipmap.ic_launcher);    //setIcon -> ????????? ??????

                                            //  setPositiveButton -> "OK"??????  //
                                            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    FirebaseUser user = fAuth.getCurrentUser();
                                                    HashMap<Object,String> result = new HashMap();
                                                    result.put("ID",PreferencesManager.getString(getApplication(),"email"));
                                                    result.put("freindID", name);

                                                    //setValue : ????????????????????? ??????
                                                    fDatabaseRef.child("FriendAccount").child(user.getUid()).setValue(result);
                                                    Toast.makeText(SearchFriendActivity.this, "?????? ??????", Toast.LENGTH_SHORT).show();

                                                }
                                            });

                                            //  setNegativeButton -> "Cancel" ??????  //
                                            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Toast.makeText(SearchFriendActivity.this, "?????? ????????? ???????????????.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            builder.show();      //????????????(dialog)?????? ??????
                                        }
                                    });
                                }
                            }
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}