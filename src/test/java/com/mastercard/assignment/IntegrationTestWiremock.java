package com.mastercard.assignment;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.junit.Assert.assertEquals;

public class IntegrationTestWiremock {

    static int port;

    static {

        try {
            // Get a free port
            ServerSocket s = new ServerSocket(0);
            port = s.getLocalPort();
            s.close();

        } catch (IOException e) {
            // No OPS
        }
    }

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(port);

    @Test
    public void integrationTestingForMatchingURL() throws IOException {

        stubFor(get(urlPathMatching("/search/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type","application/json;charset=UTF-8")
                        .withBody("ResponseCount{searchTerm='test', movies=[], count=0}")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(String.format("http://localhost:%s/search/abc", port));
        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertHttpResponseToString(httpResponse);

        verify(getRequestedFor(urlEqualTo("/search/abc")));
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
        assertEquals("application/json;charset=UTF-8", httpResponse.getFirstHeader("Content-Type").getValue());
        assertEquals("ResponseCount{searchTerm='test', movies=[], count=0}", stringResponse);
    }

    @Test
    public void integrationTestForMatchingHeaders() throws IOException {

        stubFor(get(urlPathEqualTo("/search/abc"))
                .withHeader("Accept", matching("text/.*"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader("Content-Type", "text/html")
                        .withBody("!!! Service Unavailable !!!")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(String.format("http://localhost:%s/search/abc", port));
        request.addHeader("Accept", "text/html");
        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertHttpResponseToString(httpResponse);

        verify(getRequestedFor(urlEqualTo("/search/abc")));
        assertEquals(503, httpResponse.getStatusLine().getStatusCode());
        assertEquals("text/html", httpResponse.getFirstHeader("Content-Type").getValue());
        assertEquals("!!! Service Unavailable !!!", stringResponse);
    }

    private static String convertHttpResponseToString(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getEntity().getContent();
        return convertInputStreamToString(inputStream);
    }

    private static String convertInputStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String string = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return string;
    }
}
