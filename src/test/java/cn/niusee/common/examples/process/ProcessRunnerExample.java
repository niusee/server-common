/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.examples.process;

import cn.niusee.common.process.OnProcessRunnerCallback;
import cn.niusee.common.process.ProcessRunner;

/**
 * ProcessRunner.java
 *
 * @author Qianliang Zhang
 */
public class ProcessRunnerExample {

    public static void main(String[] args) {
//        ProcessRunner runner = new ProcessRunner();
//        runner.addParams(new String[]{"ulimit", "-n", "4096"});
//        runner.execute();


        ProcessRunner runner = new ProcessRunner();
        runner.addParam("bash");
        runner.addParam("-c");
        runner.addParam("cd ~/deploy/video-downloader/ && python bootstrap.py -i " +
                "https://media.niusee.cn/p1/vedit/m/ba00e187c914d3edd9d72339df9df0e2/706a32262eb78a60373d11fe157fa6a0.m3u8 " +
                "-o 课程名称：校园No.1大脑平衡力挑战.mp4");
        runner.setOnScriptRunnerCallback(new OnProcessRunnerCallback() {
            @Override
            public void onStart(ProcessRunner processRunner) {
                System.out.println("start");
            }

            @Override
            public void onMessage(ProcessRunner processRunner, String message) {
                System.out.println(message);
            }

            @Override
            public void onComplete(ProcessRunner processRunner, boolean success) {
                System.out.println("complete: " + success);
            }
        });
        runner.execute();
        System.out.println("finish");
    }
}
