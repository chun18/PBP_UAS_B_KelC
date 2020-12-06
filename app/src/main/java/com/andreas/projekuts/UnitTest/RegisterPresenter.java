package com.andreas.projekuts.UnitTest;

import java.util.regex.Pattern;

public class RegisterPresenter {
    private RegisterView view;
    private RegisterService service;
    private RegisterCallback callback;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public RegisterPresenter(RegisterView view, RegisterService service) {
        this.view = view;
        this.service = service;
    }
    public void onRegisterClicked() {
        if (view.getNama().isEmpty()) {
            view.showNamaError("Nama Tidak Boleh Kosong");
            return;
        }
        else if (view.getKelas().isEmpty()) {
            view.showKelasError("Kelas Tidak Boleh Kosong");
            return;
        }
        if (view.getEmail().isEmpty()) {
            view.showEmailError("Email Tidak Boleh Kosong");
            return;
        }else if (!EMAIL_ADDRESS_PATTERN.matcher(view.getEmail()).matches()) {
            view.showEmailInvalid("Email Tidak Valid");
            return;
        }else if (view.getPassword().isEmpty()) {
            view.showPasswordError("Password Tidak Boleh Kosong");
            return;
        }
        else{
            service.register(view, view.getNama(),view.getKelas(), view.getEmail(), view.getPassword(), new
                    RegisterCallback() {

                        @Override
                        public void onSuccess(boolean value) {
                            view.startLoginActivity();
                        }

                        @Override
                        public void onError() {
                            view.showRegisterError("Register Failed");
                        }
                    });
            return;
        }
    }

}
