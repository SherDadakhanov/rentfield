package com.example.sherd.rentfield;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class Fragment_Price extends Fragment {

    private ListView listView;

    android.widget.ListAdapter customAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_price,container,false);

        listView = (ListView) v.findViewById(R.id.list_price);
        GetField field_list = new GetField();
        customAdapter = new ListAdap(getActivity(), R.layout.list_item, field_list.getField_list());
        listView.setAdapter(customAdapter);
        return v;
    }
}
