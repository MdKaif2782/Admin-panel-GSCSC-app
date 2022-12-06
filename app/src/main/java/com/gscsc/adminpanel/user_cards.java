package com.gscsc.adminpanel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.gscsc.adminpanel.Adapter.MemberCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class user_cards extends AppCompatActivity {
    private List<Member> memberList;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cards);
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.user_card_list);
        memberList = new ArrayList<>();
       for (int i = 0; i < 30; i++) {
            Member member = new Member("Md Kaif","department of IT");
            memberList.add(member);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        MemberCardAdapter memberCardAdapter = new MemberCardAdapter(this,memberList);
        recyclerView.setAdapter(memberCardAdapter);
    }
}