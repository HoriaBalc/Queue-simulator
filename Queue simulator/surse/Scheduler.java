import java.util.*;
public class Scheduler {

    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers,int maxTasksPerServer){
        Thread t[]=new Thread[maxNoServers];
        this.maxNoServers=maxNoServers;
        this.maxTasksPerServer=maxTasksPerServer;
        servers= new ArrayList<Server>();
        for(int i=0;i<maxNoServers;i++){
            Server s=new Server(maxTasksPerServer);
             servers.add(s);
           // System.out.println("Da");
             t[i]=new Thread(s);
            t[i].start();
        }
    }

    public void changeStrategy(){
            strategy=new ConcreteStrategyTime();
    }

    public void dispatchTask(Task t){
        strategy.addTask(this.servers,t);
    }

    public List<Server> getServers(){
        return servers;
    }

    public String toString(){
        String s="";
        for(Server srv : servers)
        s+=""+srv.toString();
        return s;
    }

}
