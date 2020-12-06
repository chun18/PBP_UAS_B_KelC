package com.andreas.projekuts.UnitTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andreas.projekuts.HomeActivity;
import com.andreas.projekuts.LoginActivity;
import com.andreas.projekuts.MainActivity;
import com.andreas.projekuts.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    public static final String TAG = "TAG";
    TextInputEditText username,password , nama, kelas;
    TextView login;
    Button register;
    FirebaseAuth auth;
    FirebaseFirestore database;
    String uid;
    RegisterPresenter presenter;
    private String CHANNEL_ID = "Channel 1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nama=findViewById(R.id.input_nama);
        kelas=findViewById(R.id.input_kelas);
        username=findViewById(R.id.input_username);
        password=findViewById(R.id.input_password);
        auth = FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();
        register=findViewById(R.id.btn_register);
        login=findViewById(R.id.txtLogin);
        presenter=new RegisterPresenter(this,new RegisterService());
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                presenter.onRegisterClicked();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
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
                .setContentText("Selamat  "+username.getText()+" anda berhasil Regis" )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
    @Override
    public String getNama() {
        return nama.getText().toString();
    }

    @Override
    public void showNamaError(String message) {
        nama.setError(message);
    }

    @Override
    public String getKelas() {
        return kelas.getText().toString();
    }

    @Override
    public void showKelasError(String message) {
        kelas.setError(message);
    }

    @Override
    public String getEmail() {
        return username.getText().toString();
    }

    @Override
    public void showEmailError(String message) {
        username.setError(message);
    }

    @Override
    public void showEmailInvalid(String message) {
        username.setError(message);
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void showPasswordError(String message) {
        password.setError(message);
    }

    @Override
    public void startLoginActivity() {
        new ActivityUtil(this).startLoginActivity();
    }

    @Override
    public void showRegisterError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

}