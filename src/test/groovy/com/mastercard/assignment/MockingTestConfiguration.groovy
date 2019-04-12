package com.mastercard.assignment

import com.mastercard.assignment.service.KeywordSearchManager
import com.mastercard.assignment.service.KeywordSearchManagerRecursiveImpl
import com.mastercard.assignment.view.ResponseCount
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import spock.mock.DetachedMockFactory

@TestConfiguration
class MockingTestConfiguration {

    private DetachedMockFactory factory = new DetachedMockFactory()

    @Bean
    KeywordSearchManager manager() {
        factory.Mock(KeywordSearchManagerRecursiveImpl)
    }

}
