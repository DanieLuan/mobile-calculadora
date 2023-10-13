package br.imd.imd_calculadora.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.imd.imd_calculadora.R;

public class CalculadoraFragment extends Fragment {
    EditText numberDisplay, numberDisplayPrevious;

    Float values[];
    String currentNumber, operation, numberPrevious;
    Boolean operationActive;

    public CalculadoraFragment() {
        // Required empty public constructor
        operationActive = false;
        currentNumber = "";
        values = new Float[2];
    }

    public static CalculadoraFragment newInstance(String param1, String param2) {
        CalculadoraFragment fragment = new CalculadoraFragment();
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
        View view = inflater.inflate(R.layout.fragment_calculadora, container, false);

        numberDisplay = (EditText) view.findViewById(R.id.editTextNumbersCalc);
        numberDisplayPrevious = (EditText) view.findViewById(R.id.editTextNumbersCalc2);

        assingButtonSetOperation(R.id.buttonPlus, view);
        assingButtonSetOperation(R.id.buttonMinus, view);
        assingButtonSetOperation(R.id.buttonMultiply, view);
        assingButtonSetOperation(R.id.buttonDivide, view);
        assingButtonSetOperation(R.id.buttonEquals, view);

        assingButtonAction(R.id.buttonDelete, view);

        assingButtonAddDigit(R.id.buttonZero, view);
        assingButtonAddDigit(R.id.buttonOne, view);
        assingButtonAddDigit(R.id.buttonTwo, view);
        assingButtonAddDigit(R.id.buttonThree, view);
        assingButtonAddDigit(R.id.buttonFour, view);
        assingButtonAddDigit(R.id.buttonFive, view);
        assingButtonAddDigit(R.id.buttonSix, view);
        assingButtonAddDigit(R.id.buttonSeven, view);
        assingButtonAddDigit(R.id.buttonEight, view);
        assingButtonAddDigit(R.id.buttonNine, view);

        return view;
    }

    private void addDigit(String digit){
        currentNumber += digit;
        numberDisplay.setText(currentNumber);
    }

    private void setOperation(String btnOperation){
        if(!operationActive && !currentNumber.equals("")){
            this.values[0] = Float.parseFloat(currentNumber);
            numberPrevious = currentNumber+" "+btnOperation;
            currentNumber = "";
            operationActive = true;
            operation = btnOperation;
            updateDisplays();
        } else {
            this.values[1] = Float.parseFloat(currentNumber);

            boolean equals = btnOperation.contains("=");

            if(operation.contains("+")){
                values[0] = values[0] + values[1];
            } else if (operation.contains("-")){
                values[0] = values[0] - values[1];
            } else if (operation.contains("*")){
                values[0] = values[0] * values[1];
            } else if (operation.contains("/")){
                if(values[1] != 0){
                    values[0] = values[0] / values[1];
                } else {
                    showToastMensage("AVISO: Não é possível dividir por zero.");
                    initialState();
                    return;
                }
            }
            values[1] = 0f;
            currentNumber = values[0].toString();
            numberPrevious = "";
            if(equals){
                operationActive = false;
            } else {
                operation = btnOperation;
                numberPrevious = currentNumber+" "+btnOperation;
                currentNumber = "";
            }
            updateDisplays();
        }
    }

    private void updateDisplays(){
        numberDisplay.setText(currentNumber);
        numberDisplayPrevious.setText(numberPrevious);
    }

    private void assingButtonSetOperation(int id, View view){
        Button btn = (Button) view.findViewById(id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewOnClick) {
                Button button = (Button) viewOnClick;
                if(currentNumber.equals("")){
                    showToastMensage("AVISO: É preciso colocar um número antes de uma operação.");
                    return;
                }
                setOperation(button.getText().toString());
            }
        });
    }

    private void assingButtonAction(int id, View view){
        Button btn = (Button) view.findViewById(id);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewOnClick) {
                Button button = (Button) viewOnClick;
                String action = button.getText().toString();
                if(action.equals("DEL")){
                    initialState();
                }
            }
        });
    }

    private void assingButtonAddDigit(int id, View view){
        Button btn = (Button) view.findViewById(id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewOnClick) {
                Button button = (Button) viewOnClick;
                addDigit(button.getText().toString());
            }
        });
    }
    private void initialState() {
        currentNumber = "";
        numberPrevious = "";
        operationActive = false;
        operation = "";
        values[0] = 0f;
        values[1] = 0f;
        updateDisplays();
    }

    private void showToastMensage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

}