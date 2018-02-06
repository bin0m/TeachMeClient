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


public class ExercisesActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    ListView listView;
    Button btnGetAll, btnAdd, btnBack;
    RestClient restClient;
    TextView exercise_Id;
    private String _Lesson_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restClient = new RestClient();
        setContentView(R.layout.activity_exercise);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Lesson_Id = "";
        Intent intent = getIntent();
        _Lesson_Id = intent.getStringExtra("lesson_Id");
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

            Intent intent = new Intent(this, ExerciseDetailActivity.class);
            intent.putExtra("exercise_Id", "");
            intent.putExtra("lesson_Id", _Lesson_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            // You should use refreshScreen() instead, just show you an easier method only :P
            refreshScreen_SimpleWay();
        }
    }


    private void refreshScreen_SimpleWay() {
        Call<List<Exercise>> call;
        if (_Lesson_Id == null || _Lesson_Id.isEmpty()) {
            call = restClient.getService().getExercise();
        } else {
            call = restClient.getService().getExercisesByLessonId(_Lesson_Id);
        }
        call.enqueue(new Callback<List<Exercise>>() {
                         @Override
                         public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);

                             ArrayList<HashMap<String, String>> exerciseList = new ArrayList<HashMap<String, String>>();

                             for (int i = 0; i < response.body().size(); i++) {
                                 HashMap<String, String> exercise = new HashMap<String, String>();
                                 exercise.put("id", String.valueOf(response.body().get(i).getId()));
                                 exercise.put("name", String.valueOf(response.body().get(i).getName()));
                                 exerciseList.add(exercise);
                             }

                             lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                     exercise_Id = (TextView) view.findViewById(R.id.user_Id);
                                     String exerciseId = exercise_Id.getText().toString();
                                     Intent objIndent = new Intent(getApplicationContext(), ExerciseDetailActivity.class);
                                     objIndent.putExtra("exercise_Id", exerciseId);
                                     objIndent.putExtra("lesson_Id", _Lesson_Id);
                                     startActivity(objIndent);
                                 }
                             });
                             ListAdapter adapter = new SimpleAdapter(ExercisesActivity.this, exerciseList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
                             lv.setAdapter(adapter);
                         }

                         @Override
                         public void onFailure(Call<List<Exercise>> call, Throwable t) {
                             Toast.makeText(ExercisesActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                         }
                     }
        );


    }
}