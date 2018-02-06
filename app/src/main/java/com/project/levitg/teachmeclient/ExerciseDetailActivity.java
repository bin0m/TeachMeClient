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


public class ExerciseDetailActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnAdd, btnDelete;
    Button btnClose, btnViewExerciseStudents, btnViewComments;
    EditText editTextName;
    EditText editLessonId;
    EditText editTextText;
    EditText editTextQuestion;
    EditText editTextAnswer;
    EditText editTextType;
    private String _Exercise_Id, _Lesson_Id;
    RestClient restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestClient();
        setContentView(R.layout.activity_exercise_detail);

        btnAdd = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnViewExerciseStudents = (Button) findViewById(R.id.btnViewExerciseStudents);
        btnViewComments = (Button) findViewById(R.id.btnViewComments);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextText = (EditText) findViewById(R.id.editTextText);
        editLessonId = (EditText) findViewById(R.id.editLessonId);
        editTextType = (EditText) findViewById(R.id.editTextType);
        editTextQuestion = (EditText) findViewById(R.id.editTextQuestion);
        editTextAnswer = (EditText) findViewById(R.id.editTextAnswer);


        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnViewExerciseStudents.setOnClickListener(this);
        btnViewComments.setOnClickListener(this);


        _Exercise_Id = "";
        _Lesson_Id = "";
        Intent intent = getIntent();
        _Exercise_Id = intent.getStringExtra("exercise_Id");
        _Lesson_Id = intent.getStringExtra("lesson_Id");
        editLessonId.setText(_Lesson_Id);

        if (_Exercise_Id != null && !_Exercise_Id.isEmpty()) {
            Call<Exercise> call = restService.getService().getExerciseById(_Exercise_Id);
            call.enqueue(new Callback<Exercise>() {
                @Override
                public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                    Exercise exercise = response.body();
                    editTextName.setText(exercise.getName());
                    editTextText.setText(exercise.getText());
                    editLessonId.setText(exercise.getLessonId());
                    editTextType.setText(exercise.getType());
                    editTextQuestion.setText(exercise.getQuestion());
                    editTextAnswer.setText(exercise.getAnswer());
                }

                @Override
                public void onFailure(Call<Exercise> call, Throwable t) {
                    Toast.makeText(ExerciseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restService.getService().deleteExerciseById(_Exercise_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(ExerciseDetailActivity.this, "Exercise Record Deleted", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ExerciseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (v == findViewById(R.id.btnViewExerciseStudents)) {
            Intent intent = new Intent(this, ExerciseStudentsActivity.class);
            intent.putExtra("exercise_Id", _Exercise_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnViewComments)) {
            Intent intent = new Intent(this, CommentsActivity.class);
            intent.putExtra("exercise_Id", _Exercise_Id);
            startActivity(intent);
        } else if (findViewById(R.id.btnSave) == v) {

            if (_Exercise_Id == null || _Exercise_Id.isEmpty()) {
                // No Id -> new exercise

                Exercise exercise = new Exercise();
                exercise.setText(editTextText.getText().toString());
                exercise.setType(editTextType.getText().toString());
                exercise.setName(editTextName.getText().toString());
                exercise.setLessonId(editLessonId.getText().toString());
                exercise.setQuestion(editTextQuestion.getText().toString());
                exercise.setAnswer(editTextAnswer.getText().toString());

                restService.getService().addExercise(exercise).enqueue(new Callback<Exercise>() {
                    @Override
                    public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                        if (response.code() == 201) {
                            Toast.makeText(ExerciseDetailActivity.this, "New Exercise Added.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ExerciseDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Exercise> call, Throwable t) {
                        Toast.makeText(ExerciseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });


            } else {
                // Update existing exercise

                Call<Exercise> call = restService.getService().getExerciseById(_Exercise_Id);
                call.enqueue(new Callback<Exercise>() {
                    @Override
                    public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                        Exercise existingExercise = response.body();
                        existingExercise.setText(editTextText.getText().toString());
                        existingExercise.setType(editTextType.getText().toString());
                        existingExercise.setName(editTextName.getText().toString());
                        existingExercise.setLessonId(editLessonId.getText().toString());
                        existingExercise.setQuestion(editTextQuestion.getText().toString());
                        existingExercise.setAnswer(editTextAnswer.getText().toString());

                        // Use Backend API to update exercise
                        restService.getService().updateExerciseById(_Exercise_Id, existingExercise).enqueue(new Callback<Exercise>() {
                            @Override
                            public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                                Toast.makeText(ExerciseDetailActivity.this, response.body().getName() + " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Exercise> call, Throwable t) {
                                Toast.makeText(ExerciseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Exercise> call, Throwable t) {
                        Toast.makeText(ExerciseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        }
    }
}