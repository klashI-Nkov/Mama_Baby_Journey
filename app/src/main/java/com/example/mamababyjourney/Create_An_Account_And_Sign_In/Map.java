package com.example.mamababyjourney.Create_An_Account_And_Sign_In;

import com.example.mamababyjourney.databinding.ActivityMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.gms.maps.model.LatLng;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import androidx.core.content.ContextCompat;
import android.content.res.ColorStateList;
import android.content.BroadcastReceiver;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.annotation.SuppressLint;
import com.example.mamababyjourney.R;
import android.content.IntentFilter;
import androidx.annotation.NonNull;
import android.widget.SearchView;
import android.location.Geocoder;
import android.location.Address;
import android.widget.TextView;
import android.content.Context;
import android.net.NetworkInfo;
import android.content.Intent;
import android.graphics.Color;
import android.net.Network;
import java.io.IOException;
import android.os.Bundle;
import android.view.View;
import java.util.List;

@SuppressWarnings ( { "unused" , "ConstantConditions" , "SpellCheckingInspection" } )

@SuppressLint ( "MissingPermission" )

public class Map extends FragmentActivity implements OnMapReadyCallback
{


    /*
        قبل كل شي و قبل ما تبلشي تقري تعليقات ي منار بدي اقلك كيف تمشي في قراية التعليقات

        اول شي بتكبسي ctrl + A و بتحددي كل الكود بعدها بتضغطي ctrl + shift مع اشارة الناقص الي عند الارقام عشان تسكري الكود كامل

        بعد ما تعملي هيك بتبلشي تقري شو مكتوب قبل كل فنكشن وتعرفي كل واحد شو بساوي و شو بعمل من الي مكتوب فوقه

       بعد ما تخلصي قراية كل شي مكتوب قبل كل الفنشكنات بترجعي لاول الكود و بتفتحي اول فنشكن فيه و بتلبشي تقري في الكومنتات الي جواته لحتى تفهمي اكثر عن الكود وتعملي هيك مع كل الفنشكن لحد ما تخلصيهم

       و ضروري جدا ي منار تلتزمي بالطريقه الي انا حكيت عنها لقرائة الكومنتات لانه غير هيك ما رح تفهمي

       وضروري ي منار تعملي هاد الحكي

      اول شي بتكبسي ctrl + A و بتحددي كل الكود بعدها بتضغطي ctrl + shift مع اشارة الناقص الي عند الارقام عشان تسكري الكود كامل

       بعد ما تعملي هيك بتبلشي تقري شو مكتوب قبل كل فنكشن وتعرفي كل واحد شو بساوي و شو بعمل من الي مكتوب فوقه

        لانه لو ما عملتيها ما رح تقدري تفهمي كيف الكود ماشي حتى لو قريت كل الكومنتات


     */


    // اول شي المتغيرات الي مش حاط الها شرح في كومنت بتلاقي شرحها في الفنكشن الي انا مستعملها فيه و بكون عامل هيك لانه شرحها بكون طويل شوي

    private ConnectivityManager connectivity_Manager ;

    private ConnectivityManager . NetworkCallback network_Callback ;

    private BroadcastReceiver location_Receiver = null ;

    private LocationManager location_Manager ;

    // هاد المتغير وظيفة انه يكون true في حالة كان النت شغال و false في حالة كان طافي
    private boolean is_Internet_Connected ;

    // هاد المتغير وظيفة انه يكون true في حالة كان الموقع شغال و false في حالة كان طافي
    private boolean is_Location_Enabled ;

    // هدول عشان اخزن فيهم احداثيات مكان العمل
    private double longitude , latitude ;

    /*
        طبعا هاد غني عن التعريف و هو الي بربط كود الجافا بالتصميم ما بده شرح على ما اعتقد اذا حابه تعرفي عنه معلومات اكثر انسخي
        السطر كامل و روحي ل chat gpt  واكتبي اله بالحرف الواحد

        شرح لكل شي في هذا السطر بالتفصيل الممل و ماذا يفعل هذا السطر بالتفصيل الممل بعدها حطي السطر

        هيك بتحكي اليه بالحرف الواحد اذا حابه تعرفي معلومات اكثر عنه

        ونفس الشي ممكن تعمليه مع اي سطر في الكود ما فهمتيه من شرحي
     */
    private ActivityMapBinding binding ;

    private GoogleMap mMap ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {

        super . onCreate ( savedInstanceState ) ;
        binding = ActivityMapBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        // هاد السطر عشان اخلي لون الستاتس بار الي بكون فيه الشبكه و البطاريه شفاف لكنه ما بصير شفاف بصير اسود ليه ما بعرف
        getWindow ( ) . setStatusBarColor ( Color . TRANSPARENT ) ;

        // هون استدعاء الفنكشن الي بتحقق من انه خدمة الموقع شغاله
        Check_Location ( ) ;

        // هون استدعاء الفنكشن الي بتحقق من انه الانترنت شغال
        Check_Internet ( ) ;

        // هون استدعاء الفنكشن الي بجهز الخريطه للعرض
        Map_Initialization ( ) ;

        // وهاد الفنكشن بستدعى لما المستخدم يضغط على زر البحث في الكيبورد لما يحط اسم الموقع الي بده يبحث عنه
        binding . SearchEditText . setOnQueryTextListener ( new SearchView . OnQueryTextListener ( )
        {
            @Override
            public boolean onQueryTextSubmit ( String query )
            {
                // هون بس يضغط يحث يستدعي الفنكشن الي مسؤول عن البحث عن المكان الي كتب اسمه في مربع البحث
                Find_a_place ( ) ;

                return false ;
            }

            @Override
            public boolean onQueryTextChange ( String newText )
            {
                return false ;
            }

        } ) ;

        // هاد الفنكشن بتنفذ لما نضغط على زر الحفظ
        binding.SaveWorkplaceOnMapBTN.setOnClickListener ( v ->
        {
            /*
                هون احنا بنعرف اوجبكت من الكلاس Intent و بخليه يساوي intent
                الشاشة الي قبلها و الي هي شاشة البيانات عشان لما نضغك على زر
                الحفظ يرجعنا لشاشة البيانات
             */
            Intent intent = getIntent ( );

            /*
                هون باستعمال المتغير intent الي مربوط في intent الشاشة الي
                قبل الشاشة الحالية استعدينا فننكشن ال putExtra و الي وظيفته
                باختصار هي انه يرجع داتا للشاشة الي قبل

                وباخد متغيرين الاول الي هو ال name و الثاني الي هو ال value

                وال name بمثل اشي اسمه ال key او المفتاح وهاد بنستعمله في
                الشاشة الي قبل الشاشة الحالية والي هي شاشة المعلومات عشان نحصل
                ال value الي انبعثت عن طريق هاد الفنكشن
             */
            intent.putExtra ( "latitude" , latitude );
            intent.putExtra ( "longitude" , longitude );

            /*
                ال setResult هي فنكشن تابعه للكلاس Activity
                بتاخد متغيرين الاول هو ال resultCode و الثاني هو الداتا او ال
                ؛ intent الي حطينا فيه البيانات الي بدنا نرجعها لشاشة المعلومات

                هسه ال resultCode هو عبارة عن متغير بشير الى انه العمليه
                كانت ناجحه ( هسه لتحت بقلك شو العمليه الي بقصدها ) وعشان
                نحكي اله هاد الشي انه العمليه كانت ناجحه لازم نستدعي فنكشن
                ال setResult عشان نحكي من خلاله انه العمليه كانت ناجحه و
                نعطيه قيمة ال resultCode تساوي RESULT_OK وهاي ال
                ؛RESULT_OK هي عبارة عن قيمه ثابته موجوده في كلاس ال Activity
                 وتساوي -1 وتشير الى انه العملية كانت ناجحه
                فلو جيتي حطيتي الماوس على ال RESULT_OK و كبستي
                 ؛ctrl مع ضغطه على الماوس رح يدخلك على كلاس ال Activity
                 و يعرض الك تعريف ال RESULT_OK ويفرجيكي قيمتها الي هي -1


                هسه المقصود بالعمليه هو عملية شرح شو هي ال RESULT_OK بواسطة ال Activity الحالية و الي هي الخارطه

                 وفي فنكشن ال setResult لما بدنا نرجع قيمه لل Activty الي
                 قبل لازم نستعمل ال RESULT_OK كقيمه لل resultCode عشان
                 في ال Activty الي قبل نقدر نحصل القيمه الي جايه من
                 intent ال Activity الحالية والي هي الخارطه في حالتنا هون
             */
            setResult ( RESULT_OK , intent );

            /*
                هاد الفنكشن finish تابع لكلاس ال Activity و وظيفته انه بعد ما نبكس على زر الحفظ يحرر شاشة
                الخارطه من الرام عشان ما يصير اشي اسمه memory leaks او resource leaks والي هم باختصار

               بسببو انه الشاشه الي خلصنا منها تضل في الرام تسحب فيها طول استخدام التطبيق و تؤدي بالنهايه
                انه الجهاز يصير يعلق

                و فنكشن ال finsh كانه بتحكي للنظام انا خلص طلعت من الخارطه ما تخلي اي شي بخصها في الرام
             */
            finish ( );
        });

    }

    // هاد الفنشكن مجرد ما تطلعي من الخارطه رح يتنفذ وما رح تفهمي الي جواته حتى لو قريتي الكومنتات لازم تشوقي الشرح الي في فنشكن ال Check_Location و الي في فنكشن ال Check_Internet بعدين ترجعي تقري الي جواته عشان تفهمي
    @Override
    protected void onPause ( )
    {
        super . onPause ( ) ;

        /*
            هسه في هدول السطرين الي تحت بنعمل الغاء تسجيل ل ال network_Callback من خلال المتغير
            connectivity_Manager

            لاستدعاء فنكشن

            ال unregisterNetworkCallback والي تابع لكلاس ConnectivityManager

            والغاء تسجيل لل location_Receiver من خلال الفنكشن unregisterReceiver

            ولازم نعمل الهم الغاء تسجيل لانه لو ما انعمل الهم الغاء تسجيل رح يصير عنا اشي بيعرف ب
            memory leaks او ممكن بيعرف ب  resource leaks

            بالعربي استنزاف المومري او استنزاف الموارد

            شو يعني استنزاف المومري او الموارد

            اول شي المقصود بالمومري هون مش الذاكره الي بتخزني فيها الملفات

            المومري الي بقصدها هي ال RAM او الرام ( ذاكرة الوصول العشوائيه )

            نجي لكيف بصير الاستنزاف

            هسه فنكنش ال onPause الي احنا حاليا فيه هي واحد من الفنكشن الخاصين بدورة حياة ال activity او
            ما يعرف activity life cycle و برضو ال onCreate تابعه لدورة حياة ال activity

            المهم مش موضوعنا دورة حياة ال activity الي بهمنا منها هو فنكشن ال onPause
            هاد الفنكشن كونه واحد من الفنكشن التابعه لدورة حياة ال activity فهو بمثل مرحلة من مراحلة دورة حياة
            ال activity من اسمه الي بعني بالعربي ( عند التوقف )

            عند توقف ايش بالزبط هسه قاعده بتحكي انا بقلك

            عند توقف ال activity عن العمل متى بصير هاد الشي

            بصير لما نطلع من الشاشه يعني في حالتنا هون لما نطلع من شاشة الخارطه و نرجع لشاشة معلومات مكان
            العمل وقتها بتشغل فنكشن ال onPause ليقوم بعملية الغاء تسجيل ال network_Callback و ال
            location_Receiver

            لحتى ما يصير عنها  resource leaks او memory leaks

           الي لو صارو بسببهم رح تتفل الرام و يصير الجهاز يعلق عشان هيك مجرد ما نطلع من الخارطه بدنا
           يلتغى تسجيلهم من الموموري مشان ما يضلو شغالين اثناء استعمال التطبيق و يسحبو الرام ويصير الجهاز يعلق

           المره الجايه بس بدنا ندخل على الخارطه بتم تسجيلهم من جديد من خلال هاد السطر

           registerReceiver ( location_Receiver , filter ) ;

           االخاص بتسجيل ال location_Receiver والي موجود في فنكشن ال Check_Location

           ومن خلال هاد السطر

           connectivity_Manager . registerDefaultNetworkCallback ( network_Callback ) ;

           الخاص بتسجيل ال network_Callback و الي موجود في فنكشن ال Check_Internet
         */
        connectivity_Manager . unregisterNetworkCallback ( network_Callback ) ;
        unregisterReceiver ( location_Receiver ) ;
    }

    // هاد الفنكشن هو الي بس تشتغل الخارطه بجهزها للعرض و مستعمله في فنكشن onCreate الي بستدعى او ما تفتحي الشاشه فرح يستدعى لما تفتحي الخارطه
    private void Map_Initialization ( )
    {

        // وملاحظه صغيره هون في الكومت الي تحت
        /*
            ال Fragment تمثل و تعتبر كجزء من ال Activity
            والفرق بينهم انه ال Activity هي عبارة عن شاشة كامله بينما ال Fragment هي عبارة عن جزء من الشاشة الكاملة

            يعني في المناقشه ان انسئلتي شو الفرق بينهم بتحكي الهم هاد الحكي

            والفرق بينهم انه ال Activity هي عبارة عن شاشة كامله بينما ال Fragment هي عبارة عن جزء من الشاشة الكاملة

            وذا حكو الك شو بتعرفي غير هيك بتحكي الهم بس هاد الي بعرفه لاني ما تعمقت في الفرق بينهم اكثر من هيك

            يعني لو جيتي على كبستي ctrl مع كبسة على الماوس على كلمة map هون R . id . map رح ينقلك على ال Fragment الي في التصميم و الي فيها الخارطه

            و حرف ال R هو عبارة عن كلاس في الاندرويد SDK بربط كود الجافا بالتصميم

            في حالة انسئلتي عنه في المناقشه

            والاندرويد SDK اختصار ل android softwaer devlopment kit

            اذا حابه تعرفي معلومات اكثر عنه بتروحي على chat gpt

            بتحكي اله بالحرف الواحد

            بالتفصيل الممل اشرح لي ما هو ال android softwaer devlopment kit وما هي فائدته و وظفيته و ماذا يفعل

            يعني خدي السطر الي فوق و حطيه في chat gpt وهاد عشان لو انسئلتي عنه تكوني عارفه شو هو
         */

        /*
            هسه هون بنعرف اوبجكت من الكلاس SupportMapFragment و بنسميه mapFragment و بنربطه ب ال Fragment الي بتمثل او معروض فيها الخريطة

            وال SupportMapFragment هي عبارة عن عنصر واجهة مستخدم بسمح للمبرمج بعرض خرائط قوقل في التطبيق


            واجهة مستخدم  هي الشاشة الي بتعرض للمستخدم و بستعملها او بمعنى اخر الشاشات لو انسئلتي
            في المناشقه شو هي واجهة المستخدم او شو هي ال ui الي هي اختصار ل  user interface
            فلو انسئلتي في المناقشه شو هي واجهة المستخدم او شو هي ال  user interface او شو هي ال UI
            بتحكي الهم هاد الحكي

            هي الشاشة الي بتعرض للمستخدم و بستعملها او بمعنى اخر الشاشات
         */
        SupportMapFragment mapFragment = ( SupportMapFragment ) getSupportFragmentManager ( ) . findFragmentById ( R . id . map ) ;

        // هون حطينا الاف عشان اذا كانت قيمة ال mapFragment  تساوي null ( لاشيء ) رح يصير خطا و يوقف التطبيق فلازم نتاكد انه مش null
        if (mapFragment!=null)

            /*
                هاد السطر وظيفته يحمل النا الخارطه من API الخرائط ولما حطينا قبله جملة اف
                حطينها عشان نتاكد انها ال Fragment في التصميم موجوده ومجهزه لحتى تنعرض فيها الخارطه
                لانه لو ما كانت موجوده او مش مجهزه للعرض الخارطه فيه رح يصير عنها خطا
                و ال getMapAsync هو فنكشن من الكلاس SupportMapFragment وهو المسؤول عن تحميل الخارطه من API الخرائط
             */
            mapFragment . getMapAsync ( this ) ;
    }

    // هاد الفنكشن بتم استدعاؤه تلقائيا بعد ما يتنفذ الي جوا فنكشن ال Map_Initialization وبياخد اوجبكت من نوع GoogleMap
    @Override
    public void onMapReady ( @NonNull GoogleMap googleMap )
    {
        // هون بنقله والله الاوبجكت mMap الي عرفناه قبل ال onCreate من الكلاس GoogleMap خلي قيمته تساوي قيمة الاوبجكت الي اسمه googleMap
        mMap = googleMap ;

        /*
            هون انا من خلال الاوبجكت الي اسمه mMap بحدد نوع او شكل الخريطه و
            النوع الي انا مستعمله و هو هاد MAP_TYPE_HYBRID هو نوع هجين يعني
            هايبرد وهو هايبرد لانه مزيج من نوع من الخرائط

            الاول هون خرائط الاقمار الصناعيه الي بتشوفيه في الخارطه
            والنوع الثاني هو النوع العادي الي بس بعطيكي خريطه مع اسماء الشوارع

            ومستعمل هاد النوع لانو بدي لما استعمل خرائط الاقمار الصناعية يظهر الي اسماء الشوراع
         */
        mMap . setMapType ( GoogleMap . MAP_TYPE_HYBRID ) ;

        // هون زر بظهر من زر تحديد الموقع الحالي لانه بكون مخفي من خلال تحويل قيمته الافتراضيه الي هي false الى true
        mMap . setMyLocationEnabled ( true ) ;

        // هون نفس الحكي لكن مع ازرار الكتبير و التصغير
        mMap . getUiSettings ( ) . setZoomControlsEnabled ( true ) ;

        /*
            هاد الفنكشن بستدعى لما نضعط على اي مكان في الخارطه ومعطي امر جواته بمسح اي دبوس تم وضعه على الخارطه قبل

           في حالة المستخدم بطل بده الدبوس الي حطه باستعمال فنكشن setOnMapLongClickListener و صار بده يتحرك لمكان ثاني
         */
        mMap . setOnMapClickListener ( latLng -> mMap . clear ( ) ) ;

        // هاد الفنكشن بستدعى لما اضل ضاغط ضغطه طويله على الخارطه
        mMap . setOnMapLongClickListener ( latLng ->
        {
            // هون معطي امر بمسح اي دوبس انحط على الخارطه تحت بتعرفي ليه عامل هيك
            mMap . clear ( ) ;

            /*
                هون عرفنا اوبجكت من الكلاس LatLng ومن خلال المتغير الي اسمه latlng الي بتمرر للفنكشن لما يستدعى بستدعي هدول الفنكشن

                الاول و هو getLatitude و وظيفته انه يحدد النا موقع المكان الي كتبه المستخدم في خانة البحث على خط العرض

                و الثاني الي هو هاد getLongitude و وظيفته انه يحدد النا موقع المكان الي كتبه المستخدم في خانة البحث على خط الطول

                بعد هيك بخزن هاي الاحداثيات في الاوبجكت الي اسمه l_atlng عشان نستعملها في الانتقال الى المكان الي المستخدم كتبه في البحث

                و ال LatLng هي عبارة عن كلاس في API خرائط قوقل بتخلينا نقدر نحط دبوس على احداثيات مكان محدد في الخارطه
            */
            LatLng l_atlng = new LatLng ( latLng . latitude , latLng . longitude ) ;


            // هون و ببساطه ومن دون اطالة شرح و شرح كل شي في السطر ببساطه الي بصير انه بنحط دبوس على احداثيات المكان الي تخزنت في ال l_atlng لما المستخدم يضغط ضغطه طويله على مكان معين في الخارطه
            mMap . addMarker ( new MarkerOptions ( ) . position ( l_atlng ) ) ;

            //هون بخزن احداثيات مكان العمل في المتغيرات الي عرفتهم فوق قبل ال onCreate
            latitude = latLng . latitude ;
            longitude = latLng . longitude ;

            /*
                طيب هسه نجي لهاي mMap . clear ( );  انا حاكي بدي قبل ما ينحط الدبوس على الخارطه امسح اي دبوس موجود قبله بس ليه حاكي هيك

                هسه هاد الفنكشن setOnMapLongClickListener انا مستعمله عشان شغله وحده بس
                وهي انه لما الدكتور بده يحدد مكان عمله على الخارطه بده يضل ضاغط ضغطه طويله ليحدد المكان وبعدها بتخزن
                المكان مع معلومات مكان العمل

                هسه بزبط انه المكان الي حدده يتخزن مع معلومات مكان العمل من دون هاي mMap . clear ( );  ما في مشكله

                لكن

                عشان الدكتور يعرف انه المكان الي بده اياه لازم ينحط دبوس على المكان الي ضل ضغط عليه ضغطه طويله

                طيب في حالة الدكتور غلط في تحديد المكان و او حب يغير المكان و هاي mMap . clear ( );  ما كانت موجوده

                الي بصير انه رح ينحط دبوس على اول مكان حدده ولما يجي يحدد المكان الجديد الي بده اياه رح يضل الدبوس موجود

                ف برايك هل من المنطقي انه يضل ظاهر للدكتور على الخارطه انه محدد موقعين لمكان عمل واحد

                اكيد لا لهيك انا قايل اله بس الدكتور يضل ضاغط ضغطه طويله اول شي شوف اذا في دبوس على الخارطه و امسحه قبل ما ينحط اي دبوس ثاني بتمنى تكون وصلت
             */

        } ) ;

    }

    // هاد الفنكشن هو المسؤول عن البحث عن الموقع باستخدام الاسم لما نكتبه في البحث
    private void Find_a_place ( )
    {
        /*
            هون بجيب النص من مربع البحث الي في الخارطه و وصلنا اله من خلال ال binding ولانه مش edit text عادي بل هو
            عباره عن اشي اسمه SearchView مماثل ل edit text لكنه مخصص لعمليات البحث ما في فنكشن ال get text ألي في ال edit text العادي
            في فنكنشن مشابهه و هي getQuery عشان نجيب النص الي كتبه المستخدم من مربع البحث
         */
        String location = binding . SearchEditText . getQuery ( ) . toString ( ) ;

        // هون عرفنا لست من نوع Address عشان نخزن فيها معلومات العنوان الي رح نوصل اله من خلال اسمه الي بحطه المستخدم في خانة البحث
        List < Address > listAddress ;

        // هاد الشرط الي جوا الاف بمنع انه ننفذ الي جواتها في حالة المستخدم ما كتب شي في البحث و كبس على كبسة البحث
        if ( !location . isEmpty ( ) )
        {
            /*
                في السطر هاد Geocoder geocoder = new Geocoder ( Map . this );  الي تحت بنعرف اوجبكت من الكلاس الي اسمه Geocoder
                 هاد الكلاس وظيفته او شغله او الي بساويه
                 هو انه من خلال الاسم الي بحطه المستخدم في البحث يجيب النا احداثيات المكان الي
                 المستخدم كتب اسمه في البحث
             */
            Geocoder geocoder = new Geocoder ( Map . this ) ;

            // هاد السطر الي تحت حطيته لحتى ازود المدة الي باخدها ال geocoder لحتى يبحث فيها عن احداثيات المكان من خلال اسمه عشان لو كان النت ضعيف ما يطلع نفس الخطا الي كان يطلع الك لما تبحثي عن مكان
            System . setProperty ( "android . location . Geocoder . SEARCH_TIME_OUT" , "30000" ) ;

            try
            {
                /*
                    هون باستعمال الاوبجكت geocoder الي عرفناه فوق بنستدعي فنكشن ال getFromLocationName الي تابع
                    للكلاس Geocoder و الي
                    بجيب النا احداثيات المكان الي انكتب اسمه في البحث و بخزنها في اللست الي اسمها listAddress طبعا في
                    حالة كان موجود المكان
                 */
                listAddress = geocoder . getFromLocationName ( location , 1 ) ;

                // هون اذا المكان الي انكتب اسمه في البحث موجود فالخارطه رح تنقلك اله مباشره من خلال الكود الي جوا الاف
                if ( !listAddress . isEmpty ( ) )
                {
                    /*
                         هون بنعرف متغير من نوع ادرس وبنخزن فيه احداثيات المكان الي جبناه من خلال هاد الفنكشن
                          ؛getFromLocationName في السطر الي قبل الاف و الي هو هاد السطر
                         listAddress = geocoder . getFromLocationName ( location , 1 ) ;
                     */
                    Address address = listAddress . get ( 0 ) ;

                    /*
                        هون عشان لما تصير عملية بحث جديده ما يضل في دبوس مثبت على المكان الي انبحث عنه في
                        الاول بنعمل ازاله لاي دبوس اتثبت من عمليات بحث سابقه والغرض او الفكره من استعمالها هون هو
                        نفس الغرض او الفكره من استعمالها في فنكشن ال setOnMapLongClickListener الي
                        موجود في فنكشن ال onMapReady
                     */
                    mMap . clear () ;

                    /*
                        هون عرفنا اوبجكت من الكلاس LatLng ومن خلال المتغير الي اسمه address باستعمال هدول الفنكشن

                        الاول و هو getLatitude و وظيفته انه يحدد النا موقع المكان الي كتبه المستخدم في خانة البحث على خط العرض

                        و الثاني الي هو هاد getLongitude و وظيفته انه يحدد النا موقع المكان الي كتبه المستخدم في خانة البحث على خط الطول

                        بعد هيك بخزن هاي الاحداثيات في الاوبجكت الي اسمه latlng عشان نستعملها في الانتقال الى المكان الي المستخدم كتبه في البحث
                     */
                    LatLng latlng = new LatLng ( address . getLatitude ( ) , address . getLongitude ( ) ) ;

                    /*
                       هون من خلال الاوبجكت mMap الي عرفناه قبل ال onCreate من الكلاس الي اسمها GoogleMap بنستدعي الفنكشن
                       الي اسمه animateCamera و الي وظيفته ينقل المستخدم للمكان
                       الي كتب اسمه في البحث

                      و ال CameraUpdateFactory هي عبارة عن الكلاس الي بيحرك الخارطه للمكان المستخدم
                       كتب اسمه في البحث ومن خلاله بنستدعي الفنكشن newLatLngZoom والي وظيفه

                       هي انه من ياخد احداثيات المكان من المتغير latlng مع نسبة زوم 18 و يعطيها للفنكشن animateCamera
                       عشان ينقل المستخدم للمكان الي كتبه في البحث
                     */
                    mMap . animateCamera ( CameraUpdateFactory . newLatLngZoom ( latlng , 18 ) ) ;

                    // هون و ببساطه ومن دون اطالة شرح و شرح كل شي في السطر ببساطه الي بصير انه بنحط دبوس على احداثيات المكان الي المستخدم كتب اسمه في البحث
                    mMap . addMarker ( new MarkerOptions ( ) . position ( latlng ) ) ;
                }

                // في حالة ما كان المكان الي انكتب اسمه في البحث موجود رح تظهر هاي المسج
                else
                    Snack_Bar ( "لا يوجد مكان بهذا الاسم\n\n جرب كتابة اسم مكان اخر" ) ;
            }

            /*
                 هاي وظيفتها انه في حالة صار اي خطا معروف يظهر في مسج واذا بدنا نحدد مسج معين في حالة صار خطا متوقع حدوثه
                 لازم نكتب اسم الخطا الي ممكن يصير مع هاي الجمله IOException و بعدها جوا نكتب المسج الي بدنا اياه و الخطا
                 الي كان يصير معك هو مثال على الي بحكي فيه يعني بدل ما يطلع الك الي كان يظهر بنقدر نحكي للمستخدم انه
                 في ضعف في النت بدل الي كان يظهر الك
             */
            catch ( IOException e )
            {
                e . printStackTrace ( ) ;
                Snack_Bar ( "Search failed: " + e . getMessage ( ) ) ;

            }
        }
    }

    // للتحقق اذا كان النت شغال او مش شغال و برضو لاني مستعمله في فنكشن onCreate و رح يستدعى لما تفتحي الخارطه
    private void Check_Internet ( )
    {

        // ال ConnectivityManager من اسمها يعني مدير الاتصال و هي عبارة عن كلاس مسؤول عن كل شي بتعلق بالانترنت و الاتصال بالشبكه في التلفون

        // ال CONNECTIVITY_SERVICE ببساطه هي عباره عن خدمه بتوفر معلومات عن حالة الاتصال بالشبكه الحالية و هي جزء من كلاس  ConnectivityManager

        /*
             هون في السطر الي تحت بروح بعمل اوبجكت من  CONNECTIVITY_SERVICE من خلال فنكشن getSystemService و
             بحولها ل ConnectivityManager من خلال هاد ( ConnectivityManager ) عشان المتغير من نوع
             فلازم احوله ل ConnectivityManager عشان يجيب معلومات عن حالة الانترنت الحاليه لانه مثل
             ما قلنا فوق انه ال CONNECTIVITY_SERVICE بتوفر معلومات عن الحاله الحاليه للاتصال بالشبكه عشان يقدر من
             خلالها يحدد هل متصل بالنت او لا
         */
        connectivity_Manager = ( ConnectivityManager ) getSystemService ( Context . CONNECTIVITY_SERVICE ) ;

        // في هاد السطر الي تحت هاد ال networkCallback هو المسؤول عن مراقبة التغيير في حالة الاتصال وعدم الاتصال بالانترنت وهو الي بتصرف وقتها بشو الي رح يصير
        /*
            ال NetworkCallback هي عباره عن كلاس موجوده داخل كلاس ConnectivityManager لهيك تحت حاكي اله
            ؛ConnectivityManager . NetworkCallback في هاد السطر
            network_Callback = new ConnectivityManager . NetworkCallback ( )

            لما اعطيت قيمه لهاد المتغير network_Callback
            وهو المسؤول انه يتصرف بشو الي بده يصير لما يطفي او يشتغل النت
        */
        network_Callback = new ConnectivityManager . NetworkCallback ( )
        {

            // هاي الفنكشن بتستدعى اول ما يشتغل النت
            @Override
            public void onAvailable ( Network network )
            {
                /*
                     هون اول ما يشتغل النت بخلي قيمة المتغير is_Internet_Connected تساوي true لانها كانت false بس
                     كان النت طافي و بعدها بستدعي Check_Location_And_Internet لحتى يتحقق اذا كان النت طافي او
                     شغال ويحدث الشاشة بناء على حالة النت مطفي او شغال
                 */
                is_Internet_Connected = true ;
                Check_Location_And_Internet ( ) ;

            }

            // هاي الفنكشن بستدعى اول ما يرجع النت يشتغل
            @Override
            public void onLost ( Network network )
            {
                /*
                     هون اول ما يفصل النت بخلي قيمة المتغير is_Internet_Connected تساوي false لانها كانت true
                     بس كان النت شغال و بعدها بستدعي Check_Location_And_Internet لحتى يتحقق اذا كان النت طافي او
                     شغال ويحدث الشاشة بناء على حالة النت مطفي او شغال
                 */
                is_Internet_Connected = false ;
                Check_Location_And_Internet ( ) ;

            }
        } ;

        /*
          في هاد السطر
          connectivity_Manager . registerDefaultNetworkCallback ( network_Callback ) ;

          تسجيل ل connectivity_Manager عشان يقدر يتحقق من النت اذا كان شغال او طافي عن طريق انه يستقبل التغيير في حالة الاتصال بالانترنت و وقتها بجي دور هاد الي فوق الي هو هاد

           network_Callback = new ConnectivityManager . NetworkCallback ( )

           وبعدل على قيمة is_Internet_Connected حسب حالة الاتصال بالانترنت
         */
        connectivity_Manager . registerDefaultNetworkCallback ( network_Callback ) ;

        /*
            ال NetworkInfo هي كلاس الي هي بالزبط بتعطيني الصافي انه النت متصل او فاصل
            وفي السطر التحت حاكي اله باستعمال الفنكشن  getActiveNetworkInfo الي هي داخل الكلاس
            ؛ConnectivityManager استعملها مع المتغير الي اسمه connectivity_Manager لانه فوق اخذ معلومات عن الحاله
            الحالية للاتصال بالانترنت لما قلنا في هاد االحكي

            connectivity_Manager = ( ConnectivityManager ) getSystemService ( Context . CONNECTIVITY_SERVICE ) ;

            فلهيك خليته يروح لهاد المتغير connectivity_Manager و يشوف الي شو وضع النت الحالي
            ويخزنه في هاد المتغير active_Network_Info
         */
        NetworkInfo active_Network_Info = connectivity_Manager . getActiveNetworkInfo ( ) ;

        /*

            ليه انا معطي قيمه لهاد المتغير is_Internet_Connected هون بالزبط

            بسبب انه network_Callback ما بشتغل شغله اول ما تفتحي الخارطه واول ما تفتح الخارطه القيمه الافتراضيه
            للمتغير network_Callback بتكون تساوي false فلو استدعينا هذا الفنكشن Check_Location_And_Internet و
            اجى شيك هل شغال ولا لا رح يلاقيه مش شغال حتى لو انه شغال بسبب ان قيمة هاد is_Internet_Connected بتكون
            ؛false اول ما تشتغل الشاشه لهيك انا لازم احطه قبل ما استدعي هاد الفنكشن Check_Location_And_Internet
         */
        is_Internet_Connected = active_Network_Info != null && active_Network_Info . isConnected ( ) ;
        Check_Location_And_Internet ( ) ;
    }

    // للتحقق اذا كان الموقع شغال او مش شغال وبرضو لاني مستعمله في فنكشن onCreate و رح يستدعى لما تفتحي الخارطه
    private void Check_Location ( )
    {

        /*
            ملاحظه صغيره بس هون قبل ما تبلشي تقري كومنتات الشرح الي جوا الفنكشن
            ال BroadcastReceiver و ال LocationManager طريقة شغلهم تقريبا مشابهه
            لطريقة شغل ال ConnectivityManager و ال NetworkCallback
         */

        /*
            هسه شو هو ال LocationManager شو بعمل شو بساوي بالمختصر مثل ما حكالي chat gpt هيك حالكي
            يعد LocationManager خدمة نظام توفر الوصول إلى الخدمات المتعلقة بالموقع ، مثل GPS والموقع المستند إلى الشبكة.

            ولحتى توضح الصوره بسمح للتطبيق بالحصول على الموقع الحالي للجهاز و تتبع التغيرات في الموقع ( هون بقصد
            مكان الشخص مش خدمة الموقع ) و طبعا هاد مستحيل يشتغل اذا ما اعطينا permission الموقع في ملف ال
            ؛AndroidManifest وبعدها طلبنا من المستخدم انه يمنح صلاحية الموقع للتطبيق غير هيك ما رح يشتغل ابدا ولا رح
            يستفيد التطبيق منه

            و ال LOCATION_SERVICE هي عبارة عن خدمة موفرها نظام الاندرويد تتيح الوصول الى الموقع الحالي للجهاز

            و دام ال  LocationManager خدمه من نظام الاندرويد و ال LOCATION_SERVICE نفس الشي

            فهاد يعني انه لما قلنا اله هيك
            location_Manager = ( LocationManager ) getSystemService ( Context . LOCATION_SERVICE ) ;

            كانو جينا حكينا اله والله انا من خلال هاد المتغير location_Manager بدي تخليني اوصل للموقع الحالي للجهاز و غير
            هيك كونه ال LocationManager مسؤوله عن كل شي بخص الموقع بدي تخليني اوصل لكل شي بخص الموقع

            ومنها تحديدا حالة خدمة الموقع اذا شغاله او مطفية
         */
        location_Manager = ( LocationManager ) getSystemService ( Context . LOCATION_SERVICE ) ;

        // هون اعطينا قيمه للاوجكت location_Receiver الي عرفناه فوق قبل فنكشن onCreate
        location_Receiver = new BroadcastReceiver ( )
        {
            // هاض الفنشكن بستدعى لما ال location_Receiver يستقبل BroadcastReceiver يعني لما يصير تغيير في جالة خدمة الموقع انها اتشغلت او انطفت
            @Override
            public void onReceive ( Context context , Intent intent )
            {
                // هون هاد المتغير الي برضو عرفناه قبل onCreate بتخزن فيه الحاله الحاليه لخدمة الموقع بس  تشتغل او بس تطفي اذا كانت مشغله بتكون قيمته true و اذا كانت طافيه بتكون قيمته false

                /*
                    ال GPS_PROVIDER هو عباره عن خدمه موفرها نظام الاندرويد و وظيفته يوفر معلومات عن الموقع باستعمال خدمة ال GPS في التلفون
                 */
                is_Location_Enabled = location_Manager . isProviderEnabled ( LocationManager . GPS_PROVIDER ) ;

                // هون شو ما كانت قيمة is_Location_Enabled كانت true او كانت false هاد الي تحت رح يتنفذ لانو قلنا انه فنكشن ال onReceive بستدعى لما ال location_Receiver يستقبل BroadcastReceiver يعني لما يصير تغيير في جالة خدمة الموقع انها اتشغلت او انطفت
                Check_Location_And_Internet ( ) ;
            }
        } ;

        // في هاد السطر بنعرف اوبجكت من الكلاس  IntentFilter و اسمه  filter و وظيفته او لازمته انه يضل متبع مع حالة خدمة الموقع لما تطفي او تشتغل

        /*
            ال PROVIDERS_CHANGED_ACTION ه عبارة عن intent لكن مش intent عادي مثل الي بعمل توجيه بين الصفحات
            هو صح انه intent  لكنه intent من نوع broadcast يعني هو broadcast intent و وظيفته انه يلقط اي شي
            بصير و بخص الموقع

            وفي حالة اي تغيير او اي شي بصير و بخص الموقع هون هاد ال intent بستدعي فنكشن ال onReceive الي استعديناه
            من خلال هاد الاوبجكت location_Receiver

            هسه صحيح انه intent بس ما بنقدر نوصل اله بشكل مباشر
            لازم نعرف اوبجكت من الكلاس IntentFilter و نربط ال PROVIDERS_CHANGED_ACTION في هاد الاوبجكت الي عملناه بعدها
            بنسجله في الستتم انه والله في عندي intent متبع مع اي تغير او اي شي بصير وبخص الموقع
         */
        IntentFilter filter = new IntentFilter ( LocationManager . PROVIDERS_CHANGED_ACTION ) ;

        /*
            في هاد السطر بنعمل تسجيل ل location_Receiver و الفلتر الخاص فيه الي متبع مع خدمة الموقع عشان يقدر يتحقق من
            الموقع اذا كان شغال او طافي عن طريق انه يستقبل التغيير في حالة خدمة الموقع و وقتها بستدعي هاد الفنكشن onReceive
            فوق وبعدل على قيمة is_Location_Enabled حسب حالة خدمة الموقع
         */
        registerReceiver ( location_Receiver , filter ) ;

        /*
            في السطر الي تحت الي هو هاد
            is_Location_Enabled = location_Manager . isProviderEnabled ( LocationManager . GPS_PROVIDER ) ;

            انا ليه راجع مخزن الحاله الحاليه لخدمة الموقع في is_Location_Enabled
            لانه لو ترجعي للفوق بتلاحظي انه location_Receiver و فنشكن onReceive ما بتنفذو الا في حالة صار تغيير
            على حالة خدمة الموقع والي كان يصير قبل ما احط هاد السطر انه لما يجي فنكشن Check_Location_And_Internet
            يشيك على متغير is_Location_Enabled يلاقيه false حتى لو كان الموقع شغال لهيك اضظريت اني احط هاد السطر
            لحتى ياخد الحاله الحالية لخدمة الموقع انها شغاله لما تفتح الخارطه كونه ال location_Receiver و فنكشن
            ؛onReceive انا ما بستفيد منهم ابدا ولا بعدلو على قيمة is_Location_Enabled الا بعد ما تفتح الخارطه وخلال
            استعمال الخارطه بجو بعدلو على قيمة is_Location_Enabled في حالة انطفت خدمة الموقع او اشتغلت عرفتي هلا ليه
            معين قيمه مرتين داخل هاد الفنشكن للمتغير is_Location_Enabled

            يعني بالنهايه هون القصه نفس قصة Check_Internet مع ال is_Internet_Connected ومع ال onLost و onAvailable

             الي في فنكشن ال Check_Internet داخل هاد

             networkCallback = new ConnectivityManager . NetworkCallback ( )

            لكن هون الفرق انه في ال is_Internet_Connected ما انجبرت اني احطه داخل هاد

            networkCallback = new ConnectivityManager . NetworkCallback ()

            ممكن لانه BroadcastReceiver و ال LocationManager

            الي في هاد الفنكشن الي هو فنكشن Check_Location طريقة شغلهم بتفرق عن طريقة شغل

            ال ConnectivityManager و ال NetworkCallback الي في فنكشن Check_Internet

            ف ممكن انه هاد السبب الي ما خلاني في فتكشن ال Check_Internet
            انجبر احط هاد is_Internet_Connected داخل هاد

            networkCallback = new ConnectivityManager . NetworkCallback ()
         */
        is_Location_Enabled = location_Manager . isProviderEnabled ( LocationManager . GPS_PROVIDER ) ;

        // هون بس بستدعي الفنكشن المسؤول عن عرض المسج اذا كان النت او الموقع واحد منهم مطفي او الاثنين مطفيين
        Check_Location_And_Internet ( ) ;

    }

    // هاد الفنكشن المسؤول عن عرض المسج اذا كان النت او الموقع واحد منهم مطفي او الاثنين مطفيين وهاد الفنكشن مستعمله في فنكشن Check_Location و فنشكن Check_Internet
    private void Check_Location_And_Internet ( )
    {
        /*
            هاي الفنكشن runOnUiThread مستعمله عشان ما يصير خطا لما يعدل محتوى الشاشه الي احنا فيها حاليا
            والي هي شاشة الخارطة والخطا الي بصير انه اوك لو النت طافي بقبل منك انه يعرض الشاشه الي بتقله
            انه النت  طافي لكن لما يشتغل النت ويجي يرجع يعرض شاشة الخارطه هون رح يعطيكي خطا لانه بحاول يغير
            محتوى الشاشة  الي فيها مسج انه النت طافي من كلاس الخارطه الي هي مش مربوطه في تصميم الشاشه الي بتعرض المسج و
            العكس برضو صحيح بحيث انه لو كان النت شغال و
            اذا اجى طفاه رح يعطيه خطا فلهيك انا مستعمل هاد الفنكشن واذا بتلاحظي مستعمله في كل الحالات انا
         */

        // هاد الشرط بحكي اذا كان اللوكشن مش مفعل او النت مش مفعل او الاثنين مش مفعلين ادخل جوا الاف غير هيك اعرض الخارطة
        if ( !is_Internet_Connected || !is_Location_Enabled || ( !is_Internet_Connected && !is_Location_Enabled ) )
        {
            // هون اذا كان النت طافي ادخل جوا الاف وجيب الشاشه الي بظهر فيها المسج واعرضها بدل الخارطة و حط فيها هاد المسج
            if ( !is_Internet_Connected )
            {
                runOnUiThread ( ( ) ->
                {
                    // هون بنجيب الشاشة الي فيها المسج و بنعرضها بدل الخارطه باستعمال فنشكن setContentView المسؤول عن تحديد الشاشة الي رح تنعرض للمستخدم
                    setContentView ( R . layout . check_location_and_internet_view ) ;

                    // هون عرفنا اوبجكت من الكلاس  TextView اسمه textView عشان نقدر نحدد مين هو ال text view الي بدنا نعرض للمتستخدم فيها نص المسج
                    TextView textView = findViewById ( R . id . textVie ) ;
                    textView . setText ( "لا يوجد انترنت يرجى التحقق من اتصالك بالانترنت و المحاوله مره اخرى" ) ;
                } ) ;
            }

            // هون نفس الشي بس في حالة كان الموقع مطفي
            if ( !is_Location_Enabled )
            {
                runOnUiThread ( ( ) ->
                {
                    setContentView ( R . layout . check_location_and_internet_view ) ;
                    TextView textView = findViewById ( R . id . textVie ) ;
                    textView . setText ( "خدمة الموقع لديك متوقفه يرجى تشغيل خدمة الموقع للمتابعة" ) ;
                } ) ;
            }

            // هون في حالة كان الموقع و النت مطفيين
            if ( !is_Internet_Connected && !is_Location_Enabled )
            {
                runOnUiThread ( ( ) ->
                {
                    setContentView ( R . layout . check_location_and_internet_view ) ;
                    TextView textView = findViewById ( R . id . textVie ) ;
                    textView . setText ( "لا يوجد اتصال بالانترنت و خدمة الموقع لديك متوقفه يرجى التحقق من الاتصال بالانترنت و تشغيل خدمة الموقع للمتابعة" ) ;
                } ) ;
            }
        }
        else
            runOnUiThread ( ( ) -> setContentView ( binding . getRoot ( ) ) ) ;
    }

    // لعرض رسالة في حالة المستخدم بحث عن اسم موقع غير موجود وهاد الفنكشن مستعمله في فنكشن Find_a_place المسؤول عن البحث عن الاماكن من خلال الاسم ولو جيتي حطيتي مؤشر الماوس عليه و ضغطتي ctrl مع كبسه يسار على الماوس رح يوديكي على فنكشن Find_a_place
    private void Snack_Bar ( String Message )
    {
        /*
            هون عرفنا اوبجكت من كلاس ال Snackbar

            و من خلال فنكشن ال make بنشء سناك بار وباخد 3 متيغرات
            الاول هو الشاشه او المكان الي رح يظهر فيها المسج

            الثاني هي المسج الي بدي اعرضها للمستخدم وهاي االمسج جايه من االمتغير الي اسمه message الي فوق وهاي بعطيها القيمه الي
            جواتها لما استدعي الفنكشن

            المتغير الثالث هو المده الي رح يضل فيها المسج ظاهر يعني يا اما بظهر لمدة طويلة او لمده قصيره وال LENGTH_LONG يعني
            مده طويله و LENGTH_SHORT يعني بظهر لفتره قصيرة

            لكن انا هو مش مستعمل لا LENGTH_LONG ولا مستعمل LENGTH_SHORT حاكي اليه مباشره المده هي 5000 ملي ثانيه يعني 5
            ثواني لانه كل 1000 ملي ثانيه بتساوي ثانيه وحده
        */
        Snackbar snackbar = Snackbar . make ( binding . constraint , Message , 7000 ) ;

        // السطر الي تحت عشان اغير خلفية السناك بار
        snackbar . getView ( ) . setBackgroundTintList ( ColorStateList . valueOf ( ContextCompat . getColor ( this , R . color . Snack_bar_BG_Color ) ) ) ;

        /*
            هدول السطرين الي تحت عملناهم عشان نقدر نوصل للتكتست تاع السناك بار و عشان نعدل في لون وحجم الخط الخاص فيه

             وكونه السناك بار اداة مثل اي اداة اخرى فيعتبر view فبنعرف اوبجكت من الكلاس view و بعدها بنستعمل الاوبجكت الي
             عرفناه فوق من كلاس snackbar لحتى تقله getview ونصل ل اداة التكست الي مستعملها ليعرض فيها المسج ونعدل
             في حجم و لون الخط تبعها
         */
        View snackbarView = snackbar . getView ( ) ;
        TextView textView = snackbarView . findViewById ( com . google . android . material . R . id . snackbar_text ) ;

        // هاد السطر قلنا اله انه ما بدي كل المسج تظهر في سطر واحد بدي لو كانت اكثر من سطر تنعرض مثل ما هي مش كلها في سطر واحد
        textView . setSingleLine ( false ) ;

        // هون غيرنا لون الخط للتكست
        textView . setTextColor ( ContextCompat . getColor ( this , R . color . white ) ) ;

        // هون غيرنا حجم الخط
        textView . setTextSize ( 15 ) ;

        // هون غيرنا محاذاة النص و خلينا النص يصير في النص بدل ما هو على اليمين
        textView . setTextAlignment ( View . TEXT_ALIGNMENT_CENTER ) ;

        // وهاد السطر بشغل السناك بار و بعرض المسج الي بعثناها للفنكشن
        snackbar . show ( ) ;

    }

}