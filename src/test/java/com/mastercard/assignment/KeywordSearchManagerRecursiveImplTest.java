package com.mastercard.assignment;

import com.mastercard.assignment.service.KeywordSearchManagerRecursiveImpl;
import com.mastercard.assignment.view.ResponseCount;
import com.mastercard.assignment.persistenceDAO.JsonDBRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class KeywordSearchManagerRecursiveImplTest {

    @Mock
    JsonDBRepository repository;

    @InjectMocks
    KeywordSearchManagerRecursiveImpl manager;

    String data = "[{\"title\": \"Amy\",\"age\": 23,\"city\": [\"Hoboken\",\"Chicago\",\"Atlanta\"]}," +
            "{\"title\": \"Rudy\",\"age\":30,\"city\": [\"Austin\",\"Brooklyn\", \"Boston\"]}, " +
            "{\"title\": \"Brooklyn\",\"age\":32,\"city\": \"Brooklyn\"}, " +
            "{\"title\": \"Andy\",\"age\":32,\"city\": \"Chicago\"}]" +
            "{\"title\": \"Andy\",\"age\":32,\"city\": \"Chicago\"}]  ";

    @Test
    public void testSearch_EmptyDataSet () throws JSONException {

        Mockito.when(repository.getJsonArray()).thenReturn(new JSONArray());

        Assert.assertEquals(new ResponseCount("ABC",new ArrayList<String>(),0).toString(),
                manager.search("ABC").toString());
    }

    @Test
    public void testSearch_SearchWithCount1 () throws JSONException {

        Mockito.when(repository.getJsonArray()).thenReturn(new JSONArray(data));

        Assert.assertNotNull(manager.search("Boston"));

        Assert.assertTrue(manager.search("Boston") instanceof ResponseCount);

        Assert.assertEquals(new ResponseCount("Boston",new ArrayList<String>(Arrays.asList("Rudy")),1).toString(),
                manager.search("Boston").toString());

        Assert.assertEquals(1, manager.search("Boston").getCount());
    }

    @Test
    public void testSearch_SearchWithCount3 () throws JSONException {

        Mockito.when(repository.getJsonArray()).thenReturn(new JSONArray(data));

        Assert.assertNotNull(manager.search("Brooklyn"));

        Assert.assertTrue(manager.search("Brooklyn") instanceof ResponseCount);

        Assert.assertEquals(new ResponseCount("Brooklyn",new ArrayList<String>(Arrays.asList("Rudy","Brooklyn")),3).toString(),
                manager.search("Brooklyn").toString());

        Assert.assertEquals(3, manager.search("Brooklyn").getCount());
    }


    @Test
    public void testSearch_NoResultFound() throws JSONException {

        Mockito.when(repository.getJsonArray()).thenReturn(new JSONArray(data));

        Assert.assertEquals(new ResponseCount("Miami",new ArrayList<String>(),0).toString(),
                manager.search("Miami").toString());

        Assert.assertEquals(0,manager.search("Miami").getCount());
    }

    @Test
    public  void testSearch_DuplicateEntriesCount() throws JSONException{
        Mockito.when(repository.getJsonArray()).thenReturn(new JSONArray(data));

        Assert.assertFalse(manager.search("Chicago").getCount() == 3);
    }

    @Test
    public  void testSearch_DuplicateEntriesTitle() throws JSONException{
        Mockito.when(repository.getJsonArray()).thenReturn(new JSONArray(data));

        Assert.assertEquals("[Amy, Andy]", manager.search("Chicago").getMovies().toString());

        Assert.assertFalse(manager.search("Chicago").getMovies().toString().equals("[Amy, Amy, Andy]"));
    }
    @Test
    public  void testSearch_CaseInsensitive() throws JSONException{
        Mockito.when(repository.getJsonArray()).thenReturn(new JSONArray(data));

        Assert.assertEquals(3,manager.search("brooklyn").getCount());
    }

    @Test
    public  void testSearch_IntegerValues() throws JSONException{
        Mockito.when(repository.getJsonArray()).thenReturn(new JSONArray(data));

        Assert.assertEquals(2,manager.search("32").getCount());

        Assert.assertEquals("[Brooklyn, Andy]", manager.search("32").getMovies().toString());
    }

    @Test
    public  void testSearch_EmptyString() throws JSONException{
        Mockito.when(repository.getJsonArray()).thenReturn(new JSONArray(data));

        Assert.assertFalse(manager.search("").getCount() > 0);;
    }

    @Test
    public  void testSearch_WhiteSpaces() throws JSONException{
        Mockito.when(repository.getJsonArray()).thenReturn(new JSONArray(data));

        Assert.assertTrue(manager.search(" ").getCount() == 0);;
    }

}
