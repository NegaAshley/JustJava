package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    public final double coffeePrice = 2.00;
    public final double whippedCreamPrice = .25;
    public final double chocolatePrice = .50;
    private int quantity = 0;
    private double total = 0;
    private boolean hasWhippedCream = false;
    private boolean hasChocolate = false;
    private String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        getUserName();
        createOrderSummary();
        resetToStart();
        displayQuantity(quantity);
        displayPrice(total);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numCoffees) {
        String textForCoffee;
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        textForCoffee = "" + numCoffees;
        quantityTextView.setText(textForCoffee);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(double number) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
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
        double extras;
        extras = 0.00;
        if(hasWhippedCream){
            extras += whippedCreamPrice;
        }
        if(hasChocolate){
            extras += chocolatePrice;
        }
        total = (quantity * (coffeePrice + extras));
    }

    public void resetToStart(){
        quantity = 0;
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckbox = findViewById(R.id.chocolate_checkbox);
        whippedCreamCheckBox.setChecked(false);
        chocolateCheckbox.setChecked(false);
        hasWhippedCream = false;
        hasChocolate = false;
        userName = "";
        resetUserEditText();
        calculatePrice();
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays a thank you message after ordering.
     */
    private void createOrderSummary() {
        TextView thanksTextView = findViewById(R.id.thank_you_text_view);
        String givenText;
        if(quantity == 0){
            givenText = "You must have more than zero coffees for an order.";
            thanksTextView.setText(givenText);
            return;
        }
        givenText = "Name: " + userName + "\nQuantity: " + quantity + "\nTotal: " +
                NumberFormat.getCurrencyInstance().format(total) + "\nWhipped Cream: " +
                hasWhippedCream + "\nChocolate: " + hasChocolate + "\nThank you!";
        thanksTextView.setText(givenText);
    }

    /*
    Adds whipped cream to order when checkbox is checked.
     */
    public void addWhippedCream(View view){
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = (whippedCreamCheckBox.isChecked());
        calculatePrice();
        displayPrice(total);
    }

    /*
    Adds chocolate to order when checkbox is checked.
     */
    public void addChocolate(View view){
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        hasChocolate = (chocolateCheckBox.isChecked());
        calculatePrice();
        displayPrice(total);
    }

    /*
    Gets user name from EditText
     */
    public void getUserName(){
        EditText userNameEditText = findViewById(R.id.name_view);
        userName = userNameEditText.getText().toString();
    }

    /*
    Reset name EditText
     */
    public void resetUserEditText(){
        EditText userNameEditText = findViewById(R.id.name_view);
        userNameEditText.setText("");
    }
}