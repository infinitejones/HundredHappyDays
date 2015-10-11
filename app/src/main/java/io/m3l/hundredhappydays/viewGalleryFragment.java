package io.m3l.hundredhappydays;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

/**
 * A placeholder fragment containing a simple view.
 */
public class viewGalleryFragment extends Fragment {

    public static final String TAG = "viewGalleryFragment";
    public static final int TAKE_PHOTO_REQUEST = 0;
    public static final int VIEW_PHOTO_REQUEST = 1;
    public static final int VIEW_GALLERY_REQUEST = 2;
    public static final int MEDIA_TYPE_IMAGE = 3;

    protected Uri mMediaUri;

    public static viewGalleryFragment newInstance() {
        viewGalleryFragment fragment = new viewGalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_viewgallery, container, false);

        Intent choosePictureIntent = new Intent(Intent.ACTION_GET_CONTENT);

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "HundredHappyDays");
        Uri mediaStorageURI = Uri.fromFile(mediaStorageDir);
        choosePictureIntent.setDataAndType(mediaStorageURI, "image/*");
        startActivityForResult(choosePictureIntent, VIEW_GALLERY_REQUEST);

        return rootView;

    }
}