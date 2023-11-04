package com.serliunx.varytalk.httpclient;

import com.serliunx.varytalk.httpclient.decoder.JacksonDecoder;
import com.serliunx.varytalk.httpclient.encoder.JacksonEncoder;
import feign.Feign;

/**
 * @author SerLiunx
 * @since 1.0
 */
public final class Builder {

    private Builder(){}

    public static <T> T build(Class<T> clazz, String url){
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(clazz, url);
    }
}
