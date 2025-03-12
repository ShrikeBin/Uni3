package com.example;

public interface DataManager
{
    public abstract void addBook(String title, String author);
    public abstract void addCopy(String title, String author);
    public abstract void addReader(String name);

    public abstract void rmBook(String title, String author);
    public abstract void rmCopy(int copyId);
    public abstract void rmReader(int readerId);

    public abstract void lendBook(int copyId, int readerId);
    public abstract void returnBook(int copyId);

    public abstract void displayLoans(); 
    public abstract void displayCopies(); 
    public abstract void displayReaders(); 
}
