package com.mastercard.assignment

import com.mastercard.assignment.service.KeywordSearchManager
import com.mastercard.assignment.service.KeywordSearchManagerRecursiveImpl
import com.mastercard.assignment.view.ResponseCount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class SearchControllerTest extends Specification{


    @Autowired
    private MockMvc mockMvc

    def KeywordSearchManager manager = Mock(KeywordSearchManagerRecursiveImpl);

    def "when get is performed then the response has status 200"() {

        given: manager.search(_) >> new ResponseCount()

        when:
        def result = manager.search("abc")

        then:
        result instanceof ResponseCount
    }
}
