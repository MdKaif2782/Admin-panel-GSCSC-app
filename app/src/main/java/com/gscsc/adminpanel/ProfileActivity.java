package com.gscsc.adminpanel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.gscsc.adminpanel.Adapter.MemberCardAdapter;
import com.gscsc.adminpanel.Models.MemberModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private TextView fName,lName,bloodGroup,fatherName,fatherCell,fatherOccupation,motherName,motherCell,motherOccupation,email,cell,presentAdr,permanentAdr,fbLink,others;
    private Button clubID;
    private ImageView profilePic,backButton,userRemove;
    private CircularProgressDrawable circularProgressDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePic = findViewById(R.id.user_profile_image_profile_activity);
        backButton = findViewById(R.id.back_button);
        userRemove = findViewById(R.id.user_remove_button);

        clubID = findViewById(R.id.appCompatButton);
        fName = findViewById(R.id.user_first_name_activity_profile);
        lName = findViewById(R.id.user_last_name_activity_profile);
        bloodGroup = findViewById(R.id.user_blood_group_activity_profile);
        fatherName = findViewById(R.id.user_father_name_activity_profile);
        fatherCell = findViewById(R.id.user_father_cell_activity_profile);
        fatherOccupation = findViewById(R.id.user_father_occupation_activity_profile);
        motherName = findViewById(R.id.user_mother_name_activity_profile);
        motherCell = findViewById(R.id.user_mother_cell_activity_profile);
        motherOccupation = findViewById(R.id.user_mother_occupation_activity_profile);
        email = findViewById(R.id.user_email_activity_profile);
        cell = findViewById(R.id.user_cell_activity_profile);
        presentAdr = findViewById(R.id.user_present_adr_activity_profile);
        permanentAdr = findViewById(R.id.user_permanent_adr_activity_profile);
        fbLink = findViewById(R.id.user_fb_activity_profile);
        others = findViewById(R.id.user_previous_exp_activity_profile);

        circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorFilter(getResources().getColor(R.color.purple_500), android.graphics.PorterDuff.Mode.SRC_IN);
        circularProgressDrawable.start();

        profilePic.setImageDrawable(circularProgressDrawable);

        clubID.setOnClickListener(v->{
            String text = clubID.getText().toString();
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("text", text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
        });

        backButton.setOnClickListener(v->{
            finish();
        });

        userRemove.setOnClickListener(v->{
            Intent intent = new Intent(ProfileActivity.this,DeleteActivity.class);
            intent.putExtra("clubId",clubID.getText().toString());
            startActivity(intent);
            finish();
        });


        String clubId = getIntent().getStringExtra("clubId");
        // query the club id and get the club details
        new Thread(()->{
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Members")
                    .addSnapshotListener((value, error) -> {
                        assert value != null;
                        for (QueryDocumentSnapshot documentSnapshot : value) {
                            MemberModel memberModel = documentSnapshot.toObject(MemberModel.class);
                            Log.d("member", "onCreate: " + memberModel.getEmail());
                            Log.d("member", "onCreate: "+memberModel.getFullName());
                            if (memberModel.getClubID()!=null){
                                if (memberModel.getClubID().equals(clubId)){
                                    runOnUiThread(()->{

                                        if (memberModel.getImage()!=null){
                                            Glide.with(this).load(memberModel.getImage()).placeholder(circularProgressDrawable).circleCrop().into(profilePic);
                                        }

                                        clubID.setText(memberModel.getClubID());
                                        fName.setText((memberModel.getFname()!=null)?"First Name: "+memberModel.getFname():"First Name: "+"Invalid");
                                        lName.setText((memberModel.getLname()!=null)?"Last Name: "+memberModel.getLname():"Last Name: "+"Invalid");
                                        bloodGroup.setText((memberModel.getBlood()!=null)?"Blood Group: "+memberModel.getBlood():"Blood Group: "+"Invalid");
                                        fatherName.setText((memberModel.getFatherName()!=null)?"Father's Name: "+memberModel.getFatherName():"Father's Name: "+"Invalid");
                                        fatherCell.setText((memberModel.getFatherNumber()!=null)?"Father's Cell: "+memberModel.getFatherNumber():"Father's Cell: "+"Invalid");
                                        fatherOccupation.setText((memberModel.getFathherOcc()!=null)?"Father's Occupation: "+memberModel.getFathherOcc():"Father's Occupation: "+"Invalid");
                                        motherName.setText((memberModel.getMotherName()!=null)?"Mother's Name: "+memberModel.getMotherName():"Mother's Name: "+"Invalid");
                                        motherCell.setText((memberModel.getMotherrNumber()!=null)?"Mother's Cell: "+memberModel.getMotherrNumber():"Mother's Cell: "+"Invalid");
                                        motherOccupation.setText((memberModel.getMotherOcc()!=null)?"Mother's Occupation: "+memberModel.getMotherOcc():"Mother's Occupation: "+"Invalid");
                                        email.setText((memberModel.getEmail()!=null)?"Email: "+memberModel.getEmail():"Email: "+"Invalid");
                                        cell.setText((memberModel.getPhone()!=null)?"Cell: "+memberModel.getPhone():"Cell: "+"Invalid");
                                        presentAdr.setText((memberModel.getPresent()!=null)?"Present Address: "+memberModel.getPresent():"Present Address: "+"Invalid");
                                        permanentAdr.setText((memberModel.getPermanent()!=null)?"Permanent Address: "+memberModel.getPermanent():"Permanent Address: "+"Invalid");
                                        fbLink.setText((memberModel.getFblink()!=null)?"Facebook : "+memberModel.getFblink():"Facebook : "+"Invalid");
                                        others.setText((memberModel.getPrevious()!=null)?"Experience: "+memberModel.getPrevious():"Experience: "+"None");


                                    });
                                }
                            }
                        }
//
                    });
        }).start();
    }
    public void onTextClicked(View view){
        String text = ((TextView)view).getText().toString();
        String[] split = text.split(":");
        String data = split[1].trim();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", data);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}