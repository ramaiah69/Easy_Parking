package com.example.christy.sweet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.nsd.NsdManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
 * Created by Christy on 3/26/2017.
 */

public class Register extends AppCompatActivity implements View.OnClickListener {

    //shared preferrence
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    //Static variables
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    Button login,register;
    EditText userId,userPassword,userEmail,userName;
    String id,name,email,password;

    @Override
    public void onCreate(Bundle instance){
        super.onCreate(instance);
        setContentView(R.layout.register);

        //shared preferences attachemtn
        pref = getApplicationContext().getSharedPreferences("pref",0);
        editor = pref.edit();

        //checking the id is present in the shared preferences
        //it also mean to user logged or not
        id = pref.getString("id",null);
        if(id!=null){
            //the user is already logged
            //It redirects to main activity
            Intent seetha = new Intent(Register.this,MainActivity.class);
            startActivity(seetha);
            //stop this login activity
            finish();
        }

        //taking data which is given by the user
        userId = (EditText) findViewById(R.id.userId);
        userPassword = (EditText) findViewById(R.id.userPassword);
        userEmail = (EditText) findViewById(R.id.userName);
        userName = (EditText) findViewById(R.id.userName);

        //taking buttons
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);

        //setting onclick listeners to buttons
        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //checking which button was clicked by user
        switch (v.getId()){
            case R.id.login:
                //redirect or call the intent to the reigster
                Intent seetha = new Intent(Register.this, Login.class);
                startActivity(seetha);
                break;
            case R.id.register:

                //get data the from edit text and converting the user data into string
                id = userId.getText().toString();
                password = userPassword.getText().toString();
                email = userEmail.getText().toString();
                name = userName.getText().toString();

                //calling asyc task for connect to server
                new AsyncLogin().execute(id,password,name,email);
                break;
        }
    }

    //async login
    class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(Register.this);
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
                url = new URL("https://easyparking.000webhostapp.com/easyparking/register_app.php");
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
                        .appendQueryParameter("password",params[1])
                        .appendQueryParameter("name",params[2])
                        .appendQueryParameter("email",params[3]);
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
                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
                editor.putString("id",id);
                editor.putString("email",email);
                editor.commit();

                //Intent calling to mainActivity
                Intent seetha = new Intent(Register.this,MainActivity.class);
                startActivity(seetha);
                //finishing this activity
                finish();
            }
            else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(Register.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(Register.this, "rey "+result.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
