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
public class Parking_places extends Fragment {


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    String parent="nuzvid";

     String sid="N110662";

    public Parking_places() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parking_places, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(getContext(), "Ok", Toast.LENGTH_SHORT).show();

        new AsyncLogin().execute(parent);
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
                        .appendQueryParameter("parent",params[0]);
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
            json(result);
            //Toast.makeText(getContext(),result.toString(),Toast.LENGTH_LONG).show();
            if(result.equalsIgnoreCase("true"))
            {
                Toast.makeText(getContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
                json(result);

                //Intent calling to mainActivity

            }
            else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getContext(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
            }
            else {
                //Toast.makeText(getContext(), "rey "+result.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }




    private double distance(double lat1,double lng1,double lat2,double lng2){

        double earthRadius=3958.75;

        double dLat=Math.toRadians(lat2-lat1);
        double dLng=Math.toRadians(lng2-lng1);

        double sindLat=Math.sin(dLat / 2);
        double sindLng=Math.sin(dLng / 2);

        double a=Math.pow(sindLat,2)+Math.pow(sindLng,2)*Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2));

        double c=2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));

        double dist=earthRadius*c;

        return dist;

    }


    public void json(String result) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        final ListView lisView1 = (ListView)getActivity().findViewById(R.id.listView);



        try {

            JSONArray data = new JSONArray(result);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();

                //These are the column names of database so plz make any changes in db
                map.put("name", c.getString("name"));



                map.put("cost", "Cost: "+c.getString("cost"));
                map.put("sno", c.getString("sno"));
                map.put("lat", c.getString("lat"));
                map.put("lng", c.getString("lng"));

                MyArrList.add(map);

            }

            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getContext(), MyArrList, R.layout.parking_places_elements,
                    new String[] {"name","cost"}, new int[] { R.id.name, R.id.cost});
            lisView1.setAdapter(sAdap);


            // OnClick Item

            lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {


                    String name = MyArrList.get(position).get("name").toString();
                    final int sno = Integer.parseInt(MyArrList.get(position).get("sno").toString());



                    //String sAttachments = MyArrList.get(position).get("put_attachments").toString();


                    //Toast.makeText(getContext(),"You clicked "+sno+" "+sid,Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder
                            = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to register?")
                            .setTitle("Are you sure?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Perform some action such as saving the item
                            //Toast.makeText(getContext(),"Your ok the "+sno+" "+sid,Toast.LENGTH_LONG).show();

                            new AsyncLogin2().execute(sid);

                        }

                        //async login
                        class AsyncLogin2 extends AsyncTask<String, String, String> {
                            ProgressDialog pdLoading = new ProgressDialog(getContext());
                            HttpURLConnection conn;
                            URL url = null;
                            int s = sno;
                            final String sd = ""+s;

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

                                    url = new URL("https://easyparking.000webhostapp.com/easyparking/parking_place_register_app.php");
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
                                            .appendQueryParameter("id",params[0])
                                            .appendQueryParameter("sno",sd);
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
                                if(result.equalsIgnoreCase("true"))
                                {
                                    Toast.makeText(getContext(),"Registered Successfully",Toast.LENGTH_LONG).show();

                                    //Intent calling to mainActivity

                                }
                                else if (result.equalsIgnoreCase("false")){

                                    // If username and password does not match display a error message
                                    Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_LONG).show();

                                } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                                    Toast.makeText(getContext(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    //Toast.makeText(getContext(), "rey "+result.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();


                }

            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
