package com.project.levitg.teachmeclient;

/**
 * Created by UserG on 16.01.2018.
 */

import java.net.MalformedURLException;

import android.content.Context;

import com.microsoft.windowsazure.mobileservices.*;

public class AzureServiceAdapter {
    private Context mContext;
    private MobileServiceClient mClient;
    private static AzureServiceAdapter mInstance = null;

    private AzureServiceAdapter(Context context) throws MalformedURLException {
        mContext = context;
        mClient = new MobileServiceClient(GlobalConstants.BACKEND_URL, mContext);

    }

    public static void Initialize(Context context) throws MalformedURLException {
        if (mInstance == null) {
            mInstance = new AzureServiceAdapter(context);
        }
    }

    public static AzureServiceAdapter getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("AzureServiceAdapter is not initialized");
        }
        return mInstance;
    }

    public MobileServiceClient getClient() {
        return mClient;
    }


    // Public methods that operate on mClient here.
}