package br.imd.CalculadoraIMD.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.imd.CalculadoraIMD.R;
import br.imd.CalculadoraIMD.fragments.CalculadoraFragment;
import br.imd.CalculadoraIMD.fragments.NotasFragment;


public class MainActivity extends AppCompatActivity {

    private Button buttonCalculadora, buttonNotas;
    private CalculadoraFragment calculadoraFragment;
    private NotasFragment notasFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCalculadora = findViewById(R.id.buttonCalculadora);
        buttonNotas = findViewById(R.id.buttonNotas);

        notasFragment = new NotasFragment();


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayoutMain, notasFragment);
        transaction.commit();

        buttonCalculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculadoraFragment = new CalculadoraFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutMain, calculadoraFragment);
                transaction.commit();
            }
        });

        buttonNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutMain, notasFragment);
                transaction.commit();
            }
        });
    }
}