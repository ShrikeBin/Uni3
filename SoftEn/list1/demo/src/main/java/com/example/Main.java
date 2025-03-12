package com.example;

import java.util.Scanner;

public class Main 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to parse? (y/n) ");
        String typeInput = scanner.nextLine().trim();

        switch (typeInput.toLowerCase())
        {
            case "y":
            case "yes":
            {
                CLI console = new CLI();
                console.run();
                break;
            }
            default:
                System.out.println("That's a no, closing....");
                break;
        }
        scanner.close(); 
    }
}