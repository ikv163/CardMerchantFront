package com.pay.typay.common.utils.jsondiff;


import com.pay.typay.common.utils.jsondiff.jsonwrap.jackson2.Jackson2Wrapper;

public class Jackson2Diff extends JsonDiff {

	public Jackson2Diff() {
		super(new Jackson2Wrapper());
	}

}
