package com.example;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class CLI
{
    private ParserBlock parsers;

    // Constructor prepares parsers for common types
    public CLI()
    {
        parsers = new ParserBlock();
    }

    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter input to parse (Ctrl+D to exit):");
        try 
        {
            while (true) 
            {
                try
                {
                    System.out.print("> ");
                    String input = scanner.nextLine().trim(); // trim any spaces

                    if (input.isEmpty()) 
                    {
                        continue; // Skip empty lines
                    }

                    if (input.equalsIgnoreCase("exit")) 
                    {
                        throw new NoSuchElementException("exiting");
                    }

                    // Try to parse the input with all the parsers
                    for (Parser parser : parsers.getParsers()) 
                    {
                        if (parser.parse(input)) 
                        {
                            System.out.println("Parsed succesfully as " + parser.getType());
                        } 
                        else 
                        {
                            System.out.println("Failed to parse as " + parser.getType());
                        }
                    }
                }
                catch (IllegalArgumentException e)
                {
                    System.err.println("Invalid argument");
                } 
            }   
        } 
        catch (NoSuchElementException e) 
        {
            System.out.println("closing...");
        }
        finally 
        {
            scanner.close();
        }
    }
}
