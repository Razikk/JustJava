/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;



import android.content.Context;
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

        // check if user wants whipped cream
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // check if user wants chocolate
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // get cost of order from calculatePrice method then display order summary
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary = createOrderSummary(userName, price, hasWhippedCream, hasChocolate);
        displayMessage(orderSummary);
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
        return "Name: " + userName.getText() +
                "\nAdd whipped cream? " + hasWhippedCream +
                "\nAdd chocolate? " + hasChocolate +
                "\nQuantity: " + quantity +
                "\nTotal: $" + price +
                "\nThank you!";
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show error message as a toast and exit method
            Toast.makeText(this, "That's too much caffeine!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "You cannot order less than one coffee", Toast.LENGTH_SHORT).show();
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
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}