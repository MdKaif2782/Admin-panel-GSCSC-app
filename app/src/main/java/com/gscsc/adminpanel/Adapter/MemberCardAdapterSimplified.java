package com.gscsc.adminpanel.Adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gscsc.adminpanel.Models.MemberModel;
import com.gscsc.adminpanel.R;

import java.util.List;

public class MemberCardAdapterSimplified extends RecyclerView.Adapter<MemberCardAdapterSimplified.viewholder> {
    private List<MemberModel> memberList;
    private Context context;


    public MemberCardAdapterSimplified(List<MemberModel> memberList, Context context) {
        this.memberList = memberList;
        this.context = context;
        Log.d("member", "MemberCardAdapterSimplified: Initialized");
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_cards_simplified,parent,false);
        Log.d("member", "onCreateViewHolder:  View Created");
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        MemberModel member = memberList.get(position);
        Log.d("member", "onBindViewHolder: uwud");
        holder.name.setText((member.getFullName()!=null)?member.getFullName():"No Name");
        Log.d("member", "onBindViewHolder: uwud");
        holder.clubId.setText((member.getClubID()!=null)?member.getClubID():"Invalid Club ID");
        //on club id click copy to clipboard
        Log.d("member", "onBindViewHolder: uwud");
        holder.clubId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: copy to clipboard
                String text = holder.clubId.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", text);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: pass the member object to the profile activity
            }
        });

    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView name,clubId;
        ImageView details;

        public viewholder(@NonNull View parent) {
            super(parent);
            name = parent.findViewById(R.id.user_name_simplified);
            clubId = parent.findViewById(R.id.club_id_simplified);
            details = parent.findViewById(R.id.user_details_button_simplified);
        }
    }
}
