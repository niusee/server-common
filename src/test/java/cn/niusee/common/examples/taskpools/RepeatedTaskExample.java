package cn.niusee.common.examples.taskpools;

import cn.niusee.common.taskpools.repeat.IRetryTask;
import cn.niusee.common.taskpools.repeat.OnRetryTaskCallback;
import cn.niusee.common.taskpools.repeat.SimpleRetryTaskExecutors;

import java.time.LocalDateTime;

public class RepeatedTaskExample {

    private static class TestTask implements IRetryTask {

        private int myNum;

        private int currentNum = 0;

        TestTask(int myNum) {
            this.myNum = myNum;
        }

        @Override
        public int getRetryTimes() {
            return 2;
        }

        @Override
        public boolean run() {
            currentNum++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return currentNum > myNum;
        }

        @Override
        public void cancel() {

        }

        @Override
        public String toString() {
            return "TestTask{" +
                    "myNum=" + myNum +
                    ", currentNum=" + currentNum +
                    '}';
        }
    }


    public static void main(String[] args) {
        SimpleRetryTaskExecutors taskExecutors = new SimpleRetryTaskExecutors("Test1", 4);
        for (int i = 0; i < 10; i++) {
            taskExecutors.executeTask(new TestTask(i), new OnRetryTaskCallback() {

                @Override
                public void onTaskStart(IRetryTask task) {
                    System.out.println("time: " + LocalDateTime.now() + " task start: " + task.toString());
                }

                @Override
                public void onTaskRetry(IRetryTask task, int tryingTime) {
                    System.out.println("time: " + LocalDateTime.now() + " task retry: " + task.toString()
                            + ", trying times: " + tryingTime);
                }

                @Override
                public void onTaskSuccess(IRetryTask task) {
                    System.out.println("time: " + LocalDateTime.now() + " task success: " + task.toString());
                }

                @Override
                public void onTaskFail(IRetryTask task) {
                    System.out.println("time: " + LocalDateTime.now() + " task fail: " + task.toString());
                }
            });
        }
    }
}
