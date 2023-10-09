package br.imd.imd_calculadora.fragments;

import android.icu.number.Precision;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.imd.imd_calculadora.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotasFragment extends Fragment {
    private EditText nota1;
    private EditText nota2;
    private EditText nota3;
    private TextView situacao;
    private Button calcularButton;

    public NotasFragment() {
    }

    public static NotasFragment newInstance(String param1, String param2) {
        NotasFragment fragment = new NotasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notas, container, false);
        nota1 = (EditText) view.findViewById(R.id.textNumberNota1);
        nota2 = (EditText) view.findViewById(R.id.textNumberNota2);
        nota3 = (EditText) view.findViewById(R.id.textNumberNota3);
        calcularButton = (Button) view.findViewById(R.id.buttonCalcularNotas);
        situacao = (TextView) view.findViewById(R.id.textSituacao);

        calcularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewOnClick) {
                if(editTextLength(nota2) == 0 && editTextLength(nota3) == 0) {
                    String situacaoAprovado, situacaoAprovadoPorNota;
                    float numNota1 = editTextGetNumber(nota1);
                    float notaFinalAprovado = (21 - numNota1)/2;
                    float notaFinalAprovadoPorNota = (15 - numNota1)/2;
                    situacaoAprovado = "Situação:\n" + "Para ser aprovado: U2: " + notaFinalAprovado + " U3: " + notaFinalAprovado;
                    if(numNota1 < 3){
                        situacaoAprovadoPorNota = "Não pode mais ser aprovado por nota.";
                    } else {
                        situacaoAprovadoPorNota = "Para ser aprovado por nota: U2: " + notaFinalAprovadoPorNota + " U3: " + notaFinalAprovadoPorNota;
                    }
                    situacao.setText(situacaoAprovado + "\n" + situacaoAprovadoPorNota);
                } else if (editTextLength(nota3) == 0) {
                    //TODO calculate nota3 to get approved
                    String situacaoAprovado, situacaoAprovadoPorNota;
                    float numNota1 = editTextGetNumber(nota1);
                    float numNota2 = editTextGetNumber(nota2);
                    float notaFinalAprovado = (21 - numNota1 + numNota2)/2;
                    float notaFinalAprovadoPorNota = (15 - numNota1 + numNota2)/2;
                    situacaoAprovado = "Situação:\n" + "Para ser aprovado: U3: " + notaFinalAprovado;
                    if(notaFinalAprovadoPorNota > 10){
                        situacaoAprovado = "Situação:\nPrecisa fazer recuperação.";
                        situacaoAprovadoPorNota = "";
                    } else if (numNota1 < 3 || numNota2 < 3 ) {
                        situacaoAprovadoPorNota = "Não pode mais ser aprovado por nota.";
                    } else {
                        situacaoAprovadoPorNota = "Para ser aprovado por nota: U3: " + notaFinalAprovadoPorNota;
                    }
                    situacao.setText(situacaoAprovado + "\n" + situacaoAprovadoPorNota);
                } else if (editTextLength(nota1) != 0 && editTextLength(nota2) != 0 && editTextLength(nota3) != 0) {
                    String situacao;
                    float numNota1 = editTextGetNumber(nota1);
                    float numNota2 = editTextGetNumber(nota2);
                    float numNota3 = editTextGetNumber(nota3);
                    float mediaFinal = Math.round((numNota1+numNota2+numNota3)/3* 2)/2;
                    if(mediaFinal >= 7) {
                        situacao = "APROVADO com média " + mediaFinal;
                    } else if (mediaFinal >= 5) {
                        situacao = "APROVADO por nota com média " + mediaFinal;
                    } else {
                        situacao = "REPROVADO com média " + mediaFinal;
                    }
                    showToastMensage(situacao);
                } else if (editTextLength(nota1) == 0){
                    showToastMensage("AVISO: É preciso colocar pelo menos a nota da unidade 1.");
                } else {
                    showToastMensage("AVISO: Digite alguma nota.");
                }
            }
        });



        return view;
    }

    private void showToastMensage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }
    private float editTextGetNumber(EditText ed) {
        return Float.parseFloat(ed.getText().toString());
    }
    private int editTextLength(EditText ed) {
        return ed.getText().toString().length();
    }
}