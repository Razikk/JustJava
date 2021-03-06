package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText userName = findViewById(R.id.name_field);

        // Check if user wants whipped cream
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Check if user wants chocolate
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Get cost of order from calculatePrice method then display order summary
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary = createOrderSummary(userName, price, hasWhippedCream, hasChocolate);

        // Deleted in Udacity course
        displayMessage(orderSummary);

        // Try to send an email with order summary
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + userName);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Toast.makeText(this, getString(R.string.intent_failed), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param hasChocolate whether or not user wants chocolate topping
     * @param hasWhippedCream whether or not user wants whipped cream topping
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        // price of one cup of coffee
        int basePrice = 5;

        // add cost of extra toppings
        int extraToppingsPrice = 0;
        if (hasWhippedCream) extraToppingsPrice += 1;
        if (hasChocolate) extraToppingsPrice += 2;

        //sum the total price
        return quantity * (basePrice + extraToppingsPrice);
    }

    /**
     * Create summary of the order
     * @param userName name of the user
     * @param price price of the order
     * @param hasWhippedCream  whether or not user wants whipped cream topping
     * @param hasChocolate whether or not user wants chocolate topping
     * @return text summary
     */
    private String createOrderSummary(EditText userName,
                                      int price,
                                      boolean hasWhippedCream,
                                      boolean hasChocolate) {
        return getString(R.string.order_summary_name, userName.getText()) +
                "\n" + getString(R.string.whipped_cream) + "? " + hasWhippedCream +
                "\n" + getString(R.string.chocolate) + "? " + hasChocolate +
                "\n" + getString(R.string.quantity) + ": " + quantity +
                "\n" + getString(R.string.total) + ": $" + price +
                "\n" + getString(R.string.thank_you);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show error message as a toast and exit method
            Toast.makeText(this, getString(R.string.cannot_increment), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity <= 1) {
            // Show error message as a toast and exit method
            Toast.makeText(this, getString(R.string.cannot_decrement), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(Integer.toString(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    // Deleted in Udacity course
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}