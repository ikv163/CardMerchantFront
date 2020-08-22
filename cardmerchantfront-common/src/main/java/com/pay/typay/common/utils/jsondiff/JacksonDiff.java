package com.pay.typay.common.utils.jsondiff;


import com.pay.typay.common.utils.jsondiff.jsonwrap.jackson.JacksonWrapper;

public class JacksonDiff extends JsonDiff {

	public JacksonDiff() {
		super(new JacksonWrapper());
	}

}
