import java.util.ArrayList;
import java.util.PriorityQueue;

public class ShortestJobFirst implements SchedulingAlgorithm{
    ArrayList<Process> processes;
    PriorityQueue<Process> readyQueue;
    int time = 0;
    int contextSwitch;
    ShortestJobFirst(ArrayList<Process> processes, int contextSwitch){
        this.contextSwitch = contextSwitch;
        this.processes = processes;
        readyQueue = new PriorityQueue<>(new BurstTimeComparator());
        for(Process process: processes){
            if(process.getArrivalTime() == 0){
                readyQueue.add(process);
            }
        }
        for(Process process: readyQueue){
            processes.remove(process);
        }
    }
    @Override
    public void simulate() {
        int numOfProcesses = processes.size();
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        while(!readyQueue.isEmpty() || !processes.isEmpty()){
            for(Process process: processes){
                if(process.getArrivalTime() <= time){
                    readyQueue.add(process);
                }
            }
            for(Process process: readyQueue){
                processes.remove(process);
            }
            Process currentProcess = readyQueue.poll();
            System.out.print(currentProcess.getProcessName() + " Started from " + time + " to ");
            time += currentProcess.getBurstTime() + contextSwitch;
            int turnaroundTime = time - currentProcess.getArrivalTime();
            int waitingTime = turnaroundTime - currentProcess.getBurstTime();
            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;
            System.out.println(time);
            System.out.println("Waiting Time: " + waitingTime);
            System.out.println("Turnaround Time: " + turnaroundTime);
        }
        System.out.println("Average Waiting Time: " + (totalWaitingTime / numOfProcesses));
        System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / numOfProcesses));
    }
}
