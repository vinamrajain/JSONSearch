package com.mastercard.assignment

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification

@SpringBootTest
class LoadContextTest extends Specification{

    @Autowired
    ApplicationContext context

    def "test context loads"() {
        expect:
        context != null
        context.containsBean("searchController")
        context.containsBean("getAllData")
    }
}
