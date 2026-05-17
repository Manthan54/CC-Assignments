import static java.lang.System.*;
import java.util.*;

public class NetworkRouting{
    static int N;
    static ArrayList<Integer>[] adj;

    public static void addEdge(int v, int u){
        adj[u].add(v);
        adj[v].add(u);
    }

    // DFS Cycle Detection 
    static boolean[] visitedDFS;

    public static boolean dfsCycle(int u, int parent){
        visitedDFS[u]=true;

        for(int v:adj[u]){
            if(!visitedDFS[v]){
                if(dfsCycle(v,u))
                    return true;
            }else if(v!=parent){
                return true;
            }
        }

        return false;
    }

    // BFS RIP Routing
    static int[] distance;
    static int[] nextHop;
    static final int INF=1_000_000;

    public static void bfsRIP(List<Integer> frontier){
        if(frontier.isEmpty())
            return;

        List<Integer> nextFrontier=new ArrayList<>();

        for(int u:frontier){
            for(int v:adj[u]){
                if(distance[v]>distance[u]+1){
                    distance[v]=distance[u]+1;
                    nextHop[v]=u;
                    nextFrontier.add(v);
                }
            }
        }

        bfsRIP(nextFrontier);
    }

    public static void main(String[] args){
        Scanner sc=new Scanner(in);
        int links;

        out.print("How many routers do you want to add in network?: ");
        N=sc.nextInt();
        adj=new ArrayList[N];

        for(int i=0; i<N; i++){
            adj[i]=new ArrayList<>();
        }

        out.print("\nHow many links do you want to create between nodes?: ");
        links=sc.nextInt();

        for(int i=0; i<links; i++){
            out.print("\nEnter source node: ");
            int src=sc.nextInt();
            out.print("Enter destination node: ");
            int dest=sc.nextInt();

            addEdge(src, dest);
        }


        // 1. Cycle Detection
        visitedDFS = new boolean[N];
        boolean hasCycle = false;

        for (int i = 0; i < N; i++) {
            if (!visitedDFS[i]) {
                if (dfsCycle(i, -1)) {
                    hasCycle = true;
                    break;
                }
            }
        }

        System.out.println("Is cycle Detected: " + hasCycle);


        // 2. RIP Routing
        distance = new int[N];
        nextHop = new int[N];

        Arrays.fill(distance, INF);

        int source = 0;
        distance[source] = 0;
        nextHop[source] = source;

        bfsRIP(Collections.singletonList(source));

        // Routing Table
        System.out.println("\nRIP Routing Table (Source = " + source + ")");
        System.out.println("Destination\tNext Hop\tHop Count");

        for (int i = 0; i < N; i++) {
            System.out.println(
                i + "\t\t" +
                (distance[i] == INF ? "-" : nextHop[i]) + "\t\t" +
                (distance[i] == INF ? "∞" : distance[i])
            );
        }
    }
}