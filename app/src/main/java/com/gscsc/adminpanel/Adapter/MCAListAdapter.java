package com.gscsc.adminpanel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.gscsc.adminpanel.Models.MemberModel;
import com.gscsc.adminpanel.R;

import java.util.ArrayList;
import java.util.List;

public class MCAListAdapter extends ArrayAdapter<MemberModel> {
    private Context context;
    private List<MemberModel> memberList;


    public MCAListAdapter(@NonNull Context context, List<MemberModel> memberList) {
        super(context, R.layout.grid_recycler_of_user);
        this.context = context;
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.grid_recycler_of_user,parent,false);
        ImageView userImage = convertView.findViewById(R.id.user_profile_image_circle);
        ImageView userBackground = convertView.findViewById(R.id.user_profile_image);
        TextView name = convertView.findViewById(R.id.user_profile_name);
        TextView department = convertView.findViewById(R.id.user_profile_department);
        MemberModel member = memberList.get(position);
        name.setText(member.getFullName());
        department.setText("Department of Uselss");
        Glide.with(context)
                .load(member.getBitmap())
                .transform(new CenterCrop(), new RoundedCorners(10))
                .into(userBackground);
        Glide.with(context)
                .load(member.getBitmap())
                .circleCrop()
                .into(userImage);
        return convertView;
    }
}
