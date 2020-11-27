/**
 * @author 911
 * @date 2020-11-20 20:44
 */

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HttpClientDemo2 {
    //构造POST请求
    public void test_post() throws Exception {
        //新建一个客户对象
        CloseableHttpClient client = HttpClients.createDefault();
        //新建一个HttpPost请求的对象 --并将uri传给这个对象
        HttpPost httpPost = new HttpPost("http://api.waditu.com/");
        httpPost.setHeader("Host","api.waditu.com");
        httpPost.setHeader("User-Agent","python-requests/2.24.0");
        httpPost.setHeader("Accept","*/*");
        httpPost.setHeader("Content-Type","application/json");
        //使用NameValuePair  键值对  对参数进行打包
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("api_name", "index_basic"));
        list.add(new BasicNameValuePair("token", "4907b8834a0cecb6af0613e29bf71847206c41ddc3e598b9a25a0203"));
        list.add(new BasicNameValuePair("params", "{\"market\": \"CSI\"}"));
        list.add(new BasicNameValuePair("fields", ""));
        //4.对打包好的参数，使用UrlEncodedFormEntity工具类生成实体的类型数据
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
        System.out.println(entity.toString());
        //5.将含有请求参数的实体对象放到post请求中
        httpPost.setEntity(entity);
        //6.新建一个响应对象来接收客户端执行post的结果
        CloseableHttpResponse response = client.execute(httpPost);
        //7.从响应对象中提取需要的结果-->实际结果,与预期结果进行对比
        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }

    //编写过程中如果有异常请选择shrow这个异常
    //然后对这个类中的方法进行调用就可以实现了对接口的测试了


    @Test
    public void getInfo() throws Exception {
        HttpClientDemo2 demo2 = new HttpClientDemo2();
        demo2.test_post();
    }

}
