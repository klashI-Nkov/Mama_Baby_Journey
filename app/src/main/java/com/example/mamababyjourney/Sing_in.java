package com.example.mamababyjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import com.example.mamababyjourney.databinding.ActivitySingInBinding;

import java.util.Objects;

public class Sing_in extends AppCompatActivity
{
    ActivitySingInBinding binding;
    int x = 1 ;
    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        getWindow ( ) . setFlags ( WindowManager. LayoutParams . FLAG_FULLSCREEN , WindowManager . LayoutParams . FLAG_FULLSCREEN  ) ;
        Objects.requireNonNull ( getSupportActionBar ( ) ).hide();

        super.onCreate ( savedInstanceState );
        binding = ActivitySingInBinding . inflate ( getLayoutInflater ( ) );
        setContentView ( binding . getRoot ( ) );

        binding . EmailEditText . addTextChangedListener ( watcher ) ;
        binding . passwordEditText . addTextChangedListener ( watcher ) ;
        /*binding.BTN.setOnClickListener ( v ->
        {

            if (x==7)
                x=1;

            switch ( x )
            {
                case 1:
                {
                    binding.con.setBackgroundColor (  getResources () .getColor ( R.color.c1 ) );
                    binding.textView5.setText ( "1" );
                    break;
                }

                case 2:
                {
                    binding.con.setBackgroundColor (  getResources () .getColor ( R.color.c2 ) );
                    binding.textView5.setText ( "2" );
                    break;
                }

                case 3:
                {
                    binding.con.setBackgroundColor (  getResources () .getColor ( R.color.c3 ) );
                    binding.textView5.setText ( "3" );
                    break;
                }

                case 4:
                {
                    binding.con.setBackgroundColor (  getResources () .getColor ( R.color.c4 ) );
                    binding.textView5.setText ( "4" );
                    break;
                }

                case 5:
                {
                    binding.con.setBackgroundColor (  getResources () .getColor ( R.color.c5 ) );
                    binding.textView5.setText ( "5" );
                    break;
                }

                case 6:
                {
                    binding.con.setBackgroundColor (  getResources () .getColor ( R.color.c6 ) );
                    binding.textView5.setText ( "6" );
                    break;
                }

            }

            x++;

        } );*/

        binding.PassFloatHint.setVisibility ( View.INVISIBLE );
        binding.EmailFloatHint.setVisibility ( View.INVISIBLE );

    }

    TextWatcher watcher = new TextWatcher ( )
    {
        @Override
        public void onTextChanged ( CharSequence s,int start,int before,int count )
        {
            if ( ! ( binding.EmailEditText.getText ().toString ().isEmpty () ) )
                binding.EmailFloatHint.setVisibility ( View.VISIBLE );
            else
                binding.EmailFloatHint.setVisibility ( View.INVISIBLE );

            if ( ! ( binding.passwordEditText.getText ().toString ().isEmpty () ) )
                binding.PassFloatHint.setVisibility ( View.VISIBLE );
            else
                binding.PassFloatHint.setVisibility ( View.INVISIBLE );

        }

        @Override
        public void beforeTextChanged ( CharSequence s,int start,int count,int after ){

        }

        @Override
        public void afterTextChanged ( Editable s ){

        }
    } ;

}