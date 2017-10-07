package com.blueangles.learningcomplexrecyclerview.activities.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blueangles.learningcomplexrecyclerview.R;
import com.blueangles.learningcomplexrecyclerview.activities.Adapter.ContactAdapter;
import com.blueangles.learningcomplexrecyclerview.activities.Custom.HexagonMaskView;
import com.blueangles.learningcomplexrecyclerview.activities.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int CONTACT_CODE = 111;
    private ContactAdapter contactAdapter = null;
    private static ArrayList<Contact> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView contactsRecyclerView = (RecyclerView) findViewById(R.id.contact_recycler);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        contactAdapter = new ContactAdapter(MainActivity.this, R.layout.card_contact_image, contactsList);
        contactsRecyclerView.setHasFixedSize(true);
        contactsRecyclerView.setAdapter(contactAdapter);

        HexagonMaskView imageViewHexBtn = (HexagonMaskView) findViewById(R.id.add_contact);
        imageViewHexBtn.setBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        imageViewHexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactIntent = new Intent(MainActivity.this, ContactActivity.class);
                contactIntent.putExtra("contactsList", contactsList);
                startActivityForResult(contactIntent, CONTACT_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Log.e("tag", "no data comes here " + requestCode + " request code " + resultCode + "  resultcode " + data.getIntExtra("selectedPosition", 9));
        if (requestCode == CONTACT_CODE && resultCode == RESULT_OK && data != null) {
            contactsList.clear();
            ArrayList<Contact> onResultContactArrayList = (ArrayList<Contact>) data.getSerializableExtra("contacts");

            for (int i = 0; i < onResultContactArrayList.size(); i++) {
                Contact contact = new Contact();
                contact.setId(onResultContactArrayList.get(i).getId());
                contact.setName(onResultContactArrayList.get(i).getName());
                contact.setImage(onResultContactArrayList.get(i).getImage());
                contact.setStatus(onResultContactArrayList.get(i).getStatus());
                contact.setColg(onResultContactArrayList.get(i).getColg());
                contact.setJob(onResultContactArrayList.get(i).getJob());
                contactsList.add(contact);
            }
            contactAdapter.notifyDataSetChanged();
        }

    }
}
