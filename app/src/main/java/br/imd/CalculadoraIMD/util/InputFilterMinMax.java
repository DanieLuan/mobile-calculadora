package br.imd.CalculadoraIMD.util;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Class that implements InputFilter methods and overrides charSquence filter to a certain range MIN and MAX.
 */
public class InputFilterMinMax implements InputFilter {

    private int MIN_RANGE = 0;
    private int MAX_RANGE = 10;

    /**
     * An InputFilter that restricts text input to decimal values within a specific range (0 to 10).
     *
     *
     * @param source String from the EditText element
     * @param start Start position of the text
     * @param end End position of the text
     * @param dest Spanned elemnt to the existing text
     * @param dstart The start position of the existing text that will be replaced
     * @param dend The end position of the existing text that will be replaced.
     * @return The filtered text to be accepted or an empty string if the input is outside the specified range or invalid.
     *
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            String newInput = dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length());
            double input = Double.parseDouble(newInput);

            if (input >= 0 && input <= 10) {
                return null;
            }
        } catch (NumberFormatException e) {
            // A entrada não é um número válido, então não permite a entrada.
        }

        return "";
    }
}