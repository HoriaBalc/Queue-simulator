import java.util.*;
        import java.util.concurrent.ArrayBlockingQueue;
        import java.util.concurrent.BlockingQueue;
        import java.util.concurrent.atomic.AtomicInteger;
        import java.lang.*;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private boolean ok;
    private int j;
    public Server(){
        tasks=new ArrayBlockingQueue<Task>(100);
        waitingPeriod= new AtomicInteger(0);
        this.ok=true;
    }
    public Server(int cap){
        tasks=new ArrayBlockingQueue<Task>(cap);
        waitingPeriod= new AtomicInteger(0);
        this.ok=true;
    }
    public void addTask(Task newTask){
        tasks.add(newTask);
        this.waitingPeriod.addAndGet(newTask.getProcessingTime());
    }

    public void run(){
        Task tsk;
        ok=true;
         j=0;
        while (ok){
            try { // System.out.println("Nu");
                if(!tasks.isEmpty()) {
                    tsk = tasks.peek();
                    int cont=tsk.getProcessingTime();
                    j=this.getWaitingPeriod().intValue();
                    //System.out.println(j);
                    for(int i=0;i<cont;i++){
                        Thread.sleep(  1000);
                        //System.out.println(tsk.getProcessingTime());
                        this.waitingPeriod.decrementAndGet();
                        tsk.scaderePT();
                       // System.out.println(tsk.getProcessingTime());
                    }
                    tasks.remove(tsk);
                }
                //else {

                //  ok=false;
                //}
            }
            catch (InterruptedException e){
                System.out.println("Intrerupere");
            }
        }
    }
    public  Task[] getTasks(){
        //ArrayList<Task> tsk=new ArrayList<>();
        BlockingQueue<Task> tsk=this.tasks;
        Task[] t1=null;
        //tsk.toArray();
        int i=0;
        try{
            while (!tsk.isEmpty()) {
                t1[i] = tsk.take();
                // tsk.remove(t1[i]);
            }
        }
        catch (InterruptedException e){
            System.out.println("Intrerupere");
        }
        return t1;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Task getFirst(){
        if(!tasks.isEmpty())
        {Task t=this.tasks.peek();
            return t;
        }
        return null;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
    public String toString(){
        String s="";
        if(!tasks.isEmpty()) {

            for (Task t : tasks) {
                s += t.toString();
            }
            s+="\n";
        }
        else s="closed\n";
        return s;
    }

    public int getJ() {
        return j;
    }
}
