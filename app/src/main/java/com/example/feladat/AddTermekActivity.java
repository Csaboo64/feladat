package com.example.feladat;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTermekActivity extends AppCompatActivity {
    private EditText etNev, etMennyiseg, etDarabAr, etKategoria;
    private Button btnAdd, btnCancel;
    private TermekApi termekApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_termek);

        etNev = findViewById(R.id.etNev);
        etMennyiseg = findViewById(R.id.etMennyiseg);
        etDarabAr = findViewById(R.id.etDarabAr);
        etKategoria = findViewById(R.id.etKategoria);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        termekApi = RetrofitClient.getClient().create(TermekApi.class);

        btnAdd.setOnClickListener(v -> addTermek());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void addTermek() {
        Termek newTermek = new Termek();
        newTermek.setNev(etNev.getText().toString());
        newTermek.setMennyiseg(Integer.parseInt(etMennyiseg.getText().toString()));
        newTermek.setDarabAr(Double.parseDouble(etDarabAr.getText().toString()));
        newTermek.setKategoria(etKategoria.getText().toString());

        termekApi.createTermek(newTermek).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(AddTermekActivity.this, "Termék hozzáadva", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddTermekActivity.this, "Hiba a termék hozzáadásakor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
