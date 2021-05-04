package com.example.multilog;

/**
 * Start some threads A, B, C... to print log, keep the running order A, B, C...
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

    public void start() {
        for (int i = 0; i < threadNum; i++) {
            String name = String.valueOf((char) ('A' + i));
            new SimpleJoinThread(new SimpleTask(callback, name)).start();
            callback.trigger("", "\n");
        }
    }

    /**
     * A SimpleJoinThread just starts a thread and join it.
     */
    private static class SimpleJoinThread extends Thread {
        private SimpleJoinThread(Runnable task) {
            super(task);
        }

        @Override
        public synchronized void start() {
            super.start();

            try {
                join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A SimpleTask just triggers a callback sequence for several times.
     */
    private static class SimpleTask implements Runnable {
        private final int LOG_COUNT = 10;
        private final Callback callback;
        private final String name;

        private int cur = 0;

        private SimpleTask(Callback callback, String name) {
            this.callback = callback;
            this.name = name;
        }

        @Override
        public void run() {
            while (cur++ < LOG_COUNT) {
                callback.trigger(name, String.valueOf(cur));
            }
        }
    };
}
