package io.m3l.hundredhappydays;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class viewGalleryFragment extends Fragment {

    public static viewGalleryFragment newInstance() {
        viewGalleryFragment fragment = new viewGalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}