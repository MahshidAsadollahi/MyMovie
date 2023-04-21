package com.example.mymovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private List<DataModel> dataModels;
    private List<FeatureModel> featureModels; // model for featured data
    private List<SeriesModel>seriesModels; // model for the series data
    private SliderAdapter sliderAdapter;
    private RecyclerView featuredRecyclerView,web_series_recycler_view;
    private FeaturedAdapter featuredAdapter;
    private SeriesAdapter seriesAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Star Movie");

        //barai navigation


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);


        bottomNavigationView.setOnItemSelectedListener(item->{
            switch (item.getItemId()){
                case R.id.bottom_search:
                    startActivity((new Intent(getApplicationContext(),searchActivity.class)));
                    overridePendingTransition(R.anim.slide_in_night,R.anim.slide_cut_left);
                    finish();
                    return true;
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(),profileActivity.class));
                    overridePendingTransition(R.anim.slide_in_night,R.anim.slide_cut_left);
                    finish();
                    return true;

            }
            return false;
        });




        FirebaseApp.initializeApp(this);
        SliderView sliderView=findViewById(R.id.sliderView);
        sliderAdapter=new SliderAdapter(this);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setScrollTimeInSec(6);
        renewItems(sliderView);

        //load data from firebase
        LoadFirebaseForSlider();
        //for loading featured movies in firebase
        LoadFeaturedData();




    }

    private void LoadData() {
        LoadFeaturedData();
        LoadMoviesData();


    }

    private void LoadMoviesData() {

    }

    private void LoadFeaturedData() {
        //load data from firebase
        DatabaseReference FRef=database.getReference("featured");
        featuredRecyclerView=findViewById(R.id.recyclerview2);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        featuredRecyclerView.setLayoutManager(layoutManager);

      featureModels=new ArrayList<>();
        featuredAdapter=new FeaturedAdapter(featureModels);
        featuredRecyclerView.setAdapter(featuredAdapter);

        FRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot contentSnapShot:snapshot.getChildren()){
                    FeatureModel dataModel=contentSnapShot.getValue(FeatureModel.class);
                    featureModels.add(dataModel);

                }
                featuredAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });

        //inja
        loadSeriesData();

    }

    private void loadSeriesData() {
        DatabaseReference SRef=database.getReference("series");
        web_series_recycler_view=findViewById(R.id.web_series_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        web_series_recycler_view.setLayoutManager(layoutManager);

        seriesModels=new ArrayList<>();
        seriesAdapter=new SeriesAdapter(seriesModels);
        web_series_recycler_view.setAdapter(seriesAdapter);

        SRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot contentSnapShot: snapshot.getChildren()){
                    SeriesModel newSeriesModel= contentSnapShot.getValue(SeriesModel.class);
                    seriesModels.add(newSeriesModel);

                }
                seriesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                //error code

            }
        });

    }

    public void renewItems(View view) {
        dataModels=new ArrayList<>();
        DataModel dataItems=new DataModel();
        dataModels.add(dataItems);

        sliderAdapter.renewItems(dataModels);
        sliderAdapter.deleteItems(0);

    }

    private void LoadFirebaseForSlider() {

        myRef.child("Trailer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot contentSlider:snapshot.getChildren()){
                    DataModel sliderItem=contentSlider.getValue(DataModel.class);
                    dataModels.add(sliderItem);

                }
                sliderAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this,"Sorry try again"+error.getMessage(),Toast.LENGTH_SHORT).show();
                finish();

            }
        });


    }
//agar moshkeli pish umad code renewitemo o bezar inja dobare

}