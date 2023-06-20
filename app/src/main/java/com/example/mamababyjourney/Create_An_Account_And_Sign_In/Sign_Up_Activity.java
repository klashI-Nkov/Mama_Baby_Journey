package com.example.mamababyjourney.Create_An_Account_And_Sign_In;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mamababyjourney.Create_An_Account_And_Sign_In.Info.Doctor_Data_Activity;
import com.example.mamababyjourney.R;
import com.example.mamababyjourney.databinding.ActivitySignUpBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

@SuppressWarnings ( { "StatementWithEmptyBody" , "deprecation" } )
@SuppressLint ( { "WrongConstant" , "ShowToast", "ClickableViewAccessibility" , "SuspiciousIndentation" } )

public class Sign_Up_Activity extends AppCompatActivity
{

    ActivitySignUpBinding binding ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags ( WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS , WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull ( getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivitySignUpBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        Buttons ( ) ;

    }

    // هاد الفنكشن عمله عشان اغير لون التكست لل Radio buttons حسب الثيم لانه حاولت اطبق عليهم ستايل يغيير لون الخط حسب الثيم ما زبط فانجبرت استعمل هاي الطريقه
    @Override
    public void onConfigurationChanged ( @NonNull Configuration newConfig )
    {
        super . onConfigurationChanged ( newConfig ) ;

        // الشرط الي جوا الاف معناه انه روح جيب الثيم الحالي و شوف اذا هو دارك ادخل و نفذ الي جوا الاف واعطي الخط تاع ال Radio buttons الوان الثيم الدارك اذا الثيم الحالي مش دارك اعطيهم الوان الثيم الفاتح
        if ( ( getResources ( ) . getConfiguration ( ) . uiMode & Configuration . UI_MODE_NIGHT_MASK ) == Configuration . UI_MODE_NIGHT_YES )
        {
            binding . DoctorRBTN . setTextColor ( getResources ( ) . getColor ( R . color . R_B_T_C_N ) ) ;
            binding . MomRBTN . setTextColor ( getResources ( ) . getColor ( R . color . R_B_T_C_N ) ) ;
        }
        else
        {
            binding . DoctorRBTN . setTextColor ( getResources ( ) . getColor ( R . color . R_B_T_C_L ) ) ;
            binding . MomRBTN . setTextColor ( getResources ( ) . getColor ( R . color . R_B_T_C_L ) ) ;
        }
    }



    /*
        هاد الفنكشن صح انه جديد لكن هو وحده الجديد اما الاكواد الي جواته قديمه والجديد في الموضوع انه الاكواد الي داخله
        كانت تتنفذ عند حدث الضغط الخاص بالشي الي انا كاتب اله الكود

        والي عملته هلا اني خليتها تتنفذ عند حدث اللمس للشي الي انا كاتب الكود عشانه

        اول شي اسرع في الاستجابه و تاني شي اقل في الكود

        ثالث شي الخاصيه الي سالتي عنها الصبح كانت المشكله لما كانت تتنفذ عند الضغط على ال Radio Buttons تاعين الام و الدكتور

        الي كان يصير مرات ما كانت تشتغل و ما تتنفذ وبس استعملت حدث اللمس زبطت مباشره
     */
    private void Buttons ( )
    {

        // هاد مربوط مع ايقونة التسجيل بواسطة فيسبوك و ظيفته انه يفعل زر انشاء الحساب بعد ما تعطل لما الام او الدكتور كبسو عليه بدون ما يحددو الصفه
        binding . FacebookIcon . setOnTouchListener ( ( v , event ) ->
        {
            if ( event . getAction ( ) == MotionEvent.ACTION_DOWN )
            {
                // هون بقله اذا المسخدم مش محدد شو صفته ادخل الاف و عطل انشاء الحساب باستخدام قوقل اما اذا حدد فعادي خليه يستعمله
                if ( !binding.MomRBTN.isChecked ( ) && !binding.DoctorRBTN.isChecked ( ) )
                 Snack_Bar ( "يرجى تحديد صفتك قبل الانتقال الي  الصفحه التاليه" );
                else
                {

                }
            }
            return false ;
        } );

        // هاد بتنفذ عند لمس ال Radio Button تاع الدكتورو ظيفته انه يفعل زر انشاء الحساب بعد ما تعطل لما الدكتور كبس عليه بدون ما يحدد صفته
        binding . DoctorRBTN . setOnTouchListener ( ( v , event ) ->
        {
            if ( event . getAction ( ) == MotionEvent.ACTION_DOWN )
            {
                binding . DoctorRBTN . setChecked ( true ) ;
                binding . singUpBTN . setEnabled ( true ) ;
            }
            return false;
        } );

        // هاد مربوط مع ايقونة التسجيل بواسطة قوقل و ظيفته انه يفعل زر انشاء الحساب بعد ما تعطل لما الام او الدكتور كبسو عليه بدون ما يحددو الصفه
        binding . GoogleIcon . setOnTouchListener ( ( v , event ) ->
        {
            if ( event . getAction ( ) == MotionEvent.ACTION_DOWN )
            {
                // هون بقله اذا المسخدم مش محدد شو صفته ادخل الاف و عطل انشاء الحساب باستخدام قوقل اما اذا حدد فعادي خليه يستعمله
                if ( ! binding . MomRBTN . isChecked ( ) && ! binding . DoctorRBTN . isChecked ( ) )
                    Snack_Bar ( "يرجى تحديد صفتك قبل الانتقال الي  الصفحه التاليه" );
                else
                {

                }
            }
            return false;
        } );

        // هاد بتنفذ عند لمس زر انشاء الحساب و وظفيته انه ينقل المستخدم للشاشه الي بعد شاشة انشاء الحساب
        binding . singUpBTN . setOnTouchListener ( ( v , event ) ->
        {
            if ( event . getAction ( ) == MotionEvent.ACTION_DOWN )
            {
                /*
                    هون في حالة المستخدم لما يسجل باستعمال الايميل كان ام او طبيب لو ما حدد صفته يعطل زر انشاء الحساب و ما يخليه قادر يكمل للشاشه الي
                    بعدها وبعرض اله مسج انه لازم يحدد صفته قبل ما يكمل اما اذا كان محدد فعادي بخليه يكمل من دون اي مشاكل
                */
                if ( ! binding . MomRBTN . isChecked ( ) && ! binding . DoctorRBTN . isChecked ( ) )
                {
                    binding . singUpBTN . setEnabled ( false ) ;
                    Snack_Bar ( "يرجى تحديد صفتك قبل الانتقال الي  الصفحه التاليه" );
                }
                else
                binding . singUpBTN . setEnabled ( true ) ;

                // هون كونه بس الام مطلوب منها الاسم بس فما رح يكون في الها بيانات تعبيها لهيك حاكي اله اذا المستخدم كان دكتور انقله لشاشة البيانات عشان يعبي البيانات اللازمه
                if ( binding . DoctorRBTN . isChecked ( ) )
                {
                    Intent intent = new Intent ( this , Doctor_Data_Activity . class ) ;
                    startActivity ( intent ) ;
                }
            }
            return false;
        } );

        // هاد بتنفذ عند لمس ال Radio Button تاع الام و ظيفته انه يفعل زر انشاء الحساب بعد ما تعطل لما الام كبست عليه بدون من تحدد صفتها
        binding . MomRBTN . setOnTouchListener ( ( v , event ) ->
        {
            if ( event . getAction ( ) == MotionEvent.ACTION_DOWN )
            {
                binding . MomRBTN . setChecked ( true ) ;
                binding . singUpBTN . setEnabled ( true ) ;
            }
            return false;
        } );

    }

    private void Snack_Bar ( String Message )
    {

        Snackbar snackbar = Snackbar . make ( binding . Constraint , Message , 7000 ) ;

        snackbar . getView ( ) . setBackgroundTintList ( ColorStateList . valueOf ( ContextCompat. getColor ( this , R . color . Snack_bar_BG_Color ) ) ) ;

        View snackbarView = snackbar . getView ( ) ;
        TextView textView = snackbarView . findViewById ( com . google . android . material . R . id . snackbar_text ) ;

        textView . setSingleLine ( false ) ;

        textView . setTextColor ( ContextCompat . getColor ( this , R . color . white ) ) ;

        textView . setTextSize ( 15 ) ;

        textView . setTextAlignment ( View . TEXT_ALIGNMENT_CENTER ) ;

        ViewGroup . MarginLayoutParams marginLayoutParams = ( ViewGroup.MarginLayoutParams ) snackbarView . getLayoutParams ( ) ;

        marginLayoutParams . setMargins
        (
            marginLayoutParams  . leftMargin  ,
            marginLayoutParams  . topMargin   ,
            marginLayoutParams . rightMargin ,
            65
        ) ;

        snackbarView . setLayoutParams ( marginLayoutParams ) ;

        snackbar . show ( ) ;
    }


}