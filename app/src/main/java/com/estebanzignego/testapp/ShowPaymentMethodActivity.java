package com.estebanzignego.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.estebanzignego.testapp.models.PaymentMethod;
import com.squareup.picasso.Picasso;

public class ShowPaymentMethodActivity extends AppCompatActivity {

    public PaymentMethod paymentMethod = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_payment_method);

        paymentMethod = (PaymentMethod) this.getIntent().getSerializableExtra("PAYMENT_METHOD");

        setData();
    }

    private void setData() {
        if (paymentMethod != null) {
            TextView name = (TextView) findViewById(R.id.payment_method_name);
            ImageView image = (ImageView) findViewById(R.id.payment_method_image);

            name.setText(paymentMethod.getName());

            //TODO: download image
            Picasso.with(this).load(paymentMethod.getSecureThumbnail()).into(image);
        }

    }
}
