package com.estebanzignego.testapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.estebanzignego.testapp.holders.PaymentMethodHolder;
import com.estebanzignego.testapp.models.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodAdapter extends BaseAdapter {

    public List<PaymentMethod> paymentMethodList = new ArrayList<>();
    public Context appContext;

    public PaymentMethodAdapter(Context context) {
        super();
        appContext = context;
    }

    @Override
    public int getCount() {
        return paymentMethodList.size();
    }

    public void setPaymentMethodsList(List<PaymentMethod> paymentMethods) {
        paymentMethodList = paymentMethods;
        this.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return paymentMethodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PaymentMethodHolder paymentMethodItemView;
        if (convertView == null) {
            paymentMethodItemView = new PaymentMethodHolder(appContext);
        } else {
            paymentMethodItemView = (PaymentMethodHolder) convertView;
        }
        paymentMethodItemView.setPadding(8, 8, 8, 8);
        paymentMethodItemView.bind(paymentMethodList.get(position));

        return paymentMethodItemView;
    }
}
