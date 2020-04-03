package com.kozak.fourthLab;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ComivoyagerQuest {

    final static int CVD = 10;
    final static int CVV = 5;
    final static String FILE_NAME = "/Users/ilona/IdeaProjects/DMCAD/src/com/kozak/fourthLab/lab4";

    public static void main(String[] args) {

        boolean[] city = new boolean[CVV];
        Potik[] umGraf = new Potik[CVD];

        try (FileReader reader = new FileReader(FILE_NAME)) {
            int i = 0;
            int numb = 0;
            int c;
            int numbOut = 0, numbIn = 0;
            while ((c = reader.read()) != -1) {

                if ((char) c == '\n') {
                    umGraf[i] = new Potik(numbOut, numbIn, numb);
                    numb = 0;
                    i++;
                } else if (c >= 65) {
                    numbOut = Transformer.upperLetterToNum((char) c);
                    while ((c = reader.read()) < 65) ;
                    numbIn = Transformer.upperLetterToNum((char) c);
                } else if (Character.getNumericValue(c) >= 0 && Character.getNumericValue(c) <= 9) {
                    numb *= 10;
                    numb += Character.getNumericValue(c);
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

        city[Transformer.upperLetterToNum(firstDot.charAt(0))] = true;
        CVWalk(city, umGraf, Transformer.upperLetterToNum(firstDot.charAt(0)), Transformer.upperLetterToNum(firstDot.charAt(0)));

    }


    public static void CVWalk(boolean[] city, Potik[] umGraf, int kordGPS, int kordSTR) {

        while (!CheckVector.isFullWay(city)) {
            int min = 6428;
            int nD = 0;
            for (int i = 0; i < CVD; i++)
                if ((umGraf[i].a == kordGPS || umGraf[i].b == kordGPS) && (city[umGraf[i].b] == false || city[umGraf[i].a] == false))
                    if (umGraf[i].c < min) {
                        min = umGraf[i].c;
                        if (umGraf[i].a == kordGPS)
                            nD = umGraf[i].b;
                        else
                            nD = umGraf[i].a;
                    }

            city[nD] = true;
            CVWalk(city, umGraf, nD, kordSTR);
            if (CheckVector.isFullWay(city)) {
                System.out.println(Transformer.numToUpperLetter(nD) + "-" + Transformer.numToUpperLetter(kordGPS));
                return;
            } else {
                city[nD] = false;
                umGraf[nD].c = 6248;
                return;
            }
        }
        System.out.println(Transformer.numToUpperLetter(kordSTR) + "-" + Transformer.numToUpperLetter(kordGPS));
        return;

    }


    private static class Potik {

        int a;
        int b;
        int c;

        Potik(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

    }

    private static class CheckVector {

        public static boolean isFullWay(boolean[] r) {
            for (int i = 0; i < r.length; i++) {
                if (!r[i]) {
                    return false;
                }
            }
            return true;
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
