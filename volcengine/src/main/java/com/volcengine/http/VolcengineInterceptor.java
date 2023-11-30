package com.volcengine.http;

import androidx.annotation.NonNull;

import com.volcengine.auth.ISignerV4;
import com.volcengine.helper.Const;
import com.volcengine.model.Header;
import com.volcengine.model.NameValuePair;
import com.volcengine.model.RequestParam;
import com.volcengine.model.SignRequest;

import okhttp3.*;
import okio.Buffer;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.volcengine.model.Credentials;

public class VolcengineInterceptor implements Interceptor {

    public ISignerV4 signer;

    public com.volcengine.model.Credentials credentials;

    public VolcengineInterceptor(ISignerV4 signer, Credentials credentials) {
        this.signer = signer;
        this.credentials = credentials;
    }

    @NonNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request req = chain.request();
        RequestParam.RequestParamBuilder param = RequestParam.builder();
        param.body(getBytes(req));
        param.host(req.url().host());
        param.path(req.url().encodedPath());
        param.method(req.method());
        param.queryList(convertQuery(req.url()));
        param.headers(convertHeader(req.headers()));
        param.isSignUrl(false);
        param.date(new Date());

        SignRequest signRequest;
        try {
            signRequest = signer.getSignRequest(param.build(), credentials);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        if (signRequest == null) {
            throw new IllegalArgumentException("Sign Error");
        }
        System.out.println(signRequest);
        Request.Builder newReq = req.newBuilder();
        newReq.addHeader(Const.XDate, signRequest.getXDate());
        if (signRequest.getXNotSignBody() != null) {
            newReq.addHeader(Const.XNotSignBody, signRequest.getXNotSignBody());
        }
        newReq.header(Const.ContentType, signRequest.getContentType());
        newReq.addHeader(Const.XContentSha256, signRequest.getXContentSha256());
        newReq.addHeader(Const.Authorization, signRequest.getAuthorization());
        return chain.proceed(newReq.build());
    }

    private List<Header> convertHeader(Headers headers) {
        List<Header> list = new ArrayList<>();
        for (String name : headers.names()) {
            for (String value : headers.values(name)) {
                list.add(new Header(name, value));
            }
        }
        return list;
    }

    private List<NameValuePair> convertQuery(HttpUrl url) {
        Set<String> names = url.queryParameterNames();
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>(names.size());
        for (String name : names) {
            for (String value : url.queryParameterValues(name)) {
                list.add(new NameValuePair(name, value));
            }
        }
        return list;
    }

    private byte[] getBytes(Request req) throws IOException {
        if (req.body() == null || req.body().contentLength() == 0) {
            return new byte[0];
        }
        RequestBody body = req.body();
        Buffer b = new Buffer();
        body.writeTo(b);
        return b.readByteArray();
    }
}
