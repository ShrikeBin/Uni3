package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Library implements DataManager
{
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Copy> copies = new ArrayList<>();
    private ArrayList<Reader> readers = new ArrayList<>();
    private ArrayList<Loan> loans = new ArrayList<>();

    public void addBook(final String title, final String author) 
    {
        Book book = new Book(title, author);
        books.add(book);
        System.out.println("Added book: " + book);
    }

    public void addCopy(final String title, final String author) 
    {
        for (Book book : books) 
        {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) 
            {
                copies.add(new Copy(book));
                return;
            }
        }
        System.out.println("Book not found. Add the book first.");
    }

    public void addReader(final String name) 
    {
        readers.add(new Reader(name));
    }

    public void rmBook(String title, String author) 
    {
        Iterator<Book> bookIterator = books.iterator();
        boolean removed = false;
    
        while (bookIterator.hasNext()) 
        {
            Book book = bookIterator.next();
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) 
            {
                if (hasActiveLoans(book)) 
                {
                    System.out.println("Cannot remove book: " + book + ". Active loans exist.");
                    return;
                }
    
                removeCopies(book);
                bookIterator.remove();
                System.out.println("Removed book: " + book);
                removed = true;
                break;
            }
        }
    
        if (!removed) 
        {
            System.out.println("Book not found: " + title + " by " + author);
        }
    }
    
    private boolean hasActiveLoans(Book book) 
    {
        for (Copy copy : copies) 
        {
            if (copy.getBook().equals(book) && !copy.isAvailable()) 
            {
                return true;
            }
        }
        return false;
    }
    
    private void removeCopies(Book book) 
    {
        Iterator<Copy> copyIterator = copies.iterator();
        while (copyIterator.hasNext()) 
        {
            Copy copy = copyIterator.next();
            if (copy.getBook().equals(book)) 
            {
                copyIterator.remove();
                System.out.println("Removed copy: " + copy);
            }
        }
    }    

    public void rmCopy(int copyId) 
    {
        Iterator<Copy> copyIterator = copies.iterator();
        boolean removed = false;

        while (copyIterator.hasNext()) 
        {
            Copy copy = copyIterator.next();
            if (copy.getCopyId() == copyId) 
            {
                if (copy.isAvailable()) 
                {
                    copyIterator.remove();
                    System.out.println("Removed copy: " + copy);
                    removed = true;
                } 
                else 
                {
                    System.out.println("Cannot remove copy " + copy + " as it is currently on loan.");
                    return;
                }
            }
        }

        if (!removed) 
        {
            System.out.println("Copy with ID " + copyId + " not found.");
        }
    }

    public void rmReader(int readerId) 
    {
        Iterator<Reader> readerIterator = readers.iterator();
        boolean removed = false;

        while (readerIterator.hasNext()) 
        {
            Reader reader = readerIterator.next();
            if (reader.getReaderId() == readerId) 
            {
                if (!hasActiveLoans(reader)) 
                {
                    readerIterator.remove();
                    System.out.println("Removed reader: " + reader);
                    removed = true;
                } 
                else 
                {
                    System.out.println("Cannot remove reader " + reader + " as they have active loans.");
                    return;
                }
            }
        }

        if (!removed) 
        {
            System.out.println("Reader with ID " + readerId + " not found.");
        }
    }

    private boolean hasActiveLoans(Reader reader) 
    {
        for (Loan loan : loans) 
        {
            if (loan.isActive() && loan.getReader().equals(reader)) 
            {
                return true;
            }
        }
        return false;
    }

    public void lendBook(int copyId, int readerId)
    {
        Copy copy = findCopyById(copyId);
        Reader reader = findReaderById(readerId);

        if (copy != null && copy.isAvailable() && reader != null) 
        {
            Loan loan = new Loan(copy, reader);
            loans.add(loan);
            System.out.println("Loan successful: " + loan);
        } 
        else 
        {
            System.out.println("Loan failed. Copy might not be available or Reader not found.");
        }
    }

    public void returnBook(int copyId)
    {
        for (Loan loan : loans) 
        {
            if (loan.isActive() && loan.getCopy().getCopyId() == copyId) 
            {
                loan.returnCopy();
                loan.getCopy().setAvailable(true);
                System.out.println("Return successful: " + loan);
                return;
            }
        }
        System.out.println("Return failed, no such copy has ben lend");
    }

    private Copy findCopyById(int copyId)
    {
        for (Copy copy : copies) 
        {
            if (copy.getCopyId() == copyId) 
            {
                return copy;
            }
        }
        return null;
    }

    private Reader findReaderById(int readerId)
    {
        for (Reader reader : readers) 
        {
            if (reader.getReaderId() == readerId) 
            {
                return reader;
            }
        }
        return null;
    }

    public void displayLoans() 
    {
        System.out.println("Loans: \n");
        for (Loan loan : loans) 
        {
            System.out.println(loan);
        }
    }

    public void displayCopies() 
    {
        System.out.println("Copies in library:");
        for (Copy copy : copies) 
        {
            System.out.println(copy);
        }
    }

    public void displayReaders() 
    {
        System.out.println("Readers registered in library:");
        for (Reader reader : readers) 
        {
            System.out.println(reader);
        }
    }

    public List<Book> getBooks() 
    {
        return books;
    }

    public List<Copy> getCopies() 
    {
        return copies;
    }

    public List<Loan> getLoans() 
    {
        return loans;
    }
}