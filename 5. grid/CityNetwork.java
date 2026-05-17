import java.util.*;

class Road{
    int u,v,cost;
    Road(int u,int v,int c){
        this.u=u; this.v=v; cost=c;
    }
}

class UnionFind{
    int parent[],rank[];

    UnionFind(int n){
        parent=new int[n];
        rank=new int[n];
        for(int i=0;i<n;i++) parent[i]=i;
    }

    int find(int x){
        if(parent[x]!=x)
            parent[x]=find(parent[x]);
        return parent[x];
    }

    boolean connect(int a,int b){
        int pa=find(a), pb=find(b);
        if(pa==pb) return false;

        if(rank[pa]<rank[pb]) parent[pa]=pb;
        else if(rank[pb]<rank[pa]) parent[pb]=pa;
        else{ parent[pb]=pa; rank[pa]++; }

        return true;
    }
}

public class CityNetwork{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);

        System.out.println("=== City Road Network Planner ===");

        System.out.print("Number of cities: ");
        int n=sc.nextInt();

        System.out.print("Number of possible roads: ");
        int m=sc.nextInt();

        List<Road> roads=new ArrayList<>();

        for(int i=0;i<m;i++){
            System.out.print("Road (city1 city2 cost): ");
            roads.add(new Road(sc.nextInt(),sc.nextInt(),sc.nextInt()));
        }

        roads.sort(Comparator.comparingInt(r->r.cost));
        UnionFind uf=new UnionFind(n);

        int total=0;
        System.out.println("\nRoads selected for minimum cost network:");

        for(Road r:roads){
            if(uf.connect(r.u,r.v)){
                System.out.println(r.u+" <--> "+r.v+"  cost="+r.cost);
                total+=r.cost;
            }
        }

        System.out.println("Total construction cost = "+total);
    }
}