package com.gscsc.adminpanel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.gscsc.adminpanel.Adapter.MemberCardAdapter;
import com.gscsc.adminpanel.Adapter.MemberCardAdapterSimplified;
import com.gscsc.adminpanel.Models.MemberModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class user_cards extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private Context context;
    private EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cards);
        recyclerView = findViewById(R.id.user_card_list);
        search = findViewById(R.id.search_user);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        context = this;
        loadList();




        //add on text change listener to search bar
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = search.getText().toString();
                  if(query.length() > 0){
                      new Thread(()->{
                          List<MemberModel> memberList = new ArrayList<>();
                          FirebaseFirestore db = FirebaseFirestore.getInstance();
                          db.collection("Members")
                                  .orderBy("clubID", Query.Direction.ASCENDING)
                                  .addSnapshotListener((value, error) -> {
                                      assert value != null;
                                      memberList.clear();
                                      for (QueryDocumentSnapshot documentSnapshot : value) {
                                          MemberModel memberModel = documentSnapshot.toObject(MemberModel.class);
                                          Log.d("member", "onCreate: " + memberModel.getEmail());
                                          Log.d("member", "onCreate: "+memberModel.getFullName());
                                          if(memberModel.getFullName()!=null){
                                              if (memberModel.getFullName().toLowerCase().contains(query.toLowerCase())) {
                                                  memberList.add(memberModel);
                                              }
                                          }
                                      }
                                      MemberCardAdapter adapter = new MemberCardAdapter(context, memberList);
                                      recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
                                      runOnUiThread(() -> {
                                          recyclerView.setAdapter(adapter);

                                      });
                                  });
                      }).start();
                  }else {
                      loadList();
                  }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });



    }
    public void loadList(){
        new Thread(()->{
            List<MemberModel> memberList = new ArrayList<>();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Members").orderBy("clubID", Query.Direction.ASCENDING)
                    .addSnapshotListener((value, error) -> {
                        assert value != null;
                        memberList.clear();
                        for (QueryDocumentSnapshot documentSnapshot : value) {
                            MemberModel memberModel = documentSnapshot.toObject(MemberModel.class);
                            Log.d("member", "onCreate: " + memberModel.getEmail());
                            Log.d("member", "onCreate: "+memberModel.getFullName());
                            memberList.add(memberModel);
                        }
//                        MemberCardAdapterSimplified adapter = new MemberCardAdapterSimplified(memberList, context);
                        MemberCardAdapter adapter = new MemberCardAdapter(context, memberList);
                        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
                        runOnUiThread(() -> {
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();
                        });
                    });
        }).start();
    }
}