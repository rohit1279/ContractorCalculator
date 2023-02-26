package com.rohithmovva.contractorcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etLaborAmount, etMaterialsAmount;
    TextView tvSubtotalAmount, tvTaxAmount, tvTotalAmount;
    Button btnCalculate;

    boolean isValidInputAmount = false;

    static final double taxRate = 5.00;
    double laborAmount = 0.00;
    double materialsAmount = 0.00;
    double subTotalAmount = 0.00;
    double taxAmount = 0.00;
    double totalAmount = 0.00;

    //Declaring and defining formatter for displaying amounts in US currency format ($#,##0.00).
    final NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLaborAmount = findViewById(R.id.etLabor);
        etMaterialsAmount = findViewById(R.id.etMaterials);

        btnCalculate = findViewById(R.id.btnCalculate);

        tvSubtotalAmount= findViewById(R.id.tvSubtotalValue);
        tvTaxAmount = findViewById(R.id.tvTaxValue);
        tvTotalAmount = findViewById(R.id.tvTotalValue);

        btnCalculate.setOnClickListener(view -> {
            try{
                subTotalAmount = 0.00;
                taxAmount = 0.00;
                totalAmount = 0.00;
                isValidInputAmount = CheckEnteredLaborMaterialsAmount();
                if(isValidInputAmount)
                {
                    String laborStringAmount = etLaborAmount.getText().toString();
                    String materialStringAmount = etMaterialsAmount.getText().toString();
                    laborAmount = Double.parseDouble(laborStringAmount);
                    materialsAmount = Double.parseDouble(materialStringAmount);
                    laborAmount = GetFormattedTwoDecimalValue(laborAmount);
                    materialsAmount = GetFormattedTwoDecimalValue(materialsAmount);
                    subTotalAmount = laborAmount + materialsAmount;
                    taxAmount = GetFormattedTwoDecimalValue(taxRate/100) * subTotalAmount;
                    taxAmount = GetFormattedTwoDecimalValue(taxAmount);
                    totalAmount = subTotalAmount + taxAmount;
                }

                tvSubtotalAmount.setText(formatter.format(subTotalAmount));
                tvTaxAmount.setText(formatter.format(taxAmount));
                tvTotalAmount.setText(formatter.format(totalAmount));
            }catch(Exception ex)
            {
                System.out.println(Arrays.toString(ex.getStackTrace()));
            }
        });
    }

    private boolean CheckEnteredLaborMaterialsAmount(){
        if(etLaborAmount.length() == 0 || etLaborAmount.getText().toString().equals(".")){
            etLaborAmount.setError("Enter valid labor amount");
            return false;
        }

        if(etMaterialsAmount.length() == 0 || etMaterialsAmount.getText().toString().equals(".")){
            etMaterialsAmount.setError("Enter valid materials amount");
            return false;
        }
        return true;
    }

    //Format any double value to two decimal value by rounding since these are amount related objects
    private double GetFormattedTwoDecimalValue(double doubleValue)
    {
        doubleValue = doubleValue * 100;
        doubleValue = Math.round(doubleValue);
        doubleValue = doubleValue/100;
        return doubleValue;
    }
}