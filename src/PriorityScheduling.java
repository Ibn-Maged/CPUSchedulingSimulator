import java.util.ArrayList;
import java.util.PriorityQueue;

public class PriorityScheduling implements SchedulingAlgorithm{
    PriorityQueue<Process> processes;
    int time = 0;
    int contextSwitching;
    int agingTIme;
    PriorityScheduling(ArrayList<Process> processes, int agingTime, int contextSwitching){
        this.agingTIme = agingTime;
        this.processes = new PriorityQueue<>(new PriorityComparator());
        this.contextSwitching=contextSwitching;
        for(Process process: processes){
            this.processes.add(process);
        }
    }
    @Override
    public void simulate() {
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        int numOfProcesses = processes.size();
        while(!processes.isEmpty()){
            modifyPriority();
            if(getNextProcess()!=null){
                Process currentProcess= getNextProcess();
                processes.remove(currentProcess);
                System.out.print(currentProcess.getProcessName() + " Started from " + time + " to ");
                time+=currentProcess.getBurstTime() + contextSwitching;
                System.out.println(time);
                int turnaroundTime = time - currentProcess.getArrivalTime();
                int waitingTime = turnaroundTime - currentProcess.getBurstTime();
                totalTurnaroundTime += turnaroundTime;
                totalWaitingTime += waitingTime;
                System.out.println("Waiting Time: " + waitingTime);
                System.out.println("Turnaround Time: " + turnaroundTime);
            }else{
                time++;
            }
        }
        System.out.println("Average Waiting Time: " + totalWaitingTime / numOfProcesses);
        System.out.println("Average Turnaround Time: " + totalTurnaroundTime / numOfProcesses);
    }

    private void modifyPriority(){
        for(Process p :processes){
            if(time-p.getArrivalTime()>=p.getBurstTime()*1.5){
                p.setPriorityNumber(p.getPriorityNumber()-1);
            }
        }
    }
    private Process getNextProcess(){
        PriorityQueue<Process> temp= new PriorityQueue<>(new PriorityComparator());
        for(Process p:processes){
            temp.add(p);
        }

        while(!temp.isEmpty()){
            Process p=temp.poll();
            if(p.getArrivalTime()<=time){
                return p;
            }
        }
        return null;
    }
}
