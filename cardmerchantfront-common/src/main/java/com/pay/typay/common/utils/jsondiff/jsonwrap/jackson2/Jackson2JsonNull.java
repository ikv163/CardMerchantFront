package com.pay.typay.common.utils.jsondiff.jsonwrap.jackson2;


import com.fasterxml.jackson.databind.node.NullNode;
import com.pay.typay.common.utils.jsondiff.jsonwrap.JzonNull;


public class Jackson2JsonNull extends Jackson2JsonElement implements JzonNull {

    static final NullNode JNULL = NullNode.getInstance();


    public final static Jackson2JsonNull INSTANCE = new Jackson2JsonNull();


    public Jackson2JsonNull() {
        super(JNULL);
    }

}
