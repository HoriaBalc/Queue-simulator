import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.io.File;
import java.lang.*;
public class SimulationManager implements Runnable {
public int timeLimit;
public int maxProcessingTime;
public int minProcessingTime;
public int maxArrivalTime;
public int minArrivalTime;
public int numberOfServers;
public int numberOfClients;
private Scheduler scheduler;
private List<Task> generatedTasks;
private String s;
private String s1;

public SimulationManager(String str1,String str2){
    this.s=str1;
    this.s1=str2;
    this.generateNRandomTasks();
    scheduler= new Scheduler(this.numberOfServers,this.numberOfClients);
    this.scheduler.changeStrategy();

}

public void citire()  {

         Scanner myReader=null;int i = 0;File f = new File("In-Test3.txt");
        try { myReader = new Scanner(f); }
        catch(FileNotFoundException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            i++;
            if (i == 1)
                numberOfClients = Integer.parseInt(data);
            if (i == 2)
                numberOfServers = Integer.parseInt(data);
            if (i == 3)
                timeLimit = Integer.parseInt(data);
            if (i == 5) {
                String[] parts = data.split(",");
                maxProcessingTime = Integer.parseInt(parts[1]);
                minProcessingTime = Integer.parseInt(parts[0]);
            }
            if (i == 4) {
                String[] parts = data.split(",");
                maxArrivalTime = Integer.parseInt(parts[1]);
                minArrivalTime = Integer.parseInt(parts[0]);
            }
            if (i > 5)
                break;
        }
        myReader.close();
}

public void scriere(PrintWriter writer,String s){
        writer.println(s);
}

public void generateNRandomTasks(){
    this.citire();
    generatedTasks=new ArrayList<Task>();
    ArrayList<Task> clienti=new ArrayList<Task>();
    for (int i=0;i<this.numberOfClients;i++){
        Random rand = new Random();
        int randAT= rand.nextInt(this.maxArrivalTime-this.minArrivalTime)+this.minArrivalTime;
        int randPT= rand.nextInt(this.maxProcessingTime-this.minProcessingTime)+this.minProcessingTime;
        Task t=new Task(i+1,randAT,randPT);
        clienti.add(t);
    }
    Collections.sort(clienti);
    generatedTasks=clienti;
}

public double averageTime(){
    double i=0;
    for(Task t:generatedTasks){
        i+=t.getProcessingTime();
    }
    return i;
}

public void run(){
    int currentTime=0;
    double i=this.averageTime();
    try {
        PrintWriter writer = new PrintWriter(s1, "UTF-8");
    while(currentTime<timeLimit){
        Iterator iterator = generatedTasks.iterator();
        while(iterator.hasNext()){
            Task t=(Task) iterator.next();
            if (t.getArrivalTime()==currentTime){
                scheduler.dispatchTask(t);
                iterator.remove();
            }
        }int k=0;
        for(Task t:generatedTasks)
            this.scriere(writer,t.toString());
        this.scriere(writer,"Time:"+currentTime);
        currentTime++;
        this.scriere(writer,scheduler.toString());
        if (generatedTasks.isEmpty()){
            for(Server s:scheduler.getServers()){
               if(s.getWaitingPeriod().intValue()==0)
               {s.setOk(false);
               }else{ k=1;
                   i+=s.getJ()-s.getWaitingPeriod().intValue();}
            }
            if(k==0)
            break;
        }
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex) {
            System.out.println("Intrerupere");
        }
    }
    if(currentTime>=timeLimit)
        for(Server s:scheduler.getServers()){
            s.setOk(false);
        }

    this.scriere(writer,"Average time: "+i/numberOfClients);
    writer.close();
}
    catch (FileNotFoundException e){
        System.out.println("fisier negasit");
    }
    catch (UnsupportedEncodingException e1){
        System.out.println("fiier nesuportat");
    }
}
public static void main (String[] args){

    SimulationManager gen=new SimulationManager(args[0],args[1]);
    Thread t=new Thread(gen);
    t.start();

}

    public int getMaxProcessingTime() {
        return maxProcessingTime;
    }
}
