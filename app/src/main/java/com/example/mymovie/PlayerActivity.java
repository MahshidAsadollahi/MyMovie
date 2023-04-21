package com.example.mymovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

public class PlayerActivity extends AppCompatActivity {

//INSTEAD OF PLAYERVIEW
    private PlayerView playerView;
    //INSTEAD OF SIMPLEEXOPLAYER
    private ExoPlayer exoPlayer;
    private String VIDEO_URL;
    private String VIDEO_TITLE;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);



        VIDEO_URL=getIntent().getStringExtra("vid");
        VIDEO_TITLE=getIntent().getStringExtra("title");

//inja moshkel darim
        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar2);

        setSupportActionBar(toolbar);
        //va inja moshkel
        getSupportActionBar().setTitle(VIDEO_TITLE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    protected void onStart() {
        super.onStart();
        playerView=findViewById(R.id.video_player);
        BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter.Builder(getApplicationContext()).build();
        TrackSelector trackSelector=new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        exoPlayer= ExoPlayerFactory.newSimpleInstance(this,trackSelector);
        playerView.setPlayer(exoPlayer);
        DataSource.Factory dataSourceFactory=new DefaultDataSourceFactory(this,
                Util.getUserAgent(this,getString(R.string.app_name)));
        MediaSource videoSource=new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                Uri.parse(VIDEO_URL));

        exoPlayer.prepare(videoSource);
        exoPlayer.setPlayWhenReady(true);
        playerView.setKeepScreenOn(true);

    }

    @Override
    protected void onStop() {
        super.onStop();
        exoPlayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            super.finish();
        }

        return super.onOptionsItemSelected(item);

    }


}