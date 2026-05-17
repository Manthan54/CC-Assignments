import java.util.*;

class Task {
    int id, arrival, burst, remaining, completion;

    Task(int id,int a,int b){
        this.id=id;
        arrival=a;
        burst=b;
        remaining=b;
    }
}

public class TaskManager {

    static void runScheduler(List<Task> tasks){
        PriorityQueue<Task> pq=
                new PriorityQueue<>(Comparator.comparingInt(t->t.remaining));

        tasks.sort(Comparator.comparingInt(t->t.arrival));

        int time=0,i=0,done=0;

        while(done<tasks.size()){

            while(i<tasks.size() && tasks.get(i).arrival<=time){
                pq.add(tasks.get(i));
                i++;
            }

            if(pq.isEmpty()){
                time++;
                continue;
            }

            Task cur=pq.poll();
            cur.remaining--;
            time++;

            if(cur.remaining==0){
                cur.completion=time;
                done++;
            }else pq.add(cur);
        }
    }

    static void report(List<Task> list){
        System.out.println("\nExecution Report:");
        System.out.println("Task  Arrival  Burst  Finish  Wait");
        for(Task t:list){
            int tat=t.completion-t.arrival;
            int wait=tat-t.burst;
            System.out.println(t.id+"     "+t.arrival+"       "+t.burst+
                    "      "+t.completion+"     "+wait);
        }
    }

    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);

        System.out.println("=== Task Manager CPU Scheduler ===");
        System.out.print("Enter number of tasks: ");
        int n=sc.nextInt();

        List<Task> tasks=new ArrayList<>();

        for(int i=1;i<=n;i++){
            System.out.print("Task "+i+" arrival time: ");
            int at=sc.nextInt();
            System.out.print("Task "+i+" burst time: ");
            int bt=sc.nextInt();
            tasks.add(new Task(i,at,bt));
        }

        runScheduler(tasks);
        report(tasks);
    }
}