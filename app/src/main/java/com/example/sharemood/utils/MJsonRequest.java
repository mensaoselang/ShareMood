package com.example.sharemood.utils;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2018/9/14/014.
 */

public class MJsonRequest extends JsonObjectRequest {
    /**
     * <p>Description:｛转码｝</p>
     * @author:yangbiyao@163.com
     * @see JsonObjectRequest#parseNetworkResponse(NetworkResponse)
     */
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            response.headers.put("HTTP.CONTENT_TYPE", "utf-8");
//                String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            String jsonString = new String(response.data,"utf-8");
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
        catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    /**
     * @param method
     * @param url
     * @param jsonRequest
     * @param listener
     * @param errorListener
     */
    public MJsonRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                        Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    /**
     * @param url
     * @param jsonRequest
     * @param listener
     * @param errorListener
     */
    public MJsonRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                        Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

}
