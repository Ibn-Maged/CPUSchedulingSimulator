import java.util.ArrayList;
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

        CPUScheduler cpuScheduler = new CPUScheduler(processes, RRTimeQuantum, contextSwitchTime);
        System.out.println("Press Enter to Begin Simulating....");

//        System.out.println("Shortest Job First");
//        System.out.println("------------------------------------");
//        cpuScheduler.setSchedulingAlgorithm(new ShortestJobFirst(cpuScheduler.getProcesses(), contextSwitchTime));
//        cpuScheduler.simulate();
//        System.out.println("Press Enter to Continue....");
//        scanner.nextLine();

        System.out.println("Shortest Remaining Time First");
        System.out.println("------------------------------------");
        cpuScheduler.setSchedulingAlgorithm(new ShortestRemainingTimeFirst(cpuScheduler.getProcesses(),0,0));
        cpuScheduler.simulate();
        System.out.println("Press Enter to Continue....");
        scanner.nextLine();
//
//        System.out.println("Priority Scheduling");
//        System.out.println("------------------------------------");
//        cpuScheduler.setSchedulingAlgorithm(new PriorityScheduling());
//        cpuScheduler.simulate();
//        System.out.println("Press Enter to Continue....");
//        scanner.nextLine();
//
//        System.out.println("AG Scheduling");
//        System.out.println("------------------------------------");
//        cpuScheduler.setSchedulingAlgorithm(new AGScheduling());
//        cpuScheduler.simulate();
    }
}