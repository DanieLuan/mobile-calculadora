package br.imd.imd_calculadora.fragments;

import android.icu.number.Precision;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.imd.imd_calculadora.R;
import br.imd.imd_calculadora.util.InputFilterMinMax;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotasFragment extends Fragment {
    private EditText nota1;
    private EditText nota2;
    private EditText nota3;
    private TextView situation;
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
        View view = inflater.inflate(R.layout.fragment_notas, container, false);

        nota1 = (EditText) view.findViewById(R.id.textNumberNota1);
        nota2 = (EditText) view.findViewById(R.id.textNumberNota2);
        nota3 = (EditText) view.findViewById(R.id.textNumberNota3);

        calcularButton = (Button) view.findViewById(R.id.buttonCalcularNotas);
        situation = (TextView) view.findViewById(R.id.textSituation);

        nota1.setFilters(new InputFilter[]{new InputFilterMinMax()});
        nota2.setFilters(new InputFilter[]{new InputFilterMinMax()});
        nota3.setFilters(new InputFilter[]{new InputFilterMinMax()});

        calcularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewOnClick) {
                if (isEditTextEmpty(nota1)){
                    showToastMensage("AVISO: É preciso colocar pelo menos a nota da unidade 1.");
                    return;
                }

                String SituationAprovado, SituationAprovadoPorNota;

                float numNota1 = editTextGetNumber(nota1);
                float numNota2 = editTextGetNumber(nota2);
                float numNota3 = editTextGetNumber(nota3);


                if(isEditTextEmpty(nota2) && isEditTextEmpty(nota3)) {
                    //SITUAÇÃO 1: APENAS A NOTA DA UNIDADE 1

                    float notaFinalAprovado = calculateGradeNoToPass(numNota1, numNota2, 21)/2;
                    float notaFinalAprovadoPorNota = calculateGradeNoToPass(numNota1, numNota2, 15)/2;

                    //VERIFICA SE PRECISA FAZER RECUPERAÇÃO POIS A NOTA É MAIOR QUE 10
                    if(notaFinalAprovadoPorNota > 10 || notaFinalAprovado > 10){
                        SituationAprovado = "Situação:\nPrecisa fazer recuperação.";
                        SituationAprovadoPorNota = "";
                        situation.setText(SituationAprovado + "\n" + SituationAprovadoPorNota);
                        return;
                    }

                    SituationAprovado = "Situação:\n" + "Para ser aprovado: U2: " + notaFinalAprovado + " U3: " + notaFinalAprovado;
                    if(numNota1 < 3){
                        SituationAprovadoPorNota = "Não pode mais ser aprovado por nota.";
                    } else {
                        SituationAprovadoPorNota = "Para ser aprovado por nota: U2: " + notaFinalAprovadoPorNota + " U3: " + notaFinalAprovadoPorNota;
                    }
                    situation.setText(SituationAprovado + "\n" + SituationAprovadoPorNota);

                } else if (isEditTextEmpty(nota3)) {
                    //SITUAÇÃO 2: NOTAS DAS UNIDADES 1 E 2

                    float notaFinalAprovado = calculateGradeNoToPass(numNota1, numNota2, 21);
                    float notaFinalAprovadoPorNota = calculateGradeNoToPass(numNota1, numNota2, 15);

                    //VERIFICA SE PRECISA FAZER RECUPERAÇÃO POIS A NOTA É MAIOR QUE 10
                    if(notaFinalAprovadoPorNota > 10 || notaFinalAprovado > 10){
                        SituationAprovado = "Situação:\nPrecisa fazer recuperação.";
                        SituationAprovadoPorNota = "";
                        situation.setText(SituationAprovado + "\n" + SituationAprovadoPorNota);
                        return;
                    }

                    SituationAprovado = "Situação:\n" + "Para ser aprovado: U3: " + notaFinalAprovado;
                    if (numNota1 < 3 || numNota2 < 3 ) {
                        SituationAprovadoPorNota = "Não pode mais ser aprovado por nota.";
                    } else {
                        SituationAprovadoPorNota = "Para ser aprovado por nota: U3: " + notaFinalAprovadoPorNota;
                    }
                    situation.setText(SituationAprovado + "\n" + SituationAprovadoPorNota);

                } else if (editTextLength(nota1) != 0 && editTextLength(nota2) != 0 && editTextLength(nota3) != 0) {
                    String toastSituation;

                    float mediaFinal = Math.round((numNota1+numNota2+numNota3)/3* 2)/2;
                    if(mediaFinal >= 7) {
                        toastSituation = "APROVADO com média " + mediaFinal;
                    } else if (mediaFinal >= 5) {
                        toastSituation = "APROVADO por nota com média " + mediaFinal;
                    } else {
                        toastSituation = "REPROVADO com média " + mediaFinal;
                    }
                    showToastMensage(toastSituation);
                } else {
                    showToastMensage("AVISO: Digite alguma nota.");
                }
            }
        });

        return view;
    }
    private float calculateGradeNoToPass(float numNota1, float numNota2, float targetTotal) {
        return (targetTotal - (numNota1 + numNota2));
    }
    private void showToastMensage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }
    private float editTextGetNumber(EditText ed) {
        String text = ed.getText().toString();
        return (text.isEmpty()) ? 0.0f : Float.parseFloat(text);
    }
    private int editTextLength(EditText ed) {
        return ed.getText().toString().length();
    }

    private boolean isEditTextEmpty(EditText ed){
        return ed.getText().toString().isEmpty();
    }
}