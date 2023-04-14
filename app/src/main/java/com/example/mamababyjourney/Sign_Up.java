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

        binding . UEmailEditText . addTextChangedListener ( watcher ) ;
        binding . UPasswordEditText . addTextChangedListener ( watcher ) ;
        binding . ConfirmPasswordEditText . addTextChangedListener ( watcher ) ;

        binding . UEmailFloatHint . setVisibility ( View. INVISIBLE ) ;
        binding . UPassFloatHint . setVisibility ( View . INVISIBLE ) ;
        binding . ConfirmPassFloatHint . setVisibility ( View . INVISIBLE ) ;

    }

    TextWatcher watcher = new TextWatcher ( )
    {
        @Override
        public void onTextChanged ( CharSequence s , int start , int before , int count )
        {
            if ( !( binding . UEmailEditText . getText ( ) . toString ( ) . isEmpty ( ) ) )
                    binding . UEmailFloatHint . setVisibility ( View . VISIBLE ) ;
            else
                    binding . UEmailFloatHint . setVisibility ( View . INVISIBLE ) ;

            if ( !( binding . UPasswordEditText . getText ( ) . toString ( ) . isEmpty ( ) ) )
                    binding . UPassFloatHint . setVisibility ( View . VISIBLE ) ;
            else
                    binding . UPassFloatHint . setVisibility ( View . INVISIBLE ) ;

            if ( !( binding . ConfirmPasswordEditText . getText ( ) . toString ( ) . isEmpty ( ) ) )
                    binding . ConfirmPassFloatHint . setVisibility ( View . VISIBLE ) ;
            else
                    binding . ConfirmPassFloatHint . setVisibility ( View . INVISIBLE ) ;

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