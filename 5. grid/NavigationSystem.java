import java.util.*;

class Nodes{
    int city,dist;
    Nodes(int c,int d){
        city=c;
        dist=d;
    }
}

public class NavigationSystem{

    static void shortestRoute(List<List<Nodes>> graph,int src){
        int n=graph.size();
        int dist[]=new int[n];
        Arrays.fill(dist,Integer.MAX_VALUE);

        PriorityQueue<Nodes> pq=
                new PriorityQueue<>(Comparator.comparingInt(n1->n1.dist));

        dist[src]=0;
        pq.add(new Nodes(src,0));

        while(!pq.isEmpty()){
            Nodes cur=pq.poll();

            if(cur.dist>dist[cur.city]) continue;

            for(Nodes nb:graph.get(cur.city)){
                int nd=cur.dist+nb.dist;
                if(nd<dist[nb.city]){
                    dist[nb.city]=nd;
                    pq.add(new Nodes(nb.city,nd));
                }
            }
        }

        System.out.println("\nShortest distances from city "+src+":");
        for(int i=0;i<n;i++)
            System.out.println("to city "+i+" = "+dist[i]);
    }

    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);

        System.out.println("=== Smart Navigation System ===");

        System.out.print("Enter number of cities: ");
        int n=sc.nextInt();

        System.out.print("Enter number of roads: ");
        int m=sc.nextInt();

        List<List<Nodes>> graph=new ArrayList<>();
        for(int i=0;i<n;i++) graph.add(new ArrayList<>());

        for(int i=0;i<m;i++){
            System.out.print("Road (from to distance): ");
            int u=sc.nextInt();
            int v=sc.nextInt();
            int w=sc.nextInt();

            graph.get(u).add(new Nodes(v,w));
            graph.get(v).add(new Nodes(u,w));
        }

        System.out.print("Enter starting city: ");
        int src=sc.nextInt();

        shortestRoute(graph,src);
    }
}