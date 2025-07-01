package com.distribuida.controller;

import com.distribuida.model.FacturaDetalle;
import com.distribuida.service.FacturaDetalleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacturaDetalleController.class)

public class FacturaDetalleControllerTestIntegracion {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaDetalleService facturaDetalleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFinAll() throws Exception {
        FacturaDetalle facturaDetalle = new FacturaDetalle(1, 15, 15.11F);
        Mockito.when(facturaDetalleService.findAll()).thenReturn(List.of(facturaDetalle));

        mockMvc.perform(get("/FacturaDetalles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cantidad").value("15"));

    }


    @Test
    public void testsave() throws Exception {
        FacturaDetalle facturaDetalle = new FacturaDetalle(0, 15, 15.11F);

        Mockito.when(facturaDetalleService.save(any(FacturaDetalle.class))).thenReturn(facturaDetalle);

        mockMvc.perform(post("/FacturaDetalles").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facturaDetalle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value("15"));

    }


    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/FacturaDetalles/1")).andExpect(status().isNoContent());
    }

}
