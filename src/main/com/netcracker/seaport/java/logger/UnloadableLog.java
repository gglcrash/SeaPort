package main.com.netcracker.seaport.java.logger;

public class UnloadableLog {
    private int arrival;
    private int departure;
    private int waiting;
    private int unloadStart;
    private int unloadDuration;
    private String name;

    public UnloadableLog(){
        name = "";
        arrival = 0;
        departure = 0;
        waiting = 0;
        unloadDuration = 0;
        unloadStart = 0;
    }

public void setName(String name){
    this.name = name;
}

public String getName(){
    return name;
}

public void setArrival(int arrival){
    this.arrival=arrival;
}

public int getArrival(){
    return arrival;
}

public void setDeparture(int departure){
    this.departure=departure;
}

public int getDeparture(){
    return departure;
}

public void setWaiting(int waiting){
    this.waiting = waiting;
}

public int getWaiting(){
    return waiting;
}

public void setUnloadStart(int unloadStart){
    this.unloadStart = unloadStart;
}

public int getUnloadStart(){
    return unloadStart;
}

public void setUnloadDuration(int unloadDuration){
    this.unloadDuration = unloadDuration;
}

public int getUnloadDuration(){
    return unloadDuration;
}


}
