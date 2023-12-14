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
        for(Process process: processes){
//            process.setAGFactor(calcAGFactor(process));
            process.setQuantum(quantum);
        }
    }
    @Override
    public void simulate() {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(new AGComparator());
        ArrayDeque<Process> orderQueue = new ArrayDeque<>();
        ArrayList<Process> dieList = new ArrayList<>();

        for(Process process: processes){
            readyQueue.add(process);
        }
        boolean finishedQuantum=false;
        while(!readyQueue.isEmpty()){
            if(getNextProcess(readyQueue) == null){
                time++;
            } else {
                Process currentProcess;
                if(finishedQuantum){
                    if(!orderQueue.isEmpty()){
                        currentProcess = orderQueue.getFirst();
                        orderQueue.remove(currentProcess);
                        readyQueue.remove(currentProcess);
                    }else{
                        currentProcess = getNextProcess(readyQueue);
                        readyQueue.remove(currentProcess);
                        orderQueue.remove(currentProcess);
                    }
                }else{
                    currentProcess = getNextProcess(readyQueue);
                    readyQueue.remove(currentProcess);
                    orderQueue.remove(currentProcess);
                }
                int currentQuantum;
                System.out.print(currentProcess.getProcessName() + " Started from " + time + " to ");
                if(currentProcess.getBurstTime() - ceil(currentProcess.getQuantum() / 2) >= 0){
                    currentProcess.setBurstTime((int) (currentProcess.getBurstTime() - ceil(currentProcess.getQuantum() / 2)));
                    time += ceil(currentProcess.getQuantum() / 2);
                    currentQuantum=(int)(currentProcess.getQuantum()-ceil(currentProcess.getQuantum() / 2));
                } else {
                    currentQuantum = 0;
                    time += currentProcess.getBurstTime();
                    currentProcess.setBurstTime(0);
                }
                while(currentQuantum>0&&currentProcess.getBurstTime()>0){
                    if(getNextProcess(readyQueue) != null){
                        Process smallest= getNextProcess(readyQueue);

                        if(smallest.getAGFactor()<currentProcess.getAGFactor()){
                            //print
                            System.out.println(time);
                            currentProcess.setQuantum(currentProcess.getQuantum()+currentQuantum);
                            readyQueue.add(currentProcess);
                            orderQueue.add(currentProcess);
                            finishedQuantum=false;
                            break;
                        }
                    }
                    currentQuantum--;
                    time++;
                    currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);
                }
                if(currentQuantum<=0 || currentProcess.getBurstTime() == 0){
                    finishedQuantum=true;
                    System.out.println(time);
                    if(currentProcess.getBurstTime()>0){
                        currentProcess.setQuantum(currentProcess.getQuantum()+ ceil(calcMeanQuantum(readyQueue)));
                        readyQueue.add(currentProcess);
                        orderQueue.add(currentProcess);
                    }else{
                        System.out.println(currentProcess.getProcessName()+" finished");
                        currentProcess.setQuantum(0);
                        dieList.add(currentProcess);
                    }
                }

            }
        }
    }

    private double calcMeanQuantum(PriorityQueue<Process> processes){
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
}
