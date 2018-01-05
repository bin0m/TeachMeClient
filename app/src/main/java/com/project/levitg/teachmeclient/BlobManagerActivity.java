package com.project.levitg.teachmeclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;


public class BlobManagerActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;"
            + "AccountName=teachme;"
            + "AccountKey=00Fbb3fwzAZ0PRj5h3ilt+kWen/vosQuNQKauzau/UsIn92Ct+x5S/zCc8zsvPxM9HbSxeXmAbSdN6QNhMo2IA==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blob_manager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void ChooseImageClick(View view) {
        Intent intent = new Intent();

        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void UploadResourceClick(View view) {
        try {
            final InputStream inputStream = getContentResolver().openInputStream(imageUri);
            final int imageLength = inputStream.available();

            new BlobUploadTask(this, (TextView) findViewById(R.id.textView), inputStream, imageLength, "testImg01.jpg")
                    .execute();
        } catch (final Throwable t) {
            printException(t);
        }

    }

    /**
     * Prints the specified text value to the view and to LogCat.
     *
     * @param view  The view to print to.
     * @param value The value to print.
     */
    public void outputText(final TextView view, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.append(value + "\n");
                System.out.println(view);
            }
        });
    }

    /**
     * Clears the text from the specified view.
     *
     * @param view The view to clear.
     */
    public void clearText(final TextView view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setText("");
            }
        });
    }

    /**
     * Prints out the exception information .
     */
    public void printException(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        t.printStackTrace(printWriter);
        outputText(
                (TextView) findViewById(R.id.textView),
                String.format(
                        "Got an exception from running samples. Exception details:\n%s\n",
                        stringWriter.toString()));
    }
}
