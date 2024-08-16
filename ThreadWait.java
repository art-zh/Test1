public class ThreadWait extends Thread{
    private boolean isdone = false;
    public synchronized void working(){
        System.out.println("Запуск программы");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        isdone = true;
        System.out.println("Первый блок выполнен");
        notify();
    }
    public synchronized void waiting(){
        while (!isdone){
            System.out.println("Ожидание");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Второй блок выполнен");
    }
    static class Worker extends Thread{
        private ThreadWait resource;
        public Worker(ThreadWait resource, String name){
            super(name);
            this.resource=resource;
        }

        @Override
        public void run() {
            if (getName().equals("Работай")){
                resource.working();
            } else {
                resource.waiting();
            }
        }
    }

    public static void main(String[] args) {
        ThreadWait resource = new ThreadWait();
        Worker todoWork = new Worker(resource, "Работай");
        Worker toWait = new Worker(resource, "Жди");
        todoWork.start();
        toWait.start();
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Завершено");
    }
}

