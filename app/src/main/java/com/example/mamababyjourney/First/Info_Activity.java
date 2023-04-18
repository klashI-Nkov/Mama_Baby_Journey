package com.example.mamababyjourney.First;

import com.example.mamababyjourney.R;
import com      . example   . mamababyjourney.databinding.ActivityInfoBinding;
import androidx . appcompat . app.AppCompatActivity;
import androidx . core      . content.ContextCompat;
import android  . content   . res.Configuration;
import android  . content   . res.Resources;
import android  . os        . Bundle;
import java     . util      . Objects;

public class Info_Activity extends AppCompatActivity
{
    ActivityInfoBinding binding ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {

        Objects . requireNonNull ( getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivityInfoBinding . inflate ( getLayoutInflater ( ) ) ;
        super . onCreate ( savedInstanceState ) ;
        setContentView ( binding . getRoot ( ) ) ;

        Resources resources = getResources ( ) ;
        Configuration configuration = resources . getConfiguration ( ) ;

        int currentModeType = configuration . uiMode & Configuration . UI_MODE_NIGHT_MASK ;

        if ( currentModeType == Configuration . UI_MODE_NIGHT_YES )
            getWindow ( ) . setStatusBarColor ( ContextCompat . getColor ( this , R. color . c8 ) ) ;
        else
            getWindow ( ) . setStatusBarColor ( ContextCompat . getColor ( this , R . color . c7 ) ) ;

    }

}