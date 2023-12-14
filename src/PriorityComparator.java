import java.util.Comparator;

public class PriorityComparator implements Comparator<Process> {
    @Override
    public int compare(Process process1, Process process2) {
        return Integer.compare(process1.getPriorityNumber(), process2.getPriorityNumber());
    }
}
