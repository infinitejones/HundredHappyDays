package io.m3l.hundredhappydays;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// For displaying the date

/**
 * A placeholder fragment containing a simple view.
 */
public class takePictureFragment extends Fragment {

    public static final String TAG = "takePictureFragment";
    public static final int TAKE_PHOTO_REQUEST = 0;
    public static final int VIEW_PHOTO_REQUEST = 1;
    public static final int VIEW_GALLERY_REQUEST = 2;
    public static final int MEDIA_TYPE_IMAGE = 3;

    protected Uri mMediaUri;

    private Button mTakePictureButton;

    public static takePictureFragment newInstance() {
        takePictureFragment fragment = new takePictureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_takepicture, container, false);
        mTakePictureButton = (Button) rootView.findViewById(R.id.takePictureButton);

        // Display today's date as a TextView
        TextView tv = (TextView) rootView.findViewById(R.id.todaysDate);
        String ct = DateFormat.getDateInstance().format(new Date());
        tv.setText(ct);

        mTakePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        return rootView;
    }

    public void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.e(TAG, "Intent created");
        mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        Log.e(TAG, "mMediaURI: " + mMediaUri);
        if (mMediaUri == null) {
            Toast.makeText(getActivity(), "Doh! Problem accessing device storage", Toast.LENGTH_LONG).show();
        } else {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
            startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST);
        }
    }

    private Uri getOutputMediaFileUri(int mediaType) {
        if (isExternalStorageAvailable()) {
            Log.e(TAG, "External storage available");
            String appName = getActivity().getString(R.string.app_name);
            File mediaStorageDir = new File
                    (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), appName);
            Log.e(TAG, "mediaStorageDir: " + mediaStorageDir);

            if (! mediaStorageDir.exists()) {
                Log.e(TAG, "mediaStorageDir doesn't exist");
                if (!mediaStorageDir.mkdirs()) {
                    Log.e(TAG, "Failed to create storage directory");
                    return null;
                }
            }

            File mediaFile;
            Date now = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

            String path = mediaStorageDir.getPath() + File.separator;
            mediaFile = new File (path + "IMG_" + timestamp + ".jpg");

            Log.d(TAG, "File: " + Uri.fromFile(mediaFile));

            return Uri.fromFile(mediaFile);

        } else {
            return null;
        }
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
}

