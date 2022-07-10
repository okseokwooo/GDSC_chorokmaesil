package com.example.alarm_test.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.alarm_test.AlarmActivity;
import com.example.alarm_test.PreferencesManager;
import com.example.alarm_test.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.Holder> {
    private Context context;
    private List<FriendsDataItem> list = new ArrayList<>();
    private long id;
    TextView title;
    Intent intent;
    ImageView imageView;

    Map<Integer, String> friendsId = new HashMap<>();
    public FriendsAdapter(Context context, List<FriendsDataItem> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        Holder holder = new Holder(view);
        title = (TextView) view.findViewById(R.id.email);


        imageView = (ImageView) view.findViewById(R.id.item_Iv) ;

        return holder;
    }

    /*
     * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
     *
     * */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        String listTitle = (list.get(itemposition).getName());
        String listImg = (list.get(itemposition).getImg());
        friendsId.put(itemposition,listTitle);

        System.out.println("list.size() = " + list.size());
        System.out.println("listTitle = " + listTitle);
        title.setText(listTitle);

        switch (listImg){
            case "0":
                imageView.setImageResource(R.drawable.character0);
                break;
            case "1":
                imageView.setImageResource(R.drawable.character1);
                break;
            case "2":
                imageView.setImageResource(R.drawable.character2);
                break;
            case "3":
                imageView.setImageResource(R.drawable.character3);
                break;
            case "4":
                imageView.setImageResource(R.drawable.character4);
                break;
            case "5":
                imageView.setImageResource(R.drawable.character5);
                break;
            case "6":
                imageView.setImageResource(R.drawable.character6);
                break;
            case "7":
                imageView.setImageResource(R.drawable.character7);
                break;
            default:
                break;
        }

    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }



    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public Holder(View view){
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getBindingAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        intent = new Intent(context, AlarmActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("friend",friendsId.get(pos));
                        System.out.println("a.get(pos) = " + friendsId.get(pos));
                        context.startActivity(intent);
                    }
                }
            });

        }


    }
}