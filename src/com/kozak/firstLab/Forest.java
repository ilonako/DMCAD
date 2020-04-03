package com.kozak.firstLab;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.TreeSet;

public class Forest {

    final static int N = 6;
    final static String FILE_NAME = "/Users/ilona/IdeaProjects/DMCAD/src/com/kozak/firstLab/lab1";

    public static void main(String[] args) {

        Graph global = new Graph();
        Graph forest = new Graph();

        int kordi = 0;
        int kordj = 0;
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
                    kordi = Transformer.upperLetterToNum((char) c);
                    while ((c = reader.read()) < 65) ;
                    jj = (char) c;
                    kordj = Transformer.upperLetterToNum((char) c);
                } else if (Character.getNumericValue(c) >= 0 && Character.getNumericValue(c) <= 9) {
                    num *= 10;
                    num += Character.getNumericValue(c);
                }
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

        forest.becomeForest(global);
        for (Edge e : forest.T) {
            System.out.println(e.u.name + "-" + e.v.name + " " + e.weight);
        }
    }


    private static class Graph {
        TreeSet<Edge> T = new TreeSet<>();
        TreeSet<Leaf> L = new TreeSet<>();

        public void addEdge(Edge e) {
            T.add(e);
            L.add(e.u);
            L.add(e.v);
        }

        public void becomeForest(Graph G) {
            this.T.add(G.T.first());
            this.L.add(G.T.first().u);
            this.L.add(G.T.first().v);
            LinkedList<LocalTree> S = new LinkedList<>();

            while (T.size() < N - 1) {
                for (Edge e : G.T) {
                    if (S.size() > 0) {
                        for (LocalTree b : S) {
                            if (b.L.contains(e.u) && this.L.contains(e.v) ||
                                    this.L.contains(e.u) && b.L.contains(e.v)) {
                                this.T.addAll(b.T);
                                this.L.addAll(b.L);
                                this.T.add(e);
                                this.L.add(e.u);
                                this.L.add(e.v);
                                S.remove(b);
                            }
                        }
                    }
                    if (L.contains(e.u) != L.contains(e.v)) {
                        this.T.add(e);
                        this.L.add(e.u);
                        this.L.add(e.v);
                    } else if (L.contains(e.u) && L.contains(e.v)) {
                        continue;
                    } else if (S.size() > 0) {
                        for (LocalTree b : S) {
                            if (b.L.contains(e.u) != b.L.contains(e.v)) {
                                b.T.add(e);
                                b.L.add(e.u);
                                b.L.add(e.v);
                                break;
                            } else if (L.contains(e.u) && L.contains(e.v)) {
                                continue;
                            }
                        }
                    } else {
                        S.add(new LocalTree(e));
                    }
                }
            }
        }
    }

    private static class LocalTree {
        TreeSet<Edge> T = new TreeSet<>();
        TreeSet<Leaf> L = new TreeSet<>();

        LocalTree(Edge e) {
            T.add(e);
            L.add(e.u);
            L.add(e.v);
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

        public static char numToUpperLetter(int num) {
            return (char) (num + 65);
        }

        public static int upperLetterToNum(char s) {
            return (int) s - 65;
        }
    }
}
