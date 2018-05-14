package com.yh.http.restful.test;

import com.yh.http.restful.bean.Salary;
import com.yh.http.restful.util.HttpClientUtil;
import org.junit.Test;

public class HttpClientUtilTest {

    /**
     * 测试获取localhost:8080/salary/get/12这个url的返回值，不加请求头信息
     *
     * @throws Exception
     */
    @Test
    public void testHttpClientUtil() throws Exception {
        Long id = 12L;
        String salary = HttpClientUtil.getSalary(id);
        System.out.println(salary);
    }

    /**
     * 测试获取localhost:8080/salary/get/13这个url的返回值，加请求头信息
     *
     * @throws Exception
     */
    @Test
    public void testHttpClientUtil2() throws Exception {
        String url = "http://127.0.0.1:8080/salary/get/";
        Long id = 13L;
        Salary salaryByRule = HttpClientUtil.getSalaryByRule(url, id);
        System.out.println(salaryByRule);
    }
}
