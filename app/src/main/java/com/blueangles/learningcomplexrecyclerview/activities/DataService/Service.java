package com.blueangles.learningcomplexrecyclerview.activities.DataService;

import android.util.Log;

import com.blueangles.learningcomplexrecyclerview.activities.model.Contact;
import com.blueangles.learningcomplexrecyclerview.activities.model.Skill;

import java.util.ArrayList;

/**
 * Created by Ashith VL on 10/5/2017.
 */

public class Service {

    private static final Service ourInstance = new Service();

    public static Service getInstance() {
        return ourInstance;
    }

    private Service() {
    }

    public ArrayList<Skill> getSkillsArrayList() {
        ArrayList<Skill> skillsArrayList = new ArrayList<>();

        skillsArrayList.add(new Skill("C", false, 0));
        skillsArrayList.add(new Skill("CRICKET", false, 1));
        skillsArrayList.add(new Skill("C++", false, 2));
        skillsArrayList.add(new Skill("JAVA", false, 3));
        skillsArrayList.add(new Skill("SWIMMING", false, 4));
        skillsArrayList.add(new Skill("FIGHTING", false, 5));

        return skillsArrayList;
    }

    public ArrayList<Contact> getContactArrayList(ArrayList<Integer> position) {

        ArrayList<Contact> contactArrayList = new ArrayList<>();
        ArrayList<Contact> finalContactArrayList = new ArrayList<>();

        contactArrayList.add(new Contact("one", "one", "Dscet", "C", "0"));
        contactArrayList.add(new Contact("six", "six", "Vels", "CRICKET", "1"));
        contactArrayList.add(new Contact("two", "two", "Anna", "C++", "2"));
        contactArrayList.add(new Contact("three", "three", "Mammallan", "JAVA", "3"));
        contactArrayList.add(new Contact("four", "four", "Christ King", "SWIMMING", "4"));
        contactArrayList.add(new Contact("five", "five", "Dhana", "FIGHTING", "5"));

        if (position.size() > 0) {
            for (int aPosition : position) {
                finalContactArrayList.add(contactArrayList.get(aPosition));
            }
        }

        return finalContactArrayList;
    }

    public ArrayList<Contact> getAllContactArrayList() {

        ArrayList<Contact> contactArrayList = new ArrayList<>();

        contactArrayList.add(new Contact("one", "one", "Dscet", "C", "0"));
        contactArrayList.add(new Contact("six", "six", "Vels", "CRICKET", "1"));
        contactArrayList.add(new Contact("two", "two", "Anna", "C++", "2"));
        contactArrayList.add(new Contact("three", "three", "Mammallan", "JAVA", "3"));
        contactArrayList.add(new Contact("four", "four", "Christ King", "SWIMMING", "4"));
        contactArrayList.add(new Contact("five", "five", "Dhana", "FIGHTING", "5"));

        return contactArrayList;
    }

}
