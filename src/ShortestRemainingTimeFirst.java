import java.util.ArrayList;
import java.util.PriorityQueue;

public class ShortestRemainingTimeFirst implements SchedulingAlgorithm{

    PriorityQueue<Process> processes;
    int time = 0;
    int contextSwitching;
    int agingTIme;
    ShortestRemainingTimeFirst(ArrayList<Process> processes, int agingTime,int contextSwitching){
        this.agingTIme = agingTime;
        this.processes = new PriorityQueue<>(new SRTFComparator());
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
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int numOfProcesses = processes.size();
        while(!processes.isEmpty()) {
            Process currenProess=getNextProcess(time);
            processes.remove(currenProess);
            if(currenProess==null){
                time++;
            }else {
                System.out.println("at t = " + time + " processing "+currenProess.getProcessName());
                while(currenProess.getBurstTime()>0){
                    currenProess.setBurstTime(currenProess.getBurstTime()-1);
                    time++;
                    addWaittingTime();
                    if(getNextProcess(time)!=null){
                        Process nextProcess=getNextProcess(time);
                        if(nextProcess.getBurstTime()<currenProess.getBurstTime()&&nextProcess.getPriorityNumber()>=currenProess.getPriorityNumber()){
                            if(currenProess.getBurstTime()>0){
                                System.out.println("at t = " + time + " switching between "+currenProess.getProcessName()+" "+nextProcess.getProcessName());
                                totalTime+=contextSwitching;
                                time+=contextSwitching;
                                currenProess.setWaitingTime(currenProess.getWaitingTime() + contextSwitching);
                                processes.add(currenProess);
                            }
                            break;
                        }
                    }
                    if(time%agingTIme==0){
                        modifyPriority();
                    }
                }
                if(currenProess.getBurstTime() == 0){
                    System.out.println("at time " + time + " " + currenProess.getProcessName() + " Finished");
                    System.out.println("Waiting Time: " + currenProess.getWaitingTime());
                    System.out.println("Turnaround Time: " + (time - currenProess.getArrivalTime()));
                    totalWaitingTime += currenProess.getWaitingTime() + contextSwitching;
                    totalTurnaroundTime += time - currenProess.getArrivalTime();
                    time += contextSwitching;
                }
            }
            totalTime+=time;
        }
        System.out.println("Average Waiting Time: " + (totalWaitingTime / numOfProcesses));
        System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / numOfProcesses));
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
            if(p.getArrivalTime() <= time){
                p.setWaitingTime(p.getWaitingTime()+1);
            }
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
