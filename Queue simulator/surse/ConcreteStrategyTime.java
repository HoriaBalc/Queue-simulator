import java.util.*;
public class ConcreteStrategyTime implements Strategy {

    public void addTask(List<Server> servers,Task t){

        //int min=0;
        Server m=servers.get(0);

        int i=0;
        for( Server s : servers){
            if(i==0){
                i=1;
                m=s;
            }
            else{
                if(m.getWaitingPeriod().intValue()>s.getWaitingPeriod().intValue()){
                    m=s;
                }
            }
        }
        m.addTask(t);

    }



}
