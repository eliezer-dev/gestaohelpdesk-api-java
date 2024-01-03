package dev.eliezer.superticket.services;

import de.cronn.testutils.h2.H2Util;
import dev.eliezer.superticket.domain.model.Status;
import dev.eliezer.superticket.domain.repository.StatusRepository;
import dev.eliezer.superticket.service.impl.StatusServiceImpl;
import dev.eliezer.superticket.utils.TestUtils;
import net.minidev.json.JSONObject;
import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import java.util.Optional;

import static dev.eliezer.superticket.utils.TestUtils.objectToJson;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StatusServiceTest {

    @Autowired
    private StatusRepository statusRepository;


    private MockMvc mvc;


    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be able to find status by id")
    public void should_be_able_to_find_status_by_id()throws Exception{
        createStatusForTest(1);
        var result = mvc.perform(MockMvcRequestBuilders.get("/status/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"));
        statusRepository.deleteAll();
    }

    @Test
    @DisplayName("Should be able to get all status")
    public void should_be_able_to_get_all_status()throws Exception{
        String jsonString = "[{\"id\":1,\"description\":\"open\"},{\"id\":2,\"description\":\"open\"},{\"id\":3,\"description\":\"open\"}]";
        createStatusForTest(3);
        var result = mvc.perform(MockMvcRequestBuilders.get("/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(jsonString));
        statusRepository.deleteAll();
    }

    @Test
    @DisplayName("Should be able to create a new status")
    public void should_be_able_to_create_a_new_status()throws Exception{
        Status status = new Status();
        status.setDescription("open");
        var result = mvc.perform(MockMvcRequestBuilders.post("/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(status))
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.content().contentType("application/json"));
        statusRepository.deleteAll();
    }

    @Test
    @DisplayName("Should be able to update a status")
    public void should_be_able_to_update_a_status()throws Exception{
        createStatusForTest(1);
        Status newStatus = new Status();
        newStatus.setDescription("closed");

        var result = mvc.perform(MockMvcRequestBuilders.put("/status/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(newStatus))
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.content().contentType("application/json"));
        statusRepository.deleteAll();
    }

    @Test
    @DisplayName("Should be able to delete a status")
    public void should_be_able_to_delete_a_status()throws Exception{
        createStatusForTest(1);

        var result = mvc.perform(MockMvcRequestBuilders.delete("/status/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestUtils.generateToken(1L
                                , "SUPERTICKET@2024")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public void createStatusForTest (int ntimes) {
        while (ntimes > 0){
            Status statusOld = new Status();
            statusOld.setDescription("open");
            statusRepository.save(statusOld);
            ntimes--;
        }

    }

}
