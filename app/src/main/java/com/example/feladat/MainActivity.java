package com.example.feladat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private TermekApi termekApi;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewTermekek);
        Button btnAddTermek = findViewById(R.id.btnAddTermek);

        termekApi = RetrofitClient.getClient().create(TermekApi.class);

        loadTermekek();

        btnAddTermek.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTermekActivity.class);
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Termek selectedTermek = (Termek) parent.getItemAtPosition(position);
            deleteTermek(selectedTermek.getId());
            return true;
        });
    }

    private void loadTermekek() {
        termekApi.getTermekek().enqueue(new Callback<List<Termek>>() {
            @Override
            public void onResponse(Call<List<Termek>> call, Response<List<Termek>> response) {
                List<Termek> termekList = response.body();
                ArrayAdapter<Termek> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, termekList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Termek>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Hiba a termékek betöltésekor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteTermek(int id) {
        termekApi.deleteTermek(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(MainActivity.this, "Termék törölve", Toast.LENGTH_SHORT).show();
                loadTermekek();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Hiba a törléskor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
