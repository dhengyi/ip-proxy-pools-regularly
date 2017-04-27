package httpbrowser;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by paranoid on 17-4-10.
 * 进行代理访问
 *
 * setConnectTimeout：设置连接超时时间，单位毫秒.
 * setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒.
 * 这个属性是新加的属性，因为目前版本是可以共享连接池的.
 * setSocketTimeout：请求获取数据的超时时间，单位毫秒.如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
 */

public class HttpResponseDemo {
    public static String getHtml( String url, String ip, String port) {
        String entity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置代理访问和超时处理
        HttpHost proxy = new HttpHost(ip, Integer.parseInt(port));
        RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(3000).
                setSocketTimeout(3000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);

        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
                "q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Cache-Control", "no-cache");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Cookie", "_free_proxy_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFVEkiJTRkYjMyM" +
                "TU3NGRjMWVhM2JlMDA5Y2IyNzZlZmVlZTYwBjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMUhtT0pjcnRT" +
                "bm9CZEllSXNTYkNZZWk2Nnp3NGNDcFFSQVFodzk1dmpLZWM9BjsARg%3D%3D--09d8736fbfb9a8544" +
                "b46eef48bb320c2b40ee721; Hm_lvt_0cf76c77469e965d2957f0553e6ecf59=1492128157,149" +
                "2160558,1492347839,1492764281; Hm_lpvt_0cf76c77469e965d2957f0553e6ecf59=1492764295");
        httpGet.setHeader("Host", "www.xicidaili.com");
        httpGet.setHeader("Pragma", "no-cache");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            //得到服务响应状态码
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                entity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            }

            httpResponse.close();
            httpClient.close();
        } catch (ClientProtocolException e) {
            entity = null;
        } catch (IOException e) {
            entity = null;
        }

        return entity;
    }

    //对上一个方法的重载，使用本机ip进行网站爬取
    public static String getHtml(String url) throws ClassNotFoundException,
            IOException {
        String entity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置超时处理
        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).
                setSocketTimeout(5000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);

        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
                "q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Cache-Control", "no-cache");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Cookie", "_free_proxy_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFVEkiJTRkYjMyM" +
                "TU3NGRjMWVhM2JlMDA5Y2IyNzZlZmVlZTYwBjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMUhtT0pjcnRT" +
                "bm9CZEllSXNTYkNZZWk2Nnp3NGNDcFFSQVFodzk1dmpLZWM9BjsARg%3D%3D--09d8736fbfb9a8544" +
                "b46eef48bb320c2b40ee721; Hm_lvt_0cf76c77469e965d2957f0553e6ecf59=1492128157,149" +
                "2160558,1492347839,1492764281; Hm_lpvt_0cf76c77469e965d2957f0553e6ecf59=1492764295");
        httpGet.setHeader("Host", "www.xicidaili.com");
        httpGet.setHeader("Pragma", "no-cache");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            //得到服务响应状态码
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                entity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            }

            httpResponse.close();
            httpClient.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        }

        return entity;
    }
}
