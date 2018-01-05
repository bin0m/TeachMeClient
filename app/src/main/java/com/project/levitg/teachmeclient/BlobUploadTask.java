package com.project.levitg.teachmeclient;

/**
 * Created by UserG on 03.01.2018.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.TextView;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.*;

public class BlobUploadTask extends AsyncTask<String, Void, Void> {

    private TextView view;
    private BlobManagerActivity act;
    private InputStream imageStream;
    private int imageLength;
    private String name;

    public BlobUploadTask(BlobManagerActivity act, TextView view, InputStream imageStream, int imageLength, String name) {
        this.view = view;
        this.act = act;
        this.imageStream = imageStream;
        this.imageLength = imageLength;
        this.name = name;
    }

    @Override
    protected Void doInBackground(String... arg0) {

        act.outputText(view, "Starting upload task");

        try {
            // Setup the cloud storage account.
            CloudStorageAccount account = CloudStorageAccount
                    .parse(BlobManagerActivity.storageConnectionString);

            // Create a blob service client
            CloudBlobClient blobClient = account.createCloudBlobClient();

            // Get a reference to a images container
            CloudBlobContainer container = blobClient.getContainerReference("images");

            act.outputText(view, "Get reference to blob container");

            // Create the container if it does not exist
            container.createIfNotExists();

            // Make the container public
            // Create a permissions object
            BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

            // Include public access in the permissions object
            containerPermissions
                    .setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

            // Set the permissions on the container
            container.uploadPermissions(containerPermissions);

            // Get a reference to a blob in the container
            CloudBlockBlob blob = container.getBlockBlobReference(name);

            act.outputText(view, "Get reference to blob");

            blob.upload(imageStream, imageLength);

            act.outputText(view, "Upload to blob completed");

        } catch (Throwable t) {
            act.printException(t);
        }

        return null;
    }
}