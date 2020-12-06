package com.andreas.projekuts.UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterPresenterTest {
    @Mock
    private RegisterView view;
    @Mock
    private RegisterService service;
    private RegisterPresenter presenter;
    @Before
    public void setUp() throws Exception {
        presenter = new RegisterPresenter(view, service);
    }
    @Test
    public void shouldShowErrorMessageWhenNamaisEmpty() throws Exception {
        when(view.getNama()).thenReturn("");
        System.out.println("nama : "+view.getNama());
        presenter.onRegisterClicked();

        verify(view).showNamaError("Nama Tidak Boleh Kosong");
    }
    @Test
    public void ErrorMessageWhenKelasisEmpty() throws Exception {
        when(view.getNama()).thenReturn("andreas");
        System.out.println("nama : "+view.getNama());
        when(view.getKelas()).thenReturn("");
        System.out.println("kelas : "+view.getKelas());
        presenter.onRegisterClicked();

        verify(view).showKelasError("Kelas Tidak Boleh Kosong");
    }
    @Test
    public void ErrorMessageWhenEmailisEmpty() throws Exception {
        when(view.getNama()).thenReturn("andreas");
        System.out.println("nama : "+view.getNama());
        when(view.getKelas()).thenReturn("SMA");
        System.out.println("kelas : "+view.getKelas());
        when(view.getEmail()).thenReturn("");
        System.out.println("email : "+view.getEmail());
        presenter.onRegisterClicked();

        verify(view).showEmailError("Email Tidak Boleh Kosong");
    }
    @Test
    public void ErrorMessageWhenEmailisInvalid() throws Exception {
        when(view.getNama()).thenReturn("andreas");
        System.out.println("nama : "+view.getNama());
        when(view.getKelas()).thenReturn("SMA");
        System.out.println("kelas : "+view.getKelas());
        when(view.getEmail()).thenReturn("andreaslai439");
        System.out.println("email : "+view.getEmail());
        presenter.onRegisterClicked();

        verify(view).showEmailInvalid("Email Tidak Valid");
    }
    @Test
    public void ErrorMessageWhenPasswordisEmpty() throws Exception {
        when(view.getNama()).thenReturn("andreas");
        System.out.println("nama : "+view.getNama());
        when(view.getKelas()).thenReturn("SMA");
        System.out.println("kelas : "+view.getKelas());
        when(view.getEmail()).thenReturn("andreaslai439@gmail.com");
        System.out.println("email : "+view.getEmail());
        when(view.getPassword()).thenReturn("");
        System.out.println("password : "+view.getPassword());
        presenter.onRegisterClicked();

        verify(view).showPasswordError("Password Tidak Boleh Kosong");
    }
    @Test
    public void shouldStartLoginActivitywhenAllCorrect() throws
            Exception {
        when(view.getNama()).thenReturn("andreas");
        System.out.println("nama : "+view.getNama());
        when(view.getKelas()).thenReturn("SMA");
        System.out.println("kelas : "+view.getKelas());
        when(view.getEmail()).thenReturn("andreaslai439@gmail.com");
        System.out.println("email : "+view.getEmail());
        when(view.getPassword()).thenReturn("111111");
        System.out.println("password : "+view.getPassword());
        when(service.getValid(view, view.getNama(),view.getKelas(),view.getEmail(),
                view.getPassword())).thenReturn(true);
        System.out.println("Hasil : "+service.getValid(view,view.getNama(),view.getKelas(),view.getEmail(),
                view.getPassword()));
        presenter.onRegisterClicked();
//        verify(view).startLoginActivity();
    }
}