package com.pay.typay.common.utils.jsondiff;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pay.typay.common.utils.jsondiff.jsonwrap.gson.GsonWrapper;

public class GsonDiff extends JsonDiff {

	public GsonDiff() {
		super(new GsonWrapper());
	}

	public JsonObject diff(JsonElement from, JsonElement to) throws IllegalArgumentException {
		return (JsonObject) super.diff(from, to);
	}
}
