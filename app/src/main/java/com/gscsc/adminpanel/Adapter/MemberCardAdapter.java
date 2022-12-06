package com.gscsc.adminpanel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView name,department;
        public viewholder(@NonNull View parent) {
            super(parent);
            name = parent.findViewById(R.id.user_profile_name);
            department = parent.findViewById(R.id.user_profile_department);
        }
    }

}



