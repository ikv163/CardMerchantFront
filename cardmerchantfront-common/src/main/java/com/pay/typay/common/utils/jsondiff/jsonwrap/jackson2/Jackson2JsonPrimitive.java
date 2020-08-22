package com.pay.typay.common.utils.jsondiff.jsonwrap.jackson2;

import com.fasterxml.jackson.databind.node.ValueNode;
import com.pay.typay.common.utils.jsondiff.jsonwrap.JzonPrimitive;


public class Jackson2JsonPrimitive extends Jackson2JsonElement implements JzonPrimitive {

    public Jackson2JsonPrimitive(ValueNode wrapped) {
        super(wrapped);
    }

}
