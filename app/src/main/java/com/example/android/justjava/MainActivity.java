package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private int quantity = 0;
    private double coffeePrice = 2.00;
    private double total = 0;
    private boolean hasWhippedCream = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        createOrderSummary();
        resetToStart();
        displayQuantity(quantity);
        displayPrice(total);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numCoffees);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(double number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    public void increment(View view){
        quantity += 1;
        calculatePrice();
        displayQuantity(quantity);
        displayPrice(total);
    }

    public void decrement(View view){
        if(quantity == 0){
            return;
        }
        quantity -= 1;
        calculatePrice();
        displayQuantity(quantity);
        displayPrice(total);
    }

    public void calculatePrice(){
        total = (quantity * coffeePrice);
    }

    public void resetToStart(){
        quantity = 0;
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        whippedCreamCheckBox.setChecked(false);
        hasWhippedCream = false;
        calculatePrice();
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays a thank you message after ordering.
     */
    private void createOrderSummary() {
        TextView thanksTextView = (TextView) findViewById(R.id.thank_you_text_view);
        String givenText;
        if(quantity == 0){
            givenText = "You must have more than zero coffees for an order.";
            thanksTextView.setText(givenText);
            return;
        }
        givenText = "Name: Kaptain Kunal\nQuantity: " + quantity + "\nTotal: " +
                NumberFormat.getCurrencyInstance().format(total) + "\nWhipped Cream: " +
                hasWhippedCream + "\nThank you!";
        thanksTextView.setText(givenText);
    }

    /*
    Adds whipped cream to order when checkbox is checked.
     */
    public void addWhippedCream(View view){
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = (whippedCreamCheckBox.isChecked());
    }
}