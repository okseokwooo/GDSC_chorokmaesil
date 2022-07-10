package com.example.alarm_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.alarm_test.list.FriendsAdapter;
import com.example.alarm_test.list.FriendsDataItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class FriendActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FriendsAdapter adapter;
    ImageButton addFriendBtn;
    int cnt1 = 0 , cnt2 =0;
    private ArrayList<FriendsDataItem> list = new ArrayList<>();
    private String Id;
    @Override
    protected void onPostResume() {
        super.onPostResume();
        list = new ArrayList<>();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        HashMap<Integer,String > friends = new HashMap<>();

        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String Friend = snapshot.getKey();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        if(Friend.equals("FriendAccount")){
                            Id = (String) ds.child("freindID").getValue();
                            if(PreferencesManager.getString(getApplication(),"email").equals(Id)){
                                Id = (String) ds.child("ID").getValue();
                                System.out.println("123123 = " + Id);
                                friends.put(cnt1,Id);
                                cnt1++;

                            }
                            else{
                                Id = "";
                                System.out.println("1231234 = " + Id);
                                friends.put(cnt1,Id);
                                cnt1++;
                            }

                        }
                        else if(Friend.equals("UserAccount")){
                            String UserID = (String) ds.child("ID").getValue();
                            System.out.println("UserID112 = " + UserID);
                            System.out.println("friends.get(cnt2) = " + friends.get(cnt2));

                            for(int i=0; i<ds.getChildrenCount();i++){
                                if(friends.get(i) == null || UserID ==null){
                                    System.out.println("nullFriend");
                                }
                                else if(friends.get(cnt2).equals(UserID)){

                                    String img = (String) ds.child("img").getValue();
                                    System.out.println("UserID = " + UserID);
                                    System.out.println("img = " + img);
                                    list.add(new FriendsDataItem(img,UserID));
                                    recyclerView.setHasFixedSize(true);
                                    adapter = new FriendsAdapter(getBaseContext(), list);
                                    recyclerView.setLayoutManager((new GridLayoutManager(getBaseContext(),2)));
                                    recyclerView.setAdapter(adapter);
                                    recyclerView.setHasFixedSize(true);
                                    break;
                                }

                            }

                        }

                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        recyclerView = findViewById(R.id.recycle_view);

        addFriendBtn = findViewById(R.id.addFriendBtn);
        addFriendBtn.bringToFront();
        addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendActivity.this,SearchFriendActivity.class);
                startActivity(intent);
            }
        });
        list = new ArrayList<>();
        cnt1 =0;cnt2=0;
        HashMap<Integer,String > friends = new HashMap<>();
        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String Friend = snapshot.getKey();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        if(Friend.equals("FriendAccount")){
                            Id = (String) ds.child("ID").getValue();
                            if(PreferencesManager.getString(getApplication(),"email").equals(Id)){
                                Id = (String) ds.child("freindID").getValue();
                                System.out.println("123123 = " + Id);
                                friends.put(cnt1,Id);
                                cnt1++;

                            }
                            else{
                                Id = "";
                                System.out.println("1231234 = " + Id);
                                friends.put(cnt1,Id);
                                cnt1++;
                            }

                        }
                        else if(Friend.equals("UserAccount")){
                            String UserID = (String) ds.child("ID").getValue();
                            System.out.println("UserID112 = " + UserID);
                            System.out.println("friends.get(cnt2) = " + friends.get(cnt2));

                            for(int i=0; i<ds.getChildrenCount();i++){
                                if(friends.get(i) == null || UserID ==null){
                                    System.out.println("nullFriend");
                                }
                                else if(friends.get(cnt2).equals(UserID)){

                                    String img = (String) ds.child("img").getValue();
                                    System.out.println("UserID = " + UserID);
                                    System.out.println("img = " + img);
                                    list.add(new FriendsDataItem(img,UserID));
                                    recyclerView.setHasFixedSize(true);
                                    adapter = new FriendsAdapter(getBaseContext(), list);
                                    recyclerView.setLayoutManager((new GridLayoutManager(getBaseContext(),2)));
                                    recyclerView.setAdapter(adapter);
                                    recyclerView.setHasFixedSize(true);
                                    break;
                                }

                            }

                        }

                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        cnt1 =0;
        cnt2 =0;

    }
}