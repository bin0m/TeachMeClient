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


public class LessonsActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    ListView listView;
    Button btnGetAll, btnAdd, btnBack;
    RestClient restClient;
    TextView lesson_Id;
    private String _Section_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restClient = new RestClient();
        setContentView(R.layout.activity_lessons);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Section_Id = "";
        Intent intent = getIntent();
        _Section_Id = intent.getStringExtra("section_Id");
    }

    //This function will call when the screen is activate
    @Override
    public void onResume() {
        super.onResume();
        refreshScreen_SimpleWay();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        refreshScreen_SimpleWay();
//    }


    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, LessonDetailActivity.class);
            intent.putExtra("lesson_Id", "");
            intent.putExtra("section_Id", _Section_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            // You should use refreshScreen() instead, just show you an easier method only :P
            refreshScreen_SimpleWay();
        }
    }


    private void refreshScreen_SimpleWay() {
        Call<List<Lesson>> call = restClient.getService().getLesson();
        call.enqueue(new Callback<List<Lesson>>() {
                         @Override
                         public void onResponse(Call<List<Lesson>> call, Response<List<Lesson>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);


                             ArrayList<HashMap<String, String>> lessonList = new ArrayList<HashMap<String, String>>();

                             for (int i = 0; i < response.body().size(); i++) {

                                 String sectionId = String.valueOf(response.body().get(i).getSectionId());
                                 if (sectionId.equals(_Section_Id)) {
                                     HashMap<String, String> lesson = new HashMap<String, String>();
                                     lesson.put("id", String.valueOf(response.body().get(i).getId()));
                                     lesson.put("name", String.valueOf(response.body().get(i).getName()));

                                     lessonList.add(lesson);
                                 }
                             }

                             lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                     lesson_Id = (TextView) view.findViewById(R.id.user_Id);
                                     String lessonId = lesson_Id.getText().toString();
                                     Intent objIndent = new Intent(getApplicationContext(), LessonDetailActivity.class);
                                     objIndent.putExtra("lesson_Id", lessonId);
                                     objIndent.putExtra("section_Id", _Section_Id);
                                     startActivity(objIndent);
                                 }
                             });
                             ListAdapter adapter = new SimpleAdapter(LessonsActivity.this, lessonList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
                             lv.setAdapter(adapter);
                         }

                         @Override
                         public void onFailure(Call<List<Lesson>> call, Throwable t) {
                             Toast.makeText(LessonsActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                         }
                     }
        );


    }
}