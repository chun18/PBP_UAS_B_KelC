package com.andreas.projekuts.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andreas.projekuts.HomeActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.andreas.projekuts.API.BukuAPI;
import com.andreas.projekuts.Adapters.AdapterBuku;

import com.andreas.projekuts.Models.Buku;

import com.andreas.projekuts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class ViewsBuku extends Fragment{

    private RecyclerView recyclerView;
    private AdapterBuku adapter;
    private List<Buku> listBuku;
    private View view;
    private Button btnBack, btnTambah;
    private SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_views_buku, container, false);
        btnBack= view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(view.getContext(),HomeActivity.class));
            }
        });

        btnTambah = view.findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serVisibilityForButton(true);

                Bundle data = new Bundle();
                data.putString("status", "tambah");
                TambahEditBuku tambahEditBuku = new TambahEditBuku();
                tambahEditBuku.setArguments(data);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager .beginTransaction()
                        .replace(R.id.frame_view_buku, tambahEditBuku)
                        .commit();


            }
        });

        SearchView searchView = (SearchView) view.findViewById(R.id.searchBook);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        loadDaftarBuku();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bar_buku, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.btnSearch);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.btnSearch).setVisible(true);
        menu.findItem(R.id.btnAdd).setVisible(true);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnAdd) {
            Bundle data = new Bundle();
            data.putString("status", "tambah");
            TambahEditBuku tambahEditBuku = new TambahEditBuku();
            tambahEditBuku.setArguments(data);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager .beginTransaction()
                    .replace(R.id.frame_view_buku, tambahEditBuku)
                    .commit();

            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void serVisibilityForButton(boolean shouldHide){
        if(shouldHide){
            btnBack.setVisibility(View.GONE);
            btnTambah.setVisibility(View.GONE);
        }else{
            btnBack.setVisibility(View.VISIBLE);
            btnTambah.setVisibility(View.VISIBLE);
        }
    }

    public void loadDaftarBuku(){
        setAdapter();
        getBuku();
    }




    public void setAdapter(){
        getActivity().setTitle("Data Buku");
        listBuku = new ArrayList<Buku>();
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new AdapterBuku(view.getContext(), listBuku, new AdapterBuku.deleteItemListener() {
            @Override
            public void deleteItem(Boolean delete) {
                if(delete){
                    loadDaftarBuku();
                }
            }
        });

        GridLayoutManager layoutManager = getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                ? new GridLayoutManager(getContext(), 2)
                : new GridLayoutManager(getContext(), 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }




    public void getBuku() {
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        //Meminta tanggapan string dari URL yang telah disediakan menggunakan method GET
        //untuk request ini tidak memerlukan parameter
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data buku");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, BukuAPI.URL_SELECT
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengambil data response json object yang berupa data buku
                    JSONArray jsonArray = response.getJSONArray("dataBuku");

                    if(!listBuku.isEmpty())
                        listBuku.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //Mengubah data jsonArray tertentu menjadi json Object
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);


                        int idBuku                  = Integer.parseInt(jsonObject.optString("idBuku"));
                        String namaBuku             = jsonObject.optString("namaBuku");
                        String pengarang        = jsonObject.optString("pengarang");
                        double harga            = Double.parseDouble(jsonObject.optString("harga"));
                        String gambar           = jsonObject.optString("gambar");


                        //Membuat objek user
                        Buku buku = new Buku(idBuku, namaBuku, pengarang, harga, gambar);

                        //Menambahkan objek user tadi ke list user
                        listBuku.add(buku);
                    }
                    adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), response.optString("message"),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}
