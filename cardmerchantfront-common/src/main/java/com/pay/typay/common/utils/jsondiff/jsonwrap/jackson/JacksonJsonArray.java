package com.pay.typay.common.utils.jsondiff.jsonwrap.jackson;

import com.pay.typay.common.utils.jsondiff.jsonwrap.JzonArray;
import com.pay.typay.common.utils.jsondiff.jsonwrap.JzonElement;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;

public class JacksonJsonArray extends JacksonJsonElement implements JzonArray {

	private final ArrayNode wrapped;

	public JacksonJsonArray(ArrayNode wrapped) {
		super(wrapped);
		this.wrapped = wrapped;
	}

	@Override
	public int size() {
		return wrapped.size();
	}

	@Override
	public JzonElement get(int index) {
		return JacksonWrapper.wrap(wrapped.get(index));
	}

	@Override
	public void insert(int index, JzonElement el) {
		wrapped.insert(index, (JsonNode) el.unwrap());
	}

	@Override
	public void set(int index, JzonElement el) {
		wrapped.set(index, (JsonNode) el.unwrap());
	}

	@Override
	public void remove(int index) {
		wrapped.remove(index);
	}

	@Override
	public String toString() {
		return wrapped.toString();
	}

}
