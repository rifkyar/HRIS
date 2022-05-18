/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.util;

import java.security.cert.CertificateException;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author HARRY-PC
 */
public class OkHttpUtil {
    private static final Logger log = LoggerFactory.getLogger(OkHttpUtil.class);
    private static final String SSL = "SSL";

    private OkHttpClient client;

    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String GET = "GET";

    private static final String CONSTANT_TOKEN = "token";
    private static final String CONSTANT_AUTH = "Authorization";

    public OkHttpClient getClient() {
        return client;
    }

    public void init(boolean ignoreCertificate) {
        log.debug("Initialising httpUtil with default configuration ignoreCertificate: {}", ignoreCertificate);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (ignoreCertificate) {
            configureToIgnoreCertificate(builder);
        }
        client = builder.build();
    }

    private OkHttpClient.Builder configureToIgnoreCertificate(OkHttpClient.Builder builder) {
        log.debug("configureToIgnoreCertificate Builder: {}", builder);
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws CertificateException {
                    log.debug("checkClientTrusted chain: {} authType: {}", chain, authType);
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws CertificateException {
                    log.debug("checkServerTrusted chain: {} authType: {}", chain, authType);
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    log.debug("getAcceptedIssuers builder: {}", builder);
                    return new java.security.cert.X509Certificate[]{};
                }
            }};

            final SSLContext sslContext = SSLContext.getInstance(SSL);
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    // TODO Auto-generated method stub
                    return true;
                }
            });
        } catch (Exception e) {
            log.debug("configureToIgnoreCertificate {} {}", e.getMessage(), e);
        }
        return builder;
    }

    public Request.Builder getBuilder() {
        return new Request.Builder();
    }

    public void addHeaders(Request.Builder builder, Map<String, String> headers) {
        log.debug("addHeaders() builder: {} headers: {}", builder, headers);
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> map : headers.entrySet()) {
                if (map.getKey().equalsIgnoreCase(CONSTANT_TOKEN)) {
                    builder.addHeader(CONSTANT_AUTH, map.getValue());
                } else {
                    builder.addHeader(map.getKey(), map.getValue());
                }
            }
        }
    }
}
