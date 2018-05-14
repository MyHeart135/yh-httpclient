package com.yh.http.restful.util;

import com.alibaba.fastjson.JSON;
import com.yh.http.restful.bean.Salary;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用HttpClient调用RESTful风格的接口
 *
 * @author 严欢
 */
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 调用http服务获取salary接口，需要先启动basessm项目，basessm项目中
     * SalaryController控制器有url:localhost:8080/salary/get/{id}对应
     * 的url，返回id对应的数据库中的salary对象
     *
     * @param id salary表中数据的主键
     * @return salary对象的json串
     * @throws Exception
     */
    public static String getSalary(Long id) throws Exception {
        logger.info("HttpClientUtil getSalary,id:{}", id);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/salary/get/" + id);
        CloseableHttpResponse response = client.execute(httpPost);
        try {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity);
                return s;
            } else {
                logger.error("HttpClientUtil getSalary error,id:{} and status-code:{}", id, statusLine.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("HttpClientUtil getSalary error,id:{} and errorMsg:{}", id, e.getMessage());
            throw new RuntimeException(e);
        } finally {
            response.close();
        }
        return null;
    }

    /**
     * 调用http服务获取salary接口，需要先启动basessm项目，basessm项目中
     * SalaryController控制器有url:localhost:8080/salary/get/{id}对应
     * 的url，返回id对应的数据库中的salary对象，在HttpPost中设置请求头信息
     *
     * @param url 请求的restful风格接口的url
     * @param id  获取salary表中id对应的orm对象
     * @return Salary对象
     * @throws Exception
     */
    public static Salary getSalaryByRule(String url, Long id) throws Exception {
        logger.info("HttpClientUtil getSalaryByRule,id:{} and url:{}", id, url);
        String routeRule = "6,6,80";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url + id);
        httpPost.setHeader("routeRule", routeRule);
        httpPost.setHeader("source", "yanhuan");
        CloseableHttpResponse response = client.execute(httpPost);
        try {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity);
                Salary salary = JSON.parseObject(s, Salary.class);
                return salary;
            } else {
                logger.error("HttpClientUtil getSalaryByRule error,id:{} and status-code:{}", id, statusLine.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("HttpClientUtil getSalaryByRule error,id:{} and errorMsg:{}", id, e.getMessage());
        } finally {
            response.close();
        }
        return null;
    }
}
