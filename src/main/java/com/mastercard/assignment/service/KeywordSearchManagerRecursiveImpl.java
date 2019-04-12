package com.mastercard.assignment.service;

import com.mastercard.assignment.view.ResponseCount;
import com.mastercard.assignment.persistenceDAO.JsonDBRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Qualifier("recursive")
public class KeywordSearchManagerRecursiveImpl implements KeywordSearchManager {

    private static final Logger logger = LoggerFactory.getLogger(KeywordSearchManager.class);

    @Autowired
    private JsonDBRepository repository;

    /**
     * This method invokes the heloer method with JSON array and zero index. The helper method will call itself
     * recursively with index++, untill reached end.
     *
     * @param keyword
     * @return responseCount instance of ResponseCount.class
     */
    @Override
    public ResponseCount search(String keyword) {

        JSONArray jsonArray = repository.getJsonArray();

        if(jsonArray.length() == 0){
            return new ResponseCount(keyword,new ArrayList<String>(),0);
        }

        ResponseCount responseCount = new ResponseCount(keyword,new ArrayList<String>(),0);

        return searchHelper(jsonArray,responseCount, keyword, 0);
    }

    /**
     * This method when invoked iterates recursively over all the JSON objects in JSON array and search for the keyword.
     * If found it increases the count of occurrence and stores the title of the movie with the count value.
     * This method search for keyword in case insensitive manner.
     * This method does not account for duplication JSON Objects. I have assumed the duplication here by
     * title of the movies a primary key.
     *
     *
     * @param jsonArray
     * @param responseCount
     * @param keyword
     * @param index
     * @return
     */
    private ResponseCount searchHelper(JSONArray jsonArray, ResponseCount responseCount, String keyword, int index) {

        if(index >= jsonArray.length()){
            return responseCount;
        }

        JSONObject jsonObject = jsonArray.getJSONObject(index);

        Iterator<String> keys = jsonObject.keys();

        long tempCount = 0;

        while(keys.hasNext()) {
            String key = keys.next();

            String value =  jsonObject.get(key).toString();

            tempCount += StringUtils.countOccurrencesOf(value.toLowerCase(),keyword.toLowerCase());
        }

        if(tempCount > 0) {
            if (!responseCount.getMovies().contains(jsonObject.get("title").toString())) {
                responseCount.setCount(responseCount.getCount() + tempCount);
                List<String> movieList = responseCount.getMovies();
                movieList.add((String) jsonObject.get("title"));
                responseCount.setMovies(movieList);
            }
        }
        return searchHelper(jsonArray,responseCount,keyword,++index);
    }

}
