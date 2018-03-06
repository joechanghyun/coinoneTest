package com.indrajch.investment.mock.pool.http;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpConnectionManager {

    private static PoolingHttpClientConnectionManager connectionManager = null;
    private static SocketConfig socketConfig = null;
    private static RequestConfig requestConfig = null;
    private static List<Header> headers = null;
    private static ConnectionKeepAliveStrategy connectionKeepAliveStrategy;

    private static PoolingHttpClientConnectionManager getInstance() {
        if ( connectionManager == null ) {
            connectionManager = new PoolingHttpClientConnectionManager();
            connectionManager.setMaxTotal( Integer.parseInt(System.getProperty("http.connection.pool.maxConnections", "2000")));
            connectionManager.setDefaultMaxPerRoute(  Integer.parseInt(System.getProperty("http.connection.pool.maxConnectionsPerRoute", "100")) );
            connectionManager.closeIdleConnections( Long.parseLong(System.getProperty("http.connection.pool.closeIdleConnections", "20")), TimeUnit.MILLISECONDS);
            connectionManager.setDefaultSocketConfig(getSocketConfig());
        }
        return connectionManager;
    }

    public static CloseableHttpClient getHttpClient(RequestConfig requestConfig) {

        HttpClientBuilder builder = HttpClientBuilder.create()
                .setConnectionManager(getInstance());

        if(requestConfig != null) {
            builder.setDefaultRequestConfig(requestConfig);
        }

        CloseableHttpClient httpClient = builder
                .setDefaultHeaders(getHeaders())
                .setKeepAliveStrategy(getConnectionKeepAliveStrategy())
                .build();

        return httpClient;
    }

    public static void abort(HttpRequestBase httpRequest) {
        if (httpRequest != null) {
            try {
                httpRequest.abort();
            } catch (Exception e) {

            }
        }
    }

    public static void releaseEntity(HttpEntity entity) {
        if (entity != null)
            EntityUtils.consumeQuietly(entity);
    }

    public static void release(HttpResponse response) {
        if (response != null && response.getEntity() != null)
            EntityUtils.consumeQuietly(response.getEntity());
    }

    public static void setSocketConfig(SocketConfig socketConfig) {
        HttpConnectionManager.socketConfig = socketConfig;
    }

    public static SocketConfig getSocketConfig() {
        if ( HttpConnectionManager.socketConfig == null ) {
            HttpConnectionManager.socketConfig = SocketConfig.custom()
// nagle 알고리즘 적용 여부
                    .setTcpNoDelay(true)
// SO_KEEPALIVE를 활성화 할 경우 소켓 내부적으로 일정시간 간격으로 heartbeat을 전송하여, 비정상적인 세션 종료에 대해 감지.
// unix 계열 : /etc/sysctl.conf
// windows : \HKEY_LOCAL_MACHINE\SystemCurrentControlSet\Services\TCPIP\Parameters
                    .setSoKeepAlive( Boolean.valueOf( System.getProperty("http.connection.pool.keepAlive", "true") ))
// socket이 close 될 때 버퍼에 남아 있는 데이터를 보내는데 기다려주는 시간(blocked)
                    .setSoLinger( 200 )
// 비정상종료된 상태에서 아직 커널이 소켓의 bind정보를 유지하고 있을 때 해당 소켓을 재사용 할 수 있도록
                    .setSoReuseAddress(true)
//소켓이 연결된후 InputStream에서 읽을때 timeout
                    .setSoTimeout(10000)
                    .build();
        }

        return HttpConnectionManager.socketConfig;
    }

    public static RequestConfig getRequestConfig() {
        if (requestConfig == null) {
            requestConfig = RequestConfig.custom()
                    .setConnectTimeout( Integer.valueOf( System.getProperty("http.request.connectionTime", "1000")) )
                    .setSocketTimeout( Integer.valueOf( System.getProperty("http.request.socketTime", "100")))
                    .build();
        }

        return requestConfig;
    }

    public static void setRequestConfig(RequestConfig requestConfig) {
        HttpConnectionManager.requestConfig = requestConfig;
    }
    public static List<Header> getHeaders() {
        if ( headers == null ) {
            headers = new ArrayList<Header>();
            headers.add(new BasicHeader("Content-Type", "application/json"));
        }
        return headers;
    }

    public static void setHeaders(List<Header> headers) {
        HttpConnectionManager.headers = headers;
    }

    public static ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy() {
        if ( connectionKeepAliveStrategy == null ) {
            connectionKeepAliveStrategy = new HttpShortKeepAliveStrategy();
        }
        return connectionKeepAliveStrategy;
    }

    public static void setConnectionKeepAliveStrategy(ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        HttpConnectionManager.connectionKeepAliveStrategy = connectionKeepAliveStrategy;
    }

    static class HttpShortKeepAliveStrategy implements ConnectionKeepAliveStrategy {
        @Override
        public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
            int AliveTime = Integer.parseInt(
                    System.getProperty("http.connection.pool.keepAlive.time", "120000")); // 2분
            return AliveTime;
        }
    }

}
