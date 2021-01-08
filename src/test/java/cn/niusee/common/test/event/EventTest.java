/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.event;

import cn.niusee.common.event.IEvent;
import cn.niusee.common.event.IEventListener;
import cn.niusee.common.event.SingleGlobalAsyncEventHandler;
import cn.niusee.common.event.SingleGlobalEventHandler;
import com.google.common.eventbus.Subscribe;
import junit.framework.TestCase;

/**
 * 事件测试
 *
 * @author Qianliang Zhang
 */
@SuppressWarnings("UnstableApiUsage")
public class EventTest extends TestCase {

    private static class TestEvent implements IEvent {

        public final String id;

        public TestEvent(String id) {
            this.id = id;
        }
    }

    private interface ITestEventListener extends IEventListener {

        @Subscribe
        void onTestEvent(TestEvent event);
    }

    public void testEvent() {
        class TestEventListener implements ITestEventListener {

            public void onTestEvent(TestEvent event) {
                System.out.println(System.currentTimeMillis() + " before event: " + event.id);
                assertEquals(event.id, "test");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + " after event: " + event.id);
            }
        }

        TestEvent testEvent = new TestEvent("test");
        TestEventListener listener = new TestEventListener();

        SingleGlobalEventHandler.instance().register(listener);
        SingleGlobalEventHandler.instance().post(testEvent);
        System.out.println(System.currentTimeMillis() + " post event");
    }

    public void testAsyncEvent() {
        class TestEventListener implements ITestEventListener {

            public void onTestEvent(TestEvent event) {
                System.out.println(System.currentTimeMillis() + " before event: " + event.id);
                assertEquals(event.id, "testAsync");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + " after event: " + event.id);
            }
        }

        TestEvent testEvent = new TestEvent("testAsync");
        TestEventListener listener = new TestEventListener();

        SingleGlobalAsyncEventHandler.instance().register(listener);
        SingleGlobalAsyncEventHandler.instance().asyncPost(testEvent);
        System.out.println(System.currentTimeMillis() + " post async event");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
