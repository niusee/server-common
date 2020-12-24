/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.media;

import cn.niusee.common.media.SimpleMediaInfo;
import cn.niusee.common.media.util.SimpleMediaInfoUtils;
import junit.framework.TestCase;

/**
 * SimpleMediaInfoUtilTest.java
 *
 * @author Qianliang Zhang
 */
public class SimpleMediaInfoUtilTest extends TestCase {

    public void testMedia1() {
        SimpleMediaInfo info = SimpleMediaInfoUtils.analyzeStreamInfo("/Users/qian/Downloads/videos/file/demo.mp4");
        assertEquals(info.getFormat(), "mov,mp4,m4a,3gp,3g2,mj2");
        assertEquals(info.getDurationInMilliseconds(), 91742);
        assertEquals(info.getDurationInSeconds(), 91.74);
        assertEquals(info.getBitrate(), 1052642);

        assertEquals(info.getVideoCodec(), "h264");
        assertEquals(info.getVideoBitrate(), 923540);
        assertEquals(info.getSize(), "1280x720");
        assertEquals(info.getFps(), "25/1");
        assertEquals(info.getFpsInDouble(), 25D);
        assertEquals(info.getAvgFpsInDouble(), 25D);

        assertEquals(info.getAudioCodec(), "aac");
        assertEquals(info.getSampleRate(), 44100);
        assertEquals(info.getChannels(), 2);
        assertEquals(info.getAudioBitrate(), 125588);

        assertTrue(info.isProgressiveVideo());
        assertTrue(info.isMediaValid());

        System.out.println(info);
    }

    public void testMedia2() {
        SimpleMediaInfo info = SimpleMediaInfoUtils.analyzeStreamInfo("/Users/qian/Downloads/videos/file/bunny.mp4");
        assertEquals(info.getFormat(), "mov,mp4,m4a,3gp,3g2,mj2");
        assertEquals(info.getDurationInMilliseconds(), 596462);
        assertEquals(info.getDurationInSeconds(), 596.46);
        assertEquals(info.getBitrate(), 3343032);

        assertEquals(info.getVideoCodec(), "h264");
        assertEquals(info.getVideoBitrate(), 2899884);
        assertEquals(info.getSize(), "853x480");
        assertEquals(info.getFps(), "24/1");
        assertEquals(info.getFpsInDouble(), 24D);
        assertEquals(info.getAvgFpsInDouble(), 24D);

        assertEquals(info.getAudioCodec(), "aac");
        assertEquals(info.getSampleRate(), 48000);
        assertEquals(info.getChannels(), 6);
        assertEquals(info.getAudioBitrate(), 437619);

        assertFalse(info.isProgressiveVideo());
        assertTrue(info.isMediaValid());

        System.out.println(info);
    }
}
