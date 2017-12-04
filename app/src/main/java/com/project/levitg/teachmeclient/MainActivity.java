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


public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    ListView listView;
    Button btnGetAll, btnAdd;
    RestClient restClient;
    TextView student_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restClient = new RestClient();
        setContentView(R.layout.activity_main);
        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);


    }

    //This function will call when the screen is activate
    @Override
    public void onResume() {

        super.onResume();
        refreshScreen();
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, StudentDetailActivity.class);
            intent.putExtra("student_Id", 0);
            startActivity(intent);

        } else {
            // You should use refreshScreen() instead, just show you an easier method only :P
            refreshScreen_SimpleWay();
        }
    }

    private void refreshScreen() {
        //Call to server to grab list of student records. this is a async
        Call<List<Student>> call = restClient.getService().getStudent();
        call.enqueue(new Callback<List<Student>>() {
                         @Override
                         public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);

                             CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, R.layout.view_student_entry, response.body());

                             lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                     student_Id = (TextView) view.findViewById(R.id.student_Id);
                                     String studentId = student_Id.getText().toString();
                                     Intent objIndent = new Intent(getApplicationContext(), StudentDetailActivity.class);
                                     objIndent.putExtra("student_Id", studentId);
                                     startActivity(objIndent);
                                 }
                             });
                             lv.setAdapter(customAdapter);
                         }

                         @Override
                         public void onFailure(Call<List<Student>> call, Throwable t) {
                             Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                         }
                     }
        );
    }


    private void refreshScreen_SimpleWay() {
        Call<List<Student>> call = restClient.getService().getStudent();
        call.enqueue(new Callback<List<Student>>() {
                         @Override
                         public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);


                             ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

                             for (int i = 0; i < response.body().size(); i++) {
                                 HashMap<String, String> student = new HashMap<String, String>();
                                 student.put("id", String.valueOf(response.body().get(i).getId()));
                                 student.put("name", String.valueOf(response.body().get(i).getFullName()));

                                 studentList.add(student);
                             }

                             lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                     student_Id = (TextView) view.findViewById(R.id.student_Id);
                                     String studentId = student_Id.getText().toString();
                                     Intent objIndent = new Intent(getApplicationContext(), StudentDetailActivity.class);
                                     objIndent.putExtra("student_Id", studentId);
                                     startActivity(objIndent);
                                 }
                             });
                             ListAdapter adapter = new SimpleAdapter(MainActivity.this, studentList, R.layout.view_student_entry, new String[]{"id", "name"}, new int[]{R.id.student_Id, R.id.student_name});
                             lv.setAdapter(adapter);
                         }

                         @Override
                         public void onFailure(Call<List<Student>> call, Throwable t) {
                             Toast.makeText(MainActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                         }
                     }
        );


    }
}