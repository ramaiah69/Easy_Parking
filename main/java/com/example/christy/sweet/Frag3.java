package com.example.christy.sweet;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag3 extends Fragment {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    EditText eName,eName2;

    String id,name,name2;

    Button submit;

    @Override
    public void onStart (){
        super.onStart();

        pref = getActivity().getSharedPreferences("pref",0);
        editor = pref.edit();

        id = pref.getString("id",null);

        eName = (EditText) getActivity().findViewById(R.id.name);
        eName2 = (EditText) getActivity().findViewById(R.id.name2);

        submit = (Button) getActivity().findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = eName.getText().toString();
                name2 = eName.getText().toString();
                //Toast.makeText(getContext(),"you registered successfully"+name2,Toast.LENGTH_SHORT).show();
                new AsyncLogin().execute(id,name);
            }
        });
    }

    public Frag3() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag3, container, false);
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
                url = new URL("http://192.168.201.1/easyparking/car_reg.php");
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
                        .appendQueryParameter("name",params[1]);

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
            //Toast.makeText(Login.this,result.toString(),Toast.LENGTH_LONG).show();
            if(result.equalsIgnoreCase("true"))
            {
                Toast.makeText(getContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
                editor.putString("id",id);
                editor.commit();

            }
            else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getContext(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getContext(), "rey "+result.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
