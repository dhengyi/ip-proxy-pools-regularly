package htmlparse;

import IPModel.IPMessage;
import httpbrowser.HttpResponseDemo;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by paranoid on 17-4-10.
 */

public class URLFecter {
    //使用代理进行爬取
    public static List<IPMessage> urlParse
            (String url, String ip, String port,
             List<IPMessage> ipMessages) throws ClassNotFoundException, IOException {
        //调用一个类使其返回html源码
        String html = HttpResponseDemo.getHtml(url, ip, port);

        if(html != null) {
            //将html解析成DOM结构
            Document document = Jsoup.parse(html);

            //提取所需要的数据
            Elements trs = document.select("table[id=ip_list]").select("tbody").select("tr");

            for (int i = 1; i < trs.size(); i++) {
                IPMessage ipMessage = new IPMessage();
                String ipAddress = trs.get(i).select("td").get(1).text();
                String ipPort = trs.get(i).select("td").get(2).text();
                String serverAddress = trs.get(i).select("td").get(3).text();
                String ipType = trs.get(i).select("td").get(5).text();
                String ipSpeed = trs.get(i).select("td").get(6).select("div[class=bar]").
                        attr("title");

                ipMessage.setIPAddress(ipAddress);
                ipMessage.setIPPort(ipPort);
                ipMessage.setServerAddress(serverAddress);
                ipMessage.setIPType(ipType);
                ipMessage.setIPSpeed(ipSpeed);

                ipMessages.add(ipMessage);
            }
        } else {
            out.println(ip+ ": " + port + " 代理不可用");
        }

        return ipMessages;
    }

    //使用本机IP爬取xici代理网站的第一页
    public static List<IPMessage> urlParse(String url, List<IPMessage> list)
            throws IOException, ClassNotFoundException {
        String html = HttpResponseDemo.getHtml(url);

        //将html解析成DOM结构
        Document document = Jsoup.parse(html);

        //提取所需要的数据
        Elements trs = document.select("table[id=ip_list]").select("tbody").select("tr");

        for (int i = 1; i < trs.size(); i++) {
            IPMessage ipMessage = new IPMessage();
            String ipAddress = trs.get(i).select("td").get(1).text();
            String ipPort = trs.get(i).select("td").get(2).text();
            String serverAddress = trs.get(i).select("td").get(3).text();
            String ipType = trs.get(i).select("td").get(5).text();
            String ipSpeed = trs.get(i).select("td").get(6).select("div[class=bar]").
                    attr("title");

            ipMessage.setIPAddress(ipAddress);
            ipMessage.setIPPort(ipPort);
            ipMessage.setServerAddress(serverAddress);
            ipMessage.setIPType(ipType);
            ipMessage.setIPSpeed(ipSpeed);

            list.add(ipMessage);
        }

        return list;
    }
}
