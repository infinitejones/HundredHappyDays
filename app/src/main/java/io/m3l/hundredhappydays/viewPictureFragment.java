package io.m3l.hundredhappydays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

// For displaying the date

/**
 * A placeholder fragment containing a simple view.
 */
public class viewPictureFragment extends Fragment {

    public static viewPictureFragment newInstance() {
        viewPictureFragment fragment = new viewPictureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_viewpicture, container, false);

        // Display today's date as a TextView
        TextView tv = (TextView) rootView.findViewById(R.id.todaysDate);
        String ct = DateFormat.getDateInstance().format(new Date());
        tv.setText(ct);

        return rootView;

    }
}
