/*
 * Niusee Video Editor Server
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.process;

import cn.niusee.common.logger.LoggerHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 命令行工具运行进程类
 *
 * @author Qianliang Zhang
 */
public class ProcessRunner {
    /**
     * 日记记录
     */
    private static LoggerHelper log = new LoggerHelper(ProcessRunner.class);

    /**
     * 进程运行停止标志
     */
    private volatile boolean onStop = false;

    /**
     * 命令行运行进程
     */
    private Process process = null;

    /**
     * 命令行运行参数
     */
    private List<String> commandParamsList;

    /**
     * 命令行程序运行情况的回调
     */
    private OnProcessRunnerCallback callback;

    /**
     * 命令行运行类构建
     *
     * @param runningBin 命令行工具路径
     */
    public ProcessRunner(String runningBin) {
        commandParamsList = new ArrayList<>();
        commandParamsList.add(runningBin);
    }

    /**
     * 命令行运行类构建
     */
    public ProcessRunner() {
        commandParamsList = new ArrayList<>();
    }

    /**
     * 建立进程的信息读取流
     *
     * @param p 运行进程
     * @return 信息读取流
     */
    private BufferedReader wrapInReader(Process p) {
        return new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
    }

    /**
     * 等待进程结束
     *
     * @param p 进程
     * @return 进程返回结果
     */
    private boolean throwOnError(Process p) {
        log.debug("Waiting for process to exist");
        try {
            boolean success = p.waitFor(1, TimeUnit.SECONDS);
            if (!success) {
                // 不成功直接退出
                return false;
            } else {
                // 成功，还要检查退出值
                int exitValue = p.exitValue();
                if (exitValue != 0) {
                    log.error("Process running error, exit value: {}", exitValue);
                }
                return exitValue == 0;
            }
        } catch (Throwable e) {
            log.debug("Timed out waiting for process to finish");
            return false;
        }
    }

    /**
     * 摧毁运行进程
     */
    private void destroyProcess() {
        // 关闭进程
        if (process != null) {
            log.debug("destroy process");
            process.destroy();
            // 仍然存在
            if (process.isAlive()) {
                process.destroyForcibly();
            }
            process = null;
        }
    }

    /**
     * 添加命令行运行参数
     *
     * @param params 命令行运行参数（列表形式）
     */
    public void addParams(List<String> params) {
        this.commandParamsList.addAll(params);
    }

    /**
     * 添加命令行运行参数
     *
     * @param params 命令行运行参数（数组形式）
     */
    public void addParams(String[] params) {
        for (String param : params) {
            addParam(param);
        }
    }

    /**
     * 添加命令行运行参数
     *
     * @param param 命令行运行参数（单个字符形式）
     */
    public void addParam(String param) {
        this.commandParamsList.add(param);
    }

    /**
     * 设置运行回调
     *
     * @param callback 运行回调
     */
    public void setOnScriptRunnerCallback(OnProcessRunnerCallback callback) {
        this.callback = callback;
    }

    /**
     * 运行
     */
    public void execute() {
        // 如果是被取消，直接忽略
        if (onStop) {
            return;
        }
        // 测试运行参数
        log.debug("Process: {}", commandParamsList);
        ProcessBuilder pb = new ProcessBuilder(commandParamsList).directory(null);
        // NOTE: 针对FFmpeg、Melt等工具，忽略掉Error Stream
        pb.redirectErrorStream(true);

        // 运行成功标识
        if (callback != null) {
            callback.onStart(ProcessRunner.this);
        }
        // 结果
        boolean success = false;
        // 读取命令运行输出信息
        BufferedReader bufferedReader = null;
        try {
            process = pb.start();
            bufferedReader = wrapInReader(process);
            // 线程读取输出信息
            String message;
            while (!onStop && (message = bufferedReader.readLine()) != null) {
                if (callback != null) {
                    callback.onMessage(ProcessRunner.this, message);
                }
            }
            // 等待结果
            success = throwOnError(process);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流信息
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            destroyProcess();
            // 参数清除
            commandParamsList.clear();
            // 如果被取消的，就不回调结果
            if (!onStop && callback != null) {
                callback.onComplete(ProcessRunner.this, success);
            }
        }
    }

    /**
     * 停止
     */
    public void stop() {
        onStop = true;
        destroyProcess();
    }
}
