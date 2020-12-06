package com.andreas.projekuts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.andreas.projekuts.API.BukuAPI;
import com.andreas.projekuts.API.UjianAPI;
import com.andreas.projekuts.Adapters.UjianRecyclerViewAdapter;
import com.andreas.projekuts.Models.Ujian;
import com.andreas.projekuts.Views.ViewsBuku;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button btnAdd, logout,profile, geo;
    RecyclerView recyclerView;
    UjianRecyclerViewAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    FirebaseAuth auth;
    SearchView search;
    List<Ujian> ujianList;
    private String CHANNEL_ID = "Channel 1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ujianList=new ArrayList<>();
        geo=findViewById(R.id.btn_geo);
        geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewsBuku viewsBuku = new ViewsBuku();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, viewsBuku)
                        .commit();
            }
        });
        btnAdd=findViewById(R.id.btn_add);
        logout=findViewById(R.id.btn_Logout);
        profile=findViewById(R.id.btn_Profil);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        });
        auth=FirebaseAuth.getInstance();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                createNotificationChannel();
                addNotification();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.user_rv);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        getUjian();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFragment addfragment = new AddFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, addfragment)
                        .commit();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUjian();
                refreshLayout.setRefreshing(false);
            }
        });
        adapter = new UjianRecyclerViewAdapter(this, ujianList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    private  void getUjian() {
        RequestQueue queue = Volley.newRequestQueue(this);

        //Meminta tanggapan string dari URL yang telah disediakan menggunakan method GET
        //untuk request ini tidak memerlukan parameter
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data ujian");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, UjianAPI.URL_SELECT
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    //Mengambil data response json object yang berupa data mahasiswa
                    JSONArray jsonArray = response.getJSONArray("data");

                    if(!ujianList.isEmpty())
                        ujianList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //Mengubah data jsonArray tertentu menjadi json Object
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        int idUjian              = jsonObject.optInt("id");
                        String tanggal            = jsonObject.optString("tanggal");
                        String mapel    = jsonObject.optString("mapel");


                        //Membuat objek user
                        Ujian ujian = new Ujian(idUjian,tanggal, mapel);

                        //Menambahkan objek user tadi ke list user
                        ujianList.add(ujian);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Toast.makeText(HomeActivity.this, response.optString("message"),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(HomeActivity.this, error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void addNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Education Center")
                .setContentText("Sampai Jumpa ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}