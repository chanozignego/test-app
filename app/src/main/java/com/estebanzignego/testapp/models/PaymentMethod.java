package com.estebanzignego.testapp.models;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class PaymentMethod implements Serializable{

    private String id;
    private String name;
    private String payment_type_id;
    private String secure_thumbnail;
    private String thumbnail;

    public PaymentMethod(String id, String name, String paymentTypeId, String secureThumbnail, String thumbnail) {
        this.id = id;
        this.name = name;
        this.payment_type_id = paymentTypeId;
        this.secure_thumbnail = secureThumbnail;
        this.thumbnail = thumbnail;
    }

    public static PaymentMethod createModel(JSONObject object) throws JSONException {
        String id = (String) object.get("id");
        String name = (String) object.get("name");
        String paymentTypeId = (String) object.get("payment_type_id");
        String secureThumbnail = (String) object.get("secure_thumbnail");
        String thumbnail = (String) object.get("thumbnail");

        return new PaymentMethod(id, name, paymentTypeId, secureThumbnail, thumbnail);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPaymentTypeId() {
        return payment_type_id;
    }

    public String getSecureThumbnail() {
        return secure_thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public boolean isCreditCard() {
        return this.getPaymentTypeId().equals("credit_card");
    }

}
