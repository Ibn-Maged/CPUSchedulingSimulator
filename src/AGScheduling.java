import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

import static java.lang.Math.ceil;

public class AGScheduling implements SchedulingAlgorithm{
    ArrayList<Process> processes;
    int time = 0;
    AGScheduling(ArrayList<Process> processes, int quantum){
        this.processes = processes;
        System.out.println("AG Factors: ");
        for(Process process: processes){
            process.setAGFactor(calcAGFactor(process));
            process.setQuantum(quantum);
            System.out.println(process.getProcessName() + ": " + process.getAGFactor());
        }
    }
    @Override
    public void simulate() {
//        PriorityQueue<Process> readyQueue = new PriorityQueue<>(new AGComparator());
//        ArrayDeque<Process> orderQueue = new ArrayDeque<>();
//        ArrayList<Process> dieList = new ArrayList<>();
        double totalTurnaroundTime = 0;
        ArrayDeque<Process> readyQueue = new ArrayDeque<>();
        PriorityQueue<Process> sortingQueue = new PriorityQueue<>(new AGComparator());
        ArrayList<Process> dieList = new ArrayList<>();

        for(Process process: processes){
            if(process.getArrivalTime() <= time){
                sortingQueue.add(process);
            }
        }
        while(!sortingQueue.isEmpty()){
            Process temp = sortingQueue.poll();
            readyQueue.add(temp);
            processes.remove(temp);
        }
        Process currentProcess = readyQueue.poll();
        while(!readyQueue.isEmpty() || !processes.isEmpty() || currentProcess != null){
                int currentQuantum;
                System.out.print(currentProcess.getProcessName() + " Started from " + time + " to ");
                if(currentProcess.getBurstTime() - ceil(currentProcess.getQuantum() / 2) >= 0){
                    currentProcess.setBurstTime((int) (currentProcess.getBurstTime() - ceil(currentProcess.getQuantum() / 2)));
                    time += ceil(currentProcess.getQuantum() / 2);
                    currentQuantum=(int)(currentProcess.getQuantum()-ceil(currentProcess.getQuantum() / 2));
                    increaseWaitingTime(readyQueue, (int) ceil(currentProcess.getQuantum() / 2));
                } else {
                    currentQuantum = 0;
                    time += currentProcess.getBurstTime();
                    increaseWaitingTime(readyQueue, currentProcess.getBurstTime());
                    currentProcess.setBurstTime(0);
                }
                while(currentQuantum > 0 && currentProcess.getBurstTime() > 0){
                    for(Process process: processes) {
                        if(process.getArrivalTime() <= time) {
                            sortingQueue.add(process);
                            process.setWaitingTime(time - process.getArrivalTime());
                        }
                    }
                    for(Process process: sortingQueue) {
                        processes.remove(process);
                    }
                    Process min = sortingQueue.poll();
                    if(min == null){
                        min = getMin(readyQueue);
                    } else {
                        if(!readyQueue.isEmpty()){
                            if(min.getAGFactor() > getMin(readyQueue).getAGFactor()){
                                readyQueue.add(min);
                                min = getMin(readyQueue);
                            }
                        }
                    }
                    while(!sortingQueue.isEmpty()) {
                        readyQueue.add(sortingQueue.poll());
                    }
                    if(min != null){
                        if(min.getAGFactor() < currentProcess.getAGFactor()){
                            System.out.println(time);
                            currentProcess.setQuantum(currentProcess.getQuantum() + currentQuantum);
                            readyQueue.add(currentProcess);
                            currentProcess = min;
                            readyQueue.remove(min);
                            break;
                        } else {
                            if(!readyQueue.contains(min)){
                                readyQueue.add(min);
                            }
                        }
                    }
                    currentQuantum--;
                    time++;
                    currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);
                    increaseWaitingTime(readyQueue, 1);
                }
                if(currentQuantum == 0 || currentProcess.getBurstTime() == 0){
                    System.out.println(time);
                    if(currentProcess.getBurstTime() > 0){
                        currentProcess.setQuantum(currentProcess.getQuantum()+ ceil(calcMeanQuantum(readyQueue)));
                        readyQueue.add(currentProcess);
                        currentProcess = readyQueue.poll();
                    }else{
                        System.out.println(currentProcess.getProcessName() + " finished");
                        System.out.println("Waiting Time: " + currentProcess.getWaitingTime());
                        System.out.println("Turnaround Time: " + (time - currentProcess.getArrivalTime()));
                        totalTurnaroundTime += (time - currentProcess.getArrivalTime());
                        currentProcess.setQuantum(0);
                        dieList.add(currentProcess);
                        currentProcess = readyQueue.poll();
                    }
                }
        }
        double totalWaitingTime = 0;
        for(Process process: dieList){
            totalWaitingTime += process.getWaitingTime();
        }
        System.out.println("Average Waiting Time: " + totalWaitingTime / dieList.size());
        System.out.println("Average Turnaround Time: " + totalTurnaroundTime / dieList.size());
    }

    private double calcMeanQuantum(ArrayDeque<Process> processes){
        double result=0;
        int count =0;
        for(Process p:processes){
            if(p.getArrivalTime()<=time){
                result+=p.getQuantum();
                count++;
            }
        }
        if(count ==0){
            return 0;
        }else{
            return result/count * 0.1;
        }
    }
    public int calcAGFactor(Process process){
        Random rand = new Random();
        int randInt = rand.nextInt(21);
        if(randInt < 10) {
            return process.getBurstTime() + process.getArrivalTime() + randInt;
        } else if(randInt > 10){
            return process.getBurstTime() + process.getArrivalTime() + 10;
        } else {
            return process.getBurstTime() + process.getArrivalTime() + process.getPriorityNumber();
        }
    }

    private Process getNextProcess(PriorityQueue<Process> processes){
        PriorityQueue<Process> temp= new PriorityQueue<>(new AGComparator());
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

    Process getMin(ArrayDeque<Process> dq){
        if(dq.size() == 0){
            return null;
        }
        Process min = dq.getFirst();
        for(Process process: dq){
            if(process.getAGFactor() < min.getAGFactor()){
                min = process;
            }
        }
        return min;
    }

    void increaseWaitingTime(ArrayDeque<Process> dq, int time){
        for(Process process: dq){
            process.setWaitingTime(process.getWaitingTime() + time);
        }
    }
}
