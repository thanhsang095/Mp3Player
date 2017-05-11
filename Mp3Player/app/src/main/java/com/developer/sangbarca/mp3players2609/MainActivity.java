package com.developer.sangbarca.mp3players2609;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtTimeStart, txtTimeToatal;
    SeekBar skSong;
    ImageButton ibtnPrev, ibtnPlay, ibtnStop, ibtnNext;

    ArrayList<Integer> arraySong;
    MediaPlayer media = null;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        AddMusic();

        media = MediaPlayer.create(MainActivity.this, arraySong.get(i));
        ibtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(media.isPlaying()){
                    media.pause();
                    ibtnPlay.setImageResource(R.drawable.play);
                }else {
                    media.start();
                    ibtnPlay.setImageResource(R.drawable.pause);
                }
                GetTotalTime();
                SetSongTimeSeekBar();
                SeekBarRunTime();
            }
        });
        ibtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i < arraySong.size() - 1){
                    i++;
                }else {
                    i = 0;
                }
                if(media.isPlaying()){
                    media.stop();
                }
                media = MediaPlayer.create(MainActivity.this, arraySong.get(i));
                media.start();

                GetTotalTime();
                SetSongTimeSeekBar();
                SeekBarRunTime();
            }
        });
        ibtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i > 0){
                    i--;
                }else {
                    i = arraySong.size() - 1;
                }
                if(media.isPlaying()){
                    media.stop();
                }
                media = MediaPlayer.create(MainActivity.this, arraySong.get(i));
                media.start();

                GetTotalTime();
                SetSongTimeSeekBar();
                SeekBarRunTime();
            }
        });
        ibtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(media.isPlaying()){
                    media.stop();
                    media = MediaPlayer.create(MainActivity.this, arraySong.get(i));
                    ibtnPlay.setImageResource(R.drawable.play);
                }
            }
        });

        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //khi click kéo thả ra mới phát tại giá trị đó
                int currenProgress = seekBar.getProgress(); //giá trị kéo đến
                media.seekTo(currenProgress); //bài hát nhảy đến giá trị kéo đến
            }
        });

    }
    //seekbar chạy và thời gian bắt đầu tự động tăng lên
    private void SeekBarRunTime(){
        final Handler handler = new Handler(); //android.os
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                skSong.setProgress(media.getCurrentPosition()); //trả về thời gian đang hát

                //set cho txtStartTime
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                txtTimeStart.setText(dinhDangGio.format(media.getCurrentPosition()));

                handler.postDelayed(this, 500); //chạy lại hàm run với thời gian delay mới tùy chọn <1000

                //Kiểm tra kết thúc bài hát
                if(skSong.getProgress() + 200 > skSong.getMax()){ //kiểm tra getProgress == getMax k đúng cho tất cả trường hợp nên công thêm sai số
                    if(i < arraySong.size() - 1){
                        i++;
                    }else {
                        i = 0;
                    }
                    if(media.isPlaying()){
                        media.stop();
                    }
                    media = MediaPlayer.create(MainActivity.this, arraySong.get(i));
                    media.start();

                    GetTotalTime();
                    SetSongTimeSeekBar();
                    SeekBarRunTime();
                }
            }
        }, 0);
    }

    //Lấy tổng thời gian gán cho seekbar
    private void SetSongTimeSeekBar(){
        skSong.setMax(media.getDuration());
    }
    //Lấy tổng thời gian bài hát
    private void GetTotalTime(){
        int total = media.getDuration();
        // Chuyển miliseconds về phút:giây
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        txtTimeToatal.setText(dinhDangGio.format(total));
    }

    private void AddMusic(){
        arraySong = new ArrayList<>();
        arraySong.add(R.raw.daumua);
        arraySong.add(R.raw.cogainongthon);
        arraySong.add(R.raw.cokhinaoroixa);
        arraySong.add(R.raw.fiction);
        arraySong.add(R.raw.minhyeunhaudi);
        arraySong.add(R.raw.myeverything);
        arraySong.add(R.raw.sautatca);
        arraySong.add(R.raw.vetmua);
    }

    private void AnhXa(){
        txtTimeStart = (TextView) findViewById(R.id.textViewStart);
        txtTimeToatal = (TextView) findViewById(R.id.textViewTotal);
        skSong = (SeekBar) findViewById(R.id.seekBarSong);
        ibtnPrev = (ImageButton) findViewById(R.id.imageButtonPrev);
        ibtnNext = (ImageButton) findViewById(R.id.imageButtonNext);
        ibtnPlay = (ImageButton) findViewById(R.id.imageButtonPlay);
        ibtnStop = (ImageButton)findViewById(R.id.imageButtonStop);
    }
}
