package dev.eliezer.gestaohelpdesk.controllers;

import dev.eliezer.gestaohelpdesk.modules.client.entities.Client;
import dev.eliezer.gestaohelpdesk.modules.status.entities.Status;
import dev.eliezer.gestaohelpdesk.modules.tickets.entities.Ticket;
import dev.eliezer.gestaohelpdesk.modules.user.entities.User;
import dev.eliezer.gestaohelpdesk.modules.client.repositories.ClientRepository;
import dev.eliezer.gestaohelpdesk.modules.status.repositories.StatusRepository;
import dev.eliezer.gestaohelpdesk.modules.tickets.repositories.TicketRepository;
import dev.eliezer.gestaohelpdesk.modules.user.repositories.UserRepository;
import dev.eliezer.gestaohelpdesk.dto.ClientForTicketRequestDTO;
import dev.eliezer.gestaohelpdesk.dto.StatusForTicketRequestDTO;
import dev.eliezer.gestaohelpdesk.dto.TicketRequestDTO;
import dev.eliezer.gestaohelpdesk.dto.UserForTicketRequestDTO;
import dev.eliezer.gestaohelpdesk.utils.TestUtils;
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

import static dev.eliezer.gestaohelpdesk.utils.TestUtils.objectToJson;

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

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be able to get all tickets")
    public void should_be_able_to_get_all_tickets()throws Exception{
        createTicketForTest(3);
        var result = mvc.perform(MockMvcRequestBuilders.get("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"));

    }


    @Test
    @DisplayName("Should be able to find ticket by id")
    public void should_be_able_to_find_ticket_by_id() throws Exception {
        createTicketForTest(1);
        var result = mvc.perform(MockMvcRequestBuilders.get("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"));
    }



    @Test
    @DisplayName("Should be able to update a ticket")
    public void should_be_able_to_update_a_user()throws Exception{
        var tickets = createTicketForTest(1);
        tickets.get(0).setShortDescription("TESTE COM SHORT DESCRIPTION ALTERADA");

        ClientForTicketRequestDTO client = ClientForTicketRequestDTO.builder()
                .id(tickets.get(0).getClient().getId())
                .build();

        List<UserForTicketRequestDTO> users = new ArrayList<>();
                tickets.get(0).getUser().forEach(user -> {
                    UserForTicketRequestDTO userForTicketRequestDTO = UserForTicketRequestDTO.builder()
                            .id(user.getId())
                            .build();
                    users.add(userForTicketRequestDTO);
                });

        StatusForTicketRequestDTO status = StatusForTicketRequestDTO.builder()
                .id(tickets.get(0).getStatus().getId())
                .build();
        TicketRequestDTO ticketRequestFormated = TicketRequestDTO.builder()
                .id(tickets.get(0).getId())
                .shortDescription("TESTE SHORTDESCRIPTION ALTERADA")
                .description("TESTE DESCRIPTION ALTERADA")
                .client(client)
                .users(users)
                .status(status)
                .build();


        var result = mvc.perform(MockMvcRequestBuilders.put("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(ticketRequestFormated))
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.content().contentType("application/json"));

    }

    @Test
    @DisplayName("Should be able to delete a ticket")
    public void should_be_able_to_delete_a_ticket()throws Exception{
        createTicketForTest(1);

        var result = mvc.perform(MockMvcRequestBuilders.delete("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public List<Client> createClientForTest (int ntimes) {
        List<Client> clients = new ArrayList<>();

        while (ntimes > 0){
            Client client = new Client();
            client.setRazaoSocialName("TESTENAME");
            client.setCpfCnpj("1234567890123" + ntimes);
            client.setCep("13346000");
            client.setCity("TESTECIDADE");
            client.setState("TESTEESTADO");
            client.setAddress("TESTE ENDERECO");
            client.setAddressNumber("TESTENUMERO");
            client.setEmail("teste@testeemail.com");

            clientRepository.save(client);

            ntimes--;

            clients.add(client);
        }
        return clients;
}


    public List<User> createUserForTest (int ntimes) {
        List<User> users = new ArrayList<>();
        while (ntimes > 0){
            User user = new User();
            user.setName("TESTENAME");
            user.setCpf("1234567890" + ntimes);
            user.setCep("13346000");
            user.setCity("TESTECIDADE");
            user.setState("TESTEESTADO");
            user.setAddress("TESTE ENDERECO");
            user.setAddressNumber("TESTENUMERO");
            user.setEmail("teste"+ntimes+"@testeemail.com");
            user.setPassword("teste1234");

            userRepository.save(user);

            ntimes--;

            users.add(user);
        }
        return users;
    }

    public List<Status> createStatusForTest (int ntimes) {
        List<Status> statusList = new ArrayList<>();
        while (ntimes > 0){
            Status status = new Status();
            status.setDescription("open" + " " + ntimes);

            statusRepository.save(status);

            ntimes--;

            statusList.add(status);
        }
        return statusList;
    }

    public List<Ticket> createTicketForTest (int ntimes) {
        List<Client> clients = createClientForTest(ntimes);
        List <User> users = createUserForTest(ntimes);
        List<Status> status = createStatusForTest(ntimes);
        List<Ticket> tickets = new ArrayList<>();
        while (ntimes > 0)  {
            Ticket ticket = returnTicketModel(clients.get(ntimes-1), users.get(ntimes-1), status.get(ntimes-1));
            ticket.setShortDescription(ticket.getShortDescription() + " " + ntimes);
            ticket.setShortDescription(ticket.getDescription() + " " + ntimes);
            ticket = ticketRepository.save(ticket);
            ntimes --;
            tickets.add(ticket);
        }
        return tickets;
    }


    public Ticket returnTicketModel (Client client, User user, Status status) {
        List<User> users = new ArrayList<>();
        users.add(user);
        Ticket ticket = new Ticket();
        ticket.setShortDescription("TESTE SHORT DESCRIPTION");
        ticket.setDescription("TESTE DESCRIPTION");
        ticket.setClient(client);
        ticket.setUser(users);
        ticket.setStatus(status);
        return ticket;

    }
}
