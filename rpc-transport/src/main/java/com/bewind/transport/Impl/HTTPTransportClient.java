package com.bewind.transport.Impl;

import com.bewind.rpc.Peer;
import com.bewind.transport.TransportClient;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPTransportClient implements TransportClient {

    private String url;
    @Override
    public void connect(Peer peer) {
//        HTTPClient基于短连接，没有必要连接server，但是要提供一个url
        this.url= "http://" +peer.getHost()+":"+peer.getPort();
    }

    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection urlConnection =(HttpURLConnection) new URL(url).openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");

            urlConnection.connect();
            IOUtils.copy(data,urlConnection.getOutputStream());

            int resultCode=urlConnection.getResponseCode();
            if (resultCode==HttpURLConnection.HTTP_OK){
                return urlConnection.getInputStream();
            }else {
                return urlConnection.getErrorStream();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}
