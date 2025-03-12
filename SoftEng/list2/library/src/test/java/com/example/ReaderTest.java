package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest 
{
    @Test
    void testReaderCreation() 
    {
        Reader reader = new Reader("John Doe");
        assertEquals("John Doe", reader.getName());
    }
}
