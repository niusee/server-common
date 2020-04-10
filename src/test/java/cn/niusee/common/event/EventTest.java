/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.event;

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
                System.out.println("event: " + event.id);
                assertEquals(event.id, "test");
            }
        }

        TestEvent testEvent = new TestEvent("test");
        TestEventListener listener = new TestEventListener();

        GlobalEventHandler.instance().register(listener);
        GlobalEventHandler.instance().post(testEvent);
    }

    public void testAsyncEvent() {
        class TestEventListener implements ITestEventListener {

            public void onTestEvent(TestEvent event) {
                System.out.println("event: " + event.id);
                assertEquals(event.id, "testAsync");
            }
        }

        TestEvent testEvent = new TestEvent("testAsync");
        TestEventListener listener = new TestEventListener();

        GlobalAsyncEventHandler.instance().register(listener);
        GlobalAsyncEventHandler.instance().asyncPost(testEvent);
    }
}
