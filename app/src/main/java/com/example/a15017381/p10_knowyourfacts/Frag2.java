package com.example.a15017381.p10_knowyourfacts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag2 extends Fragment {

    ImageView iv;

    public Frag2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        iv = (ImageView) view.findViewById(R.id.iv);
        String imageUrl = "http://68.media.tumblr.com/1ab11a2d2e82d977cf0edd0c94557667/tumblr_ots2mx7vZY1roqv59o1_500.png";
        Picasso.with(getContext()).load(imageUrl).into(iv);
        return view;
    }
}
