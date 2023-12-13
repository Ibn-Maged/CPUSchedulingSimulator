import java.util.Comparator;

public class BurstTimeComparator implements Comparator<Process> {
    @Override
    public int compare(Process process1, Process process2) {
        int result = Integer.compare(process1.getArrivalTime(), process2.getArrivalTime());
        if(result == 0){
            return Integer.compare(process1.getBurstTime(), process2.getBurstTime());
        }
        return result;
    }
}
