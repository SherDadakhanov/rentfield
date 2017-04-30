package com.example.sherd.rentfield;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetField extends MainActivity{

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "http://95.85.44.250:8000/api/fields/";

    List<Field_list> field_list = new ArrayList<Field_list>();


    public List<Field_list> getField_list() {
        field_list.clear();
        new GetContacts().execute();
        return field_list;
    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        int id = c.getInt("id");
                        String name = c.getString("name");
                        String game = c.getString("game");
                        int hovered = c.getInt("hovered");
                        int status = c.getInt("status");
                        String description = c.getString("description");
                        String price = c.getString("price");
                        String width = c.getString("width");
                        String length = c.getString("length");
                        String address = c.getString("address");
                        String phone_number = c.getString("phone_number");
                        String likes = c.getString("likes");
                        //int longitude = c.getInt("longitude");
                        //int latitude = c.getInt("latitude");
                        String owner = c.getString("owner");


                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();



                        Field_list f = new Field_list(id,R.drawable.addphoto,name,price,phone_number,address,0,0);
                        field_list.add(f);


                        // adding contact to contact list
                        //contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

       }

    }
}
