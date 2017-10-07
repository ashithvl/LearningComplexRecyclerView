package com.blueangles.learningcomplexrecyclerview.activities.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.blueangles.learningcomplexrecyclerview.R;
import com.blueangles.learningcomplexrecyclerview.activities.Adapter.ContactAdapter;
import com.blueangles.learningcomplexrecyclerview.activities.Adapter.SkillAdapter;
import com.blueangles.learningcomplexrecyclerview.activities.DataService.Service;
import com.blueangles.learningcomplexrecyclerview.activities.Interface.ItemClickListener;
import com.blueangles.learningcomplexrecyclerview.activities.model.AlphabetItem;
import com.blueangles.learningcomplexrecyclerview.activities.model.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

public class ContactActivity extends AppCompatActivity {

    private ContactAdapter contactAdapter = null;
    private ArrayList<Contact> multiSelect_list = new ArrayList<>();
    private ArrayList<Contact> contactsLists = new ArrayList<>();
    private ArrayList<Integer> selected = new ArrayList<>();
    private ArrayList<Contact> multiContactList = new ArrayList<>();

    private ActionMode mActionMode;

    public ContactActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("All Contacts");
        }

        IndexFastScrollRecyclerView contactsRecyclerViewT = (IndexFastScrollRecyclerView) findViewById(R.id.recyclerViewContact);
        RecyclerView skillRecyclerView = (RecyclerView) findViewById(R.id.skill_container_scroll);
        IndexFastScrollRecyclerView mRecyclerView = (IndexFastScrollRecyclerView) findViewById(R.id.fast_scroller);

        RecyclerView.LayoutManager mSkillLayoutManager = new LinearLayoutManager(ContactActivity.this, LinearLayoutManager.HORIZONTAL, false);
        skillRecyclerView.setLayoutManager(mSkillLayoutManager);
        SkillAdapter skillAdapter = new SkillAdapter(ContactActivity.this, R.layout.card_skill, Service.getInstance().getSkillsArrayList());
        skillRecyclerView.setHasFixedSize(true);
        skillRecyclerView.setAdapter(skillAdapter);

        for (int i = 0; i < Service.getInstance().getSkillsArrayList().size(); i++) {
            selected.add((int) Service.getInstance().getSkillsArrayList().get(i).getSkillId());
        }
        contactsLists.addAll(Service.getInstance().getAllContactArrayList());

        skillAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, Integer id) {
                ArrayList<Contact> selectedContactList = contactAdapter.selected_usersList;

                if (selected.size() > 0) {
                    boolean sameFlag = false;
                    for (int i = 0; i < selected.size(); i++) {
                        if (Objects.equals(selected.get(i), id)) {
                            sameFlag = true;
                        }
                    }
                    if (!sameFlag) {
                        selected.add(id);
                    } else {
                        selected.remove(id);
                    }
                } else if (selected.size() == 0) {
                    selected.add(id);
                }

                contactsLists.clear();
                contactsLists.addAll(Service.getInstance().getContactArrayList(selected));

                for (Contact u1 : selectedContactList) {
                    boolean unique = true;
                    for (Contact u2 : multiContactList) {
                        if (u1.getId().equals(u2.getId())) {
                            unique = false;
                            break;
                        }
                    }
                    if (unique) {
                        multiContactList.add(u1);
                    }
                }

                for (Contact u1 : multiContactList) {
                    for (Contact u2 : contactsLists) {
                        if (u1.getId().equals(u2.getId())) {
                            contactsLists.remove(u2);
                            contactsLists.add(u1);
                            break;
                        }
                    }
                }

                if (!contactsLists.isEmpty()) {
                    Collections.sort(contactsLists, new Comparator<Contact>() {
                        @Override
                        public int compare(Contact lhs, Contact rhs) {
                            return lhs.getName().compareTo(rhs.getName());
                        }
                    });
                }

                contactAdapter.notifyDataSetChanged();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ContactActivity.this);
        contactsRecyclerViewT.setLayoutManager(mLayoutManager);
        contactAdapter = new ContactAdapter(ContactActivity.this, R.layout.card_contact, contactsLists, multiSelect_list);

        ArrayList<Contact> bundleContactArrayList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bundleContactArrayList = (ArrayList<Contact>) getIntent().getSerializableExtra("contactsList");
        }
        if (!bundleContactArrayList.isEmpty()) {
            multiSelect_list.addAll(bundleContactArrayList);

            for (Contact u1 : multiSelect_list) {
                for (Contact u2 : contactsLists) {
                    if (u1.getId().equals(u2.getId())) {
                        contactsLists.remove(u2);
                        contactsLists.add(u1);
                        break;
                    }
                }
            }

            contactAdapter.notifyDataSetChanged();
        }

        if (!contactsLists.isEmpty()) {
            Collections.sort(contactsLists, new Comparator<Contact>() {
                @Override
                public int compare(Contact lhs, Contact rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });
        }
        contactAdapter.notifyDataSetChanged();

        //For performance, tell OS RecyclerView won't change size
        contactsRecyclerViewT.setHasFixedSize(true);
        contactsRecyclerViewT.setItemViewCacheSize(20);
        contactsRecyclerViewT.setDrawingCacheEnabled(true);
        contactsRecyclerViewT.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        contactsRecyclerViewT.addItemDecoration(new DividerItemDecoration(ContactActivity.this, DividerItemDecoration.VERTICAL));
        contactsRecyclerViewT.setAdapter(contactAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ContactActivity.this));
        contactsRecyclerViewT.setIndexTextSize(12);
        contactsRecyclerViewT.setIndexBarColor("#33334c");
        contactsRecyclerViewT.setIndexBarCornerRadius(0);
        contactsRecyclerViewT.setIndexBarTransparentValue((float) 0.4);
        contactsRecyclerViewT.setIndexbarMargin(0);
        contactsRecyclerViewT.setIndexbarWidth(40);
        contactsRecyclerViewT.setPreviewPadding(0);
        contactsRecyclerViewT.setIndexBarTextColor("#FFFFFF");
        contactsRecyclerViewT.setIndexBarVisibility(true);

        ArrayList<AlphabetItem> mAlphabetItems = new ArrayList<>();
        List<String> strAlphabets = new ArrayList<>();
        for (int i = 0; i < contactsLists.size(); i++) {
            Contact contact = contactsLists.get(i);
            String name = contact.getName();
            if (name == null || name.trim().isEmpty())
                continue;
            String word = name.substring(0, 1);
            if (!strAlphabets.contains(word)) {
                strAlphabets.add(word);
                mAlphabetItems.add(new AlphabetItem(i, word, false));
            }
        }

        contactAdapter.setCallback(new ContactAdapter.InterfaceCallback() {
            @Override
            public void multiSelect(ArrayList<Contact> multiselect_list) {

                if (mActionMode == null)
                    mActionMode = startActionMode(mActionModeCallback);

                if (multiselect_list.size() > 0) {
                    mActionMode.setTitle("" + multiselect_list.size() + " Selected");
                } else {
                    mActionMode.setTitle("All Contacts");
                }
                refreshAdapter();
            }
        });

    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            getSupportActionBar().hide();
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.contact, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.done:
                    if (multiSelect_list != null) {
                        int param = getIntent().getIntExtra("selectedPosition", -1);
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("selectedPosition", param);
                        Log.e("tag", "true");
                        resultIntent.putExtra("contacts", multiSelect_list);
                        setResult(Activity.RESULT_OK, resultIntent);
                        onBackPressed();
                    }
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            multiSelect_list = new ArrayList<>();
            refreshAdapter();
            onBackPressed();
        }
    };

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshAdapter() {
        contactAdapter.selected_usersList = multiSelect_list;
        contactAdapter.contactList = contactsLists;
        contactAdapter.notifyDataSetChanged();
    }

}
