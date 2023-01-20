package com.gscsc.adminpanel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.gscsc.adminpanel.Adapter.MemberCardAdapter;
import com.gscsc.adminpanel.Models.MemberModel;

import java.util.ArrayList;
import java.util.List;

public class DeleteActivity extends AppCompatActivity {
    private CheckBox checkBox;
    private EditText clubID;
    private Context context;
    private FirebaseFirestore db;
    private ImageView delete_illustration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        checkBox = findViewById(R.id.confirm_delete);
        clubID = findViewById(R.id.club_id_delete);
        context = this;
        delete_illustration = findViewById(R.id.delete_illus);
        db = FirebaseFirestore.getInstance();
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        // get Extra from intent
        Intent intent = getIntent();
        if (intent.getStringExtra("clubId")!=null){
            clubID.setText(intent.getStringExtra("clubId"));
        }

        resizeImage(screenHeight);
    }

    public void onDeleteButtonPressed(View v){
        if (checkBox.isChecked()){
            new Thread(()->{
                List<MemberModel> memberList = new ArrayList<>();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Members")
                        .addSnapshotListener((value, error) -> {
                            assert value != null;
                            for (QueryDocumentSnapshot documentSnapshot : value) {
                                MemberModel memberModel = documentSnapshot.toObject(MemberModel.class);
                                Log.d("member", "onCreate: " + memberModel.getEmail());
                                Log.d("member", "onCreate: "+memberModel.getFullName());
                                if (memberModel.getClubID()!=null){
                                    if (memberModel.getClubID().equals(clubID.getText().toString())){
                                        db.collection("Members").document(documentSnapshot.getId()).delete().addOnCompleteListener(task -> {
                                            if (task.isSuccessful()){
                                                Log.d("delete", "onCreate: "+documentSnapshot.getId()+" deleted");
                                                runOnUiThread(()->{
                                                    switchToAmogus(memberModel.getFullName());
                                                });
                                            }
                                        });
                                    }
                                }
                            }
//
                        });
            }).start();
        }

    }

    public void resizeImage(int height){
        int newHeight = (int) (height*0.32);
        int imageHeight = delete_illustration.getDrawable().getIntrinsicHeight();
        int imageWidth = delete_illustration.getDrawable().getIntrinsicWidth();
        int newWidth = (int) (newHeight*imageWidth/imageHeight);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, newHeight);
        delete_illustration.setLayoutParams(params);
    }

    public void switchToAmogus(String extra){
        Intent intent = new Intent(context, amongus.class);
        intent.putExtra("name", extra);
        startActivity(intent);
    }
}