package com.andreas.projekuts;

import android.app.ProgressDialog;
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



public class UpdateFragment extends Fragment {
    TextInputEditText editTextTanggal,editTextMapel;
    Button saveBtn, deleteBtn,cancelBtn;
    Ujian ujian;
    private  int idUjian;

    public UpdateFragment() {

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                update(idUjian,editTextTanggal.getText().toString(),editTextMapel.getText().toString());
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(idUjian);
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.hide(UpdateFragment.this).commit();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateFragment.this).commit();
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update,container,false);
        ujian=(Ujian) getArguments().getSerializable("ujian");
        idUjian = ujian.getId();
        editTextTanggal=view.findViewById(R.id.input_tanggal);
        editTextMapel=view.findViewById(R.id.input_mapel);
        saveBtn=view.findViewById(R.id.btn_update);
        deleteBtn=view.findViewById(R.id.btn_delete);
        cancelBtn=view.findViewById(R.id.btn_cancel);

        try {
            if(ujian.getMapel()!=null && ujian.getTanggal()!=null){
                editTextTanggal.setText(ujian.getTanggal());
                editTextMapel.setText(ujian.getMapel());
            }else{
                editTextTanggal.setText("-");
                editTextMapel.setText("-");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return view;
    }


    private void update(final int idUjian , final  String tanggal , final String mapel){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Mengubah data ujian");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UjianAPI.URL_UPDATE + idUjian, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);

                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
                    transaction.hide(UpdateFragment.this).commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                /*
                    Disini adalah proses memasukan/mengirimkan parameter key dengan data value,
                    dan nama key nya harus sesuai dengan parameter key yang diminta oleh jaringan
                    API.
                */
                Map<String, String>  params = new HashMap<String, String>();

                params.put("tanggal", tanggal);
                params.put("mapel", mapel);



                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void delete(final int idUjian){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menghapus data ujian");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UjianAPI.URL_DELETE + idUjian , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);
                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}