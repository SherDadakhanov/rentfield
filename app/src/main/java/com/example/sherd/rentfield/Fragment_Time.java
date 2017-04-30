package com.example.sherd.rentfield;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Fragment_Time extends Fragment {
    private ListView listView;

    ListAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_time,container,false);

        listView = (ListView) v.findViewById(R.id.list_time);
        GetField field_list = new GetField();
        customAdapter = new ListAdapter(getActivity(), R.layout.list_item, field_list.getField_list());
        listView.setAdapter(customAdapter);
        return v;
    }
}
