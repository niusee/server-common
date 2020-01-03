/*
 * Niusee server-common
 *
 * Copyright 2015-2018 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route;

import cn.niusee.common.httpclient.SingletonHttpClient;
import cn.niusee.common.route.common.IllegalRequestParamException;
import cn.niusee.common.route.exception.User400Exception;
import cn.niusee.common.route.exception.User404Exception;
import cn.niusee.common.route.message.IRequestMessage;
import cn.niusee.common.route.util.RequestParseUtils;
import com.google.gson.Gson;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spark.utils.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static spark.Spark.*;

/**
 * 路由测试
 *
 * @author Qianliang Zhang
 */
public class RouterTest {

    static class Book implements IRequestMessage {
        int id;

        String name;

        Book(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public void checkValid() {
            if (id < 10000 || StringUtils.isBlank(name)) {
                throw new IllegalRequestParamException();
            }
        }
    }

    @Before
    public void startRouter() {

        final Map<Integer, Book> bookMap = new ConcurrentHashMap<>(10);

        post("book", (request, response) -> {
            Book book = RequestParseUtils.parse(request.body(), Book.class);
            if (bookMap.containsKey(book.id)) {
                throw new User400Exception(-1000, "id is exists");
            }
            bookMap.put(book.id, book);
            return "{\"success\":true}";
        });

        get("book", (request, response) -> {
            Collection<Book> books = bookMap.values();
            return "{\"books\":" + new Gson().toJson(books) + "}";
        });

        get("book/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            if (!bookMap.containsKey(id)) {
                throw new User404Exception(-1001, "id of book not exists");
            }
            Book book = bookMap.get(id);
            return new Gson().toJson(book);
        });

        put("book/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            if (!bookMap.containsKey(id)) {
                throw new User404Exception(-1001, "id of book not exists");
            }
            Book book = RequestParseUtils.parse(request.body(), Book.class);
            if (book.id != id) {
                throw new User400Exception(-1002, "id of book to modify not equal");
            }
            bookMap.replace(id, book);
            return "{\"success\":true}";
        });

        delete("book/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            if (!bookMap.containsKey(id)) {
                throw new User404Exception(-1001, "id of book not exists");
            }
            bookMap.remove(id);
            return "{\"success\":true}";
        });

        new DefaultJsonExceptionHandler().handleException();
    }

    @Test
    public void testNotFoundRoute() throws IOException {
        Response response = SingletonHttpClient.getInstance().get("http://127.0.0.1:4567/music");
        Assert.assertEquals(404, response.code());
        System.out.println(Objects.requireNonNull(response.body()).string());
    }

    @Test
    public void testRoute() throws IOException {
        Book book1 = new Book(10001, "Hello World");
        Book book2 = new Book(10002, "Java Book");
        Book book3 = new Book(10003, "Python Book");
        Book book4 = new Book(10004, "Javascript Book");
        Book book44 = new Book(10004, "Js Book");

        Book errorBook1 = new Book(1, "Js Book");
        Book errorBook2 = new Book(20000, null);

        // 添加书本
        Response response11 = SingletonHttpClient.getInstance().postJson("http://127.0.0.1:4567/book",
                new Gson().toJson(book1));
        Assert.assertEquals(200, response11.code());
        System.out.println(Objects.requireNonNull(response11.body()).string());

        Response response12 = SingletonHttpClient.getInstance().postJson("http://127.0.0.1:4567/book",
                new Gson().toJson(book2));
        Assert.assertEquals(200, response12.code());
        System.out.println(Objects.requireNonNull(response12.body()).string());

        Response response13 = SingletonHttpClient.getInstance().postJson("http://127.0.0.1:4567/book",
                new Gson().toJson(book3));
        Assert.assertEquals(200, response13.code());
        System.out.println(Objects.requireNonNull(response13.body()).string());

        Response response14 = SingletonHttpClient.getInstance().postJson("http://127.0.0.1:4567/book",
                new Gson().toJson(book4));
        Assert.assertEquals(200, response14.code());
        System.out.println(Objects.requireNonNull(response14.body()).string());

        // 添加错误书本
        Response response15 = SingletonHttpClient.getInstance().postJson("http://127.0.0.1:4567/book",
                new Gson().toJson(errorBook1));
        Assert.assertEquals(400, response15.code());
        System.out.println(Objects.requireNonNull(response15.body()).string());

        Response response16 = SingletonHttpClient.getInstance().postJson("http://127.0.0.1:4567/book",
                new Gson().toJson(errorBook2));
        Assert.assertEquals(400, response16.code());
        System.out.println(Objects.requireNonNull(response16.body()).string());

        // 查询书本
        Response response21 = SingletonHttpClient.getInstance().get("http://127.0.0.1:4567/book");
        Assert.assertEquals(200, response21.code());
        System.out.println(Objects.requireNonNull(response21.body()).string());

        Response response22 = SingletonHttpClient.getInstance().get("http://127.0.0.1:4567/book/1001");
        Assert.assertEquals(404, response22.code());
        System.out.println(Objects.requireNonNull(response22.body()).string());

        Response response23 = SingletonHttpClient.getInstance().get("http://127.0.0.1:4567/book/10001");
        Assert.assertEquals(200, response23.code());
        System.out.println(Objects.requireNonNull(response23.body()).string());

        // 更新书本
        Response response31 = SingletonHttpClient.getInstance().putJson("http://127.0.0.1:4567/book/10001",
                new Gson().toJson(book44));
        Assert.assertEquals(400, response31.code());
        System.out.println(Objects.requireNonNull(response31.body()).string());

        Response response32 = SingletonHttpClient.getInstance().putJson("http://127.0.0.1:4567/book/10004",
                new Gson().toJson(book44));
        Assert.assertEquals(200, response32.code());
        System.out.println(Objects.requireNonNull(response32.body()).string());

        // 删除书本
        Response response41 = SingletonHttpClient.getInstance().delete("http://127.0.0.1:4567/book/1001");
        Assert.assertEquals(404, response41.code());
        System.out.println(Objects.requireNonNull(response41.body()).string());

        Response response42 = SingletonHttpClient.getInstance().delete("http://127.0.0.1:4567/book/10004");
        Assert.assertEquals(200, response42.code());
        System.out.println(Objects.requireNonNull(response42.body()).string());

        // 查询书本
        Response response5 = SingletonHttpClient.getInstance().get("http://127.0.0.1:4567/book");
        Assert.assertEquals(200, response5.code());
        System.out.println(Objects.requireNonNull(response5.body()).string());
    }
}
