package com.gscsc.adminpanel;

import android.content.Context;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.firestore.FirebaseFirestore;

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


        resizeImage(screenHeight);
    }

    public void onDeleteButtonPressed(View v){
        if (checkBox.isChecked()){
            db.collection("Members")
                    .document(clubID.getText().toString())
                    .delete().addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Toast.makeText(context, "User Removed Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Error happened or the Club ID doesn't Exist", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    public void resizeImage(int height){
        int newHeight = (int) (height*0.37);
        int imageHeight = delete_illustration.getDrawable().getIntrinsicHeight();
        int imageWidth = delete_illustration.getDrawable().getIntrinsicWidth();
        int newWidth = (int) (newHeight*imageWidth/imageHeight);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, newHeight);
        delete_illustration.setLayoutParams(params);
    }
}