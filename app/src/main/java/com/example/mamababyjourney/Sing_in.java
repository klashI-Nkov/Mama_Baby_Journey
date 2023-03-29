package com.example.mamababyjourney;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.example.mamababyjourney.databinding.ActivitySingInBinding;

import java.util.Objects;

public class Sing_in extends AppCompatActivity
{
    ActivitySingInBinding binding;
    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        Objects.requireNonNull ( getSupportActionBar ( ) ).hide();
        binding = ActivitySingInBinding . inflate ( getLayoutInflater ( ) );
        setContentView ( binding . getRoot ( ) );

    }
}