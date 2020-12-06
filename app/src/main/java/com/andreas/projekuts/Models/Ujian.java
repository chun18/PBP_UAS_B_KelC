package com.andreas.projekuts.Models;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.SerializedName;


public class Ujian implements Serializable{
    private int id;
    String tanggal , mapel;

    public Ujian(int id, String tanggal, String mapel){
        this.id=id;
        this.tanggal=tanggal;
        this.mapel=mapel;
    }
    public Ujian(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getMapel() { return mapel; }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }


}
