package sample;

public class Recognision {

    public static void main(String[] args) {

        int[] dset={1,1,1,1,0,0,1,0,1,1,1,0,0,0,0,1,1,1,1};
        System.out.println("Before Union (of Sets with Elements 3 and 4)\n-------------------------------------------");
        for(int id=0;id<dset.length;id++)
            System.out.println("The root of element "+id+" is "+find(dset,id)+" (element value: "+dset[id]+")");
        union(dset,1,0); //Union disjoint sets containing elements with ids 3 and 4
        System.out.println("\nAfter Union\n-------------------------------------------");
        for(int id=0;id<dset.length;id++)
            System.out.println("The root of element "+id+" is "+find(dset,id)+" (element value: "+dset[id]+")");

    }

    //Recursive version of union find
    public static int findE(int[] a, int id) {
        if(a[id] == id) return id;
        else return findE(a,a[id]);
    }

    //Quick union of disjoint sets containing elements p and q
    public static void union(int[] a, int p, int q) {
        a[find(a,q)]=find(a,p);
    }


    //Iterative
    public static int find(int[] a, int id) {
        if (a[id] == -1) return -1;
        while(a[id] != id) {
            a[id] = a[a[id]]; //compress path
            id=a[id];
        }
        return id;
    }




}
