package com.project.levitg.teachmeclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ListOfActivities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_activities);
    }

    public void GoToUsers(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void GoToPatterns(View view) {
        Intent intent = new Intent(this, PatternsActivity.class);
        startActivity(intent);
    }

    public void GoToCourseCreateFlow(View view) {
        Intent intent = new Intent(this, TeachersActivity.class);
        startActivity(intent);
    }

    public void GoToBlobManager(View view) {
        Intent intent = new Intent(this, BlobManagerActivity.class);
        startActivity(intent);
    }

    public void GoToLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void GoToAzureTestLoginActivity(View view) {
        Intent intent = new Intent(this, AzureTestLoginActivity.class);
        startActivity(intent);
    }
}
