package com.pay.typay.common.utils.jsondiff.jsonwrap.jackson;

import com.pay.typay.common.utils.jsondiff.jsonwrap.JzonNull;
import org.codehaus.jackson.node.NullNode;


public class JacksonJsonNull extends JacksonJsonElement implements JzonNull {

    static final NullNode JNULL = NullNode.getInstance();


    public final static JacksonJsonNull INSTANCE = new JacksonJsonNull();


    public JacksonJsonNull() {
        super(JNULL);
    }

}
