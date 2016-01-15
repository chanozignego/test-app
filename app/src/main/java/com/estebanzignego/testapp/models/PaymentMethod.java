package com.estebanzignego.testapp.models;

import org.json.JSONException;
import org.json.JSONObject;


public class PaymentMethod {

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
}
