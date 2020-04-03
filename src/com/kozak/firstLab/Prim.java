package com.kozak.firstLab;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class Prim {

    final static int N = 6;
    final static String FILE_NAME = "/Users/ilona/IdeaProjects/DMCAD/src/com/kozak/firstLab/lab1";


    public static void main(String[] args) {

        Graph global = new Graph();
        Graph resultTree = new Graph();

        char ii = ' ', jj = ' ';
        for (int i = 0; i < N; i++) {
            global.L.add(new Leaf(Transformer.numToUpperLetter(i)));
        }

        try (FileReader reader = new FileReader(FILE_NAME)) {
            int c;
            int num = 0;
            while ((c = reader.read()) != -1) {
                if ((char) c == '\n') {
                    global.addEdge(new Edge(num, new Leaf(ii), new Leaf(jj)));
                    num = 0;
                    continue;
                }
                if (c >= 65) {
                    ii = (char) c;
                    while ((c = reader.read()) < 65) ;
                    jj = (char) c;
                } else if (Character.getNumericValue(c) >= 0 && Character.getNumericValue(c) <= 9) {
                    num *= 10;
                    num += Character.getNumericValue(c);
                }
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

        Scanner in = new Scanner(System.in);
        System.out.print("Input place to start: ");
        String firstDot = in.next();

        resultTree.L.add(new Leaf(firstDot.charAt(0)));
        resultTree.becomeTree(global);
    }

    private static class Graph {

        TreeSet<Edge> T = new TreeSet<>();
        TreeSet<Leaf> L = new TreeSet<>();

        void addEdge(Edge e) {
            T.add(e);
            L.add(e.u);
            L.add(e.v);
        }

        void becomeTree(Graph G) {

            while (T.size() < N - 1) {
                for (Edge e : G.T) {
                    if (L.contains(e.u) != L.contains(e.v)) {
                        this.addEdge(e);
                        System.out.println(e.v.name + "-" + e.u.name + " " + e.weight);
                        break;
                    } else if (L.contains(e.u) && L.contains(e.v)) {
                        G.T.remove(e);
                        break;
                    } else
                        continue;
                }
            }

        }
    }

    private static class Edge implements Comparable<Edge> {

        int weight;
        Leaf v;
        Leaf u;

        Edge(int weight, Leaf v, Leaf u) {
            this.weight = weight;
            this.v = v;
            this.u = u;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }

    }

    public static class Leaf implements Comparable<Leaf> {

        private char name;

        Leaf(char da) {
            name = da;
        }

        @Override
        public int compareTo(Leaf o) {
            return this.name - o.name;
        }

    }

    private static class Transformer {

        static char numToUpperLetter(int num) {
            return (char) (num + 65);
        }

    }
}
