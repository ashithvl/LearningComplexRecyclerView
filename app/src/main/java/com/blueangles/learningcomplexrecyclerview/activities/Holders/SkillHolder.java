package com.blueangles.learningcomplexrecyclerview.activities.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blueangles.learningcomplexrecyclerview.R;

/**
 * Created by Ashith VL on 10/5/2017.
 */

public class SkillHolder extends RecyclerView.ViewHolder {

    public TextView skillName;
    public RelativeLayout myBackground;

    public SkillHolder(View itemView) {
        super(itemView);
        this.skillName = (TextView) itemView.findViewById(R.id.skill_name);
        this.myBackground = (RelativeLayout) itemView.findViewById(R.id.background);

    }
}
