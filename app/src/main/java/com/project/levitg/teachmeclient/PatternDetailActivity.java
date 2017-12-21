package com.project.levitg.teachmeclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PatternDetailActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnAdd, btnDelete;
    Button btnClose;
    EditText editTextName;
    EditText editLessonId;
    EditText editTextJson;
    EditText editTextType;
    private String _Pattern_Id, _Lesson_Id;
    RestClient restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestClient();
        setContentView(R.layout.activity_pattern_detail);

        btnAdd = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextJson = (EditText) findViewById(R.id.editTextJson);
        editLessonId = (EditText) findViewById(R.id.editLessonId);
        editTextType = (EditText) findViewById(R.id.editTextType);


        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Pattern_Id = "";
        _Lesson_Id = "";
        Intent intent = getIntent();
        _Pattern_Id = intent.getStringExtra("pattern_Id");
        _Lesson_Id = intent.getStringExtra("lesson_Id");
        editLessonId.setText(_Lesson_Id);

        if (_Pattern_Id != null && !_Pattern_Id.isEmpty()) {
            Call<Pattern> call = restService.getService().getPatternById(_Pattern_Id);
            call.enqueue(new Callback<Pattern>() {
                @Override
                public void onResponse(Call<Pattern> call, Response<Pattern> response) {
                    Pattern pattern = response.body();
                    editTextName.setText(pattern.getName());
                    editTextJson.setText(pattern.getJsonText());
                    editLessonId.setText(pattern.getLessonId());
                    editTextType.setText(pattern.getType());
                }

                @Override
                public void onFailure(Call<Pattern> call, Throwable t) {
                    Toast.makeText(PatternDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restService.getService().deletePatternById(_Pattern_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(PatternDetailActivity.this, "Pattern Record Deleted", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(PatternDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (findViewById(R.id.btnSave) == v) {

            if (_Pattern_Id == null || _Pattern_Id.isEmpty()) {
                // No Id -> new pattern

                Pattern pattern = new Pattern();
                pattern.setJsonText(editTextJson.getText().toString());
                pattern.setType(editTextType.getText().toString());
                pattern.setName(editTextName.getText().toString());
                pattern.setLessonId(editLessonId.getText().toString());

                restService.getService().addPattern(pattern).enqueue(new Callback<Pattern>() {
                    @Override
                    public void onResponse(Call<Pattern> call, Response<Pattern> response) {
                        if (response.code() == 201) {
                            Toast.makeText(PatternDetailActivity.this, "New Pattern Added.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PatternDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Pattern> call, Throwable t) {
                        Toast.makeText(PatternDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });


            } else {
                // Update existing pattern

                Call<Pattern> call = restService.getService().getPatternById(_Pattern_Id);
                call.enqueue(new Callback<Pattern>() {
                    @Override
                    public void onResponse(Call<Pattern> call, Response<Pattern> response) {
                        Pattern existingPattern = response.body();
                        existingPattern.setJsonText(editTextJson.getText().toString());
                        existingPattern.setType(editTextType.getText().toString());
                        existingPattern.setName(editTextName.getText().toString());
                        existingPattern.setLessonId(editLessonId.getText().toString());

                        // Use Backend API to update pattern
                        restService.getService().updatePatternById(_Pattern_Id, existingPattern).enqueue(new Callback<Pattern>() {
                            @Override
                            public void onResponse(Call<Pattern> call, Response<Pattern> response) {
                                Toast.makeText(PatternDetailActivity.this, response.body().getName() + " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Pattern> call, Throwable t) {
                                Toast.makeText(PatternDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Pattern> call, Throwable t) {
                        Toast.makeText(PatternDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        }
    }
}