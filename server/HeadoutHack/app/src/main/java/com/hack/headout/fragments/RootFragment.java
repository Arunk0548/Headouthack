package com.hack.headout.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Arun Kumar on 2/7/2016.
 */
public class RootFragment extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((RootFragmentListener) getActivity()).onFragmentViewCreated(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((RootFragmentListener) getActivity()).onFragmentAttached(this);
    }

    public interface RootFragmentListener {
        void onFragmentAttached(RootFragment fragment);

        void onFragmentViewCreated(RootFragment fragment);
    }
}
