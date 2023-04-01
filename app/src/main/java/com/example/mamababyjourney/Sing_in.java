package com.example.mamababyjourney;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import com.example.mamababyjourney.databinding.ActivitySingInBinding;
import java.util.Objects;

public class Sing_in extends AppCompatActivity
{
    ActivitySingInBinding binding;
    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        getWindow ( ) . setFlags ( WindowManager. LayoutParams . FLAG_FULLSCREEN , WindowManager . LayoutParams . FLAG_FULLSCREEN  ) ;
        super.onCreate ( savedInstanceState );
        Objects.requireNonNull ( getSupportActionBar ( ) ).hide();
        binding = ActivitySingInBinding . inflate ( getLayoutInflater ( ) );
        setContentView ( binding . getRoot ( ) );

    }



}