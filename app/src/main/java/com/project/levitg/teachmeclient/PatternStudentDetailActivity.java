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


public class PatternStudentDetailActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnRegister, btnDelete, btnDeleteFull;
    Button btnClose;
    EditText editTextStudentId;
    EditText editTextStudentAnswer;
    CheckBox checkBoxIsDone;
    private String _PatternStudent_Id, _Pattern_Id;
    RestClient restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestClient();
        setContentView(R.layout.activity_patternstudent_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextStudentId = (EditText) findViewById(R.id.editTextStudentId);
        editTextStudentAnswer = (EditText) findViewById(R.id.editTextStudentAnswer);
        checkBoxIsDone = (CheckBox) findViewById(R.id.checkBoxIsDone);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        checkBoxIsDone.setOnClickListener(this);


        _PatternStudent_Id = "";
        _Pattern_Id = "";
        Intent intent = getIntent();
        _PatternStudent_Id = intent.getStringExtra("patternstudent_Id");
        _Pattern_Id = intent.getStringExtra("pattern_Id");

        if (_PatternStudent_Id != null && !_PatternStudent_Id.isEmpty()) {
            Call<PatternStudent> call = restService.getService().getPatternStudentById(_PatternStudent_Id);
            call.enqueue(new Callback<PatternStudent>() {
                @Override
                public void onResponse(Call<PatternStudent> call, Response<PatternStudent> response) {
                    PatternStudent patternstudent = response.body();
                    editTextStudentId.setText(patternstudent.getUserId());
                    editTextStudentAnswer.setText(patternstudent.getStudentAnswer());
                    checkBoxIsDone.setChecked(patternstudent.getIsDone());
                    _Pattern_Id = patternstudent.getPatternId();
                }

                @Override
                public void onFailure(Call<PatternStudent> call, Throwable t) {
                    Toast.makeText(PatternStudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restService.getService().deletePatternStudentById(_PatternStudent_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 204) {
                        Toast.makeText(PatternStudentDetailActivity.this, "PatternStudent Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(PatternStudentDetailActivity.this, "Error: Not Deleted" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(PatternStudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (findViewById(R.id.btnSave) == v) {

            if (_PatternStudent_Id == null || _PatternStudent_Id.isEmpty()) {
                // No Id -> new patternstudent

                PatternStudent patternstudent = new PatternStudent();
                patternstudent.setUserId(editTextStudentId.getText().toString());
                patternstudent.setStudentAnswer(editTextStudentAnswer.getText().toString());
                patternstudent.setIsDone(checkBoxIsDone.isChecked());
                patternstudent.setPatternId(_Pattern_Id);

                restService.getService().addPatternStudent(patternstudent).enqueue(new Callback<PatternStudent>() {
                    @Override
                    public void onResponse(Call<PatternStudent> call, Response<PatternStudent> response) {
                        if (response.code() == 201) {
                            Toast.makeText(PatternStudentDetailActivity.this, "New PatternStudent Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PatternStudentDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PatternStudent> call, Throwable t) {
                        Toast.makeText(PatternStudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                // Update existing patternstudent

                Call<PatternStudent> call = restService.getService().getPatternStudentById(_PatternStudent_Id);
                call.enqueue(new Callback<PatternStudent>() {
                    @Override
                    public void onResponse(Call<PatternStudent> call, Response<PatternStudent> response) {
                        PatternStudent existingPatternStudent = response.body();
                        existingPatternStudent.setUserId(editTextStudentId.getText().toString());
                        existingPatternStudent.setStudentAnswer(editTextStudentAnswer.getText().toString());
                        existingPatternStudent.setIsDone(checkBoxIsDone.isChecked());
                        existingPatternStudent.setPatternId(_Pattern_Id);

                        // Use Backend API to update patternstudent
                        restService.getService().updatePatternStudentById(_PatternStudent_Id, existingPatternStudent).enqueue(new Callback<PatternStudent>() {
                            @Override
                            public void onResponse(Call<PatternStudent> call, Response<PatternStudent> response) {
                                Toast.makeText(PatternStudentDetailActivity.this, response.body().getUserId() + " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<PatternStudent> call, Throwable t) {
                                Toast.makeText(PatternStudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<PatternStudent> call, Throwable t) {
                        Toast.makeText(PatternStudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        }
    }
}