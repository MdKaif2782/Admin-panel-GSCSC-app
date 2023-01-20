package com.gscsc.adminpanel.Adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.gscsc.adminpanel.Models.MemberModel;
import com.gscsc.adminpanel.ProfileActivity;
import com.gscsc.adminpanel.R;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.List;

public class MemberCardAdapter extends RecyclerView.Adapter<MemberCardAdapter.viewholder> {
    private Context context;
    private List<MemberModel> memberList;
    public MemberCardAdapter(Context context, List<MemberModel> memberList) {
        this.context = context;
        this.memberList = memberList;


    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_recycler_of_user,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        MemberModel member = memberList.get(position);
        holder.name.setText(member.getFullName());
        holder.department.setText((member.getClubID()!=null)?member.getClubID():"Invalid Club ID");
        holder.department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = holder.department.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", text);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }});
        holder.button.setOnClickListener(v->{
            if (member.getClubID()!=null){
                String text  = member.getClubID();
                //switch to profile activity
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("clubId",text);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView name,department;
        ImageView userImage, userBackground;
        ImageView button;
        public viewholder(@NonNull View parent) {
            super(parent);
            name = parent.findViewById(R.id.user_name_simplified);
            department = parent.findViewById(R.id.club_id_simplified);
            button = parent.findViewById(R.id.user_details_button_simplified);
//            userImage = parent.findViewById(R.id.user_profile_image_circle);
//            userBackground = parent.findViewById(R.id.user_profile_image);
//            userBackground.setClipToOutline(true);
        }
    }

    public static class LoadImage extends AsyncTask<String,Void,Bitmap>{
        private final WeakReference<MemberCardAdapter> context;
        private final WeakReference<ImageView> imageViewWeakReference;
        private final WeakReference<ImageView> imageViewWeakReference2;
        private int position;
        public LoadImage(ImageView userImage, ImageView userBackground, MemberCardAdapter memberCardAdapter) {
            imageViewWeakReference = new WeakReference<>(userImage);
            imageViewWeakReference2 = new WeakReference<>(userBackground);
            context = new WeakReference<>(memberCardAdapter);
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            position = Integer.parseInt(strings[1]);
            int WIDTH = Integer.parseInt(strings[2]);
            Bitmap bitmap = null;
            Bitmap resizedBitmap = null;
          
            try {
                InputStream inputStream = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                float ratio = (float) width / (float) height;
                float scaleHeight = (float) WIDTH / ratio;
                resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int) (float) WIDTH, (int) scaleHeight, true);
                // set the rounded corner to 10dp and background color to transparent

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resizedBitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            context.get().memberList.get(position).setBitmap(bitmap);
            if (imageViewWeakReference != null && bitmap != null){
                ImageView imageView = (ImageView) imageViewWeakReference.get();
                if (imageView != null){
                    Glide.with(imageView.getContext())
                            .load(bitmap)
                            .circleCrop()
                            .into(imageView);
                }
            }
            if (imageViewWeakReference2 != null && bitmap != null){
                ImageView imageView = (ImageView) imageViewWeakReference2.get();
                if (imageView != null){
                    Glide.with(imageView.getContext())
                            .load(bitmap)
                            .transform(new CenterCrop(), new RoundedCorners(16))
                            .into(imageView);
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}



