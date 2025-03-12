package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CopyTest 
{

    @Test
    void testCopyCreation() 
    {
        Book book = new Book("1984", "George Orwell");
        Copy copy = new Copy(book);
        assertTrue(copy.isAvailable());
    }

    @Test
    void testSetAvailable() 
    {
        Book book = new Book("1984", "George Orwell");
        Copy copy = new Copy(book);
        copy.setAvailable(false);
        assertFalse(copy.isAvailable());
    }
}
