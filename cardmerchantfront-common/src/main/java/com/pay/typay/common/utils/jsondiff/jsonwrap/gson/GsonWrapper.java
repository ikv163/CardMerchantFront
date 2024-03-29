package com.pay.typay.common.utils.jsondiff.jsonwrap.gson;

import com.google.gson.*;
import com.pay.typay.common.utils.jsondiff.jsonwrap.JzonArray;
import com.pay.typay.common.utils.jsondiff.jsonwrap.JzonElement;
import com.pay.typay.common.utils.jsondiff.jsonwrap.JzonObject;
import com.pay.typay.common.utils.jsondiff.jsonwrap.Wrapper;

public class GsonWrapper implements Wrapper {

	private final static JsonParser JSON = new JsonParser();

	public static JzonElement wrap(JsonElement el) {
		if (el == null || el.isJsonNull()) {
			return GsonJsonNull.INSTANCE;
		} else if (el.isJsonArray()) {
			return new GsonJsonArray((JsonArray) el);
		} else if (el.isJsonObject()) {
			return new GsonJsonObject((JsonObject) el);
		} else if (el.isJsonPrimitive()) {
			return new GsonJsonPrimitive((JsonPrimitive) el);
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	public JzonElement parse(String json) {
		return wrap(JSON.parse(json));
	}

	@Override
	public JzonElement wrap(Object o) {
		return wrap((JsonElement) o);
	}

	@Override
	public JzonObject createJsonObject() {
		return (JzonObject) wrap(new JsonObject());
	}

	@Override
	public JzonArray createJsonArray() {
		return (JzonArray) wrap(new JsonArray());
	}

}
