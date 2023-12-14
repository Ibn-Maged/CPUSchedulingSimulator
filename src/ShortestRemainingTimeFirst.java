import java.util.ArrayList;
import java.util.PriorityQueue;

public class ShortestRemainingTimeFirst implements SchedulingAlgorithm{

    PriorityQueue<Process> processes;
    int time = 0;
    int agingTIme;
    ShortestRemainingTimeFirst(ArrayList<Process> processes, int agingTime,int contextSwitching){
        this.agingTIme = agingTime;
        this.processes = new PriorityQueue<>(new PriorityComparator());
        for(Process process: processes){
            process.setPriorityNumber(0);
        }
        for(Process process: processes){
            this.processes.add(process);
        }
    }
    @Override
    public void simulate() {
        while(!processes.isEmpty()) {
            Process currenProess=getNextProcess(time);
            System.out.println(currenProess.getProcessName());
            processes.remove(currenProess);
            if(currenProess==null){
                time++;
            }else {
                while(currenProess.getBurstTime()>0){
                    currenProess.setBurstTime(currenProess.getBurstTime()-1);
                    if(getNextProcess(time)!=null){
                        Process nextProcess=getNextProcess(time);
                        if(nextProcess.getBurstTime()<currenProess.getBurstTime()){
                            if(currenProess.getBurstTime()>0){
                                processes.add(currenProess);
                            }
                            break;
                        }
                    }
                    if(time%agingTIme==0){
                        modifyPriority();
                    }
                    time++;
                }
            }
        }

    }
    public Process getNextProcess(int time){
        for (Process process:processes){
            if(process.getArrivalTime()<=time){
                return process;
            }
        }
        return null;

    }
    public void modifyPriority(){
        for(Process p :processes){
            if(p.getBurstTime()>time){
                p.setPriorityNumber(p.getPriorityNumber()+1);
            }
        }
    }

}
