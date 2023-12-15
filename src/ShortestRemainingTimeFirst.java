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
            Process currentProcess=getNextProcess(time);
            processes.remove(currentProcess);
            if(currentProcess==null){
                time++;
            }else {
//                System.out.println("at t = " + time + " processing "+currentProcess.getProcessName());
                System.out.print(currentProcess.getProcessName() + " Started from " + time + " to ");
                while(currentProcess.getBurstTime()>0){
                    currentProcess.setBurstTime(currentProcess.getBurstTime()-1);
                    addWaitingTime();
                    time++;
                    if(getNextProcess(time)!=null){
                        Process nextProcess = getNextProcess(time);
                        if(nextProcess.getBurstTime()<currentProcess.getBurstTime()&&nextProcess.getPriorityNumber()>=currentProcess.getPriorityNumber()){
                            if(currentProcess.getBurstTime()>0){
                                System.out.println(time);
//                                System.out.println("at t = " + time + " switching between "+currentProcess.getProcessName()+" "+nextProcess.getProcessName());
                                System.out.println("Switching between " + currentProcess.getProcessName() + " " + nextProcess.getProcessName());
//                                System.out.print(nextProcess.getProcessName() + " Started from " + time + " to ");
                                totalTime+=contextSwitching;
                                time+=contextSwitching;
                                currentProcess.setWaitingTime(currentProcess.getWaitingTime() + contextSwitching);
                                processes.add(currentProcess);
                            }
                            break;
                        }
                    }
                    if(time%agingTIme==0){
                        modifyPriority();
                    }
                }
                if(currentProcess.getBurstTime() == 0){
//                    System.out.println("at time " + time + " " + currentProcess.getProcessName() + " Finished");
                    System.out.println(time);
                    System.out.println(currentProcess.getProcessName() + " Finished");
                    System.out.println("Waiting Time: " + currentProcess.getWaitingTime());
                    System.out.println("Turnaround Time: " + (time - currentProcess.getArrivalTime()));
                    totalWaitingTime += currentProcess.getWaitingTime() + contextSwitching;
                    totalTurnaroundTime += time - currentProcess.getArrivalTime();
                    time += contextSwitching;
                }
            }
            totalTime+=time;
        }
        System.out.println("Average Waiting Time: " + (totalWaitingTime / numOfProcesses));
        System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / numOfProcesses));
    }
    private Process getNextProcess(int time){
        PriorityQueue<Process> temp= new PriorityQueue<>(new SRTFComparator());
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
    private void addWaitingTime(){
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
