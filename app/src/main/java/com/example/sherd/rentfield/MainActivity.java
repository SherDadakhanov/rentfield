package com.example.sherd.rentfield;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends ActionBarActivity implements
        ViewPager.OnPageChangeListener,TabHost.OnTabChangeListener{

    boolean logged = false;//kirdi ma sony tekseredi!!!
    CustomSwipeAdapter adapter;
    private ViewPager viewPager;
    private TabHost tabHost;

    String str = "first";

    boolean admin = false;
    List<Field_list> field_list = new ArrayList<Field_list>();
    ListAdapter customAdapter;

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "http://95.85.44.250:8000/api/fields/";

    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.adduser);

        tabHost = (TabHost)findViewById(R.id.tabhost);
        viewPager = (ViewPager)findViewById(R.id.view_pager);

        initViewPager();
        initTabHost();
        try{
            logged = intent.getBooleanExtra("logged",false);
        }
        catch (NullPointerException e){}

        if(logged){
            getSupportActionBar().setLogo(R.drawable.logout);
        }
    }


    public void initViewPager(){


        List<Fragment> listFragments = new ArrayList<>();
        listFragments.add(new Fragment_Like());
        listFragments.add(new Fragment_Price());
        listFragments.add(new Fragment_Time());
        listFragments.add(new Fragment_Location());

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),listFragments);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setOnPageChangeListener(this);

    }


    private void initTabHost() {

        tabHost.setup();
        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("Like");
        tabSpec.setIndicator("", getResources().getDrawable(R.drawable.like_style));
        tabSpec.setContent(new FakeContent(getApplicationContext()));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Price");
        tabSpec.setIndicator("", getResources().getDrawable(R.drawable.price_style));
        tabSpec.setContent(new FakeContent(getApplicationContext()));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Time");
        tabSpec.setIndicator("", getResources().getDrawable(R.drawable.time_style));
        tabSpec.setContent(new FakeContent(getApplicationContext()));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Location");
        tabSpec.setIndicator("", getResources().getDrawable(R.drawable.location_style));
        tabSpec.setContent(new FakeContent(getApplicationContext()));
        tabHost.addTab(tabSpec);
        tabHost.setOnTabChangedListener(this);

        tabHost.getTabWidget().getChildAt(0)
                .setBackgroundResource(R.drawable.like_corner);
        tabHost.getTabWidget().getChildAt(1)
                .setBackgroundResource(R.drawable.price_corner);
        tabHost.getTabWidget().getChildAt(2)
                .setBackgroundResource(R.drawable.time_corner);
        tabHost.getTabWidget().getChildAt(3)
                .setBackgroundResource(R.drawable.location_corner);


    }




    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int selectedItem) {
        tabHost.setCurrentTab(selectedItem);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int selectedItem = tabHost.getCurrentTab();
        viewPager.setCurrentItem(selectedItem);

        tabHost.getTabWidget().getChildAt(0)
                .setBackgroundResource(R.drawable.like_corner);
        tabHost.getTabWidget().getChildAt(1)
                .setBackgroundResource(R.drawable.price_corner);
        tabHost.getTabWidget().getChildAt(2)
                .setBackgroundResource(R.drawable.time_corner);
        tabHost.getTabWidget().getChildAt(3)
                .setBackgroundResource(R.drawable.location_corner);


    }

    public class FakeContent implements TabHost.TabContentFactory {
        Context context;

        public FakeContent(Context mContext){
            context = mContext;
        }

        @Override
        public View createTabContent(String tag) {
            View fakeView = new View(context);
            fakeView.setMinimumHeight(0);
            fakeView.setMinimumWidth(0);
            return fakeView;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Drawable d = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
                d.setAlpha(70);
                dialog.getWindow().setBackgroundDrawable(d);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_registration, null);
                layout.setPadding(40,40,40,40);
                dialog.setContentView(layout);
                EditText reg_login = (EditText)dialog.findViewById(R.id.reg_login);
                EditText reg_tel = (EditText)dialog.findViewById(R.id.reg_tel);

                EditText reg_mail = (EditText)dialog.findViewById(R.id.reg_mail);
                Button reg_user = (Button)dialog.findViewById(R.id.button_user);
                Button reg_admin = (Button)dialog.findViewById(R.id.button_admin);



                reg_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.dialog_registration2, null);
                        layout.setPadding(40,40,40,40);
                        dialog.setContentView(layout);
                        Button reg_ok = (Button)dialog.findViewById(R.id.button_reg);
                        reg_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                reg_admin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.dialog_registration2, null);
                        layout.setPadding(40,40,40,40);
                        dialog.setContentView(layout);
                        Button reg_ok = (Button)dialog.findViewById(R.id.button_reg);
                        reg_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        ///////////////////////////////REGISTRATION PART
                    }
                });
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem enter = menu.findItem(R.id.enter);
        final MenuItem search = menu.findItem(R.id.search);


        if(!logged){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            enter.setIcon(R.drawable.login);
        }
        enter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {


            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (logged) {
                    Intent i = new Intent(getApplicationContext(), Profile_user.class);
                    startActivity(i);
                } else {
                    final Dialog dialog = new Dialog(MainActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    Drawable d = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
                    d.setAlpha(70);
                    dialog.getWindow().setBackgroundDrawable(d);
                    View layout = inflater.inflate(R.layout.dialog_login, null);
                    layout.setPadding(40,40,40,40);
                    dialog.setContentView(layout);

                    final EditText log = (EditText) dialog.findViewById(R.id.login_login);
                    EditText pass = (EditText) dialog.findViewById(R.id.login_pass);
                    Button ok = (Button) dialog.findViewById(R.id.button_login);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            logged = true;
                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            enter.setIcon(R.drawable.profile);
                            if (log.getText().toString().equals("admin")) {
                                admin = true;
                            }
                            if (admin) {
                                Intent i = new Intent(getApplicationContext(), Admin.class);
                                startActivity(i);
                            }
                            dialog.dismiss();

                        }
                    });

                    dialog.show();
                }
                return false;
            }
        });

        search.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    public static void registration_post (String username, String password, String phone_number, int type, String email){
        URL url;
        URLConnection urlConn;
        DataOutputStream printout;
        DataInputStream input;
        try {
            url = new URL("http://95.85.44.250:8000/auth/");

            urlConn = url.openConnection();
            urlConn.setDoInput (true);
            urlConn.setDoOutput (true);
            urlConn.setUseCaches (false);
            urlConn.setRequestProperty("Content-Type","application/json");
            //urlConn.setRequestProperty("Host", "android.schoolportal.gr");
            urlConn.connect();
            //Create JSONObject here
            JSONObject jsonParam = new JSONObject();
            JSONObject jsonParam2 = new JSONObject();
            jsonParam2.put("phone_number",phone_number);
            jsonParam2.put("type",type);
            jsonParam2.put("email",email);

            jsonParam.put("username", username);
            jsonParam.put("password", password);

            jsonParam.put("profile",jsonParam2);

            // Send POST output.
            printout = new DataOutputStream(urlConn.getOutputStream ());
            printout.writeBytes(URLEncoder.encode(jsonParam.toString(),"UTF-8"));
            printout.flush ();
            printout.close ();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

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

                        // adding each child node to HashMap key => value
//                    contact.put("id", id);
//                    contact.put("name", name);
//                    contact.put("price", price);
//                    contact.put("width", width);
//                    contact.put("length", length);
//                    contact.put("address", address);
//                    contact.put("phone_number", phone_number);
//                    contact.put("likes", likes);
//                    contact.put("longitude", longitude);
//                    contact.put("latitude", latitude);

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
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

        }

    }

    private class PostUser extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            registration_post("maks","maks","87759771615",1,"galiev.maksat@gmail.com");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

        }

    }
}
