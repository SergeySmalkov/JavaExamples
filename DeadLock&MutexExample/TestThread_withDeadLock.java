public class TestThread_withDeadLock {
    public static final Object Lock1 = new Object();
    public static final Object Lock2 = new Object();

    public static void main(String args[]) throws InterruptedException {
        Task T1 = new Task();
        Task2 T2 = new Task2();
        Thread thread1 = new Thread(T1);
        Thread thread2 = new Thread(T2);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

    }

    private static class Task implements Runnable {
        public void run() {
            synchronized (Lock1) {
                System.out.println(Thread.currentThread().getName() + " Lock 1");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " Waiting for Lock 2");

                synchronized (Lock2) {
                    System.out.println(Thread.currentThread().getName() + " Lock 1 & 2");
                }
            }
        }
    }
    private static class Task2 implements Runnable {
        public void run() {
            synchronized (Lock2) {
                System.out.println(Thread.currentThread().getName() + " Lock 2");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " Waiting for Lock 1");

                synchronized (Lock1) {
                    System.out.println(Thread.currentThread().getName() + " Lock 1 & 2");
                }
            }
        }
    }
}
