public class Process {
    public Process(String name, int burT, int oldBurT, int arrT) {
        this.name = name;
        this.burT = burT;
        this.oldBurT = oldBurT;
        this.arrT = arrT;
    }

    public String name;  //Name process
    public int burT;    //Burst time
    public int oldBurT; //Old burst time
    public int arrT;    //Arrival time
    public int tarT = 0;    //Turnaround time
    public int waiT = 0;    //Waiting time
}
