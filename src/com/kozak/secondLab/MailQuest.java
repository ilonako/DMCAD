package com.kozak.secondLab;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MailQuest {

    final static int N = 6;
    final static String FILE_NAME = "/Users/ilona/IdeaProjects/DMCAD/src/com/kozak/secondLab/lab2";


    public static void main(String[] args) {

        boolean[] gasse = new boolean[N];
        Eji[] wvGraf = new Eji[N];

        try (FileReader reader = new FileReader(FILE_NAME)) {
            int c;
            int numbOut = 0, numbIn = 0, i = 0;
            while ((c = reader.read()) != -1) {

                if ((char) c == '\n') {
                    wvGraf[i] = new Eji(numbOut, numbIn);
                    i++;
                } else if (c >= 65) {
                    numbOut = Transformer.upperLetterToNum((char) c);
                    while ((c = reader.read()) < 65) ;
                    numbIn = Transformer.upperLetterToNum((char) c);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner in = new Scanner(System.in);
        System.out.print("Input place to start: ");
        String firstDot = in.next();

        if (!Walk(gasse, wvGraf, Transformer.upperLetterToNum(firstDot.charAt(0))))
            System.out.println("no way");

    }

    public static boolean fullWay(boolean[] gasse) {

        for (int i = 0; i < gasse.length; i++) {
            if (!gasse[i]) {
                return false;
            }
        }
        return true;

    }

    public static boolean Walk(boolean[] gasse, Eji[] wvGraf, int kordGPS) {

        if (fullWay(gasse))
            return true;
        else {
            for (int i = 0; i < N; i++)
                if (gasse[i] == false && (wvGraf[i].A == kordGPS || wvGraf[i].B == kordGPS)) {
                    gasse[i] = true;
                    if (wvGraf[i].A == kordGPS) {
                        Walk(gasse, wvGraf, wvGraf[i].B);
                        if (fullWay(gasse)) {
                            System.out.println(Transformer.numToUpperLetter(wvGraf[i].B) + "-" + Transformer.numToUpperLetter(wvGraf[i].A));
                            return true;
                        } else
                            gasse[i] = false;
                    } else {
                        Walk(gasse, wvGraf, wvGraf[i].A);
                        if (fullWay(gasse)) {
                            System.out.println(Transformer.numToUpperLetter(wvGraf[i].A) + "-" + Transformer.numToUpperLetter(wvGraf[i].B));
                            return true;
                        } else
                            gasse[i] = false;
                    }
                }
            return false;
        }

    }

    private static class Eji {

        int A;
        int B;

        public Eji(int a, int b) {
            A = a;
            B = b;
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
