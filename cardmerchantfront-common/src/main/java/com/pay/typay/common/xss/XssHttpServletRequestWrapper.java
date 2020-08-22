package com.pay.typay.common.xss;

import com.pay.typay.common.utils.RequestReadUtils;
import com.pay.typay.common.utils.html.EscapeUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * XSS过滤处理
 *
 * @author js-oswald
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private String body="";

    /**
     * @param request
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        try {
            this.body = RequestReadUtils.read(request);
        }catch (IllegalStateException e){

        }
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                // 防xss攻击和过滤前后空格
                escapseValues[i] = EscapeUtil.clean(values[i]).trim();
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }

    public String getBody() {
        return body;
    }



    @Override
    public ServletInputStream getInputStream()  {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read(){
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader(){
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }


}