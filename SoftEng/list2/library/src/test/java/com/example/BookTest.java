package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest 
{
    @Test
    void testBookCreation() 
    {
        Book book = new Book("1984", "George Orwell");
        assertEquals("1984", book.getTitle());
        assertEquals("George Orwell", book.getAuthor());
    }

    @Test
    void testToString() 
    {
        Book book = new Book("1984", "George Orwell");
        assertEquals("1984 by George Orwell", book.toString());
    }
}
