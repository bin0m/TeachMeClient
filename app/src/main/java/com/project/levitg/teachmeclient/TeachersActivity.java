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


public class TeachersActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    ListView listView;
    Button btnGetAll, btnAdd, btnBackToMain;
    RestClient restClient;
    TextView teacher_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restClient = new RestClient();
        setContentView(R.layout.activity_teachers);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBackToMain = (Button) findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(this);
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

            Intent intent = new Intent(this, TeacherDetailActivity.class);
            intent.putExtra("teacher_Id", 0);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBackToMain)) {
            finish();
        } else {
            // You should use refreshScreen() instead, just show you an easier method only :P
            refreshScreen_SimpleWay();
        }
    }


    private void refreshScreen_SimpleWay() {
        Call<List<User>> call = restClient.getService().getUser();
        call.enqueue(new Callback<List<User>>() {
                         @Override
                         public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);


                             ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();

                             for (int i = 0; i < response.body().size(); i++) {
                                 String userRole = String.valueOf(response.body().get(i).getUserRole());
                                 if (userRole.equals("Teacher")) {
                                     HashMap<String, String> user = new HashMap<String, String>();
                                     user.put("id", String.valueOf(response.body().get(i).getId()));
                                     user.put("name", String.valueOf(response.body().get(i).getFullName()));

                                     userList.add(user);
                                 }
                             }

                             lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                     teacher_Id = (TextView) view.findViewById(R.id.user_Id);
                                     String userId = teacher_Id.getText().toString();
                                     Intent objIndent = new Intent(getApplicationContext(), TeacherDetailActivity.class);
                                     objIndent.putExtra("teacher_Id", userId);
                                     startActivity(objIndent);
                                 }
                             });
                             ListAdapter adapter = new SimpleAdapter(TeachersActivity.this, userList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
                             lv.setAdapter(adapter);
                         }

                         @Override
                         public void onFailure(Call<List<User>> call, Throwable t) {
                             Toast.makeText(TeachersActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                         }
                     }
        );


    }
}