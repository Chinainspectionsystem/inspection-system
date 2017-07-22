package com.ice.edupatrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ice.edupatrol.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment1 extends Fragment {


    public GuideFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guide_fragment1, container, false);
    }

}