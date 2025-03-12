package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest 
{

    @Test
    void testLoanCreation() 
    {
        Book book = new Book("1984", "George Orwell");
        Copy copy = new Copy(book);
        Reader reader = new Reader("John Doe");
        Loan loan = new Loan(copy, reader);

        assertTrue(loan.isActive());
        assertEquals(copy, loan.getCopy());
        assertEquals(reader, loan.getReader());
    }

    @Test
    void testReturnCopy() 
    {
        Book book = new Book("1984", "George Orwell");
        Copy copy = new Copy(book);
        Reader reader = new Reader("John Doe");
        Loan loan = new Loan(copy, reader);
        loan.returnCopy();

        assertFalse(loan.isActive());
    }

    @Test
    void testToString() 
    {
        Book book = new Book("1984", "George Orwell");
        Copy copy = new Copy(book);
        Reader reader = new Reader("John Doe");
        Loan loan = new Loan(copy, reader);

        assertTrue(loan.toString().contains("Loan"));
    }
}
