/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.process;

import cn.niusee.common.process.OnProcessRunnerCallback;
import cn.niusee.common.process.ProcessRunner;
import junit.framework.TestCase;

/**
 * 测试FFmpeg运行进程
 *
 * @author Qianliang Zhang
 */
public class TestFFmpegProcess extends TestCase {

    public void testFFmpegProgress() {
        ProcessRunner runner = new ProcessRunner("ffmpeg");
        runner.addParams(new String[]{"-i", "/usr/local/var/www/vod/resource/10_1.flv", "-preset",
                "medium", "-movflags", "faststart", "/usr/local/var/www/vod/resource/1/12345678/source.mp4"});
        runner.setOnScriptRunnerCallback(new OnProcessRunnerCallback() {
            @Override
            public void onStart(ProcessRunner processRunner) {
                System.out.println("runner start");
            }

            @Override
            public void onMessage(ProcessRunner processRunner, String message) {
                System.out.println("runner message: " + message);
            }

            @Override
            public void onComplete(ProcessRunner processRunner, boolean success) {
                System.out.println("runner complete: " + success);
            }
        });
        runner.execute();
    }
}
