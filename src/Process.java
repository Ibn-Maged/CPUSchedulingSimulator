public class Process {
    private String processName;
    private int arrivalTime;
    private int burstTime;
    private int priorityNumber;
    private String color;
    Process(String processName, int arrivalTime, int burstTime, int priorityNumber, String color){
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
        this.color = color;
    }
}
