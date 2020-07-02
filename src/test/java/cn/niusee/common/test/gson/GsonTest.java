/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.gson;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import junit.framework.TestCase;

/**
 * GsonTest.java
 *
 * @author Qianliang Zhang
 */
public class GsonTest extends TestCase {

    static class Book {
        @SerializedName(value = "id", alternate = {"book_id"})
        private int id;

        @SerializedName(value = "name", alternate = {"book_name"})
        private String name;

        @SerializedName(value = "author", alternate = {"book_author"})
        private String author;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }

    public void testBook1() {
        String rawData = "{\"id\":1001,\"name\":\"Think In Java\",\"author\":\"Sam Lin\"}";
        Book book = new Gson().fromJson(rawData, Book.class);
        assertEquals(book.getId(), 1001);
        assertEquals(book.getName(), "Think In Java");
        assertEquals(book.getAuthor(), "Sam Lin");
        System.out.println(new Gson().toJson(book));
    }

    public void testBook2() {
        String rawData = "{\"book_id\":1001,\"book_name\":\"Think In Java\",\"book_author\":\"Sam Lin\"}";
        Book book = new Gson().fromJson(rawData, Book.class);
        assertEquals(book.getId(), 1001);
        assertEquals(book.getName(), "Think In Java");
        assertEquals(book.getAuthor(), "Sam Lin");
        System.out.println(new Gson().toJson(book));
    }
}
