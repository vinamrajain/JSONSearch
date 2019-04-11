package com.mastercard.assignment;

import com.mastercard.assignment.persistenceDAO.JsonDBRepository;
import com.mastercard.assignment.service.KeywordSearchManagerIterativeImpl;
import com.mastercard.assignment.view.ResponseCount;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockingDetails;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class KeywordSearchManagerIterativeImplTest {

    @Mock
    JsonDBRepository repository;

    @InjectMocks
    KeywordSearchManagerIterativeImpl manager;

    String data = "[{\"title\": \"Amy\",\"age\": 23,\"city\": [\"Hoboken\",\"Chicago\",\"Atlanta\"]}," +
            "{\"title\": \"Rudy\",\"age\":30,\"city\": [\"Austin\",\"Brooklyn\", \"Boston\"]}, " +
                "{\"title\": \"Brooklyn\",\"age\":32,\"city\": \"Brooklyn\"}, " +
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

        Assert.assertEquals(new ResponseCount("Brooklyn",new ArrayList<String>(Arrays.asList("Brooklyn","Rudy")),3).toString(),
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

}
