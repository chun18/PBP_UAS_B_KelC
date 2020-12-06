package com.andreas.projekuts.UnitTest;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterService {
    String uid;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    public void register(final RegisterView view, String nama, String kelas, String email, String password,
                         final RegisterCallback callback){
        final FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            auth.getCurrentUser().sendEmailVerification();
                            uid = auth.getCurrentUser().getUid();
                            DocumentReference documentReference = database.collection("users").document(uid);

                            Map<String,Object> user = new HashMap<>();
                            user.put("Nama",nama);
                            user.put("Kelas",kelas);
                            user.put("Email",email);
                            documentReference.set(user);
                            callback.onSuccess(true);
                        }else
                        {
                            callback.onError();
                            view.showRegisterError("Register Failed");
                        }
                    }
                });

    }
    public Boolean getValid(final RegisterView view, String nama, String kelas, String email, String
            password){
        final Boolean[] bool = new Boolean[1];
        register(view, nama, kelas ,email, password, new RegisterCallback(){

            @Override
            public void onSuccess(boolean value) {
                bool[0] = true;
            }
            @Override
            public void onError() {
                bool[0] = false;
            }
        });
        return bool[0];
    }
}
