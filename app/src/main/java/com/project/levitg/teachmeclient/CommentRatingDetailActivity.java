package com.project.levitg.teachmeclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentRatingDetailActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnRegister, btnDelete, btnDeleteFull;
    Button btnClose;
    EditText editTextUserId;
    EditText editTextUserName;
    EditText editTextCommentRating;
    private String _CommentRating_Id, _Comment_Id;
    RestClient restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestClient();
        setContentView(R.layout.activity_commentrating_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextUserId = (EditText) findViewById(R.id.editTextUserId);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextCommentRating = (EditText) findViewById(R.id.editTextCommentRating);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _CommentRating_Id = "";
        _Comment_Id = "";
        Intent intent = getIntent();
        _CommentRating_Id = intent.getStringExtra("commentRating_Id");
        _Comment_Id = intent.getStringExtra("comment_Id");

        if (_CommentRating_Id != null && !_CommentRating_Id.isEmpty()) {
            Call<CommentRating> call = restService.getService().getCommentRatingById(_CommentRating_Id);
            call.enqueue(new Callback<CommentRating>() {
                @Override
                public void onResponse(Call<CommentRating> call, Response<CommentRating> response) {
                    CommentRating commentRating = response.body();
                    editTextUserId.setText(commentRating.getUserId());
                    editTextUserName.setText(commentRating.getUser().getFullName());
                    editTextCommentRating.setText(String.valueOf(commentRating.getRating()));
                    _Comment_Id = commentRating.getCommentId();
                }

                @Override
                public void onFailure(Call<CommentRating> call, Throwable t) {
                    Toast.makeText(CommentRatingDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restService.getService().deleteCommentRatingById(_CommentRating_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 204) {
                        Toast.makeText(CommentRatingDetailActivity.this, "CommentRating Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(CommentRatingDetailActivity.this, "Error: Not Deleted" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CommentRatingDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (findViewById(R.id.btnSave) == v) {

            if (_CommentRating_Id == null || _CommentRating_Id.isEmpty()) {
                // No Id -> new commentRating

                CommentRating commentRating = new CommentRating();
                commentRating.setUserId(editTextUserId.getText().toString());
                commentRating.setRating(Integer.parseInt(editTextCommentRating.getText().toString()));
                commentRating.setCommentId(_Comment_Id);

                restService.getService().addCommentRating(commentRating).enqueue(new Callback<CommentRating>() {
                    @Override
                    public void onResponse(Call<CommentRating> call, Response<CommentRating> response) {
                        if (response.code() == 201) {
                            Toast.makeText(CommentRatingDetailActivity.this, "New CommentRating Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CommentRatingDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommentRating> call, Throwable t) {
                        Toast.makeText(CommentRatingDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                // Update existing commentRating

                Call<CommentRating> call = restService.getService().getCommentRatingById(_CommentRating_Id);
                call.enqueue(new Callback<CommentRating>() {
                    @Override
                    public void onResponse(Call<CommentRating> call, Response<CommentRating> response) {
                        CommentRating existingCommentRating = response.body();
                        existingCommentRating.setUserId(editTextUserId.getText().toString());
                        existingCommentRating.setRating(Integer.parseInt(editTextCommentRating.getText().toString()));
                        existingCommentRating.setCommentId(_Comment_Id);

                        // Use Backend API to update commentRating
                        restService.getService().updateCommentRatingById(_CommentRating_Id, existingCommentRating).enqueue(new Callback<CommentRating>() {
                            @Override
                            public void onResponse(Call<CommentRating> call, Response<CommentRating> response) {
                                Toast.makeText(CommentRatingDetailActivity.this, response.body().getUserId() + " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<CommentRating> call, Throwable t) {
                                Toast.makeText(CommentRatingDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<CommentRating> call, Throwable t) {
                        Toast.makeText(CommentRatingDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        }
    }
}