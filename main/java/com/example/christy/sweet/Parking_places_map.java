package com.example.christy.sweet;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Parking_places_map extends Fragment {


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    TextView tv,tv1;

    String parent="nuzvid";

    String sid="N110662";

    public Parking_places_map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parking_places_map, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getContext(), "Ok", Toast.LENGTH_SHORT).show();

        tv = (TextView) getActivity().findViewById(R.id.register_places);
        tv1 = (TextView) getActivity().findViewById(R.id.other_places);

        new AsyncLogin().execute(sid);
        new AsyncLogin2().execute(parent);
        //new AsyncLogin2().execute(parent);
    }

    //async login
    class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getContext());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("Getting inside\nPlease wait...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://easyparking.000webhostapp.com/easyparking/registered_parking_places.php");
                //Toast.makeText(getApplicationContext(),url.toString(),Toast.LENGTH_LONG).show();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"ss",Toast.LENGTH_LONG).show();
                return "exception";
            }
            try{
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                conn.setDoOutput(true);
                conn.setDoInput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("id","N110662");
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            }catch (Exception e1){
                e1.printStackTrace();
                return "Exception";
            }
            try{
                int response_code = conn.getResponseCode();
                if(response_code == HttpURLConnection.HTTP_OK){
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while((line=reader.readLine())!=null)
                    {
                        result.append(line);
                    }
                    return (result.toString());
                }
                else
                {
                    return("unsuccessful");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception";
            }finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result){
            pdLoading.dismiss();

            //Toast.makeText(getContext(),result.toString(),Toast.LENGTH_LONG).show();
            if(result.equalsIgnoreCase("There is no parking places nearest you"))
            {


                //Toast.makeText(getContext(),"No registered parking places for you",Toast.LENGTH_LONG).show();
                tv.setText("No registered parking places for you");

                //Intent calling to mainActivity

            }
            else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getContext(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
            }
            else {
                 json(result);
                //Toast.makeText(getContext(), "rey "+result.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    //async login
    class AsyncLogin2 extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getContext());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("Getting inside\nPlease wait...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://easyparking.000webhostapp.com/easyparking/nearest_parking_places_app.php");
                //Toast.makeText(getApplicationContext(),url.toString(),Toast.LENGTH_LONG).show();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"ss",Toast.LENGTH_LONG).show();
                return "exception";
            }
            try{
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                conn.setDoOutput(true);
                conn.setDoInput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("parent","nuzvid");
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            }catch (Exception e1){
                e1.printStackTrace();
                return "Exception";
            }
            try{
                int response_code = conn.getResponseCode();
                if(response_code == HttpURLConnection.HTTP_OK){
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while((line=reader.readLine())!=null)
                    {
                        result.append(line);
                    }
                    return (result.toString());
                }
                else
                {
                    return("unsuccessful");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception";
            }finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result){
            pdLoading.dismiss();

            //there is no fields in the database
            if(result.equalsIgnoreCase("There is no parking places nearest you"))
            {
                //Toast.makeText(getContext(),"There is no parking places nearest you",Toast.LENGTH_LONG).show();
                tv1.setText("There is no parking places nearest you");

            }
            else if (result.equalsIgnoreCase("false")){

                // connection unsucess of failed
                Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getContext(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
            }
            else {
                //Toast.makeText(getContext(), "rey "+result.toString(), Toast.LENGTH_LONG).show();
                json2(result);
            }
        }
    }



    public void json(String result) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        final ListView lisView1 = (ListView)getActivity().findViewById(R.id.listView3);

        //Toast.makeText(getContext(),result.toString(),Toast.LENGTH_LONG).show();

        try {

            JSONArray data = new JSONArray(result);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();

                //These are the column names of database so plz make any changes in db
                map.put("name", c.getString("name"));
                map.put("cost", c.getString("cost"));
                map.put("sno", c.getString("sno"));
                map.put("lng", c.getString("lng"));
                map.put("lat", c.getString("lat"));

                MyArrList.add(map);

            }

            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getContext(), MyArrList, R.layout.parking_places_elements,
                    new String[] {"name","cost"}, new int[] { R.id.name,R.id.cost});
            lisView1.setAdapter(sAdap);


            // OnClick Item

            lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {


                    String name = MyArrList.get(position).get("name").toString();
                    String lat = MyArrList.get(position).get("lat").toString();
                    String lng = MyArrList.get(position).get("lng").toString();
                    final int sno = Integer.parseInt(MyArrList.get(position).get("sno").toString());



                    //String sAttachments = MyArrList.get(position).get("put_attachments").toString();

                    Bundle seetha = new Bundle();
                    seetha.putString("lat",lat);
                    seetha.putString("lng",lng);
                    seetha.putString("name",name);


                    //Toast.makeText(getContext(),"You clicked "+sno+" "+sid,Toast.LENGTH_LONG).show();
                    Intent jaanu = new Intent(getContext(),Gmpas.class);
                    jaanu.putExtras(seetha);
                    startActivity(jaanu);
                }

            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void json2(String result) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        final ListView lisView1 = (ListView)getActivity().findViewById(R.id.listView4);

        //Toast.makeText(getContext(),result.toString(),Toast.LENGTH_LONG).show();

        try {

            JSONArray data = new JSONArray(result);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();

                //These are the column names of database so plz make any changes in db
                map.put("name", c.getString("name"));
                map.put("cost", c.getString("cost"));
                map.put("sno", c.getString("sno"));
                map.put("lng", c.getString("lng"));
                map.put("lat", c.getString("lat"));

                MyArrList.add(map);

            }

            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getContext(), MyArrList, R.layout.parking_places_elements,
                    new String[] {"name","cost"}, new int[] { R.id.name,R.id.cost});
            lisView1.setAdapter(sAdap);


            // OnClick Item

            lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {


                    String name = MyArrList.get(position).get("name").toString();
                    String lat = MyArrList.get(position).get("lat").toString();
                    String lng = MyArrList.get(position).get("lng").toString();
                    final int sno = Integer.parseInt(MyArrList.get(position).get("sno").toString());



                    //String sAttachments = MyArrList.get(position).get("put_attachments").toString();

                    Bundle seetha = new Bundle();
                    seetha.putString("lat",lat);
                    seetha.putString("lng",lng);
                    seetha.putString("name",name);


                    //Toast.makeText(getContext(),"You clicked "+sno+" "+sid,Toast.LENGTH_LONG).show();
                    Intent jaanu = new Intent(getContext(),Gmpas.class);
                    jaanu.putExtras(seetha);
                    startActivity(jaanu);
                }

            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
