package dev.eliezer.superticket.controllers;

import dev.eliezer.superticket.domain.model.Client;
import dev.eliezer.superticket.domain.model.Status;
import dev.eliezer.superticket.domain.model.Ticket;
import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.repository.ClientRepository;
import dev.eliezer.superticket.domain.repository.StatusRepository;
import dev.eliezer.superticket.domain.repository.TicketRepository;
import dev.eliezer.superticket.domain.repository.UserRepository;
import dev.eliezer.superticket.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static dev.eliezer.superticket.utils.TestUtils.objectToJson;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TicketControllerTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private StatusRepository statusRepository;

    private UserControllerTest userControllerTest = new UserControllerTest();

    private ClientControllerTest clientControllerTest = new ClientControllerTest();

    private StatusControllerTest statusControllerTest = new StatusControllerTest();

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be able to find ticket by id")
    public void should_be_able_to_find_ticket_by_id() throws Exception {
        Ticket ticket = returnTicketModel();
        ticketRepository.save(ticket);
        var result = mvc.perform(MockMvcRequestBuilders.get("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    @DisplayName("Should be able to get all tickets")
    public void should_be_able_to_get_all_tickets()throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"));

    }
//
//    @Test
//    @DisplayName("Should be able to create a new users")
//    public void should_be_able_to_create_a_new_users()throws Exception{
//        User user = returnUserModel();
//        var result = mvc.perform(MockMvcRequestBuilders.post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectToJson(user))
//                        .header("Authorization", TestUtils.generateToken(1L
//                                , "SUPERTICKET@2024")))
//                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
//                        MockMvcResultMatchers.content().contentType("application/json"));
//    }
//
//    @Test
//    @DisplayName("Should be able to update a user")
//    public void should_be_able_to_update_a_user()throws Exception{
//        createUserForTest(1);
//        User user = returnUserModel();
//        user.setId(1L);
//        user.setName("TESTENAME2");
//
//        var result = mvc.perform(MockMvcRequestBuilders.put("/users/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectToJson(user))
//                        .header("Authorization", TestUtils.generateToken(1L
//                                , "SUPERTICKET@2024")))
//                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
//                        MockMvcResultMatchers.content().contentType("application/json"));
//    }
//
//    @Test
//    @DisplayName("Should be able to delete a user")
//    public void should_be_able_to_delete_a_user()throws Exception{
//        createUserForTest(1);
//
//        var result = mvc.perform(MockMvcRequestBuilders.delete("/users/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", TestUtils.generateToken(1L
//                                , "SUPERTICKET@2024")))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//

    public List<User> createUserForTest(int ntimes) {
        List<User> users = new ArrayList<>();
        while (ntimes > 0){
            User user = userControllerTest.returnUserModel();
            user.setCpf("1234567890" + ntimes);
            user.setEmail("teste"+ntimes+"@testeemail.com");
            userRepository.save(user);
            ntimes--;
            users.add(user);
        }
        return users;
    }


    public Ticket returnTicketModel () {
        Ticket ticket = new Ticket();
        Client client = clientControllerTest.returnClientModel();
        clientRepository.save(client);
        Status status = statusControllerTest.returnStatusModel();
        statusRepository.save(status);
        ticket.setShortDescription("TESTE SHORT DESCRIPTION");
        ticket.setDescription("TESTE DESCRIPTION");
        ticket.setClient(client);
        ticket.setUser(createUserForTest(1));
        ticket.setStatus(status);
        return ticket;
        /*precisa criar os usuarios, clientes e status antes de criar o ticket*/

    }
}
