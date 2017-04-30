package com.example.sherd.rentfield;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



public class ListAdap extends ArrayAdapter<Field_list> {

    public ListAdap(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdap(Context context, int resource, List<Field_list> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item, null);
        }

        final Field_list p = getItem(position);

        if (p != null) {
            ImageView imageView = (ImageView)v.findViewById(R.id.list_img);
            TextView name = (TextView)v.findViewById(R.id.list_name);
            TextView address = (TextView)v.findViewById(R.id.list_address);
            TextView tel = (TextView)v.findViewById(R.id.list_tel);
            TextView price = (TextView)v.findViewById(R.id.list_price);

            imageView.setImageResource(p.getImg());
            name.setText("Название: "+p.getName());
            address.setText("Адрес: "+p.getAddress());
            tel.setText("Тел: "+p.getTel());
            price.setText("Цена: "+p.getPrice()+" тг");


        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), field_information.class);
                intent.putExtra("id",p.getId());
                intent.putExtra("name",p.getName());
                intent.putExtra("tel",p.getTel());
                intent.putExtra("address",p.getAddress());
                intent.putExtra("price",p.getPrice());
                intent.putExtra("admin",true);
                getContext().startActivity(intent);
            }
        });
        return v;
    }

}
