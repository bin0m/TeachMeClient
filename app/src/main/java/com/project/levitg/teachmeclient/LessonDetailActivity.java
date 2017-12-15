package com.project.levitg.teachmeclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LessonDetailActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnRegister, btnDelete;
    Button btnClose, btnViewPatterns;
    EditText editTextName;
    private String _Lesson_Id, _Section_Id;
    RestClient restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestClient();
        setContentView(R.layout.activity_lesson_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnViewPatterns = (Button) findViewById(R.id.btnViewPatterns);

        editTextName = (EditText) findViewById(R.id.editTextName);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnViewPatterns.setOnClickListener(this);


        _Lesson_Id = "";
        _Section_Id = "";
        Intent intent = getIntent();
        _Lesson_Id = intent.getStringExtra("lesson_Id");
        _Section_Id = intent.getStringExtra("section_Id");

        if (_Lesson_Id != null && !_Lesson_Id.isEmpty()) {
            Call<Lesson> call = restService.getService().getLessonById(_Lesson_Id);
            call.enqueue(new Callback<Lesson>() {
                @Override
                public void onResponse(Call<Lesson> call, Response<Lesson> response) {
                    Lesson lesson = response.body();
                    editTextName.setText(lesson.getName());
                }

                @Override
                public void onFailure(Call<Lesson> call, Throwable t) {
                    Toast.makeText(LessonDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restService.getService().deleteLessonById(_Lesson_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(LessonDetailActivity.this, "Lesson Record Deleted", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(LessonDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (v == findViewById(R.id.btnViewPatterns)) {
            Intent intent = new Intent(this, PatternsActivity.class);
            intent.putExtra("lesson_Id", _Lesson_Id);
            startActivity(intent);
        } else if (findViewById(R.id.btnSave) == v) {

            if (_Lesson_Id == null || _Lesson_Id.isEmpty()) {
                // No Id -> new lesson

                Lesson lesson = new Lesson();
                lesson.setName(editTextName.getText().toString());
                lesson.setSectionId(_Section_Id);

                restService.getService().addLesson(lesson).enqueue(new Callback<Lesson>() {
                    @Override
                    public void onResponse(Call<Lesson> call, Response<Lesson> response) {
                        if (response.code() == 201) {
                            Toast.makeText(LessonDetailActivity.this, "New Lesson Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LessonDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Lesson> call, Throwable t) {
                        Toast.makeText(LessonDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });


            } else {
                // Update existing lesson

                Call<Lesson> call = restService.getService().getLessonById(_Lesson_Id);
                call.enqueue(new Callback<Lesson>() {
                    @Override
                    public void onResponse(Call<Lesson> call, Response<Lesson> response) {
                        Lesson existingLesson = response.body();
                        existingLesson.setName(editTextName.getText().toString());

                        // Use Backend API to update lesson
                        restService.getService().updateLessonById(_Lesson_Id, existingLesson).enqueue(new Callback<Lesson>() {
                            @Override
                            public void onResponse(Call<Lesson> call, Response<Lesson> response) {
                                Toast.makeText(LessonDetailActivity.this, response.body().getName() + " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Lesson> call, Throwable t) {
                                Toast.makeText(LessonDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Lesson> call, Throwable t) {
                        Toast.makeText(LessonDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        }
    }
}