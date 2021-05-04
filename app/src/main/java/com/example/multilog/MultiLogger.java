package com.example.multilog;

/**
 * Start 3 thread A, B, C to print log, keep the running order A, B, C
 * Hint: implementing with Thread.join() or Kotlin协程
 */
public class MultiLogger {
    private final int threadNum;
    private final Callback callback;

    public interface Callback {
        void trigger(String name, String value);
    }

    public MultiLogger(int n, Callback callback) {
        this.threadNum = n;
        this.callback = callback;
    }

    public void run() {
        Thread lastThread = null;
        for (int i = 1; i <= threadNum; i++) {
            String name = String.valueOf('A' + i - 1);
            Thread thread = new Thread(task, name);
            thread.start();
            if (null != lastThread) {
                try {
                    lastThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lastThread = thread;
        }
    }

    private final Runnable task = new Runnable() {
        private final int LOG_COUNT = 21;
        private int cur = 0;
        @Override
        public void run() {
            while (cur++ < LOG_COUNT) {
                callback.trigger(Thread.currentThread().getName(), String.valueOf(cur));
                // Thread.yield();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
