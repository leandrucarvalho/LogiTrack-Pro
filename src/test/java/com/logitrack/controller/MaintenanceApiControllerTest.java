package com.logitrack.controller;

import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.logitrack.exception.GlobalExceptionHandler;
import com.logitrack.exception.ResourceNotFoundException;
import com.logitrack.service.MaintenanceService;

@WebMvcTest(MaintenanceApiController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class MaintenanceApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaintenanceService maintenanceService;

    @Test
    void createReturnsValidationErrorsWhenRequestInvalid() throws Exception {
        String payload = """
                {
                  "vehicleId": null,
                  "startDate": null,
                  "serviceType": " ",
                  "estimatedCost": -10,
                  "status": null
                }
                """;

        mockMvc.perform(post("/api/maintenances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.fieldErrors.length()").value(greaterThan(0)));
    }

    @Test
    void updateReturnsNotFoundWhenMaintenanceMissing() throws Exception {
        when(maintenanceService.getById(42))
                .thenThrow(new ResourceNotFoundException("Manutencao nao encontrada"));

        String payload = """
                {
                  "vehicleId": 1,
                  "startDate": "2026-03-10",
                  "serviceType": "Troca de oleo",
                  "estimatedCost": 150.50,
                  "status": "PENDENTE"
                }
                """;

        mockMvc.perform(put("/api/maintenances/42")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
