package com.andreas.projekuts;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.andreas.projekuts.API.BukuAPI;
import com.andreas.projekuts.API.UjianAPI;
import com.andreas.projekuts.Models.Ujian;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AddFragment extends Fragment {


    private TextInputEditText txtTanggal,txtMapel;
    private Button btnCancel ,btnAdd;
    public AddFragment() {
        // Required empty public constructor
    }




    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtTanggal.getText().length()==0 || txtMapel.getText().length()==0 )
                {
                    Toast.makeText(view.getContext(), "data tidak boleh kosong", Toast.LENGTH_SHORT).show();

                }

                else {
                    addBuku(txtTanggal.getText().toString(),txtMapel.getText().toString());

                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(AddFragment.this).commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add,container,false);
        txtTanggal = view.findViewById(R.id.input_tanggal);
        txtMapel=view.findViewById(R.id.input_mapel);

        btnAdd = view.findViewById(R.id.btn_update);
        btnCancel=view.findViewById(R.id.btn_cancel);


        return view;
    }

    private void addBuku(final String tanggal , final String mapel) {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menambahkan data Mata Ujian");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UjianAPI.URL_ADD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.hide(AddFragment.this).commit();


                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String>  params = new HashMap<String, String>();
                params.put("tanggal", tanggal);
                params.put("mapel", mapel);



                return params;
            }
        };

        queue.add(stringRequest);
    }
}