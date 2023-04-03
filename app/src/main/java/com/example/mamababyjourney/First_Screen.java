package com.example.mamababyjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.util.Objects;

public class First_Screen extends AppCompatActivity
{

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        getWindow ( ) . setFlags ( WindowManager. LayoutParams . FLAG_FULLSCREEN , WindowManager . LayoutParams . FLAG_FULLSCREEN  ) ;
        Objects.requireNonNull ( getSupportActionBar ( ) ).hide();
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_first_screen );
    }

    public void Go_to_Sing_In ( View view )
    {
        Intent intent = new Intent ( this , Sing_in . class ) ;
        startActivity ( intent ) ;
    }

    public void Go_to_Sing_Up ( View view )
    {
        Intent intent = new Intent ( this , Sign_Up . class ) ;
        startActivity ( intent ) ;
    }

}