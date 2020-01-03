package cn.niusee.common.httpclient;

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
        Response response = SingletonHttpClient.getInstance().get("http://api.jirengu.com/getWeather.php?city=深圳");
        assertEquals(200, response.code());
    }

    public void testGetNotFound() throws IOException {
        Response response = SingletonHttpClient.getInstance().get("http://api.jirengu.com/notFound.php");
        assertEquals(404, response.code());
    }
}