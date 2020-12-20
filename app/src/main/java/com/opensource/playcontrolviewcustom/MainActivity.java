package com.opensource.playcontrolviewcustom;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SimpleExoPlayer player;
    private PlayerControlView pcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pcv = findViewById(R.id.main_pcv);

        initializePlayer();

    }

    private void initializePlayer(){
        if(player == null){
            player = new SimpleExoPlayer.Builder(MainActivity.this).build();
            pcv.setPlayer(player);

            //플레이리스트 만들기
            List<MediaItem> mediaItems = new ArrayList<>();
            makePlayList(mediaItems);

            //playList
            player.setMediaItems(mediaItems);
            player.prepare();
            player.setPlayWhenReady(true);
        }
    }

    private void makePlayList(List<MediaItem> mediaitems){
        MediaItem mediaitem;
        Field[] raws = R.raw.class.getFields();
        for(int count=0; count < raws.length; count++){
            Log.e("Raw Asset", raws[count].getName());
            int musicID = this.getResources().getIdentifier(raws[count].getName(), "raw", this.getPackageName());
            Uri musicUri = RawResourceDataSource.buildRawResourceUri(musicID);
            mediaitem = MediaItem.fromUri(musicUri);
            mediaitems.add(mediaitem);
        }
    }
}