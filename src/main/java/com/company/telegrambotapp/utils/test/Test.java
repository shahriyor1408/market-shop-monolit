package com.company.telegrambotapp.utils.test;

import java.util.Scanner;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 12/12/22 Monday 11:06
 * telegram-bot-app/IntelliJ IDEA
 */
public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Text: ");
        String str = scanner.nextLine();

        System.out.print("Key: ");
        String key = scanner.nextLine();
        String encrypt = AES.encrypt(str, key);
        System.out.println("encrypt = " + encrypt);
    }
}
