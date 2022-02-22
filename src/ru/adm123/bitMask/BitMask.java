package ru.adm123.bitMask;

import sun.swing.StringUIClientPropertyKey;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author Dmitry Ushakov at 22.02.2022
 */
public class BitMask {

    public static void main(String[] args) {
        System.out.println("Вводим данные через пробел (\"1\" или \"true\" - true, иначе - false), потом жмем <Enter>");
        List<Boolean> userInput = getData();
        BitMasker bitMasker1 = new BitMasker(userInput.size());
        bitMasker1.setList(userInput);
        System.out.println("bitMask = " + bitMasker1.getMask());
        System.out.println("booleanList = " + bitMasker1.getList());
        BitMasker bitMasker2 = new BitMasker(userInput);
        System.out.println("bitMask = " + bitMasker2.getMask());
        System.out.println("booleanList = " + bitMasker2.getList());
    }

    private static List<Boolean> getData() {
        try (Scanner scanner = new Scanner(System.in)) {
            return Arrays.stream(scanner.nextLine().split("\\s"))
                    .map(BitMask::getBool)
                    .collect(Collectors.toList());
        }
    }

    private static boolean getBool(String s) {
        return s != null
                && (s.equalsIgnoreCase("1") || s.equalsIgnoreCase("true"));
    }

}
