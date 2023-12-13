import java.util.ArrayList;

public class CPUScheduler {
    private SchedulingAlgorithm schedulingAlgorithm;
    private ArrayList<Process> processes;
    private int RRTimeQuantum;
    private int contextSwitchTime;
    CPUScheduler(ArrayList<Process> processes, int RRTimeQuantum, int contextSwitchTime){
        this.processes = processes;
        this.RRTimeQuantum = RRTimeQuantum;
        this.contextSwitchTime = contextSwitchTime;
    }
    public void setSchedulingAlgorithm(SchedulingAlgorithm schedulingAlgorithm){
        this.schedulingAlgorithm = schedulingAlgorithm;
    }
    public void simulate(){
        schedulingAlgorithm.simulate();
    }

}
