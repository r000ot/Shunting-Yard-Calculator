package com.example.smitp.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setResult(String input) {
        result = (TextView) findViewById(R.id.result);
        String operation = result.getText().toString();
        System.out.println(operation);
        // define the function of "AC" button
        if (input.compareTo("ac") == 0) {
            operation = "";
            // define the function of "Del" button
        } else if (input.compareTo("⌫") == 0) {
            int textLength = operation.length();
            if (textLength > 0) {
                operation = operation.substring(0, textLength - 1);     // remove the last one char
                if (operation.length() == 0)                     // when the textview is empty, reset the operation to empty
                {
                    operation = "";
                }
            } else
                operation = "";
            // calculate the expression part, use Shunting Yard Algorithms in class Calculate
        } else if(operation.length() > 2 && input.compareTo("=")==0) {
            Calculate vm = new Calculate(operation);
            operation = vm.eval();
        }
        // error warn when no number input
        else if(operation.length() == 0 && input.matches("[+,\\-,×,/,=]")) {
            Toast.makeText(this, "Input number first", Toast.LENGTH_LONG).show();
            operation = "";
        }
        // show the expression in the TextView
        else {
            operation = result.getText().toString();
            operation = operation.concat(input);
        }
        result.setText(operation);
    }

    public void onClickBtn(View v)
    {
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        setResult(buttonText);
    }

}