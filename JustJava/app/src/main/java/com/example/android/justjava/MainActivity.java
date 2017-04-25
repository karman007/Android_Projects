package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String message = createOrderSummary();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, "sawhney.karman@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT,"JustJava order for " + getName());
        intent.putExtra(Intent.EXTRA_TEXT, message);


        if(intent.resolveActivity(getPackageManager())!= null){
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     * }
     */

    public void increment(View view) {
        if (quantity < 100){
            displayQuantity(++quantity);
        } else {
            Toast.makeText(this,"Maximum number of coffees reached", Toast.LENGTH_SHORT).show();
        }
    }

    public void decrement(View view) {
        if (quantity > 1) {
            displayQuantity(--quantity);
        } else {
            Toast.makeText(this, "Minimum number of coffees reached", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private boolean isChecked(String topping){
        boolean checkedStatus = false;
        CheckBox whippedCream = (CheckBox)findViewById(R.id.whipped_cream_checkBox);
        CheckBox chocolate = (CheckBox)findViewById(R.id.chocolate_checkBox);

        if (topping.equals("Whipped Cream")){
            checkedStatus = whippedCream.isChecked();
        }else if(topping.equals("Chocolate")){
            checkedStatus = chocolate.isChecked();
        }

        return  checkedStatus;
    }

    private int calculatePrice(){
        int price = 5;

        if (isChecked("Whipped Cream")){
            price += 1;
        }
        if (isChecked("Chocolate")){
            price += 2;
        }

        return price * quantity;
    }

    private String getName(){
        EditText nameTextField = (EditText)findViewById(R.id.name_textfield);
        return nameTextField.getText().toString();
    }

    private String createOrderSummary(){
        String orderSummary = getString(R.string.order_summary_name, getName());
        orderSummary += "\nAdd Whipped cream? " + isChecked("Whipped Cream");
        orderSummary += "\nAdd Chocolate? " + isChecked("Chocolate");
        orderSummary += "\nQuantity: " + quantity;
        orderSummary += "\nTotal: $" + calculatePrice();
        orderSummary += "\n" + getString(R.string.thanks);

        return orderSummary ;
    }

    /*
    private void displayPrice(int number) {
    TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
    priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));


    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    */

}
