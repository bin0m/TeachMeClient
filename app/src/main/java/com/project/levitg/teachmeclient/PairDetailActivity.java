package com.project.levitg.teachmeclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PairDetailActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnRegister, btnDelete, btnDeleteFull;
    Button btnClose;
    EditText editTextValue;
    EditText editTextEqual;
    private String _Pair_Id, _Exercise_Id;
    RestClient restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestClient();
        setContentView(R.layout.activity_pair_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextValue = (EditText) findViewById(R.id.editTextValue);
        editTextEqual = (EditText) findViewById(R.id.editTextEqual);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Pair_Id = "";
        _Exercise_Id = "";
        Intent intent = getIntent();
        _Pair_Id = intent.getStringExtra("pair_Id");
        _Exercise_Id = intent.getStringExtra("exercise_Id");

        if (_Pair_Id != null && !_Pair_Id.isEmpty()) {
            Call<Pair> call = restService.getService().getPairById(_Pair_Id);
            call.enqueue(new Callback<Pair>() {
                @Override
                public void onResponse(Call<Pair> call, Response<Pair> response) {
                    Pair pair = response.body();
                    editTextValue.setText(pair.getValue());
                    editTextEqual.setText(pair.getEqual());
                    _Exercise_Id = pair.getExerciseId();
                }

                @Override
                public void onFailure(Call<Pair> call, Throwable t) {
                    Toast.makeText(PairDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restService.getService().deletePairById(_Pair_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 204) {
                        Toast.makeText(PairDetailActivity.this, "Pair Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(PairDetailActivity.this, "Error: Not Deleted" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(PairDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (findViewById(R.id.btnSave) == v) {

            if (_Pair_Id == null || _Pair_Id.isEmpty()) {
                // No Id -> new pair

                Pair pair = new Pair();
                pair.setValue(editTextValue.getText().toString());
                pair.setEqual(editTextEqual.getText().toString());
                pair.setExerciseId(_Exercise_Id);

                restService.getService().addPair(pair).enqueue(new Callback<Pair>() {
                    @Override
                    public void onResponse(Call<Pair> call, Response<Pair> response) {
                        if (response.code() == 201) {
                            Toast.makeText(PairDetailActivity.this, "New Pair Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PairDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Pair> call, Throwable t) {
                        Toast.makeText(PairDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                // Update existing pair

                Call<Pair> call = restService.getService().getPairById(_Pair_Id);
                call.enqueue(new Callback<Pair>() {
                    @Override
                    public void onResponse(Call<Pair> call, Response<Pair> response) {
                        Pair existingPair = response.body();
                        existingPair.setValue(editTextValue.getText().toString());
                        existingPair.setEqual(editTextEqual.getText().toString());
                        existingPair.setExerciseId(_Exercise_Id);

                        // Use Backend API to update pair
                        restService.getService().updatePairById(_Pair_Id, existingPair).enqueue(new Callback<Pair>() {
                            @Override
                            public void onResponse(Call<Pair> call, Response<Pair> response) {
                                Toast.makeText(PairDetailActivity.this, response.body().getValue() + " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Pair> call, Throwable t) {
                                Toast.makeText(PairDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Pair> call, Throwable t) {
                        Toast.makeText(PairDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        }
    }
}