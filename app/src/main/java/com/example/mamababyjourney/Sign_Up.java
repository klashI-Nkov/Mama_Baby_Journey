package com.example.mamababyjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;

import com.example.mamababyjourney.databinding.ActivitySignUpBinding;
import com.example.mamababyjourney.databinding.ActivitySingInBinding;

import java.util.Objects;

public class Sign_Up extends AppCompatActivity {

    ActivitySignUpBinding binding ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        getWindow ( ) . setFlags ( WindowManager. LayoutParams . FLAG_FULLSCREEN , WindowManager . LayoutParams . FLAG_FULLSCREEN ) ;
        Objects. requireNonNull ( getSupportActionBar ( ) ) . hide ( ) ;

        super . onCreate ( savedInstanceState ) ;
        binding = ActivitySignUpBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        binding . EmailEditText . addTextChangedListener ( watcher ) ;
        binding . passwordEditText . addTextChangedListener ( watcher ) ;

        binding . PassFloatHint . setVisibility ( View. INVISIBLE ) ;
        binding . EmailFloatHint . setVisibility ( View . INVISIBLE ) ;

    }

    TextWatcher watcher = new TextWatcher ( )
    {
        @Override
        public void onTextChanged ( CharSequence s , int start , int before , int count )
        {
            if ( !( binding . EmailEditText . getText ( ) . toString ( ) . isEmpty ( ) ) )
            { binding . EmailFloatHint . setVisibility ( View . VISIBLE ) ; }
            else
            { binding . EmailFloatHint . setVisibility ( View . INVISIBLE ) ; }

            if ( !( binding . passwordEditText . getText ( ) . toString ( ) . isEmpty ( ) ) )
            { binding . PassFloatHint . setVisibility ( View . VISIBLE ) ; }
            else
            { binding . PassFloatHint . setVisibility ( View . INVISIBLE ) ; }

        }

        @Override
        public void beforeTextChanged ( CharSequence s , int start , int count , int after )
        {

        }

        @Override
        public void afterTextChanged ( Editable s )
        {

        }
    };

}