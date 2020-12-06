package com.andreas.projekuts.UnitTest;

public interface RegisterView {
    String getNama();
    void showNamaError(String message);
    String getKelas();
    void showKelasError(String message);
    String getEmail();
    void showEmailError(String message);
    void showEmailInvalid(String message);
    String getPassword();
    void showPasswordError(String message);
    void startLoginActivity();
    void showRegisterError(String message);
    void showErrorResponse(String message);
}
