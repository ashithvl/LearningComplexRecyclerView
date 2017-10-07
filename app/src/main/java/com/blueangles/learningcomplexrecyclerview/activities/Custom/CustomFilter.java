package com.blueangles.learningcomplexrecyclerview.activities.Custom;

import android.widget.Filter;

import com.blueangles.learningcomplexrecyclerview.activities.Adapter.ContactAdapter;
import com.blueangles.learningcomplexrecyclerview.activities.model.Contact;

import java.util.ArrayList;

/**
 * Created by Ashith VL on 10/5/2017.
 */

public class CustomFilter extends Filter {
    private ContactAdapter contactAdapter;
    private ArrayList<Contact> contactArrayList;

    public CustomFilter(ArrayList<Contact> contactArrayList, ContactAdapter contactAdapter) {
        this.contactAdapter = contactAdapter;
        this.contactArrayList = contactArrayList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if (constraint != null && constraint.length() > 0) {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Contact> filteredContact = new ArrayList<>();
            for (int i = 0; i < contactArrayList.size(); i++) {
                //CHECK
                if (contactArrayList.get(i).getName().toUpperCase().contains(constraint)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredContact.add(contactArrayList.get(i));
                }
            }
            results.count = filteredContact.size();
            results.values = filteredContact;
        } else {
            results.count = contactArrayList.size();
            results.values = contactArrayList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        contactAdapter.contactList = (ArrayList<Contact>) results.values;
        contactAdapter.notifyDataSetChanged();
    }
}
