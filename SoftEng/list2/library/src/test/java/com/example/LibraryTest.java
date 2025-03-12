package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest 
{

    private Library library;

    @BeforeEach
    void setUp() 
    {
        library = new Library();
    }

    @Test
    void testAddBook() 
    {
        library.addBook("1984", "George Orwell");
        assertEquals(1, library.getBooks().size());
    }

    @Test
    void testAddCopy() 
    {
        library.addBook("1984", "George Orwell");
        library.addCopy("1984", "George Orwell");
        assertEquals(1, library.getCopies().size());
    }

    @Test
    void testRemoveBook() 
    {
        library.addBook("1984", "George Orwell");
        library.rmBook("1984", "George Orwell");
        assertEquals(0, library.getBooks().size());
    }
}
