import java.text.DecimalFormat;
import java.util.*;

public class SRTF {
    PriorityQueue<Process> queRaw = new PriorityQueue<>(Comparator.comparingInt(pro -> pro.arrT));  //Raw queue
    PriorityQueue<Process> queWai = new PriorityQueue<>(Comparator.comparing(pro -> pro.burT));     //Wait queue
    Queue<Process> queRes = new LinkedList<>();   //Result queue

    void AddProcess() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("\n" + "_".repeat(64));
            System.out.print("How many process you want to add: ");
            int numPro = sc.nextInt();
            System.out.println();
            for (int i = 1; i <= numPro; i++) {
                System.out.print(" -".repeat(16) + "\nProcess name\t: ");
                System.out.printf("Pro%d", i);
                System.out.print("\nBurst time\t\t: ");
                int burTime = sc.nextInt();
                System.out.print("Arrival time\t: ");
                int arrTime = sc.nextInt();
                queRaw.add(new Process(("Pro" + i), burTime, burTime, arrTime));
            }
        }
    }

    void ShowWaitQueue() {
        System.out.println("\n" + "_".repeat(64));
        System.out.println("Raw queue");
        System.out.println("- ".repeat(32));
        System.out.println(" | Process name\t\t | Burst time\t\t | Arrival time");
        System.out.println("- ".repeat(32));
        for (Process pro : queRaw) {
            System.out.printf(" | %s\t\t\t\t | %s\t\t\t\t | %s\n", pro.name, pro.burT, pro.arrT);
        }
    }

    private void addProcessToWait(int currentTime) {
        for (Process pro : queRaw) {
            if (pro.arrT == currentTime) {
                queWai.add(pro);
            }
        }
    }

    private void executeProcessInWait(int currentTime) {
        if (queWai.size() > 0) {
            Process pro = queWai.poll();
            pro.burT--;
            if (pro.burT == 0) {
                pro.tarT = (currentTime + 1) - pro.arrT;
                pro.waiT = pro.tarT - pro.oldBurT;
                queRes.add(pro);
            } else if (pro.burT > 0) {
                queWai.add(pro);
            }
        }
    }

    void ShortestRemainingTimeFirst() {
        int currentTime = 0;
        do {
            addProcessToWait(currentTime);
            executeProcessInWait(currentTime);
            currentTime++;
        } while (queRes.size() != queRaw.size());
    }

    void ShowResultQueue() {
        System.out.println("\n" + "_".repeat(64));
        System.out.println("Result");
        System.out.println("- ".repeat(32));
        System.out.println(" | Process name\t\t | Turnaround time\t | Waiting time");
        System.out.println("- ".repeat(32));
        double waiT = 0;
        int proCou = queRes.size();
        while (!queRes.isEmpty()) {
            Process pro = queRes.poll();
            waiT += pro.waiT;
            System.out.printf(" | %s\t\t\t\t | %s\t\t\t\t | %s\n", pro.name, pro.tarT, pro.waiT);
        }
        double avgT = waiT / proCou;
        DecimalFormat decFor = new DecimalFormat("#.##");
        String res = decFor.format(avgT);
        System.out.printf("\nThe average waiting time for each process is: %s (time unit)\n", res);
    }

    public void Run() {
        AddProcess();
        ShowWaitQueue();
        try {
            System.out.println("\n" + "_".repeat(64));
            System.out.println("Executing ...");
            Thread.sleep(1000);
            ShortestRemainingTimeFirst();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ShowResultQueue();
    }
}
