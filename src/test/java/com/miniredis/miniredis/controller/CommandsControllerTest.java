package com.miniredis.miniredis.controller;

import com.miniredis.miniredis.domain.service.RedisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CommandsController.class)
public class CommandsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RedisService redisService;

    @Test
    void whenNotValidInput_thenReturns400() throws Exception {
        mockMvc.perform(get("/")
                .contentType("application/json"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        mockMvc.perform(get("/?cmd=something")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void whenServiceThrows_thenReturn400()throws Exception{
        when(redisService.executeCommand("command"))
                .thenThrow(IllegalArgumentException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/?cmd=command")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}
