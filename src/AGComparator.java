import java.util.Comparator;

public class AGComparator implements Comparator<Process> {
    @Override
    public int compare(Process process1, Process process2) {
        return Double.compare(process1.getAGFactor(), process2.getAGFactor());
    }
}
