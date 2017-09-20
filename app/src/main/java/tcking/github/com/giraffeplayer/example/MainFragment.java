package tcking.github.com.giraffeplayer.example;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.github.tcking.viewquery.ViewQuery;

import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.Option;
import tcking.github.com.giraffeplayer2.VideoInfo;
import tcking.github.com.giraffeplayer2.VideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by TangChao on 2017/6/15.
 */

public class MainFragment extends Fragment {
    private ViewQuery $;
    private int aspectRatio = VideoInfo.AR_ASPECT_FIT_PARENT;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        $ = new ViewQuery(view);

        String testUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
        //testUri = "file:///sdcard/tmp/o.mp4" //test local file;

        final VideoView videoView = $.id(R.id.video_view).view();
        videoView.setVideoPath(testUrl);

        $.id(R.id.et_url).text(testUrl);
        CheckBox cb = $.id(R.id.cb_pwf).view();
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                videoView.getVideoInfo().setPortraitWhenFullScreen(isChecked);
            }
        });

        RadioGroup rb = $.id(R.id.rg_ra).view();
        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_4_3) {
                    aspectRatio = VideoInfo.AR_4_3_FIT_PARENT;
                } else if (checkedId == R.id.rb_16_9) {
                    aspectRatio = VideoInfo.AR_16_9_FIT_PARENT;
                } else if (checkedId == R.id.rb_fill_parent) {
                    aspectRatio = VideoInfo.AR_ASPECT_FILL_PARENT;
                } else if (checkedId == R.id.rb_fit_parent) {
                    aspectRatio = VideoInfo.AR_ASPECT_FIT_PARENT;
                } else if (checkedId == R.id.rb_wrap_content) {
                    aspectRatio = VideoInfo.AR_ASPECT_WRAP_CONTENT;
                } else if (checkedId == R.id.rb_match_parent) {
                    aspectRatio = VideoInfo.AR_MATCH_PARENT;
                }
                videoView.getPlayer().aspectRatio(aspectRatio);

            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.btn_play) {
                    if (videoView.getPlayer().isPlaying()) {
                        videoView.getPlayer().pause();
                    } else {
                        videoView.getPlayer().start();
                    }
                } else if (v.getId() == R.id.btn_full) {
                    videoView.getPlayer().toggleFullScreen();
                } else if (v.getId() == R.id.btn_list) {
                    startActivity(new Intent(getActivity(), ListExampleActivity.class));
                } else if (v.getId() == R.id.btn_play_in_standalone) {
                    VideoInfo videoInfo = new VideoInfo(Uri.parse($.id(R.id.et_url).text()))
                            .setTitle("test video")
                            .setAspectRatio(aspectRatio)
                            .addOption(Option.create(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "timeout", 30000000L))
                            .setShowTopBar(true);

                    GiraffePlayer.play(getContext(), videoInfo);
                    getActivity().overridePendingTransition(0, 0);
                }
            }
        };
        $.id(R.id.btn_play).view().setOnClickListener(onClickListener);
        $.id(R.id.btn_full).view().setOnClickListener(onClickListener);
        $.id(R.id.btn_play_in_standalone).view().setOnClickListener(onClickListener);
        $.id(R.id.btn_list).view().setOnClickListener(onClickListener);


    }


}
