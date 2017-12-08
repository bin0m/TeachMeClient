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
    TextView user_Id;

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

            Intent intent = new Intent(this, UserDetailActivity.class);
            intent.putExtra("user_Id", 0);
            startActivity(intent);

        } else {
            // You should use refreshScreen() instead, just show you an easier method only :P
            refreshScreen_SimpleWay();
        }
    }

    private void refreshScreen() {
        //Call to server to grab list of user records. this is a async
        Call<List<User>> call = restClient.getService().getUser();
        call.enqueue(new Callback<List<User>>() {
                         @Override
                         public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);

                             CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, R.layout.view_user_entry, response.body());

                             lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                     user_Id = (TextView) view.findViewById(R.id.user_Id);
                                     String userId = user_Id.getText().toString();
                                     Intent objIndent = new Intent(getApplicationContext(), UserDetailActivity.class);
                                     objIndent.putExtra("user_Id", userId);
                                     startActivity(objIndent);
                                 }
                             });
                             lv.setAdapter(customAdapter);
                         }

                         @Override
                         public void onFailure(Call<List<User>> call, Throwable t) {
                             Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                         }
                     }
        );
    }


    private void refreshScreen_SimpleWay() {
        Call<List<User>> call = restClient.getService().getUser();
        call.enqueue(new Callback<List<User>>() {
                         @Override
                         public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);


                             ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();

                             for (int i = 0; i < response.body().size(); i++) {
                                 HashMap<String, String> user = new HashMap<String, String>();
                                 user.put("id", String.valueOf(response.body().get(i).getId()));
                                 user.put("name", String.valueOf(response.body().get(i).getFullName()));

                                 userList.add(user);
                             }

                             lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                     user_Id = (TextView) view.findViewById(R.id.user_Id);
                                     String userId = user_Id.getText().toString();
                                     Intent objIndent = new Intent(getApplicationContext(), UserDetailActivity.class);
                                     objIndent.putExtra("user_Id", userId);
                                     startActivity(objIndent);
                                 }
                             });
                             ListAdapter adapter = new SimpleAdapter(MainActivity.this, userList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
                             lv.setAdapter(adapter);
                         }

                         @Override
                         public void onFailure(Call<List<User>> call, Throwable t) {
                             Toast.makeText(MainActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                         }
                     }
        );


    }
}