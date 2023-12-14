import java.util.ArrayList;
import java.util.PriorityQueue;

public class ShortestRemainingTimeFirst implements SchedulingAlgorithm{

    PriorityQueue<Process> processes;
    int time = 0;
    int contextSwitching;
    int agingTIme;
    ShortestRemainingTimeFirst(ArrayList<Process> processes, int agingTime,int contextSwitching){
        this.agingTIme = agingTime;
        this.processes = new PriorityQueue<>(new PriorityComparator());
        this.contextSwitching=contextSwitching;
        for(Process process: processes){
            process.setPriorityNumber(0);
        }
        for(Process process: processes){
            this.processes.add(process);
        }
    }
    @Override
    public void simulate() {
        int totalTime=0;
        while(!processes.isEmpty()) {
            Process currenProess=getNextProcess(time);
            System.out.println(currenProess.getProcessName());
            processes.remove(currenProess);
            if(currenProess==null){
                time++;
            }else {
                while(currenProess.getBurstTime()>0){
                    currenProess.setBurstTime(currenProess.getBurstTime()-1);
                    time++;
                    addWaittingTime();
                    if(getNextProcess(time)!=null){
                        Process nextProcess=getNextProcess(time);
                        if(nextProcess.getBurstTime()<currenProess.getBurstTime()){
                            if(currenProess.getBurstTime()>0){
                                System.out.println("switching between"+currenProess.getProcessName()+" "+nextProcess.getProcessName());
                                totalTime+=contextSwitching;
                                processes.add(currenProess);
                            }
                            break;
                        }
                    }
                    if(time%agingTIme==0){
                        modifyPriority();
                    }
                }
            }
            totalTime+=time;
        }

    }
    private Process getNextProcess(int time){
        for (Process process:processes){
            if(process.getArrivalTime()<=time){
                return process;
            }
        }
        return null;

    }
    private void addWaittingTime(){
        for(Process p :processes){
            p.setWaitingTime(p.getWaitingTime()+1);
        }
    }
    private void modifyPriority(){
        for(Process p :processes){
            if(p.getWaitingTime()>p.getBurstTime()*1.5){
                p.setPriorityNumber(p.getPriorityNumber()+1);
            }
        }
    }

}
