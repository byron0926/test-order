package com.bjmu.order.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpClient {

    public static Logger log = LoggerFactory.getLogger(HttpClient.class);

    /*连接超时时间*/
    private static int CONNECT_TIMEOUT = 120000;
    /*连接请求超时时间*/
    private static int CONNECT_REQUEST_TIMEOUT = 60000;
    /*socket超时时间*/
    private static int SOCKET_TIMEOUT = 120000;
    /*最大重连次数*/
    private static int MAX_REDIRECTS = 6;
    /*是否允许重连*/
    private static boolean CIRCULAR_REDIRECTS_ALLOWED = true;
    /* 默认编码格式*/
    private final static String DEFAULT_CHARSET = "GBK";
    public final static String DEFAULT_CONTENT_TYPE = "application/json";
    public final static String X_WWW_FORM_URL_ENCODED = "application/x-www-form-urlencoded";

    /*连接池*/
    private PoolingHttpClientConnectionManager cm;
    private RequestConfig config;
    private DefaultHttpRequestRetryHandler requestRetryHandler;

    private HttpClient() {
        if (null == config) {
            config = RequestConfig.custom()
                    .setExpectContinueEnabled(true)
                    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).
                            setConnectTimeout(CONNECT_TIMEOUT).
                            setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT).
                            setSocketTimeout(SOCKET_TIMEOUT).
                            setMaxRedirects(MAX_REDIRECTS).
                            setCircularRedirectsAllowed(CIRCULAR_REDIRECTS_ALLOWED).build();
        }
        if (null == cm) {
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().
                    register("http", PlainConnectionSocketFactory.getSocketFactory()).
                    register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
            cm = new PoolingHttpClientConnectionManager(registry);
            /*最大连接数*/
            cm.setMaxTotal(200);
            /*每个路由最大连接数*/
            cm.setDefaultMaxPerRoute(20);
        }
        if (null == requestRetryHandler) {
            requestRetryHandler = new DefaultHttpRequestRetryHandler(MAX_REDIRECTS, true);
        }
    }

    public static HttpClient newInstance() {
        return new HttpClient();
    }


    /**
     * 发送 get请求
     *
     * @param url
     * @return
     */
    public ResponseResult sendHttpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        return sendHttpGet(httpGet);
    }

    public ResponseResult sendHttpGet(String url,String key,String value) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(key,value);
        return sendHttpGet(httpGet);
    }

    /**
     * 发送Get请求Https
     *
     * @param url
     * @return
     */
    public ResponseResult sendHttpsGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        return sendHttpsGet(httpGet);
    }

    /**
     * 发送 post请求
     *
     * @param url        请求地址
     * @param param      参数
     * @param contenType 请求体参数类型 传null类型默认json格式数据
     * @param headers    请求头信息
     * @return
     * @description: <p>
     * <b>note:</b>
     * contenType为null时，默认application/json 参数param即为json格式参数
     * contenType为application/x-www-form-urlencoded时，参数param即为格式:key1=value1&key2=value2参数
     * .
     * .
     * .
     * <p>
     * </p>
     */
    public ResponseResult sendHttpPost(String url, String param, String contenType, Map<String, String> headers) throws Exception {
        log.info("==>调用sendHttpPost(String url,String param,String contenType,Map<String, String> headers)方法");
        HttpPost httpPost = new HttpPost(url);
        if (null != param) {
            try {
                httpPost.setHeader("Content-Type", null != contenType ? contenType : DEFAULT_CONTENT_TYPE);
                if (headers != null) {
                    Set<String> keys = headers.keySet();
                    for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                        String key = (String) i.next();
                        httpPost.addHeader(key, headers.get(key));
                    }
                }
                StringEntity stringEntity = new StringEntity(param, DEFAULT_CHARSET);
                stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, null != contenType ? contenType : DEFAULT_CONTENT_TYPE));
                httpPost.setEntity(stringEntity);
            } catch (Exception e) {
                log.info("调用httpclient post请求抛出异常:" + e.getMessage());
            }
        }
        return sendHttpPost(httpPost);
    }


    private ResponseResult sendHttpPost(HttpPost httpPost) throws Exception {
        log.info("==>调用sendHttpPost(HttpPost httpPost)方法");
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        ResponseResult rr = null;
        try {
            /*采用连接池方式获取连接*/
            httpClient = HttpClientBuilder.create().
                    setConnectionManager(cm).
                    setRetryHandler(requestRetryHandler).build();
            /*不用连接池方式*/
            /*httpClient = HttpClients.createDefault();*/
            httpPost.setConfig(config);
            response = httpClient.execute(httpPost);
            rr = getContent(response);
        } catch (Exception e) {
            log.info("调用httpclient post请求抛出异常:" + e.getMessage());
            throw new Exception(e);
        } finally {
            close(response, httpClient);
        }
        return rr;
    }

    public ResponseResult sendHttpPostFile(String url,File file) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        log.info("==>调用sendHttpPost(HttpPost httpPost) file 方法");
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        ResponseResult rr = null;
        try {
            /*采用连接池方式获取连接*/
            httpClient = HttpClientBuilder.create().
                    setConnectionManager(cm).
                    setRetryHandler(requestRetryHandler).build();
            /*不用连接池方式*/
            /*httpClient = HttpClients.createDefault();*/
            FileBody fileBody = new FileBody(file);
            StringBody stringBody = new StringBody("application/text", Charset.defaultCharset());
            HttpEntity reqEntity =  MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addPart("file",fileBody)
                    .addPart("type",stringBody)
                    .build();
            httpPost.setConfig(config);
            httpPost.setEntity(reqEntity);
            response = httpClient.execute(httpPost);
            rr = getContent(response);
        } catch (Exception e) {
            log.info("调用httpclient post请求抛出异常:" + e.getMessage());
            throw new Exception(e);
        } finally {
            close(response, httpClient);
        }
        return rr;
    }

    public ResponseResult sendHttpGet(HttpGet httpGet) {
        log.info("==>调用sendHttpGet(HttpGet httpGet)方法");
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        ResponseResult rr = null;
        try {
            /*采用连接池方式获取连接*/
            httpClient = HttpClients.custom()
                    .setConnectionManager(cm)
                    .setRetryHandler(requestRetryHandler).build();
            /*不用连接池方式*/
            /*httpClient = HttpClients.createDefault();*/
            httpGet.setConfig(config);
            response = httpClient.execute(httpGet);
            rr = getContent(response);
        } catch (IOException e) {
            log.info("调用httpclient get请求抛出异常:" + e.getMessage());
        } finally {
            close(response, httpClient);
        }
        return rr;
    }

    public ResponseResult sendHttpsGet(HttpGet httpGet) {
        log.info("==>调用sendHttpsGet(HttpGet httpGet)方法");
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        ResponseResult rr = null;
        try {
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpGet.getURI().toString()));
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
            /*采用连接池方式获取连接*/
            httpClient = HttpClients.custom()
                    .setConnectionManager(cm)
                    .setSSLHostnameVerifier(hostnameVerifier)
                    .setRetryHandler(requestRetryHandler).build();
            /*不用连接池方式*/
            /*httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();*/
            httpGet.setConfig(config);
            response = httpClient.execute(httpGet);
            rr = getContent(response);
        } catch (IOException e) {
            log.info("调用httpclient get请求抛出异常:" + e.getMessage());
        } finally {
            close(response, httpClient);
        }
        return rr;
    }

    private ResponseResult sendHttpsPost(HttpPost httpPost) throws Exception {
        log.info("==>调用sendHttpsPost(HttpPost httpPost)方法");
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        ResponseResult rr = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            response = httpClient.execute(httpPost);
            rr = getContent(response);
        } catch (IOException e) {
            log.info("调用httpclient get请求抛出异常:" + e.getMessage());
        } finally {
            close(response, httpClient);
        }
        return rr;
    }

    public ResponseResult sendHttpsPost(String url, String param, String contenType, Map<String, String> headers) throws Exception {
        log.info("==>调用sendHttpsPost(String url,String param,String contenType,Map<String, String> headers)方法");
        HttpPost httpPost = new HttpPost(url);
        if (null != param) {
            try {
                httpPost.setHeader("Content-Type", null != contenType ? contenType : DEFAULT_CONTENT_TYPE);
                if (headers != null) {
                    Set<String> keys = headers.keySet();
                    for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                        String key = (String) i.next();
                        httpPost.addHeader(key, headers.get(key));
                    }
                }
                StringEntity stringEntity = new StringEntity(param, DEFAULT_CHARSET);
                stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, null != contenType ? contenType : DEFAULT_CONTENT_TYPE));
                httpPost.setEntity(stringEntity);
            } catch (Exception e) {
                log.info("调用httpclient post请求抛出异常:" + e.getMessage());
            }
        }
        return sendHttpsPost(httpPost);
    }

    private ResponseResult getContent(CloseableHttpResponse response) throws IOException {
        ResponseResult rr = new ResponseResult();
        rr.setHttpCode(response.getStatusLine().getStatusCode());
        String content = EntityUtils.toString(response.getEntity(),DEFAULT_CHARSET);
        rr.setContent(content);
        Header[] headers = response.getAllHeaders();
        log.info("***************客户端 get 请求头信息*******************");
        for (Header header : headers) {
            log.info(header.getName() + ":" + header.getValue());
        }
        log.info("***************客户端 get 请求响应内容*****************");
        log.info("response:" + content);
        return rr;
    }

    /**
     * 关闭连接,释放资源
     *
     * @param response
     * @param httpClient
     */
    private void close(CloseableHttpResponse response, CloseableHttpClient httpClient) {
        try {
            if (null != response) {
                response.close();
            }
            if (null != httpClient) {
                httpClient.close();
            }
        } catch (IOException e) {
            log.info("关闭资源抛出异常:" + e.getMessage());
        }
    }

    public static final String getResult(String urlStr, String content, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();// 新建连接实例
            connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
            connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
            connection.setDoOutput(true);// 是否打开输出流 true|false
            connection.setDoInput(true);// 是否打开输入流true|false
            connection.setRequestMethod("GET");// 提交方法POST|GET
            connection.setUseCaches(false);// 是否缓存true|false
            connection.connect();// 打开连接端口
            DataOutputStream out = new DataOutputStream(connection
                    .getOutputStream());// 打开输出流往对端服务器写数据
            out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.flush();// 刷新
            out.close();// 关闭输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();// 关闭连接
            }
        }
        return null;
    }

    public class ResponseResult implements Serializable {
        private static final long serialVersionUID = 2286588404364386239L;
        /*http请求返回码*/
        private int HttpCode;
        /*http请求返回数据*/
        private String content;

        public ResponseResult() {
        }

        public int getHttpCode() {
            return HttpCode;
        }

        public void setHttpCode(int httpCode) {
            HttpCode = httpCode;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "ResponseResult{" +
                    "HttpCode=" + HttpCode +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) throws Exception {
//        String url = "http://ip.taobao.com/service/getIpInfo.php";
//        System.out.println(HttpClient.getResult(url,"format=json&ip=218.192.3.42","utf-8"));
//        String sms = "https://api.weex.com/v1/market/ticker?market=ETHUSD";
//        System.out.println(HttpClient.newInstance().sendHttpGet(sms));
//        JSONObject obj = JSON.parseObject(HttpClient.newInstance().sendHttpGet(sms).getContent());
//        JSONObject obj2 = obj.getJSONObject("data");
//        JSONObject obj3 = obj2.getJSONObject("ticker");
//        System.out.println(obj3.get("last"));
        ResponseResult re = HttpClient.newInstance().sendHttpPostFile("http://13.230.250.74:8585/ocr/ocrAPI/upload",
                new File("E:\\456.jpg"));
        System.out.println(re);
    }

}
