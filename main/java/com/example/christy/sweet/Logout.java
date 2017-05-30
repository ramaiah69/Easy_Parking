package com.example.christy.sweet;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class Logout extends Fragment {


    public Logout() {
        // Required empty public constructor
    }

    @Override
    public void onStart()
    {
        super.onStart();
        logout();
    }

    public void logout(){
        //Toast.makeText(getContext(), "Your logout", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder
                = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to register?")
                .setTitle("Are you sure?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Perform some action such as saving the item
                                Toast.makeText(getContext(),"Your ok the ",Toast.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

}
