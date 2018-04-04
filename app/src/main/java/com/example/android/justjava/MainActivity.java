package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    double coffeePrice = 2.00;
    double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        if(quantity == 0){
            displayThanks("You must have more than zero coffees for an order.");
            return;
        }
        displayThanks("Thank you for your order of " + quantity + " cup(s) of coffee for " +
                NumberFormat.getCurrencyInstance().format(total) + "!");
        resetToStart();
        display(quantity);
        displayPrice(total);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(double number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    public void increment(View view){
        quantity += 1;
        updatePrice();
        display(quantity);
        displayPrice(total);
    }

    public void decrement(View view){
        if(quantity == 0){
            return;
        }
        quantity -= 1;
        updatePrice();
        display(quantity);
        displayPrice(total);
    }

    public void updatePrice(){
        total = (quantity * coffeePrice);
    }

    public void resetToStart(){
        quantity = 0;
        updatePrice();
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    /**
     * This method displays a thank you message after ordering.
     */
    private void displayThanks(String givenString) {
        TextView thanksTextView = (TextView) findViewById(R.id.thank_you_text_view);
        thanksTextView.setText(givenString);
    }
}