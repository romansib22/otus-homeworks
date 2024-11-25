package ru.romansib.otus.service;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ScannerServiceImpl implements ScannerService {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getString(String message) {
        System.out.println(message + ": ");
        return scanner.nextLine();
    }

    @Override
    public int getInt(String message) {
        System.out.println(message + ": ");
        return scanner.nextInt();
    }


}
