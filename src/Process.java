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

    public String getProcessName() {
        return processName;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }
    public void setBurstTime(int burstTime) {
        this.burstTime=burstTime;
    }


    public int getPriorityNumber() {
        return priorityNumber;
    }
    public void setPriorityNumber(int priorityNumber) {
        this.priorityNumber=priorityNumber;
    }

    public String getColor() {
        return color;
    }
}
