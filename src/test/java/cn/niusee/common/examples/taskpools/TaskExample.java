package cn.niusee.common.examples.taskpools;

import cn.niusee.common.taskpools.ITask;
import cn.niusee.common.taskpools.OnTaskCallback;
import cn.niusee.common.taskpools.SimpleTaskExecutors;

import java.time.LocalDateTime;

public class TaskExample {

    private static class TestTask implements ITask {

        private final int myNum;

        TestTask(int myNum) {
            this.myNum = myNum;
        }

        @Override
        public boolean run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return myNum % 2 == 0;
        }

        @Override
        public void cancel() {

        }

        @Override
        public String toString() {
            return "TestTask{" +
                    "myNum=" + myNum +
                    '}';
        }
    }


    public static void main(String[] args) {
        SimpleTaskExecutors taskExecutors = new SimpleTaskExecutors("Test1", 2);
        for (int i = 0; i < 10; i++) {
            taskExecutors.executeTask(new TestTask(i), new OnTaskCallback() {
                @Override
                public void onTaskStart(ITask task) {
                    System.out.println("time: " + LocalDateTime.now() + " task start: " + task.toString());
                }

                @Override
                public void onTaskSuccess(ITask task) {
                    System.out.println("time: " + LocalDateTime.now() + " task success: " + task.toString());
                }

                @Override
                public void onTaskFail(ITask task) {
                    System.out.println("time: " + LocalDateTime.now() + " task fail: " + task.toString());
                }
            });
        }
    }
}
