package com.gscsc.adminpanel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.gscsc.adminpanel.Member;
import com.gscsc.adminpanel.R;

import java.util.List;

public class MemberCardAdapter extends RecyclerView.Adapter<MemberCardAdapter.viewholder> {
    private Context context;
    private List<Member> memberList;
    public MemberCardAdapter(Context context, List<Member> memberList) {
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
        Member member = memberList.get(position);
        holder.name.setText(member.getName());
        holder.department.setText(member.getDepartment());
        Glide.with(context).load(R.drawable.aqua).circleCrop().into(holder.userImage);
        // set image radius to 10 dp and set to userBackground

    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView name,department;
        ImageView userImage, userBackground;
        public viewholder(@NonNull View parent) {
            super(parent);
            name = parent.findViewById(R.id.user_profile_name);
            department = parent.findViewById(R.id.user_profile_department);
            userImage = parent.findViewById(R.id.user_profile_image_circle);
            userBackground = parent.findViewById(R.id.user_profile_image);

        }
    }

}



