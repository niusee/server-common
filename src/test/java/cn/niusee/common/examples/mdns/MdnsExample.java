/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.examples.mdns;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;

/**
 * MdnsExample.java
 *
 * @author Qianliang Zhang
 */
public class MdnsExample {

    public static void startServer() throws InterruptedException {
        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local", "nscloud", 1234, "path=index.html");
            jmdns.registerService(serviceInfo);

            Thread.sleep(2000);

            jmdns.unregisterAllServices();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static class SampleListener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println("Service added: " + event.getInfo());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("Service removed: " + event.getInfo());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.println("Service resolved: " + event.getInfo());
        }
    }

    public static void startClient() throws InterruptedException {
        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Add a service listener
            jmdns.addServiceListener("_http._tcp.local.", new SampleListener());

            // Wait a bit
            Thread.sleep(30000);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                startServer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                startClient();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
