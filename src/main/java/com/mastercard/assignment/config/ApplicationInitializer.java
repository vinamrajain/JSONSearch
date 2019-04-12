package com.mastercard.assignment.config;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Configuration
public class ApplicationInitializer {


    private static final Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);

    /**
     * This method is invoked at the time of application start. This reads the Json data from
     * the file stored in the class path and create a bean with Qualifier: jsonData. This
     * bean is stored in application context and can be retrieved any time during the
     * execution of the program.
     *
     * @return jsonArray i.e. instance of JSONArray.class
     */

    @Bean
    @Qualifier("jsonData")
    public JSONArray getAllData() {

        JSONArray jsonArray = new JSONArray();
        try {

            File file = new ClassPathResource("Movies.json").getFile();
            String allData = new String(Files.readAllBytes(file.toPath()));
            jsonArray = new JSONArray(allData);

        } catch (IOException e) {

           logger.error("Could not load the JSON file", e);

        } catch (org.json.JSONException jsonExc){

            logger.error("Invalid JSON objects in the file", jsonExc);
        }

        return jsonArray;
    }
}
