package com.mastercard.assignment;


import com.mastercard.assignment.view.ResponseCount;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ResponseCountTest {

    ResponseCount responseCount =
            new ResponseCount("keyword",
                    new ArrayList<String>(Arrays.asList("ABC", "DEF")), 10);

    @Test
    public void testGetSearchTerm(){

        assertEquals("keyword", responseCount.getSearchTerm());
    }

    @Test
    public void testSetSearchTerm(){

        responseCount.setSearchTerm("New Keyword");
        assertNotEquals("keyword", responseCount.getSearchTerm());
        assertEquals("New Keyword", responseCount.getSearchTerm());
    }

    @Test
    public void testGetMovies(){

        assertArrayEquals(new ArrayList<String>(Arrays.asList("ABC","DEF")).toArray(), responseCount.getMovies().toArray());
    }

    @Test
    public void testSetMovies(){

        List<String> newMovies = new ArrayList<String>();
        newMovies.add("UVW");
        newMovies.add("XYZ");
        responseCount.setMovies(newMovies);
        assertFalse(Arrays.equals(new ArrayList<String>(Arrays.asList("ABC","DEF")).toArray(), responseCount.getMovies().toArray()));
        assertArrayEquals(newMovies.toArray(),responseCount.getMovies().toArray());
    }

    @Test
    public void testGetCount(){

        assertEquals(10,responseCount.getCount());
    }

    @Test
    public void testSetCount(){

        responseCount.setCount(20);
        assertNotEquals(10, responseCount.getCount());
        assertEquals(20,responseCount.getCount());
    }

    @Test
    public void testToString(){

        ResponseCount rc = new ResponseCount();
        rc.setSearchTerm("Test Keyword");
        rc.setMovies(new ArrayList<>(Arrays.asList("Movie1", "Movie2")));
        rc.setCount(100);
        assertEquals("ResponseCount{searchTerm='Test Keyword', movies=[Movie1, Movie2], count=100}", rc.toString());
    }

}
