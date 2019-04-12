package com.mastercard.assignment.service;

import com.mastercard.assignment.persistenceDAO.JsonDBRepository;
import com.mastercard.assignment.view.ResponseCount;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Qualifier("iterative")
public class KeywordSearchManagerIterativeImpl implements KeywordSearchManager {

    @Autowired
    private JsonDBRepository repository;

    /**
     * This method when invoked iterates over all the JSON objects in JSON array and search for the keyword.
     * If found it increases the count of occurrence and stores the title of the movie with the count value.
     * This method search for keyword in case insensitive manner.
     * This method does not account for duplication JSON Objects. I have assumed the duplication here by
     * title of the movies a primary key.
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

        Set<String> movies = new HashSet<String>();
        long count = 0;

        for(int i=0; i<jsonArray.length(); i++){

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            long tempCount = this.searchInJSONObject(jsonObject, keyword);

            if(tempCount > 0) {

                if(!movies.contains(jsonObject.get("title").toString())) {
                    count = count + tempCount;

                    movies.add((String) jsonObject.get("title"));
                }
            }
        }

        ResponseCount responseCount = new ResponseCount(keyword,new ArrayList<String>(movies),count);

        return responseCount;
    }

    /**
     * This method iterates over all the keys of a JSON Object and compares their values for the keyword search
     * and maintain the count of their occurance.
     *
     * @param jsonObject
     * @param keyword
     * @return count i.e. count of occurance of the keyword in the JSON Object.
     */
    private long searchInJSONObject(JSONObject jsonObject, String keyword) {

        long count = 0;

        Iterator<String> keys = jsonObject.keys();

        while(keys.hasNext()) {
            String key = keys.next();

            String value =  jsonObject.get(key).toString();

            count += StringUtils.countOccurrencesOf(value.toLowerCase(),keyword.toLowerCase());
        }

     return count;
    }
}
