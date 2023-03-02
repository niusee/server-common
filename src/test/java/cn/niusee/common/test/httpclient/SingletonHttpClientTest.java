package cn.niusee.common.test.httpclient;

import cn.niusee.common.httpclient.SingletonHttpClient;
import junit.framework.TestCase;
import okhttp3.Response;

import java.io.IOException;

/**
 * Singleton http client test
 *
 * @author Qianliang Zhang
 */
public class SingletonHttpClientTest extends TestCase {

    public void testGet() throws IOException {
        String url = "https://k12.niusee.cn/core/live/api/v2/box?school=niusee";
        try (Response response = SingletonHttpClient.getInstance().get(url)){
            System.out.println(response.body().string());
            assertEquals(200, response.code());
        }
    }

    public void testGetNotFound() throws IOException {
        String url = "https://k12.niusee.cn/core/live/api/v2/box/not_found";
        try (Response response = SingletonHttpClient.getInstance().get(url)) {
            System.out.println(response.body().string());
            assertEquals(404, response.code());
        }
    }

    public void testGetBox() throws IOException {
        String url = "https://k12.niusee.cn/core/live/api/v2/box/box_A45E60BA3383";
        try (Response response = SingletonHttpClient.getInstance().get(url)) {
            System.out.println(response.body().string());
            assertEquals(200, response.code());
        }
    }

    public void testPost() throws IOException {
        String boxInfo = "{"
                + "\"device_id\":\"box_A45E60BA3383\""
                + ",\"type\":1"
                + ",\"version\":\"3.0.2\""
                + "}";
        String url = "https://k12.niusee.cn/core/live/api/v2/box";
        try (Response response = SingletonHttpClient.getInstance().postJson(url, boxInfo)) {
            System.out.println(response.body().string());
            assertEquals(200, response.code());
        }
    }

    public void testPut() throws IOException {
        String url = "https://k12.niusee.cn/core/live/api/v2/box/box_A45E60BA3383/pushstream/main";
        try (Response response = SingletonHttpClient.getInstance().putJson(url, "")) {
            System.out.println(response.body().string());
            assertEquals(200, response.code());
        }
    }

    public void testDelete() throws IOException {
        String url = "https://k12.niusee.cn/core/live/api/v2/box/box_A45E60BA3383/pushstream/main";
        try (Response response = SingletonHttpClient.getInstance().delete(url)) {
            System.out.println(response.body().string());
            assertEquals(200, response.code());
        }
    }
}