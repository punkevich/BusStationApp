package com.example.busstationapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.HashSet;

public class SearchActivity extends Fragment {

    public HashSet<String> Cities;

    public SearchActivity() {

    }

    public static SearchActivity newInstance() {
        return new SearchActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_search, container, false);
        Cities = new HashSet<>();
        fillCitiesSet();

        return view;
    }

    public void fillCitiesSet(){
        Cities.add("Москва");
        Cities.add("Рязань");
        Cities.add("Пенза");
    }

    public boolean checkCity(String City){
        Log.d("debug", "checkCity called");
        if (Cities.contains(City))
            return true;
        else
            return false;
    }
}
