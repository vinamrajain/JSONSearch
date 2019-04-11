package com.mastercard.assignment.controller;

import com.mastercard.assignment.service.KeywordSearchManager;
import com.mastercard.assignment.view.ResponseCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    //Autowire(Dependency injection) the recursive implementation of the search service.
    @Autowired
    @Qualifier("recursive")
    KeywordSearchManager manager;

    /**
     * Rest controller to communicate with server. All the get requests to search a keyword in the
     * JSON database are directed here from the front end using the end point.
     *
     * RestController endpoint: /search/{keyword}
     *
     * @param keyword
     * @return instannce of ResponseCount.class
     */
    @GetMapping("/{keyword}")
    public ResponseCount getTheSearchCount(@PathVariable String keyword){

        return manager.search(keyword);
    }

}
