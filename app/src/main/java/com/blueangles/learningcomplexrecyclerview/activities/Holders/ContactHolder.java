package com.blueangles.learningcomplexrecyclerview.activities.Holders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blueangles.learningcomplexrecyclerview.R;
import com.blueangles.learningcomplexrecyclerview.activities.Custom.HexagonMaskView;
import com.blueangles.learningcomplexrecyclerview.activities.model.Contact;

/**
 * Created by Ashith VL on 10/5/2017.
 */

public class ContactHolder extends RecyclerView.ViewHolder {

    public TextView name, college, job, mentee, mentor, participant;
    public ImageView selected;
    public HexagonMaskView img;
    public RelativeLayout rr_layout;


    public ContactHolder(View itemView) {
        super(itemView);
        img = (HexagonMaskView) itemView.findViewById(R.id.contact_image);
        name = (TextView) itemView.findViewById(R.id.contact_name);
        college = (TextView) itemView.findViewById(R.id.contact_colg);
        job = (TextView) itemView.findViewById(R.id.contact_job);
        mentee = (TextView) itemView.findViewById(R.id.mentee);
        mentor = (TextView) itemView.findViewById(R.id.mentor);
        participant = (TextView) itemView.findViewById(R.id.participant);
        rr_layout = (RelativeLayout) itemView.findViewById(R.id.rr_layout);
        selected = (ImageView) itemView.findViewById(R.id.tic_contact_selected);
    }

    public void updateUI(Contact contact, Context context) {
        String uri = contact.getImage();
        int resource = img.getResources().getIdentifier(uri, null, img.getContext().getPackageName());
         Log.e("tag", " " + resource + " ==> " + uri);
        img.setImageResource(resource);
        img.setBorderColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }
}
