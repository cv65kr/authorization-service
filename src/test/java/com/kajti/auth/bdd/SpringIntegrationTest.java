package com.kajti.auth.bdd;

import com.kajti.auth.AuthServiceApplication;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = AuthServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@Ignore
public class SpringIntegrationTest {

    @LocalServerPort
    protected int port = 4000;

    protected String url = "http://localhost:"+port;
}