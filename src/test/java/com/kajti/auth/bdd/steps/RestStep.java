package com.kajti.auth.bdd.steps;

import com.kajti.auth.bdd.SpringIntegrationTest;
import com.kajti.auth.bdd.config.ClientErrorHandler;
import com.kajti.auth.bdd.dto.AccessToken;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestStep extends SpringIntegrationTest {

    private RestTemplate restTemplate = new RestTemplate();

    private String url = "http://localhost:"+port;

    private String accessToken;

    private String jsonResponse;

    private int jsonStatusCode;

    RestStep() {
       restTemplate.setErrorHandler(new ClientErrorHandler());

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);

        restTemplate.setRequestFactory(requestFactory);
    }

    @Given("There is access token for service provided in the request header")
    public void getServiceTokenStep() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic c2VydmljZTpzZXJ2aWNlLXBhc3N3b3Jk");

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<AccessToken> response = restTemplate.exchange(
            url+"/uaa/oauth/token",
            HttpMethod.POST,
            request,
            AccessToken.class
        );

        accessToken = response.getBody().getAccessToken();
    }

    @When("I send a authorized {string} request to {string} with body:")
    public void authorizedRequestStep(String httpMethod, String path, String body) {
        this.bodyRequest(httpMethod, path, body, true);
    }

    @When("I send a {string} request to {string} with body:")
    public void bodyRequestStep(String httpMethod, String path, String body) {
        this.bodyRequest(httpMethod, path, body, false);
    }

    private void bodyRequest(String httpMethod, String path, String body, boolean isAuthorized) {
        if (!httpMethod.equals(HttpMethod.POST) && httpMethod.equals(HttpMethod.PUT)) {
            throw new IllegalArgumentException("Only PUT or POST method allowed here.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (isAuthorized) {
            headers.add("Authorization", "Bearer "+accessToken);
        }

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url+path,
                HttpMethod.resolve(httpMethod),
                request,
                String.class
        );

        jsonResponse = response.getBody();
        jsonStatusCode = response.getStatusCodeValue();
    }

    @Then("Then the response status code should be {int}")
    public void thenTheResponseStatusCodeShouldBe(int statusCode) {
        Assert.assertEquals(statusCode, jsonStatusCode);
    }
}
