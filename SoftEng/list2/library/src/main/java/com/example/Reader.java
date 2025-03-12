package com.example;

public class Reader 
{
    private static int readerNextID = 1;
    private String name;
    private int readerId;

    public Reader(String name) 
    {
        this.name = name;
        this.readerId = readerNextID;
        readerNextID++;
    }

    public String getName() 
    {
        return name;
    }

    public int getReaderId() 
    {
        return readerId;
    }

    @Override
    public String toString() 
    {
        return "Reader{" +
                "name='" + name + '\'' +
                ", readerId=" + readerId +
                '}';
    }
}
