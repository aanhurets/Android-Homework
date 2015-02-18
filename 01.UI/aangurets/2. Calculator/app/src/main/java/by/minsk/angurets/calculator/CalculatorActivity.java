package by.minsk.angurets.calculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CalculatorActivity extends ActionBarActivity {

    EditText mNum1EditText;
    EditText mNum2EditText;
    TextView mResult;
    RadioGroup mRadioGroup;
    final static String RESULT = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNum1EditText = (EditText) findViewById(R.id.num1);
        mNum2EditText = (EditText) findViewById(R.id.num2);
        mResult = (TextView) findViewById(R.id.result);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);

        findViewById(R.id.compute_button).setOnClickListener(new OnComputingClickListener());
    }

    private static double getDouble(TextView textView) {
        CharSequence text = textView.getText();
        if (TextUtils.isEmpty(text)) {
            throw new IllegalArgumentException();
        } else {
            try {
                return Double.parseDouble(text.toString());
            } catch (Exception e) {
                throw new IllegalArgumentException();
            }
        }
    }

    private class OnComputingClickListener implements View.OnClickListener {

        public void operatorNotSelect() {
            final AlertDialog.Builder builder =
                    new AlertDialog.Builder(CalculatorActivity.this);
            builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }
            );
            builder.setTitle(R.string.error_operator_not_select).setMessage(R.string.operator_not_select);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        ;

        public void incorrectOperand() {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(CalculatorActivity.this);
            builder.setTitle(R.string.error_incorrect_operand)
                    .setMessage(R.string.incorrect_operand);
            builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }
            );
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        ;

        @Override
        public void onClick(View v) {
            try {
                switch (mRadioGroup.getCheckedRadioButtonId()) {
                    case View.NO_ID:
                        operatorNotSelect();
                    case R.id.operator_sum:
                        result(new Calculation(getDouble(mNum1EditText), getDouble(mNum2EditText))
                                .sum());
                        return;
                    case R.id.operator_subtr:
                        result(new Calculation(getDouble(mNum1EditText), getDouble(mNum2EditText))
                                .subtraction());
                        return;
                    case R.id.operator_div:
                        result(new Calculation(getDouble(mNum1EditText), getDouble(mNum2EditText))
                                .division());
                        return;
                    case R.id.operator_mult:
                        result(new Calculation(getDouble(mNum1EditText), getDouble(mNum2EditText))
                                .multiplication());
                        return;
                    default:
                        operatorNotSelect();
                }
            } catch (IllegalArgumentException e) {
                if (!TextUtils.isEmpty(mResult.getText())) {
                    incorrectOperand();
                }
            }
        }
    }

    public void result(double doubleResult) {
        String mStringResult;
        mStringResult = getString(R.string.result_format, doubleResult);
        mResult.setText(mStringResult);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(RESULT, mResult.getText());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mResult.setText(savedInstanceState.getCharSequence(RESULT));
    }
}
