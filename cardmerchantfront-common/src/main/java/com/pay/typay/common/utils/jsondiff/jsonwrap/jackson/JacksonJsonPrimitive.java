package com.pay.typay.common.utils.jsondiff.jsonwrap.jackson;

import com.pay.typay.common.utils.jsondiff.jsonwrap.JzonPrimitive;
import org.codehaus.jackson.node.ValueNode;


public class JacksonJsonPrimitive extends JacksonJsonElement implements JzonPrimitive {

    public JacksonJsonPrimitive(ValueNode wrapped) {
        super(wrapped);
    }

}
