import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Number of Processes: ");
        int numOfProcesses = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Round Robin Time Quantum: ");
        int RRTimeQuantum = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Context Switch Time: ");
        int contextSwitchTime = Integer.parseInt(scanner.nextLine());
        ArrayList<Process> processes = new ArrayList<>();
        for(int i = 0; i < numOfProcesses; i++){
            System.out.print("Enter the Name of the Process: ");
            String processName = scanner.nextLine();
            System.out.print("Enter the Arrival Time of the Process: ");
            int arrivalTime = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter the Burst Time of the Process: ");
            int burstTime = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter the Priority of the Process: ");
            int priority = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter the Color of the Process: ");
            String color = scanner.nextLine();
            processes.add(new Process(processName, arrivalTime, burstTime, priority, color));
        }

        CPUScheduler cpuScheduler = new CPUScheduler(processes, 4, contextSwitchTime);

        System.out.println("Shortest Job First");
        System.out.println("------------------------------------");
        cpuScheduler.setSchedulingAlgorithm(new ShortestJobFirst(makeCopy(cpuScheduler.getProcesses()), contextSwitchTime));
        cpuScheduler.simulate();
        System.out.println("Press Enter to Continue....");
        scanner.nextLine();

        System.out.println("Shortest Remaining Time First");
        System.out.println("------------------------------------");
        cpuScheduler.setSchedulingAlgorithm(new ShortestRemainingTimeFirst((makeCopy(cpuScheduler.getProcesses())),20,0));
        cpuScheduler.simulate();
        System.out.println("Press Enter to Continue....");
        scanner.nextLine();

        System.out.println("Priority Scheduling");
        System.out.println("------------------------------------");
        cpuScheduler.setSchedulingAlgorithm(new PriorityScheduling(makeCopy(cpuScheduler.getProcesses()), 20, 0));
        cpuScheduler.simulate();
        System.out.println("Press Enter to Continue....");
        scanner.nextLine();

        System.out.println("AG Scheduling");
        System.out.println("------------------------------------");
        cpuScheduler.setSchedulingAlgorithm(new AGScheduling(makeCopy(cpuScheduler.getProcesses()), RRTimeQuantum));
        cpuScheduler.simulate();
//        Process p1 =new Process("p1",0,17,0,"0");
//        Process p2 =new Process("p2",3,6,0,"0");
//        Process p3 =new Process("p3",4,10,0,"0");
//        Process p4 =new Process("p4",29,4,0,"0");
//
//        p1.setAGFactor(20);
//        p2.setAGFactor(17);
//        p3.setAGFactor(16);
//        p4.setAGFactor(43);
//        processes.add(p1);
//        processes.add(p2);
//        processes.add(p3);
//        processes.add(p4);

    }

    static ArrayList<Process> makeCopy(ArrayList<Process> processes){
        ArrayList<Process> copyList = new ArrayList<>();
        for(Process process: processes){
            copyList.add(new Process(process.getProcessName(), process.getArrivalTime(), process.getBurstTime(), process.getPriorityNumber(), process.getColor()));
        }
        return copyList;
    }
}