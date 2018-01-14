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


public class CommentRatingsActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    ListView listView;
    Button btnGetAll, btnAdd, btnBack;
    RestClient restClient;
    TextView commentRating_Id;
    private String _Comment_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restClient = new RestClient();
        setContentView(R.layout.activity_commentratings);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Comment_Id = "";
        Intent intent = getIntent();
        _Comment_Id = intent.getStringExtra("comment_Id");
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

            Intent intent = new Intent(this, CommentRatingDetailActivity.class);
            intent.putExtra("commentRating_Id", "");
            intent.putExtra("comment_Id", _Comment_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            // You should use refreshScreen() instead, just show you an easier method only :P
            refreshScreen_SimpleWay();
        }
    }

    private String buildQueryCommentId(String param) {
        String query = String.format("commentId eq '%s'", param);
        return query;
    }

    private void refreshScreen_SimpleWay() {
        Call<List<CommentRating>> call = restClient.getService().getCommentRatingByQuery(buildQueryCommentId(_Comment_Id));
        call.enqueue(new Callback<List<CommentRating>>() {
                         @Override
                         public void onResponse(Call<List<CommentRating>> call, Response<List<CommentRating>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);


                             ArrayList<HashMap<String, String>> commentRatingList = new ArrayList<HashMap<String, String>>();

                             for (int i = 0; i < response.body().size(); i++) {
                                 HashMap<String, String> commentRating = new HashMap<String, String>();
                                 commentRating.put("id", String.valueOf(response.body().get(i).getId()));
                                 commentRating.put("name", String.valueOf(response.body().get(i).getUser().getFullName()));
                                 commentRatingList.add(commentRating);
                             }

                             lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                     commentRating_Id = (TextView) view.findViewById(R.id.user_Id);
                                     String commentRatingId = commentRating_Id.getText().toString();
                                     Intent objIndent = new Intent(getApplicationContext(), CommentRatingDetailActivity.class);
                                     objIndent.putExtra("commentRating_Id", commentRatingId);
                                     objIndent.putExtra("comment_Id", _Comment_Id);
                                     startActivity(objIndent);
                                 }
                             });
                             ListAdapter adapter = new SimpleAdapter(CommentRatingsActivity.this, commentRatingList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
                             lv.setAdapter(adapter);
                         }

                         @Override
                         public void onFailure(Call<List<CommentRating>> call, Throwable t) {
                             Toast.makeText(CommentRatingsActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                         }
                     }
        );


    }
}