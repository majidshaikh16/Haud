package com.haud.api;

import com.haud.TelecomApplication;
import com.haud.api.impl.CustomerService;
import com.haud.api.impl.SimService;
import com.haud.entity.Customer;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TelecomApplication.class)
@ActiveProfiles("test")
public class CustomerApiTest {
    @Autowired
    private CustomerService customerService;

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
    public void createCustomerSuccess() throws Exception {
        Customer response = Customer.builder()
                .id(1L)
                .name("Majid")
                .build();

        Mockito.when(customerService.createCustomer(Mockito.any()))
                .thenReturn(response);

        JSONObject payload = new JSONObject();
        payload.put("name", "majid");
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/create").contentType(MediaType.APPLICATION_JSON)
                .content(payload.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Majid"));
    }

    @Test
    public void createCustomerThrowErrorWhenInvalidRequestBody() throws Exception {

        JSONObject payload = new JSONObject();
        payload.put("name123", "majid");
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/create").contentType(MediaType.APPLICATION_JSON)
                .content(payload.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors.[0].title").value("Invalid Post Request"))
                .andExpect(jsonPath("$.errors.[0].detail").value("Customer name required!"));
    }

    @Test
    public void createCustomerThrowErrorWhenEmptyPayload() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post("/customer/create").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors.[0].title").value("Invalid Attempt!"))
                .andExpect(jsonPath("$.errors.[0].detail").value("Something Went Wrong."));
    }

    @Test
    public void getCustomerSuccess() throws Exception {
        Customer response = Customer.builder()
                .id(1L)
                .name("Majid")
                .build();

        Mockito.when(customerService.isCustomerExists(Mockito.anyLong()))
                .thenReturn(Boolean.TRUE);
        Mockito.when(customerService.getCustomer(Mockito.anyLong()))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Majid"));
    }

    @Test
    public void getCustomerWithInvalidIdThrowCustomerDoesNotExists() throws Exception {

        Mockito.when(customerService.isCustomerExists(Mockito.anyLong()))
                .thenReturn(Boolean.FALSE);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/15"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].title").value("Invalid Request"))
                .andExpect(jsonPath("$.errors.[0].detail").value("Customer does not exist with the given id."));
    }

    @Test
    public void anyCustomerRequestWhenUsedBadRequestMethod() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/customer/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors.[0].title").value("Bad Request"))
                .andExpect(jsonPath("$.errors.[0].detail").value("Request method 'POST' not supported"));
    }

    @Test
    public void linkCustomerWithSimSuccess() throws Exception {
        Customer response = Customer.builder()
                .id(1L)
                .name("Majid")
                .sims(List.of(Sim.builder()
                        .id(2L)
                        .name("Sim1")
                        .build()))
                .build();
        Mockito.when(customerService.isCustomerExists(Mockito.anyLong()))
                .thenReturn(Boolean.TRUE);
        Mockito.when(simService.isSimExists(Mockito.anyLong()))
                .thenReturn(Boolean.TRUE);
        Mockito.when(customerService.linkSim(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/link/sim/1/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Majid"))
                .andExpect(jsonPath("$.sims").exists())
                .andExpect(jsonPath("$.sims.[0].id").value(2L))
                .andExpect(jsonPath("$.sims.[0].name").value("Sim1"))

        ;
    }

    @Test
    public void linkCustomerWithInvalidSimIdThrowSimDoesNotExistsError() throws Exception {
        Mockito.when(customerService.isCustomerExists(Mockito.anyLong()))
                .thenReturn(Boolean.TRUE);
        Mockito.when(simService.isSimExists(Mockito.anyLong()))
                .thenReturn(Boolean.FALSE);
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/link/sim/1/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors.[0].title").value("Invalid Request"))
                .andExpect(jsonPath("$.errors.[0].detail").value("Sim does not exist with the given id."));

        ;
    }
}
