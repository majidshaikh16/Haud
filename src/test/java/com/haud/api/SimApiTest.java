package com.haud.api;

import com.haud.TelecomApplication;
import com.haud.api.impl.SimService;
import com.haud.entity.Sim;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TelecomApplication.class)
@ActiveProfiles("test")
public class SimApiTest {
    @Autowired
    private SimService simService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void createSimSuccess() throws Exception {
        Sim sim = Sim.builder()
                .id(1L)
                .name("Sim1")
                .build();
        Mockito.when(simService.createSim(Mockito.any(Sim.class)))
                .thenReturn(sim);

        JSONObject payload = new JSONObject();
        payload.put("name", "Sim1");

        mockMvc.perform(MockMvcRequestBuilders.post("/sim/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Sim1"));

    }

    @Test
    public void createSimThrowErrorWhenNoNameProvided() throws Exception {

        JSONObject payload = new JSONObject();
        payload.put("name", "");

        mockMvc.perform(MockMvcRequestBuilders.post("/sim/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors.[0].title").value("Invalid Post Request"))
                .andExpect(jsonPath("$.errors.[0].detail").value("Sim name required!"));

    }
}
