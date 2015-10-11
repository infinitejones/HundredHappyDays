package io.m3l.hundredhappydays;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    AsyncTask<Void, Void, rssItem> fetchRSSaSync = null;

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

        String title = null;
        String description = null;

        rssItem todaysQuote = new rssItem(title, description);
        new AsyncTask<Void, Void, rssItem>() {

            @Override
            protected rssItem doInBackground(Void... params) {
                String title = null;
                String description = null;
                rssItem todaysQuote = new rssItem(title, description);

                URL url = null;
                try {
                    url = new URL("http://www.quotationspage.com/data/mqotd.rss");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                rssParser parser = new rssParser();
                try {
                    return parser.parse(urlConnection.getInputStream());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return todaysQuote;
            }
        };



        /* Display today's date as a TextView */
        TextView dateText = (TextView) rootView.findViewById(R.id.todaysDate);
        String todaysDate = DateFormat.getDateInstance().format(new Date());
        dateText.setText(todaysDate);

        /* Display today's Quote of the Day in two text views - description and title */

        TextView quoteText = (TextView) rootView.findViewById(R.id.qotdText);
        TextView quoteName = (TextView) rootView.findViewById(R.id.qotdName);
        String qotdText = todaysQuote.getDescription();
        String qotdName = todaysQuote.getTitle();
        quoteText.setText(qotdText);
        quoteName.setText(qotdName);


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
        mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        if (mMediaUri == null) {
            Toast.makeText(getActivity(), "Problem accessing device storage", Toast.LENGTH_LONG).show();
        } else {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
            startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST);
        }
    }

    private Uri getOutputMediaFileUri(int MEDIA_TYPE_IMAGE) {
        if (isExternalStorageAvailable()) {
            File mediaStorageDir = new File
                    (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "HundredHappyDays");

            if (! mediaStorageDir.exists()) {
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

