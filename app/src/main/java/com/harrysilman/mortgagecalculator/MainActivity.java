package com.harrysilman.mortgagecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing the tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text

import java.text.NumberFormat; // for currency formatting
import java.lang.*; // for Math.pow

public class MainActivity extends AppCompatActivity {

    // currency and percent formatter objects
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    //initialize variables and objects for input/views
    private double mortgageAmount = 0.0;
    private double downPaymentAmount = 0.0;
    private double interestRate = 0.0;
    private int years = 15;
    private TextView mortgageAmountTextView;
    private TextView downPaymentTextView;
    private TextView interestRateTextView;
    private TextView yearsTextView;
    private TextView monthlyPaymentTextView;

    //called when activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get references to programmatically manipulated TextViews
        mortgageAmountTextView = (TextView) findViewById(R.id.mortgageAmountTextView);
        downPaymentTextView = (TextView) findViewById(R.id.downPaymentTextView);
        interestRateTextView = (TextView) findViewById(R.id.interestRateTextView);
        yearsTextView = (TextView) findViewById(R.id.yearsTextView);
        monthlyPaymentTextView = (TextView) findViewById(R.id.monthlyPaymentTextView);
        mortgageAmountTextView.setText(currencyFormat.format(0));
        downPaymentTextView.setText(currencyFormat.format(0));
        monthlyPaymentTextView.setText(currencyFormat.format(0));

        //set mortgageAmountEditText's TextWatcher
        EditText mortgageAmountEditText =
                (EditText) findViewById(R.id.mortgageAmountEditText);
        mortgageAmountEditText.addTextChangedListener(mortgageAmountEditTextWatcher);

        //set downPaymentEditText's TextWatcher
        EditText downPaymentEditText =
                (EditText) findViewById(R.id.downPaymentEditText);
        downPaymentEditText.addTextChangedListener(downPaymentEditTextWatcher);

        //set interestRateEditText's TextWatcher
        EditText interestRateEditText =
                (EditText) findViewById(R.id.interestRateEditText);
        interestRateEditText.addTextChangedListener(interestRateEditTextWatcher);

        //set yearsSeekBar's OnSeekBarChangeListener
        SeekBar yearsSeekBar =
                (SeekBar) findViewById(R.id.yearsSeekBar);
        yearsSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    //calculate and display monthly payment amount
    private void calculate(){
        //format years and display in yearsTextView
        yearsTextView.setText(String.valueOf(years));

        //format interest rate and display in interestRateTextView
        interestRateTextView.setText(percentFormat.format(interestRate));

        //calculate loan balance after down payment
        double balance = mortgageAmount - downPaymentAmount;

        //calculate monthly payment
        double monthlyRate = interestRate / 12;
        int termInMonths = years * 12;

        double monthlyPayment =
                (balance*monthlyRate) /
                        (1-Math.pow(1+monthlyRate, -termInMonths));

        //display monthly payment formatted as currency
        if(Double.isNaN(monthlyPayment)){//check for NaN
            monthlyPaymentTextView.setText("");
        } else{
            monthlyPaymentTextView.setText(currencyFormat.format(monthlyPayment));
        }


    }

    // listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {
                // update years, then call calculate
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    years = progress+1; // show user years based on progress(1 year minimum)
                    calculate(); // calculate and display monthly payment
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };

    // listener object for the mortgageAmountEditText's text-changed events
    private final TextWatcher mortgageAmountEditTextWatcher = new TextWatcher() {
        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {


            try { // get mortgage amount and display currency formatted value
                mortgageAmount = Double.parseDouble(s.toString()) / 100.0;
                mortgageAmountTextView.setText(currencyFormat.format(mortgageAmount));
            } catch (NumberFormatException e) { // if s is empty or non-numeric
                mortgageAmountTextView.setText("Enter Mortgage Amount");
                mortgageAmount = 0.0;
            }

            calculate(); // update the tip and total TextViews
        }


        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) {
        }
    };

    // listener object for the downPaymentEditText's text-changed events
    private final TextWatcher downPaymentEditTextWatcher = new TextWatcher() {
        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get mortgage amount and display currency formatted value
                downPaymentAmount = Double.parseDouble(s.toString()) / 100.0;
                downPaymentTextView.setText(currencyFormat.format(downPaymentAmount));
            } catch (NumberFormatException e) { // if s is empty or non-numeric
                downPaymentTextView.setText("Enter Downpayment Amount");
                downPaymentAmount = 0.0;
            }

            calculate(); // update the tip and total TextViews
        }


        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) {
        }
    };

    // listener object for the interestRateEditText's text-changed events
    private final TextWatcher interestRateEditTextWatcher = new TextWatcher() {
        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get mortgage amount and display currency formatted value
                interestRate = Double.parseDouble(s.toString()) / 100.0;
                interestRateTextView.setText(percentFormat.format(interestRate));
            } catch (NumberFormatException e) { // if s is empty or non-numeric
                interestRateTextView.setText("Enter Interest Rate");
                interestRate = 0.0;
            }

            calculate(); // update the tip and total TextViews
        }


        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) {
        }
    };
}
