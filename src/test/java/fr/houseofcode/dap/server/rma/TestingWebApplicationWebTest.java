package fr.houseofcode.dap.server.rma;

import fr.houseofcode.dap.server.rma.google.GmailService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ConcurrentModel;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingWebApplicationWebTest {
    
    @Autowired
	private TestRestTemplate restTemplate;
    
    @MockBean
    private GmailService gmailService;
    
    @Autowired
    private EmailController emailController;

    @Test
    public void contextLoads() {
        Assertions.assertThat(emailController).isNotNull();
    }
    
	@Test
	public void exampleTest() {
		String body = this.restTemplate.getForObject("/", String.class);
		Assertions.assertThat(body).isEqualTo("Bienvenue sur Spring Boot");
	}
	
	@Test
	public void mockitoExempleTest() throws IOException, GeneralSecurityException {
         // RemoteService has been injected into the reverser bean
        BDDMockito.given(this.gmailService.getNbUnreadEmail("jose")).willReturn(17);
        Integer body = this.restTemplate.getForObject("/email/nbUnread?userKey=jose", Integer.class);
        Assertions.assertThat(body).isEqualTo(17);
    }
    
}
