package com.example.mymovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class searchActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerView;

    private CombinedAdapter combinedAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference moviesRef = database.getReference("featured");
    DatabaseReference SRef=database.getReference("series");


    private List<DataModel> dataModels=new ArrayList<>();
    private List<FeatureModel>featureModels; // model for featured data
    private List<SeriesModel>seriesModels=new ArrayList<>(); // model for the series data
    private List<CombinedModel>combinedModels = new ArrayList<>();
   private RecyclerView featuredRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);




        //barai khode search
        recyclerView=findViewById(R.id.recyclerview_search);
        searchView=findViewById(R.id.searchView);

        featureModels=new ArrayList<>();
        seriesModels=new ArrayList<>();
        dataModels = new ArrayList<>();

        combinedModels=new ArrayList<>();
        combinedAdapter=new CombinedAdapter(combinedModels);
        recyclerView.setAdapter(combinedAdapter);

        // set up the GridLayoutManager with 2 columns
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));






        //barai bottom navigation
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_search);

        bottomNavigationView.setOnItemSelectedListener(item->{
            switch (item.getItemId()){

                case R.id.bottom_search:
                    return true;
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_night,R.anim.slide_cut_left);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(),profileActivity.class));
                    overridePendingTransition(R.anim.slide_in_night,R.anim.slide_cut_left);
                    finish();
                    return true;
            }
            return false;
        });

        // Set up searchView listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovies(query);
                searchSeries(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMovies(newText);
                searchSeries(newText);
                return true;
            }
        });


}



    private void searchSeries(String query) {

        combinedModels.clear();

        SRef.orderByChild("Stitle")
                .startAt(query)
                .endAt(query + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot contentSnapShot : snapshot.getChildren()) {
                            SeriesModel seriesModel = contentSnapShot.getValue(SeriesModel.class);
                            CombinedModel combinedModel = new CombinedModel(seriesModel);
                            combinedModels.add(combinedModel);

                        }
                        combinedAdapter.notifyDataSetChanged();

                    }

                    //
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


    private void searchMovies(String query) {
        combinedModels.clear();
        moviesRef.orderByChild("Ftitle")
                .startAt(query)
                .endAt(query + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot contentSnapShot : snapshot.getChildren()) {
                            FeatureModel dataModel = contentSnapShot.getValue(FeatureModel.class);
                            CombinedModel combinedModel= new CombinedModel(dataModel);
                            combinedModels.add(combinedModel);
                        }
                        combinedAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }

    }





