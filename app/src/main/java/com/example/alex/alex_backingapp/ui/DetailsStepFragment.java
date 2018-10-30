package com.example.alex.alex_backingapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alex.alex_backingapp.R;
import com.example.alex.alex_backingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;


public class DetailsStepFragment extends Fragment {
    private static final String TAG = "DetailsStepFragment";
    private OnDetailsStepFragmentInteractionListener mListener;
    Context mContext;

    @BindView(R.id.tv_description)
    TextView tv_description;
    @BindView(R.id.tv_currentStep)
    TextView tv_currentStep;
    @BindView(R.id.btn_next)
    FloatingActionButton btn_next;
    @BindView(R.id.btn_back)
    FloatingActionButton btn_back;

    @BindView(R.id.tv_empty)
    TextView tv_empty;
    @BindView(R.id.rootLinearLayput)
    LinearLayout rootLinearLayput;
    @BindView(R.id.fram1)
    FrameLayout fram1;
    @BindView(R.id.fram2)
    FrameLayout fram2;
    @BindView(R.id.image)
    ImageView imageView;

    @BindView(R.id.playerView)
    SimpleExoPlayerView mExoPlayerView;

    private SimpleExoPlayer mExoPlayer;


    private ArrayList<Step> steps;
     int position;
    int sizeOfList;

    long positionOfExoplayer ;

    public void setBundleFromActivity(Bundle bundleFromActivity) {
        this.bundleFromActivity = bundleFromActivity;
    }

    Bundle bundleFromActivity;


    public DetailsStepFragment() {
        // Required empty public constructor
    }
    String videoUrl="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_details_step, container, false);
        mContext=getContext();
        ButterKnife.bind(this,root);

        if(savedInstanceState == null)
        {
            Log.d(TAG, "onCreateView: savedInstanceState == null");

            //this is the first time the the fragment created
            if (bundleFromActivity !=null){
                position=bundleFromActivity.getInt("position");
                sizeOfList=bundleFromActivity.getInt("SizeOfList");
                steps=bundleFromActivity.getParcelableArrayList("steps");
                positionOfExoplayer=0;//because this is first time
            }


        }else {
            Log.d(TAG, "onCreateView: savedInstanceState =! null");
            //check if there is reotate and get save value !!
            position=savedInstanceState.getInt("position");
            sizeOfList=savedInstanceState.getInt("SizeOfList");
            steps=savedInstanceState.getParcelableArrayList("steps");
            positionOfExoplayer=savedInstanceState.getLong("positionOfExoplayer");


        }

        update();
//        if (mStep!=null){
//            //when rotate the screen will  crash !!
//            //use need use view holder
//
//
//        }



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position==0) return;
                position--;
                update();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==sizeOfList-1)
                    return;
                position++;
                update();
            }
        });
        return root;
    }

    private void update() {
        if (position <= 0) {
            btn_back.setVisibility(GONE);
        } else {
            btn_back.setVisibility(View.VISIBLE);
        }


        if (position >= sizeOfList- 1) {
            btn_next.setVisibility(GONE);
        } else {
            btn_next.setVisibility(View.VISIBLE);
        }

        //remove preivous state of player
        releasePlayer();
//
//        //trigger method in the activity to update
//        if (mListener != null) {
//            mListener.setCurrnt(position);
//        }

        if (steps.get(position).getVideoURL().isEmpty() && steps.get(position).getThumbnailURL().isEmpty()) {
            mExoPlayerView.setVisibility(GONE);
            imageView.setVisibility(GONE);
            tv_empty.setVisibility(View.VISIBLE);
        } else if (!steps.get(position).getVideoURL().isEmpty()) {
              videoUrl = steps.get(position).getVideoURL();
            tv_empty.setVisibility(View.GONE);
            imageView.setVisibility(GONE);
            mExoPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(videoUrl));

        } else{
            String imageUrl = steps.get(position).getThumbnailURL();
            tv_empty.setVisibility(View.GONE);
            mExoPlayerView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);


            Picasso.get().load(imageUrl).placeholder(R.drawable.details_not_found).into(imageView);
        }


//      hideSystemUi();

        String description =steps.get(position).getDescription();
        tv_description.setText(description);
        tv_currentStep.setText((position + 1) + "/" + sizeOfList);


    }

    private void initializePlayer(Uri mediaUri ) {

        if (mExoPlayer == null) {

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();


            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);

            mExoPlayer.seekTo(positionOfExoplayer);

            Log.d(TAG, "initializePlayer:  mExoPlayer.seekTo "+positionOfExoplayer);
            mExoPlayerView.setPlayer(mExoPlayer);




            String userAgent = Util.getUserAgent(mContext, "alex_backingapp");
            MediaSource mediaSource = new ExtractorMediaSource(
                    mediaUri,
                    new DefaultDataSourceFactory(mContext, userAgent),
                    new DefaultExtractorsFactory(), null, null);

            //create playlist
            mExoPlayer.prepare( mediaSource,false,false);

            mExoPlayer.setPlayWhenReady(false);




        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailsStepFragmentInteractionListener) {
            mListener = (OnDetailsStepFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("position",position);
        outState.putInt("SizeOfList",sizeOfList);
        outState.putParcelableArrayList("steps",steps);
        outState.putLong("positionOfExoplayer",mExoPlayer.getCurrentPosition());

        Log.d(TAG, "onSaveInstanceState: positionOfExoplayer : "+mExoPlayer.getCurrentPosition());

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnDetailsStepFragmentInteractionListener {

        void setCurrnt(int position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();

    }

    private void releasePlayer() {

        if (mExoPlayer!=null){
//            positionOfExoplayer=mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
//        if(tablet)
//        {
//            return;
//        }
        mExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        tv_empty.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        imageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(Uri.parse(videoUrl));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer(Uri.parse(videoUrl));
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}
