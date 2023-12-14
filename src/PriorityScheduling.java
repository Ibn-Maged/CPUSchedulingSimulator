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
        while(!processes.isEmpty()){
            modifyPriority();
            if(getNextProcess()!=null){
                Process currentProcess= getNextProcess();
                processes.remove(currentProcess);
                time+=currentProcess.getBurstTime();
            }else{
                time++;
            }
        }
    }

    private void modifyPriority(){
        for(Process p :processes){
            if(time-p.getArrivalTime()>=p.getBurstTime()*1.5){
                p.setPriorityNumber(p.getPriorityNumber()-1);
            }
        }
    }
    private Process getNextProcess(){
        for(Process p:processes){
            if(p.getArrivalTime()<time){
                return p;
            }
        }
        return null;
    }
}
