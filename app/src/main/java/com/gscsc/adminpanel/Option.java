package com.gscsc.adminpanel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import com.gscsc.adminpanel.Adapter.MemberCardAdapter;
import com.gscsc.adminpanel.HelperClass.BitmapFromUrl;
import com.gscsc.adminpanel.Models.MemberModel;

import java.util.ArrayList;
import java.util.List;

public class Option extends AppCompatActivity {
    private Intent inviteIntent;
    private Context context;
    private ImageView admin_panel_image;
    private Button invite;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        context = this;
        inviteIntent = new Intent(this, Invite.class);
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        admin_panel_image = findViewById(R.id.admin_panel_image);
        invite = findViewById(R.id.invite);
        db=FirebaseFirestore.getInstance();
        resizeImage(screenHeight);
        //wait for 2 seconds

    }
    public void switchToInvite(View view) {
        //delay 200ms
        invite.postDelayed(() -> {
            context.startActivity(inviteIntent);
        }, 320);

    }
    public void switchToDelete(View view) {
        //Slide animation
        Intent deleteIntent = new Intent(context, DeleteActivity.class);
        invite.postDelayed(() -> {
            context.startActivity(deleteIntent);
        }, 320);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void resizeImage(int height){
        int newHeight = (int) (height * 0.37);
        int newWidth = 187*newHeight/220;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, newHeight);
        admin_panel_image.setLayoutParams(params);
    }

    public void notReadyYet(View view) {
        Toast.makeText(context, "Can't be ready until Website is complete", Toast.LENGTH_SHORT).show();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        finish();
//        Intent intent = new Intent(context, Dashboard.class);
//        context.startActivity(intent);
    }
    public void switchToUserCards(View view) {
        Intent intent = new Intent(context, user_cards.class);
        context.startActivity(intent);
    }
    public void downloadUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        List<MemberModel> memberList = new ArrayList<>();
        db.collection("Members")
                .addSnapshotListener((value, error) -> {
                    assert value != null;
                    for (QueryDocumentSnapshot documentSnapshot : value) {
                        MemberModel memberModel = documentSnapshot.toObject(MemberModel.class);
                        System.out.println(memberModel.getFullName());
                        memberList.add(memberModel);
                    }
                    for (int i = 0; i < 20; i++) {
                        MemberModel memberModel2 = new MemberModel();
                        memberModel2.setFullName("Aqua Sama");
                        memberModel2.setImage("https://i.pinimg.com/736x/75/5a/4b/755a4b9b7b1ca6312a640b6616bec684.jpg");
                        memberList.add(memberModel2);
                    }
                    for (int i=0; i<memberList.size(); i++){
                        String link = memberList.get(i).getImage();
                        try {
                            BitmapFromUrl bitmapFromUrl = new BitmapFromUrl();
                            Bitmap bitmap = bitmapFromUrl.execute(link).get();
                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();
                            float ratio = (float) width / (float) height;
                            float scaleWidth = 250;
                            float scaleHeight = scaleWidth / ratio;
                            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int) scaleWidth, (int) scaleHeight, true);
                            memberList.get(i).setBitmap(resizedBitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Gson gson = new Gson();
                    String json = gson.toJson(memberList);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("memberList", json);
                    editor.apply();
                    Toast.makeText(context, "Downloaded", Toast.LENGTH_SHORT).show();
                });

    }
}