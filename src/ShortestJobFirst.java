import java.util.ArrayList;
import java.util.PriorityQueue;

public class ShortestJobFirst implements SchedulingAlgorithm{
    PriorityQueue<Process> processes;
    int time = 0;
    int contextSwitch;
    ShortestJobFirst(ArrayList<Process> processes, int contextSwitch){
        this.contextSwitch = contextSwitch;
        this.processes = new PriorityQueue<>(new BurstTimeComparator());
        for(Process process: processes){
            this.processes.add(process);
        }
    }
    @Override
    public void simulate() {
        int numOfProcesses = processes.size();
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        while(!processes.isEmpty()){
            Process currentProcess = processes.poll();
            int turnaroundTime = time + currentProcess.getBurstTime() + contextSwitch;
            int waitingTime = turnaroundTime - currentProcess.getBurstTime();
            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;
            System.out.println(currentProcess.getProcessName());
            System.out.println("Waiting Time: " + waitingTime);
            System.out.println("Turnaround Time: " + turnaroundTime);
            time += currentProcess.getBurstTime() + contextSwitch;
        }
        System.out.println("Average Waiting Time: " + (totalWaitingTime / numOfProcesses));
        System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / numOfProcesses));
    }
}
