import java.util.Comparator;

public class SRTFComparator implements Comparator<Process> {
    @Override
    public int compare(Process process1, Process process2) {

        int result = Integer.compare(process2.getPriorityNumber(),process1.getPriorityNumber());
        if (result == 0) {
            result = Integer.compare(process1.getBurstTime(), process2.getBurstTime());
        }

        return result;
    }
}