package com.mastercard.assignment.persistenceDAO;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class JsonDBRepository {

    // retrieving the existing bean from application context.
    @Autowired
    @Qualifier("jsonData")
    private JSONArray jsonArray;


    /**
     * This method loads the existing bean from application context created at the time of application
     * initialization and return back as instance of JSONArray.class/
     *
     * @return jsonArray instance of JSONArray.class
     */
    public JSONArray getJsonArray(){

        return jsonArray;
    }

}
