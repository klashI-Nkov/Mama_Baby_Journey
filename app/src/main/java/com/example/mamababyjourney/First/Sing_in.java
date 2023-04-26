package com.example.mamababyjourney.First;

import com      . example   . mamababyjourney.databinding.ActivitySingInBinding;
import androidx . appcompat . app.AppCompatActivity;
import android  . view      . WindowManager;
import java     . util      . Objects;
import android  . os        . Bundle;

public class Sing_in extends AppCompatActivity
{
    ActivitySingInBinding binding ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        getWindow ( ) . setFlags ( WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS , WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull ( getSupportActionBar ( ) ) . hide ( ) ;

        super . onCreate ( savedInstanceState ) ;
        binding = ActivitySingInBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;
    }
}