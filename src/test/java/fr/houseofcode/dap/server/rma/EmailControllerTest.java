package fr.houseofcode.dap.server.rma;


import fr.houseofcode.dap.server.rma.google.GmailServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.ConcurrentModel;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmailController.class)
public class EmailControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmailController emailController;

    @MockBean
    private GmailServiceImpl gmailServiceImpl;

    @Test
    public void testDisplayNbUnreadEmail() throws Exception {
        emailController.setGmailService(new GmailServiceMock());

        int result = emailController.displayNbUnreadEmail("jose", new ConcurrentModel());
        int expectedNbEmail = 12;
        Assert.assertNotNull("Nombre d'email non présent : ", result);
        Assert.assertEquals(expectedNbEmail, result);

//        mvc.perform(MockMvcRequestBuilders
//                .get("/email/nbUnread?userKey={userKey}", "jose")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
    }
}