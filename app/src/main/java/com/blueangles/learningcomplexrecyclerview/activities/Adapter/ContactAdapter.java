package com.blueangles.learningcomplexrecyclerview.activities.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;

import com.blueangles.learningcomplexrecyclerview.R;
import com.blueangles.learningcomplexrecyclerview.activities.Custom.CustomFilter;
import com.blueangles.learningcomplexrecyclerview.activities.Holders.ContactHolder;
import com.blueangles.learningcomplexrecyclerview.activities.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashith VL on 10/5/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactHolder> implements SectionIndexer, Filterable {

    public ArrayList<Contact> contactList;
    private Context context;
    private int itemResource;
    private InterfaceCallback callback;
    private ArrayList<Contact> filteredUsersList;
    private CustomFilter filter;
    public ArrayList<Contact> selected_usersList = new ArrayList<>();

    private ArrayList<Integer> mSectionPositions = new ArrayList<>();

    public ContactAdapter(Context mContext, int itemResource, ArrayList<Contact> contactList, ArrayList<Contact> selectedList) {
        this.contactList = contactList;
        this.context = mContext;
        this.itemResource = itemResource;
        this.selected_usersList = selectedList;
        this.filteredUsersList = contactList;
    }

    public ContactAdapter(Context context, int itemResource, ArrayList<Contact> contactList) {
        this.contactList = contactList;
        this.context = context;
        this.itemResource = itemResource;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemResource, parent, false);
        return new ContactHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        if (itemResource == R.layout.card_contact_image) {
            holder.updateUI(contactList.get(position), context);
        } else if (itemResource == R.layout.card_contact) {
            final Contact contact = contactList.get(position);

            holder.college.setText(contact.getColg());
            holder.name.setText(contact.getName());
            holder.job.setText(contact.getJob());
            holder.img.setBorderColor(android.R.color.darker_gray);
            int resource = holder.img.getResources().getIdentifier(contact.getImage(), null, holder.img.getContext().getPackageName());
            holder.img.setImageResource(resource);
            holder.img.setVisibility(View.VISIBLE);

            if (selected_usersList.contains(contactList.get(position))) {
                holder.rr_layout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                holder.selected.setVisibility(View.VISIBLE);
                holder.mentee.setVisibility(View.VISIBLE);
                holder.mentor.setVisibility(View.VISIBLE);
                holder.participant.setVisibility(View.VISIBLE);

                if ("2".equalsIgnoreCase(contact.getStatus()) || "2".equalsIgnoreCase(selected_usersList.get(position).getStatus())) {
                    holder.mentor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    holder.mentee.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    holder.participant.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                } else if ("0".equalsIgnoreCase(contact.getStatus())) {
                    holder.mentor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    holder.participant.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    holder.mentee.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                } else if ("1".equalsIgnoreCase(contact.getStatus())) {
                    holder.mentor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                    holder.participant.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    holder.mentee.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                } else {
                    holder.mentor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    holder.mentee.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    holder.participant.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                }

                holder.mentor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected_usersList.remove(contact);
                        contact.setStatus("1");
                        selected_usersList.add(contact);
                        notifyDataSetChanged();
                        callback.multiSelect(selected_usersList);
                    }
                });

                holder.mentee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected_usersList.remove(contact);
                        contact.setStatus("0");
                        selected_usersList.add(contact);
                        notifyDataSetChanged();
                        callback.multiSelect(selected_usersList);
                    }
                });

                holder.participant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected_usersList.remove(contact);
                        contact.setStatus("2");
                        selected_usersList.add(contact);
                        notifyDataSetChanged();
                        callback.multiSelect(selected_usersList);
                    }
                });

            } else {
                holder.rr_layout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                holder.selected.setVisibility(View.GONE);
                holder.mentee.setVisibility(View.GONE);
                holder.mentor.setVisibility(View.GONE);
                holder.participant.setVisibility(View.GONE);
                holder.mentor.setOnClickListener(null);
                holder.mentee.setOnClickListener(null);
                holder.participant.setOnClickListener(null);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // default participant
                    contact.setStatus("2");
                    addOrRemoveInMultiSelect(contact);
                    notifyDataSetChanged();
                }
            });
        }
    }

    private void addOrRemoveInMultiSelect(Contact contact) {
        if (selected_usersList.contains(contact))
            selected_usersList.remove(contact);
        else
            selected_usersList.add(contact);

        //multi_select(selected_usersList);
        callback.multiSelect(selected_usersList);
    }


    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = contactList.size(); i < size; i++) {
            Contact contact = contactList.get(i);
            String section = String.valueOf(contact.getName().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(filteredUsersList, this);
        }
        return filter;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void setCallback(InterfaceCallback callback) {
        this.callback = callback;
    }

    public interface InterfaceCallback {

        void multiSelect(ArrayList<Contact> position);
    }

}
