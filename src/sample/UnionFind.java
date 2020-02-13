package sample;

public class UnionFind {

    public static void main(String[] args) {


    }

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

    //Recursive version of find using the ternary (question mark) operator -
//negative value stored at root
    public static int find3(int[] a, int id) {
        return a[id] <0 ? id : find3(a,a[id]);
    }


}
