package com.project.levitg.teachmeclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;


public class CoursesActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    ListView listView;
    Button btnGetAll, btnAdd, btnBack;
    RestClient restClient;
    TextView course_Id;
    private String _Teacher_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restClient = new RestClient();
        setContentView(R.layout.activity_courses);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Teacher_Id = "";
        Intent intent = getIntent();
        _Teacher_Id = intent.getStringExtra("teacher_Id");
    }

    //This function will call when the screen is activate
    @Override
    public void onResume() {
        super.onResume();
        refreshScreen_SimpleWay();
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshScreen_SimpleWay();
    }


    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra("course_Id", "");
            intent.putExtra("teacher_Id", _Teacher_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            // You should use refreshScreen() instead, just show you an easier method only :P
            refreshScreen_SimpleWay();
        }
    }


    private void refreshScreen_SimpleWay() {
        Call<List<Course>> call = restClient.getService().getCoursesByUserId(_Teacher_Id, true);
        call.enqueue(new Callback<List<Course>>() {
                         @Override
                         public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);


                             ArrayList<HashMap<String, String>> courseList = new ArrayList<HashMap<String, String>>();

                             for (int i = 0; i < response.body().size(); i++) {
                                 HashMap<String, String> course = new HashMap<String, String>();
                                 course.put("id", String.valueOf(response.body().get(i).getId()));
                                 course.put("name", String.valueOf(response.body().get(i).getName()));
                                 courseList.add(course);
                             }

                             lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                     course_Id = (TextView) view.findViewById(R.id.user_Id);
                                     String courseId = course_Id.getText().toString();
                                     Intent objIndent = new Intent(getApplicationContext(), CourseDetailActivity.class);
                                     objIndent.putExtra("course_Id", courseId);
                                     objIndent.putExtra("teacher_Id", _Teacher_Id);
                                     startActivity(objIndent);
                                 }
                             });
                             ListAdapter adapter = new SimpleAdapter(CoursesActivity.this, courseList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
                             lv.setAdapter(adapter);
                         }

                         @Override
                         public void onFailure(Call<List<Course>> call, Throwable t) {
                             Toast.makeText(CoursesActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                         }
                     }
        );


    }
}