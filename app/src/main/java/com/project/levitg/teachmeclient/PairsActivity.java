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


public class PairsActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    ListView listView;
    Button btnGetAll, btnAdd, btnBack;
    RestClient restClient;
    TextView pair_Id;
    private String _Exercise_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restClient = new RestClient();
        setContentView(R.layout.activity_pairs);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Exercise_Id = "";
        Intent intent = getIntent();
        _Exercise_Id = intent.getStringExtra("exercise_Id");
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

            Intent intent = new Intent(this, PairDetailActivity.class);
            intent.putExtra("pair_Id", "");
            intent.putExtra("exercise_Id", _Exercise_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            // You should use refreshScreen() instead, just show you an easier method only :P
            refreshScreen_SimpleWay();
        }
    }


    private void refreshScreen_SimpleWay() {
        Call<List<Pair>> call = restClient.getService().getPairsByExerciseId(_Exercise_Id);
        call.enqueue(new Callback<List<Pair>>() {
                         @Override
                         public void onResponse(Call<List<Pair>> call, Response<List<Pair>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);


                             ArrayList<HashMap<String, String>> pairList = new ArrayList<HashMap<String, String>>();

                             for (int i = 0; i < response.body().size(); i++) {
                                 HashMap<String, String> pair = new HashMap<String, String>();
                                 pair.put("id", String.valueOf(response.body().get(i).getId()));
                                 pair.put("name", String.valueOf(response.body().get(i).getValue()));
                                 pairList.add(pair);
                             }

                             lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                     pair_Id = (TextView) view.findViewById(R.id.user_Id);
                                     String pairId = pair_Id.getText().toString();
                                     Intent objIndent = new Intent(getApplicationContext(), PairDetailActivity.class);
                                     objIndent.putExtra("pair_Id", pairId);
                                     objIndent.putExtra("exercise_Id", _Exercise_Id);
                                     startActivity(objIndent);
                                 }
                             });
                             ListAdapter adapter = new SimpleAdapter(PairsActivity.this, pairList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
                             lv.setAdapter(adapter);
                         }

                         @Override
                         public void onFailure(Call<List<Pair>> call, Throwable t) {
                             Toast.makeText(PairsActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                         }
                     }
        );


    }
}