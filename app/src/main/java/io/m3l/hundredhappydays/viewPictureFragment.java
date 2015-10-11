package io.m3l.hundredhappydays;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;

import static java.lang.String.valueOf;

public class viewPictureFragment extends Fragment {

    public static final String TAG = "viewPictureFragment";

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

        ImageView imgView = (ImageView) rootView.findViewById(R.id.todaysImage);
        imgView.setImageURI(Uri.parse(valueOf(getNewestMediaFileUri())));

        return rootView;

    }

    // generate the URI of the most recent image
    private Uri getNewestMediaFileUri() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "HundredHappyDays");

        if (isExternalStorageAvailable()) {
            if (!mediaStorageDir.exists()) {
                Log.e(TAG, "Failed to find storage directory");
                return null;
            } else if (mediaStorageDir.listFiles().length == 0) {
                Log.e(TAG, "Storage directory is empty");
                return null;
            }
        }

        File newestPicture = null;
        File[] allPictures = mediaStorageDir.listFiles();
        for (File thisPicture : allPictures) {

            Date thisPicturesDate = new Date(thisPicture.lastModified());

            if (newestPicture == null) {
                newestPicture = thisPicture;
            } else {
                Date newestDate = new Date(newestPicture.lastModified());
                if (thisPicturesDate.after(newestDate)) {
                    newestPicture = thisPicture;
                }
            }
        }

        return Uri.fromFile(newestPicture);
    }

    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
}

