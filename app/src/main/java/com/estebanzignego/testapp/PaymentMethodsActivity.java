package com.estebanzignego.testapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.estebanzignego.testapp.adapters.PaymentMethodAdapter;
import com.estebanzignego.testapp.models.PaymentMethod;
import com.estebanzignego.testapp.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class PaymentMethodsActivity extends AppCompatActivity {

    public String BASE_URL = "https://api.mercadopago.com";
    public String URI = "/v1/payment_methods";
    public String PUBLIC_KEY = "444a9ef5-8a6b-429f-abdf-587639155d88";

    public List<PaymentMethod> paymentMethodList = new ArrayList<>();
    public PaymentMethodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);

        initListView();
        setPaymentMethodsList();
        setItemListener();
    }

    private void initListView() {
        adapter = new PaymentMethodAdapter(this);
        ListView listView = (ListView) findViewById(R.id.payment_methods);
        listView.setAdapter(adapter);
    }

    private void setPaymentMethodsList() {
        showProgressbar();

        sendRequest(this.BASE_URL, this.URI, this.PUBLIC_KEY);
    }

    private void setItemListener() {

        ListView listView = (ListView) findViewById(R.id.payment_methods);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {

                final PaymentMethod paymentMethod = (PaymentMethod) adapter.getItemAtPosition(pos);
                Intent intent = new Intent(getApplicationContext(), ShowPaymentMethodActivity.class);
                intent.putExtra("PAYMENT_METHOD", paymentMethod);
                startActivity(intent);

            }
        });
    }

    private void sendRequest(String baseUrl, String uri, String publicKey) {
        String url = setUrl(baseUrl, uri, publicKey);

        HttpUtils.get(url, new HttpUtils.HttpCallback() {
            @Override
            public void onFailure(Response response, Throwable throwable) {
                showToast(getString(R.string.connection_error));
                showRetryButton();
            }

            @Override
            public void onSuccess(Response response) {
                JSONArray json = null;
                try {
                    json = new JSONArray(response.body().string());
                } catch (Exception e) {
                    showToast(getString(R.string.connection_error));
                    showRetryButton();
                }

                if (json != null && json.length() > 0) {
                    createPaymentMethods(json);
                } else {
                    showEmptyListMessage();
                }
            }
        });
    }

    private void createPaymentMethods(JSONArray json) {
        paymentMethodList.clear();
        int max = json.length();
        for(int i = 0; i < max; i++) {
            try {
                PaymentMethod paymentMethod = PaymentMethod.createModel((JSONObject) json.get(i));
                if (paymentMethod.isCreditCard()) {
                    paymentMethodList.add(paymentMethod);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter.setPaymentMethodsList(paymentMethodList);
        showList();
    }

    private String setUrl(String baseUrl, String uri, String publicKey) {
        return baseUrl + uri + "?public_key=" + publicKey;
    }

    private void showProgressbar() {
        crossfade(this.findViewById(R.id.progressBar), this.findViewById(R.id.payment_methods_layout));
    }

    private void showList() {
        crossfade(findViewById(R.id.payment_methods_layout), findViewById(R.id.progressBar));
    }

    private void showEmptyListMessage() {
        crossfade(findViewById(R.id.no_methods), findViewById(R.id.progressBar));
    }

    private void showRetryButton() {
        //TODO:
        //crossfade(findViewById(R.id.progressBar), findViewById(R.id.progressBar));
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    private void crossfade(final View in, final View out) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                in.setAlpha(0f);
                in.setVisibility(View.VISIBLE);
                in.animate()
                        .alpha(1f)
                        .setDuration(300)
                        .setListener(null);

                out.animate()
                        .alpha(0f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                out.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}
