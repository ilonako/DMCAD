package com.kozak.thirdLab;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Stonks {

    final static int T = 7;
    final static int N = 6;
    final static String FILE_NAME = "/Users/ilona/IdeaProjects/DMCAD/src/com/kozak/thirdLab/lab3";

    public static void main(String[] args) {

        Potik[] tred = new Potik[T];
        Kpp[] Sr = new Kpp[N];
        int maxTer;

        try (FileReader reader = new FileReader(FILE_NAME)) {

            int i = 0;
            int numb = 0;
            int c;
            int numbOut = 0, numbIn = 0;
            while ((c = reader.read()) != -1) {

                if ((char) c == '\n') {
                    tred[i] = new Potik(numbOut, numbIn, numb);
                    numb = 0;
                    i++;
                } else if (c >= 65) {
                    numbOut = Transformer.upperLetterToNum((char) c);
                    if (numbOut == 19) {
                        numbOut = N - 2;
                    } else if (numbOut == 18) {
                        numbOut = N - 1;
                    }
                    while ((c = reader.read()) < 65) ;
                    numbIn = Transformer.upperLetterToNum((char) c);
                    if (numbIn == 19) {
                        numbIn = N - 2;
                    } else if (numbIn == 18) {
                        numbIn = N - 1;
                    }
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

        for (int i = 0; i < N; i++) {
            Sr[i] = new Kpp();
            Sr[i].name = i;
            Sr[i].input = 0;
            Sr[i].output = 0;
        }

        for (int i = 0; i < N; i++)
            for (int j = 0; j < T; j++) {
                if (Sr[i].name == tred[j].a)
                    Sr[i].output += tred[j].c;
                if (Sr[i].name == tred[j].b)
                    Sr[i].input += tred[j].c;
            }

        Sr[N - 2].input = Sr[N - 2].output;
        Sr[N - 1].output = Sr[N - 1].input;

        for (int i = 0; i < N; i++)
            Sr[i].prop = Math.max(Sr[i].input, Sr[i].output);
        maxTer = Math.max(Sr[N - 1].prop, Sr[N - 2].prop);

        for (int j = 0; j < T; j++)
            if (tred[j].b == N - 1)
                Sr[N - 1].prop -= Math.min(tred[j].c, Sr[tred[j].a].prop);

        for (int i = N - 3; i >= 0; i--)
            for (int j = 0; j < T; j++)
                if (tred[j].b == i)
                    Sr[i].prop -= Math.min(tred[j].c, Sr[tred[j].a].prop);

        System.out.println("Thread: " + maxTer);

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

    private static class Kpp {

        int name;
        int input;
        int output;
        int prop;

    }

    private static class Transformer {

        public static int upperLetterToNum(char s) {
            return (int) s - 65;
        }

    }
}
