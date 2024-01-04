package dev.eliezer.superticket.controllers;

import dev.eliezer.superticket.domain.model.Client;
import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.repository.ClientRepository;
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

import static dev.eliezer.superticket.utils.TestUtils.objectToJson;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be able to find user by id")
    public void should_be_able_to_find_user_by_id()throws Exception{
        createUserForTest(1);
        var result = mvc.perform(MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    @DisplayName("Should be able to get all users")
    public void should_be_able_to_get_all_users()throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"));

    }

    @Test
    @DisplayName("Should be able to create a new users")
    public void should_be_able_to_create_a_new_users()throws Exception{
        User user = returnUserModel();
        var result = mvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(user))
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    @DisplayName("Should be able to update a user")
    public void should_be_able_to_update_a_user()throws Exception{
        createUserForTest(1);
        User user = returnUserModel();
        user.setId(1L);
        user.setName("TESTENAME2");

        var result = mvc.perform(MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(user))
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    @DisplayName("Should be able to delete a user")
    public void should_be_able_to_delete_a_user()throws Exception{
        createUserForTest(1);

        var result = mvc.perform(MockMvcRequestBuilders.delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public void createUserForTest (int ntimes) {
        while (ntimes > 0){
            User user = returnUserModel();
            user.setCpf("1234567890" + ntimes);
            user.setEmail("teste"+ntimes+"@testeemail.com");
            userRepository.save(user);
            ntimes--;
        }

    }

    public User returnUserModel (){
        User user = new User();
        user.setName("TESTENAME");
        user.setCpf("12345678901");
        user.setCep("13346000");
        user.setCity("TESTECIDADE");
        user.setState("TESTEESTADO");
        user.setAddress("TESTE ENDERECO");
        user.setAddressNumber("TESTENUMERO");
        user.setEmail("teste@testeemail.com");
        user.setPassword("teste1234");
        return user;
    }

}
