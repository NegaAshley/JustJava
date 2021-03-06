package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    public final double coffeePrice = 5.00;
    public final double whippedCreamPrice = 1.00;
    public final double chocolatePrice = 2.00;
    public final int minCoffees = 0;
    public final int maxCoffees = 100;
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
        //createOrderSummary();
        sendEmail(getString(R.string.confirmation_email_title, userName), createOrderSummaryText());
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
        if(quantity >= maxCoffees){
            Toast.makeText(getApplicationContext(), getString(R.string.error_max_coffees,
                    maxCoffees), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        calculatePrice();
        displayQuantity(quantity);
        displayPrice(total);
    }

    public void decrement(View view){
        if(quantity <= minCoffees){
            Toast.makeText(getApplicationContext(), getString(R.string.error_min_coffees,
                    minCoffees), Toast.LENGTH_SHORT).show();
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
     * This method displays a thank you message after ordering.
     */
    private String createOrderSummaryText() {
        String givenText;
        if(quantity == 0){
            givenText = getString(R.string.error_min_coffees, minCoffees);
            return givenText;
        }
        givenText = getString(R.string.order_summary_name, userName);
        givenText += "\n";
        givenText += getString(R.string.order_summary_quantity, quantity);
        givenText += "\n";
        givenText += getString(R.string.order_summary_total,
                NumberFormat.getCurrencyInstance().format(total));
        givenText += "\n";
        givenText += getString(R.string.order_summary_whipped_cream, String.valueOf(hasWhippedCream));
        givenText += "\n";
        givenText += getString(R.string.order_summary_chocolate, String.valueOf(hasChocolate));
        givenText += "\n";
        givenText += getString(R.string.thank_you);
        return givenText;
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

    /*
    Uses intent to send email with order
     */
    public void sendEmail(String subject, String message){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
}