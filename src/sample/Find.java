package sample;

public class Find {

    public static void main(String[] args) {


        int[] dset = {10, 11, 11, 2, 8, 13, 2, 7, 8, 9, 11, 11, 7, 8};

        for (int id = 0; id < dset.length; id++)
            System.out.println("The root of " + id + " is " +
                    Find(dset, id));

    }

    //Recursive version of union find
    public static int Find(int[] a, int id) {
        if(a[id] == id) return id;
        else return Find(a,a[id]);
    }







/*
    public void quickUnion() {

        int[] dset = {10, 11, 11, 2, 8, 13, 2, 7, 8, 9, 11, 11, 7, 8};
        System.out.println("Before Union (of Sets with Elements 3 and 4)\n-------------------------------------------");
        for (int id = 0; id < dset.length; id++)
            System.out.println("The root of element " + id + " is " + find3(dset, id) + " (element value: " + dset[id] + ")");
        union(dset, 3, 4); //Union disjoint sets containing elements with ids 3 and 4
        System.out.println("\nAfter Union\n-------------------------------------------");
        for (int id = 0; id < dset.length; id++)
            System.out.println("The root of element " + id + " is " + find3(dset, id) + " (element value: " + dset[id] + ")");
    }

    //Quick union of disjoint sets containing elements p and q (Version 2)
    public static void union(DisjointSetNode<?> p, DisjointSetNode<?> q) {
        find3(q).parent=find3(p); //The root of q is made reference the root of p
    }



    //Iterative version of find with path compression
    public static int find(int[] a, int id) {
        while(a[id]!=id) {
            a[id]=a[a[id]]; //Compress path
            id=a[id];
        }
        return id;
    }

*/
}
