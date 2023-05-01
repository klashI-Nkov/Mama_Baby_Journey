package com.example.mamababyjourney.First;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import com.example.mamababyjourney.R;
import com.example.mamababyjourney.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings ( { "UnnecessaryLocalVariable" , "CommentedOutCode" } )
@SuppressLint ( "MissingPermission" )

public class MapsFragment extends Fragment
{
    private ConnectivityManager connectivityManager;

    private ConnectivityManager.NetworkCallback networkCallback;

    private boolean connected;
    boolean isLocationEnabled;

    private BroadcastReceiver locationReceiver = null;
    private LocationManager locationManager;

    private GoogleMap mMap;
    FragmentMapsBinding binding;
    private OnMapReadyCallback callback = googleMap ->
    {
        mMap = googleMap;

        mMap.setMapType ( GoogleMap.MAP_TYPE_HYBRID );

        mMap.setMyLocationEnabled ( true );

        mMap.getUiSettings ( ).setZoomControlsEnabled ( true );
        mMap.setBuildingsEnabled ( true );

        // for find my location automatically when activity start

        //LatLng sydney = new LatLng ( currentLocation.getLatitude ( ) , currentLocation.getLongitude ( ) );
        //googleMap.addMarker ( new MarkerOptions ( ).position ( sydney ).title ( "Marker in Sydney" ) );
        //googleMap.moveCamera ( CameraUpdateFactory.newLatLngZoom ( sydney,18 ) );

        mMap.setOnMapClickListener ( latLng -> mMap.clear ( ) );

        mMap.setOnMapLongClickListener ( latLng ->
        {
            mMap.clear ( );
            LatLng sydney = new LatLng ( latLng.latitude , latLng.longitude );
            mMap.addMarker ( new MarkerOptions ( ).position ( sydney ).title ( "Marker in Sydney" ) );
            mMap.moveCamera ( CameraUpdateFactory.newLatLng ( latLng ) );
        } );
    };

    @Override
    public View onCreateView ( @NonNull LayoutInflater inflater , @NonNull ViewGroup container , @NonNull Bundle savedInstanceState )
    {
        binding= FragmentMapsBinding.inflate ( inflater,container,false );
        View root = binding.getRoot ();
        Check_Internet ( );
        Check_Location ( );

        return root;
    }



    @Override
    public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState )
    {
        Map_Initialization();

    }

    public void onDestroyView ( )
    {
        super.onDestroyView ( );
        binding = null;
        requireActivity().unregisterReceiver ( locationReceiver );

    }

    @Override
    public void onResume ( )
    {
        super.onResume ( );
        connectivityManager.registerDefaultNetworkCallback ( networkCallback );

    }
    @Override
    public void onPause ( )
    {
        super.onPause ( );
        connectivityManager.unregisterNetworkCallback ( networkCallback );

    }

    private void Map_Initialization ( )
    {
        SupportMapFragment mapFragment = ( SupportMapFragment ) getChildFragmentManager ( ).findFragmentById ( R.id.maps );
        if ( mapFragment != null )
        {
            mapFragment.getMapAsync ( callback );
        }
    }

    private void Find_a_place ( )
    {


        String location = binding.SearchEditText.getQuery ( ).toString ( );

        AtomicReference < List < Address > > listAddress = new AtomicReference <> ( );

        if ( !location.isEmpty ( ) )
        {
            Geocoder geocoder = new Geocoder ( requireContext() );

            try
            {
                listAddress.set ( geocoder.getFromLocationName ( location , 1 ) );

                if ( !listAddress.get ( ).isEmpty ( ) )
                {
                    Address address = listAddress.get ( ).get ( 0 );

                    LatLng latlng = new LatLng ( address.getLatitude ( ) , address.getLongitude ( ) );
                    mMap.animateCamera ( CameraUpdateFactory.newLatLngZoom ( latlng , 18 ) );
                    mMap.addMarker ( new MarkerOptions ( ).position ( latlng ).title ( "Marker in Sydney" ) );
                }
                else
                { Snack_Bar ( "لا يوجد مكان بهذا الاسم\n\n جرب كتابة اسم مكان اخر" ); }

            }
            catch ( IOException e )
            {
                e.printStackTrace ( );
                Snack_Bar ( "Search failed: " + e.getMessage ( ) );

            }

        }

    }

    private void Check_Internet ( )
    {

        connectivityManager = ( ConnectivityManager ) requireActivity().getSystemService ( Context.CONNECTIVITY_SERVICE );

        networkCallback = new ConnectivityManager.NetworkCallback ( )
        {

            @Override
            public void onAvailable ( Network network )
            {
                connected = true;
                //runOnUiThread ( ( ) -> binding.con.setVisibility ( View.INVISIBLE ) );
                Check_Location_And_Internet ( );

            }

            @Override
            public void onLost ( Network network )
            {
                connected = false;
                Check_Location_And_Internet ( );

            }
        };


        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo ( );

        connected = activeNetworkInfo != null && activeNetworkInfo.isConnected ( );
        if ( !connected )
        {
            /*runOnUiThread ( ( ) ->
            {

                binding.con.setVisibility ( View.VISIBLE );
                binding.textView.setText ( "لا يوجد انترنت يرجى التحقق من اتصالك بالانترنت و المحاوله مره اخرى" );
            });*/
            Check_Location_And_Internet ( );

        }

    }

    private void Check_Location ( )
    {


        // Create a broadcast receiver to listen for connectivity changes
        locationReceiver = new BroadcastReceiver ( )
        {

            @Override
            public void onReceive ( Context context , Intent intent )
            {


        // Check if location service is available
        isLocationEnabled = locationManager.isProviderEnabled ( LocationManager.GPS_PROVIDER );

        // Display or hide the message view based on location service availability

        if ( isLocationEnabled )
        {
            Check_Location_And_Internet ( );
        }
        else
        {
            Check_Location_And_Internet ( );
        }


            }
        };

        // Register the broadcast receiver to listen for location service changes
        IntentFilter filter = new IntentFilter ( LocationManager.PROVIDERS_CHANGED_ACTION );
        requireActivity().registerReceiver ( locationReceiver , filter );

        // Initialize the location manager
        locationManager = ( LocationManager ) requireActivity().getSystemService ( Context.LOCATION_SERVICE );


        isLocationEnabled = locationManager.isProviderEnabled ( LocationManager.GPS_PROVIDER );

        Check_Location_And_Internet ( );




    }

    private void Check_Location_And_Internet ( )
    {
   /*     FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if ( !connected || !isLocationEnabled )
        {
            if ( !connected )
                requireActivity().runOnUiThread ( ( ) ->
                {
                    transaction.replace(binding.constraint, R.layout.check_location_and_internet_view);
                    TextView textView = requireActivity().findViewById ( R.id.textVie );
                    textView.setText ( "لا يوجد انترنت يرجى التحقق من اتصالك بالانترنت و المحاوله مره اخرى" );

                } );

            if ( !isLocationEnabled )
                requireActivity().runOnUiThread ( ( ) ->
                {
                    view = inflater.inflate(R.layout.check_location_and_internet_view, container, false);
                    TextView textView = requireActivity().findViewById ( R.id.textVie );
                    textView.setText ( "خدمة الموقع لديك متوقفه يرجى تشغيل خدمة الموقع" );
                } );

            if ( !connected && !isLocationEnabled )
                requireActivity().runOnUiThread ( ( ) ->
                {
                    view = inflater.inflate(R.layout.check_location_and_internet_view, container, false);
                    TextView textView = requireActivity().findViewById ( R.id.textVie );
                    textView.setText ( "لا يوجد اتصال بالانترنت و خدمة الموقع لديك متوقفه يرجى التحقق من الاتصال بالانترنت و تشغيل خدمة الموقع للمتابعة" );

                } );

        }
        else
            view = inflater.inflate(R.layout.fragment_maps, container, false);*/

    }

    private void Snack_Bar ( String Message )
    {
        Snackbar snackbar = Snackbar.make ( binding.constraint , Message , Snackbar.LENGTH_LONG );
        snackbar.getView ( ).setBackgroundTintList ( ColorStateList.valueOf ( ContextCompat.getColor ( requireContext() , R.color.Snack_bar_BG_Color ) ) );

        View snackbarView = snackbar.getView ( );
        TextView textView = snackbarView.findViewById ( com.google.android.material.R.id.snackbar_text );

        textView.setSingleLine ( false );
        textView.setTextColor ( ContextCompat.getColor ( requireContext() , R.color.white ) );
        textView.setTextSize ( 15 );
        textView.setTextAlignment ( View.TEXT_ALIGNMENT_CENTER );

        snackbar.show ( );


    }
}