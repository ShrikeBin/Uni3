package com.example;

import java.util.Scanner;

public class Main 
{
    public static void main(String[] args)
    {
        DataManager library = new Library();
        CLI cli = new CLI();

        try (Scanner scanner = new Scanner(System.in))
        {
            cli.addCommand("1", new Executable() 
            {
                @Override
                public void run() 
                {
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    library.addBook(title, author);
                }
        
                @Override
                public String getDescription() 
                {
                    return "Adds a new book";
                }
            });
        
            cli.addCommand("2", new Executable() 
            {
                @Override
                public void run() 
                {
                    System.out.print("Enter book title for adding a copy: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    library.addCopy(title, author);
                }
        
                @Override
                public String getDescription() 
                {
                    return "Adds a copy of an existing book";
                }
            });
        
            cli.addCommand("3", new Executable() 
            {
                @Override
                public void run() 
                {
                    System.out.print("Enter reader name: ");
                    String readerName = scanner.nextLine();
                    library.addReader(readerName);
                }
        
                @Override
                public String getDescription() 
                {
                    return "Adds a new reader to the library";
                }
            });
        
            cli.addCommand("4", new Executable() 
            {
                @Override
                public void run() 
                {
                    System.out.print("Enter copy ID: ");
                    int copyId = scanner.nextInt();
                    System.out.print("Enter reader ID: ");
                    int readerId = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    library.lendBook(copyId, readerId);
                }
        
                @Override
                public String getDescription() 
                {
                    return "Lends a book copy to a reader";
                }
            });
        
            cli.addCommand("5", new Executable() 
            {
                @Override
                public void run() 
                {
                    System.out.print("Enter copy ID: ");
                    int copyId = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    library.returnBook(copyId);
                }
        
                @Override
                public String getDescription() 
                {
                    return "Returns a borrowed book copy";
                }
            });
        
            cli.addCommand("6", new Executable() 
            {
                @Override
                public void run() 
                {
                    library.displayLoans();
                }
        
                @Override
                public String getDescription() 
                {
                    return "Displays all active loans";
                }
            });
        
            cli.addCommand("7", new Executable() 
            {
                @Override
                public void run() 
                {
                    library.displayCopies();
                }
        
                @Override
                public String getDescription() 
                {
                    return "Displays all copies of books in the library";
                }
            });
        
            cli.addCommand("8", new Executable() 
            {
                @Override
                public void run() 
                {
                    library.displayReaders();
                }
        
                @Override
                public String getDescription() 
                {
                    return "Displays all registered readers";
                }
            });
        
            cli.addCommand("0", new Executable() 
            {
                @Override
                public void run() 
                {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
        
                @Override
                public String getDescription() 
                {
                    return "Exits the application";
                }
            });
        
            cli.run(scanner);
        }
    }
}
