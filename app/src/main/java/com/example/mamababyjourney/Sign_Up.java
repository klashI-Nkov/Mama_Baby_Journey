package com.example.mamababyjourney;

import androidx.appcompat.app.AppCompatActivity;
import android . view    . WindowManager;
import android . text    . TextWatcher;
import android . text    . Editable;
import java    . util    . Objects;
import android . content . Intent;
import android . os      . Bundle;
import android . view    . View;


import com.example.mamababyjourney.databinding.ActivitySignUpBinding;


public class Sign_Up extends AppCompatActivity
{

    ActivitySignUpBinding binding ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        getWindow ( ) . setFlags ( WindowManager . LayoutParams . FLAG_FULLSCREEN , WindowManager . LayoutParams . FLAG_FULLSCREEN ) ;
        Objects. requireNonNull ( getSupportActionBar ( ) ) . hide ( ) ;

        super . onCreate ( savedInstanceState ) ;
        binding = ActivitySignUpBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        binding . UEmailEditText          . addTextChangedListener ( watcher ) ;
        binding . UPasswordEditText       . addTextChangedListener ( watcher ) ;
        binding . ConfirmPasswordEditText . addTextChangedListener ( watcher ) ;

        binding . UEmailFloatHint      . setVisibility ( View . INVISIBLE ) ;
        binding . UPassFloatHint       . setVisibility ( View . INVISIBLE ) ;
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

    public void Go_to_Info ( View view )
    {
        Intent intent = new Intent ( this , Info_Activity . class ) ;
        startActivity ( intent ) ;
    }



}