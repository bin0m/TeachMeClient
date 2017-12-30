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


public class SectionsActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    ListView listView;
    Button btnGetAll, btnAdd, btnBack;
    RestClient restClient;
    TextView section_Id;
    private String _Course_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restClient = new RestClient();
        setContentView(R.layout.activity_sections);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Course_Id = "";
        Intent intent = getIntent();
        _Course_Id = intent.getStringExtra("course_Id");
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

            Intent intent = new Intent(this, SectionDetailActivity.class);
            intent.putExtra("section_Id", "");
            intent.putExtra("course_Id", _Course_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            // You should use refreshScreen() instead, just show you an easier method only :P
            refreshScreen_SimpleWay();
        }
    }


    private void refreshScreen_SimpleWay() {
        Call<List<Section>> call = restClient.getService().getSectionsByCourseId(_Course_Id);
        call.enqueue(new Callback<List<Section>>() {
                         @Override
                         public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);


                             ArrayList<HashMap<String, String>> sectionList = new ArrayList<HashMap<String, String>>();

                             for (int i = 0; i < response.body().size(); i++) {
                                 HashMap<String, String> section = new HashMap<String, String>();
                                 section.put("id", String.valueOf(response.body().get(i).getId()));
                                 section.put("name", String.valueOf(response.body().get(i).getName()));
                                 sectionList.add(section);
                             }

                             lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                     section_Id = (TextView) view.findViewById(R.id.user_Id);
                                     String sectionId = section_Id.getText().toString();
                                     Intent objIndent = new Intent(getApplicationContext(), SectionDetailActivity.class);
                                     objIndent.putExtra("section_Id", sectionId);
                                     objIndent.putExtra("course_Id", _Course_Id);
                                     startActivity(objIndent);
                                 }
                             });
                             ListAdapter adapter = new SimpleAdapter(SectionsActivity.this, sectionList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
                             lv.setAdapter(adapter);
                         }

                         @Override
                         public void onFailure(Call<List<Section>> call, Throwable t) {
                             Toast.makeText(SectionsActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                         }
                     }
        );


    }
}