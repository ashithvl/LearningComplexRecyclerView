package com.blueangles.learningcomplexrecyclerview.activities.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blueangles.learningcomplexrecyclerview.activities.Holders.SkillHolder;
import com.blueangles.learningcomplexrecyclerview.activities.Interface.ItemClickListener;
import com.blueangles.learningcomplexrecyclerview.activities.model.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashith VL on 10/5/2017.
 */


public class SkillAdapter extends RecyclerView.Adapter<SkillHolder> {

    private List<Skill> skillList;
    private Context context;
    private int itemResource;
    private ArrayList<String> usersSkillList = new ArrayList<>();
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private ItemClickListener itemClickListener;

    public SkillAdapter(Context context, int itemResource, List<Skill> skillList) {
        this.skillList = skillList;
        this.context = context;
        this.itemResource = itemResource;

        for (int i = 0; i < skillList.size(); i++) {
            selectedItems.put(i, true);
            if (usersSkillList.size() > 0 && usersSkillList != null) {
                if (!usersSkillList.contains(skillList.get(i).getTitle())) {
                    usersSkillList.add(skillList.get(i).getTitle());
                }
            } else {
                usersSkillList.add(skillList.get(i).getTitle());
            }
        }
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public SkillHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemResource, parent, false);
        return new SkillHolder(v);
    }

    @Override
    public void onBindViewHolder(final SkillHolder holder, final int position) {

        final Skill skill = skillList.get(position);
        holder.skillName.setText(skill.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usersSkillList.size() > 0 && usersSkillList != null) {
                    if (!usersSkillList.contains(skillList.get(position).getTitle())) {
                        usersSkillList.add(skillList.get(position).getTitle());
                        selectedItems.put(position, true);
                        itemClickListener.onItemClick(v, (int) skillList.get(position).getSkillId());
                    } else {
                        usersSkillList.remove(skillList.get(position).getTitle());
                        selectedItems.delete(position);
                        itemClickListener.onItemClick(v, (int) skillList.get(position).getSkillId());
                    }
                } else {
                    usersSkillList.add(skillList.get(position).getTitle());
                    selectedItems.put(position, true);
                    itemClickListener.onItemClick(v, (int) skillList.get(position).getSkillId());
                }
                notifyDataSetChanged();
            }
        });

        if (selectedItems.get(position)) {
            holder.myBackground.setBackground(ContextCompat.getDrawable(context, android.R.color.background_dark));
            holder.skillName.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
        } else {
            holder.myBackground.setBackgroundColor(ContextCompat.getColor(context, android.R.color.background_dark));
            holder.skillName.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark));
        }

    }

    @Override
    public int getItemCount() {
        return this.skillList.size();
    }

}