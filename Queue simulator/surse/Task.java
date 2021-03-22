import java.util.Collections;
import java.util.*;
import java.lang.*;
public class Task implements Comparable<Task>{
private int arrivalTime;
private int processingTime;
private int id;

public Task(int idc,int aT,int pT){
    this.id=idc;
    this.arrivalTime=aT;
    this.processingTime=pT;
}

public int finishTime(Server s){
    return this.arrivalTime+this.processingTime+ s.getWaitingPeriod().intValue();
}

public void scaderePT(){
    processingTime--;
}

public int compareTo(Task t){
    return this.arrivalTime-t.arrivalTime;

}

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public int getId() {
        return id;
    }

    public String toString(){
        String s="";
        s+="("+id+","+arrivalTime +","+ processingTime + ")";
        return s;
    }
}
