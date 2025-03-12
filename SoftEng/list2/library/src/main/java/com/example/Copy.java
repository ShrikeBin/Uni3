package com.example;

public class Copy 
{
    private static int copyNextID = 1;
    private int copyId;
    private boolean isAvailable;
    private Book book;

    public Copy(Book book) 
    {
        this.copyId = copyNextID;
        copyNextID++;
        this.book = book;
        this.isAvailable = true;
    }

    public int getCopyId() 
    {
        return copyId;
    }

    public boolean isAvailable() 
    {
        return isAvailable;
    }

    public void setAvailable(boolean input) 
    {
        isAvailable = input;
    }

    public Book getBook() 
    {
        return book;
    }

    @Override
    public String toString() 
    {
        return "Copy " + copyId + " of " + book.toString();
    }
}
