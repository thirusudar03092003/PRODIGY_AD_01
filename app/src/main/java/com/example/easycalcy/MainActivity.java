package com.example.easycalcy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView expressionTV, resultTV;
    private String currentNumber = "";
    private String currentExpression = "";
    private Double firstNumber = null;
    private String operation = null;
    private boolean isNewCalculation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextViews
        expressionTV = findViewById(R.id.expressionTV);
        resultTV = findViewById(R.id.resultTV);

        // Initialize number buttons
        setupNumberButton(R.id.btn0, "0");
        setupNumberButton(R.id.btn1, "1");
        setupNumberButton(R.id.btn2, "2");
        setupNumberButton(R.id.btn3, "3");
        setupNumberButton(R.id.btn4, "4");
        setupNumberButton(R.id.btn5, "5");
        setupNumberButton(R.id.btn6, "6");
        setupNumberButton(R.id.btn7, "7");
        setupNumberButton(R.id.btn8, "8");
        setupNumberButton(R.id.btn9, "9");

        // Initialize operator buttons
        setupOperatorButton(R.id.btnPlus, "+");
        setupOperatorButton(R.id.btnMinus, "-");
        setupOperatorButton(R.id.btnMultiply, "×");
        setupOperatorButton(R.id.btnDivide, "÷");

        // Initialize decimal point button
        findViewById(R.id.btnDot).setOnClickListener(v -> handleDecimal());

        // Initialize equals button
        findViewById(R.id.btnEquals).setOnClickListener(v -> handleEquals());

        // Initialize clear button
        findViewById(R.id.btnClear).setOnClickListener(v -> handleClear());
    }

    private void setupNumberButton(int buttonId, String number) {
        findViewById(buttonId).setOnClickListener(v -> handleNumber(number));
    }

    private void setupOperatorButton(int buttonId, String op) {
        findViewById(buttonId).setOnClickListener(v -> handleOperator(op));
    }

    private void handleNumber(String num) {
        if (isNewCalculation) {
            currentExpression = "";
            expressionTV.setText("");
            isNewCalculation = false;
        }

        currentNumber += num;
        resultTV.setText(currentNumber);
        currentExpression += num;
        expressionTV.setText(currentExpression);
    }

    private void handleOperator(String op) {
        if (!currentNumber.isEmpty()) {
            if (firstNumber == null) {
                firstNumber = Double.parseDouble(currentNumber);
            } else {
                calculateResult();
            }
        }

        operation = op;
        currentNumber = "";
        currentExpression += " " + op + " ";
        expressionTV.setText(currentExpression);
    }

    private void handleDecimal() {
        if (isNewCalculation) {
            currentExpression = "0.";
            currentNumber = "0.";
            isNewCalculation = false;
        } else if (!currentNumber.contains(".")) {
            if (currentNumber.isEmpty()) {
                currentNumber = "0";
                currentExpression += "0";
            }
            currentNumber += ".";
            currentExpression += ".";
        }
        resultTV.setText(currentNumber);
        expressionTV.setText(currentExpression);
    }

    private void handleEquals() {
        if (firstNumber != null && operation != null && !currentNumber.isEmpty()) {
            calculateResult();
            currentExpression += " = ";
            expressionTV.setText(currentExpression);
            isNewCalculation = true;
        }
    }

    @SuppressLint("SetTextI18n")
    private void calculateResult() {
        if (!currentNumber.isEmpty()) {
            double secondNumber = Double.parseDouble(currentNumber);
            double result = 0;

            switch (operation) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "×":
                    result = firstNumber * secondNumber;
                    break;
                case "÷":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        resultTV.setText("Error");
                        return;
                    }
                    break;
            }

            // Format result to remove unnecessary decimal places
            @SuppressLint("DefaultLocale") String resultStr = String.format("%s",
                    result == (long) result ? String.format("%d", (long) result) : String.format("%s", result));

            resultTV.setText(resultStr);
            firstNumber = result;
            currentNumber = resultStr;
        }
    }

    private void handleClear() {
        currentNumber = "";
        currentExpression = "";
        firstNumber = null;
        operation = null;
        isNewCalculation = false;
        expressionTV.setText("");
        resultTV.setText("0");
    }
}