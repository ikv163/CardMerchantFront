package com.pay.typay.common.utils.jsondiff.jsonwrap.gson;

import com.google.gson.JsonPrimitive;
import com.pay.typay.common.utils.jsondiff.jsonwrap.JzonPrimitive;


public class GsonJsonPrimitive extends GsonJsonElement implements JzonPrimitive {

    public GsonJsonPrimitive(JsonPrimitive wrapped) {
        super(wrapped);
    }

}
