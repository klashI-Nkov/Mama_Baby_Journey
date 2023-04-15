package com . example . mamababyjourney ;

import com      . example   . mamababyjourney.databinding.ActivitySingInBinding;
import androidx . appcompat . app.AppCompatActivity;
import android  . view      . WindowManager;
import android  . text      . TextWatcher;
import android  . text      . Editable;
import java     . util      . Objects;
import android  . os        . Bundle;
import android  . view      . View;

public class Sing_in extends AppCompatActivity
{
    ActivitySingInBinding binding ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        getWindow ( ) . setFlags ( WindowManager . LayoutParams . FLAG_FULLSCREEN , WindowManager . LayoutParams . FLAG_FULLSCREEN ) ;
        Objects . requireNonNull ( getSupportActionBar ( ) ) . hide ( ) ;

        super . onCreate ( savedInstanceState ) ;
        binding = ActivitySingInBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        binding . EmailEditText . addTextChangedListener ( watcher ) ;
        binding . passwordEditText . addTextChangedListener ( watcher ) ;

        binding . PassFloatHint . setVisibility ( View . INVISIBLE ) ;
        binding . EmailFloatHint . setVisibility ( View . INVISIBLE ) ;

    }

    TextWatcher watcher = new TextWatcher ( )
    {
        @Override
        public void onTextChanged ( CharSequence s , int start , int before , int count )
        {
            if ( !( binding . EmailEditText . getText ( ) . toString ( ) . isEmpty ( ) ) )
                   binding . EmailFloatHint . setVisibility ( View . VISIBLE ) ;
            else
                   binding . EmailFloatHint . setVisibility ( View . INVISIBLE ) ;

            if ( !( binding . passwordEditText . getText ( ) . toString ( ) . isEmpty ( ) ) )
                   binding . PassFloatHint . setVisibility ( View . VISIBLE ) ;
            else
                   binding . PassFloatHint . setVisibility ( View . INVISIBLE ) ;

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