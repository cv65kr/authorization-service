package com.kajti.auth.bdd.steps;

import com.kajti.auth.bdd.SpringIntegrationTest;
import com.kajti.auth.bdd.config.ClientErrorHandler;
import com.kajti.auth.bdd.dto.AccessToken;
import com.kajti.auth.bdd.matcher.TimestampMatcher;
import com.kajti.auth.bdd.matcher.UuidMatcher;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.TreeMap;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;

public class RestStep extends SpringIntegrationTest {

    private RestTemplate restTemplate = new RestTemplate();

    private Map<String, String> providedHeaders = new TreeMap<>();

    private String accessToken;

    private String jsonResponse;

    private int jsonStatusCode;

    RestStep() {
       restTemplate.setErrorHandler(new ClientErrorHandler());

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);

        restTemplate.setRequestFactory(requestFactory);
    }

    @Given("There is provided header with {string} key and value {string}")
    public void addHeader(String key, String value) {
        providedHeaders.put(key, value);
    }

    @When("I send a authorized {string} request to {string} with body:")
    public void authorizedRequestStep(String httpMethod, String path, String body) {
        this.bodyRequest(httpMethod, path, body, true);
    }

    @When("I send a {string} request to {string} with body:")
    public void bodyRequestStep(String httpMethod, String path, String body) {
        this.bodyRequest(httpMethod, path, body, false);
    }

    @Then("Then the response status code should be {int}")
    public void thenTheResponseStatusCodeShouldBe(int statusCode) {
        Assert.assertEquals(statusCode, jsonStatusCode);
    }

    @And("the JSON should be equal to:")
    public void theJSONShouldBeEqualTo(String body) {
        assertThatJson(jsonResponse)
                .when(IGNORING_ARRAY_ORDER)
                .withMatcher("timestamp", new TimestampMatcher())
                .withMatcher("uuid", new UuidMatcher())
                .isEqualTo(body);
    }

    @When("I send a {string} request to {string}")
    public void requestStep(String httpMethod, String path) {
        simpleRequest(httpMethod, path, false);
    }

    @When("I send a authorized {string} request to {string}")
    public void authorizedRequestStep(String httpMethod, String path) {
        simpleRequest(httpMethod, path, true);
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

    @Given("There is access token for username {string} provided in the request header")
    public void getUserTokenStep(String username) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic YnJvd3Nlcjo=");

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("scope", "ui");
        map.add("username", username);
        map.add("password", UserStep.PASSWORD);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<AccessToken> response = restTemplate.exchange(
                url+"/uaa/oauth/token",
                HttpMethod.POST,
                request,
                AccessToken.class
        );

        accessToken = response.getBody().getAccessToken();
    }

    private void simpleRequest(String httpMethod, String path,  boolean isAuthorized) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (isAuthorized) {
            headers.add("Authorization", "Bearer "+accessToken);
        }
        for (Map.Entry<String, String> entry : providedHeaders.entrySet()) {
            headers.add(entry.getKey(), entry.getValue());
        }

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url+path,
                HttpMethod.resolve(httpMethod),
                request,
                String.class
        );

        jsonResponse = response.getBody();
        jsonStatusCode = response.getStatusCodeValue();
    }

    private void bodyRequest(String httpMethod, String path, String body, boolean isAuthorized) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (isAuthorized) {
            headers.add("Authorization", "Bearer "+accessToken);
        }
        for (Map.Entry<String, String> entry : providedHeaders.entrySet()) {
            headers.add(entry.getKey(), entry.getValue());
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
}
