import java.util.concurrent.locks.ReentrantLock;

public class TestThread_withoutDeadLock_with_mutex {


    public static void main(String args[]) throws InterruptedException {

        Task T1 = new Task();
        Task T2 = new Task();
        Thread thread1 = new Thread(T1);
        Thread thread2 = new Thread(T2);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

    }

    private static class Task implements Runnable {
        private final ReentrantLock mutex = new ReentrantLock();

        public void run() {
                try {
                    mutex.lock();
                    System.out.println(Thread.currentThread().getName() + " Starting");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + " Ending");
                }  finally {
                    mutex.unlock();
                }

        }
    }
}
