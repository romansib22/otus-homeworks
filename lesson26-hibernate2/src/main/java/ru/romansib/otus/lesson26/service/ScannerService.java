package ru.romansib.otus.lesson26.service;

import java.util.Scanner;

public class ScannerService {
    private final Scanner scanner = new Scanner(System.in);

    public String getString(String message) {
        System.out.println(message + ": ");
        return scanner.nextLine();
    }

    public int getInt(String message) {
        System.out.println(message + ": ");
        return scanner.nextInt();
    }
}
