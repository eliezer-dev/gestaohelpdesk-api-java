package dev.eliezer.superticket.controllers;

import dev.eliezer.superticket.domain.model.Client;
import dev.eliezer.superticket.domain.repository.ClientRepository;
import dev.eliezer.superticket.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.stereotype.Component;
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
public class ClientControllerTest {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;


    private ClientRepository clientRepository;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }


    @Test
    @DisplayName("Should be able to find client by id")
    public void should_be_able_to_find_client_by_id()throws Exception{
        createClientForTest(1);
        var result = mvc.perform(MockMvcRequestBuilders.get("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    @DisplayName("Should be able to get all clients")
    public void should_be_able_to_get_all_clients()throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"));

    }

    @Test
    @DisplayName("Should be able to create a new clients")
    public void should_be_able_to_create_a_new_clients()throws Exception{
        Client client = returnClientModel();
        var result = mvc.perform(MockMvcRequestBuilders.post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(client))
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    @DisplayName("Should be able to update a client")
    public void should_be_able_to_update_a_client()throws Exception{
        createClientForTest(1);
        Client client = returnClientModel();
        client.setId(1L);
        client.setRazaoSocialName("TESTECLIENTE2");

        var result = mvc.perform(MockMvcRequestBuilders.put("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(client))
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    @DisplayName("Should be able to delete a client")
    public void should_be_able_to_delete_a_client()throws Exception{
        createClientForTest(1);

        var result = mvc.perform(MockMvcRequestBuilders.delete("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public List<Client> createClientForTest (int ntimes) {
        List<Client> clients = new ArrayList<>();

        while (ntimes > 0){
            Client client = returnClientModel();
            client.setCpfCnpj("1234567890123" + ntimes);
            clientRepository.save(client);
            ntimes--;
            clients.add(client);
        }
        return clients;
    }

    public Client returnClientModel (){
        Client client = new Client();
        client.setRazaoSocialName("TESTENAME");
        client.setCpfCnpj("12345678901234");
        client.setCep("13346000");
        client.setCity("TESTECIDADE");
        client.setState("TESTEESTADO");
        client.setAddress("TESTE ENDERECO");
        client.setAddressNumber("TESTENUMERO");
        client.setEmail("teste@testeemail.com");
        return client;
    }


}




