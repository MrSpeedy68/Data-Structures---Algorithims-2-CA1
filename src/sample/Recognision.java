package sample;

public class Recognision {

    public static void main(String[] args) {

        int[] dset= new int[128*128];
        for(int i =0; i < 16384;i ++) {
            dset[i] = 1;
        }
        System.out.println("Before Union (of Sets with Elements 3 and 4)\n-------------------------------------------");
        for(int id=0;id<dset.length;id++)
            System.out.println("The root of element "+id+" is "+find(dset,id)+" (element value: "+dset[id]+")");
        union(dset,3,4); //Union disjoint sets containing elements with ids 3 and 4
        System.out.println("\nAfter Union\n-------------------------------------------");
        for(int id=0;id<dset.length;id++)
            System.out.println("The root of element "+id+" is "+find(dset,id)+" (element value: "+dset[id]+")");

    }

    //Recursive version of union find
    public static int find(int[] a, int id) {
        if(a[id] == id) return id;
        else return find(a,a[id]);
    }

    //Quick union of disjoint sets containing elements p and q
    public static void union(int[] a, int p, int q) {
        a[find(a,q)]=find(a,p);
    }





}
