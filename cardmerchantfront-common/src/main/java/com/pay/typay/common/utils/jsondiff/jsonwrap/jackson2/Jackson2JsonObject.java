package com.pay.typay.common.utils.jsondiff.jsonwrap.jackson2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pay.typay.common.utils.jsondiff.jsonwrap.JzonElement;
import com.pay.typay.common.utils.jsondiff.jsonwrap.JzonObject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;


public class Jackson2JsonObject extends Jackson2JsonElement implements JzonObject {

    private final ObjectNode wrapped;


    public Jackson2JsonObject(ObjectNode wrapped) {
        super(wrapped);
        this.wrapped = wrapped;
    }


    @Override
    public boolean has(String key) {
        return wrapped.has(key);
    }


    @Override
    public void add(String key, JzonElement prop) {
        wrapped.put(key, (JsonNode) prop.unwrap());
    }


    @Override
    public void addProperty(String key, int prop) {
        wrapped.put(key, prop);
    }


    @Override
    public Collection<? extends Entry<String, JzonElement>> entrySet() {

        HashSet<Entry<String, JzonElement>> jset = new HashSet<Entry<String, JzonElement>>();

        for (Iterator<Entry<String, JsonNode>> i = wrapped.fields(); i.hasNext();) {

            final Entry<String, JsonNode> e = i.next();

            final JzonElement el = Jackson2Wrapper.wrap(e.getValue());

            jset.add(new Entry<String, JzonElement>() {

                @Override
                public String getKey() {
                    return e.getKey();
                }


                @Override
                public JzonElement getValue() {
                    return el;
                }


                @Override
                public JzonElement setValue(JzonElement value) {
                    throw new UnsupportedOperationException();
                }
            });
        }

        return jset;

    }


    @Override
    public JzonElement get(String key) {
        return Jackson2Wrapper.wrap(wrapped.get(key));
    }


    @Override
    public void remove(String key) {
        wrapped.remove(key);
    }
    
    
    @Override
    public String toString() {
        return wrapped.toString();
    }


}
