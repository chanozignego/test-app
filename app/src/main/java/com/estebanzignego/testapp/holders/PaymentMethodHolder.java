package com.estebanzignego.testapp.holders;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estebanzignego.testapp.R;
import com.estebanzignego.testapp.models.PaymentMethod;
import com.squareup.picasso.Picasso;

public class PaymentMethodHolder extends LinearLayout {

    public Context appContext;

    public PaymentMethodHolder(Context context) {
        super(context);
        inflate(context, R.layout.payment_method, this);
        appContext = context;
    }

    public void bind(PaymentMethod paymentMethod) {
        TextView name = (TextView) findViewById(R.id.payment_method_name);
        ImageView image = (ImageView) findViewById(R.id.payment_method_image);

        name.setText(paymentMethod.getName());

        //TODO: download image
        Picasso.with(appContext).load(paymentMethod.getSecureThumbnail()).into(image);
    }
}
