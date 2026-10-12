package hr.java.vjezbe.hardwareapp.controller;

import hr.java.vjezbe.hardwareapp.domain.Type;
import hr.java.vjezbe.hardwareapp.dto.AuthRequestDTO;
import hr.java.vjezbe.hardwareapp.dto.HardwareDTO;
import hr.java.vjezbe.hardwareapp.dto.JwtResponseDTO;
import hr.java.vjezbe.hardwareapp.repository.SpringDataHardwareRepository;
import hr.java.vjezbe.hardwareapp.service.HardwareService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HardwareControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private SpringDataHardwareRepository hardwareRepository;

  @Autowired
  private HardwareService hardwareService;

  @Autowired
  private AuthController authController;

  private String accessToken;

  private HardwareDTO hardwareDTO;

  @BeforeEach
  void setUp() throws Exception {
    // Prvo se autentificiramo i dobijemo access token
    AuthRequestDTO authRequest = new AuthRequestDTO();
    authRequest.setUsername("admin");
    authRequest.setPassword("admin");

    JwtResponseDTO jwtResponse = authController.authenticateAndGetToken(authRequest);
    accessToken = jwtResponse.getAccessToken();

    // Inicijalizacija HardwareDTO objekta
    hardwareDTO = new HardwareDTO();
    hardwareDTO.setCode("9876543");
    hardwareDTO.setName("Test Hardware");
    hardwareDTO.setType(new Type(1, "CPU", "Central processing unit"));
    hardwareDTO.setStock(100);
    hardwareDTO.setPrice(new BigDecimal("999.99"));
  }

  @Test
  void testGetAll() throws Exception {
    hardwareService.save(hardwareDTO);

    mockMvc.perform(get("/hardware")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(6)))
        .andExpect(jsonPath("$[0].code", is("1234561")));
  }

  @Test
  void testGetByCode() throws Exception {
    hardwareService.save(hardwareDTO);

    mockMvc.perform(get("/hardware/{code}", "1234561")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is("1234561")))
        .andExpect(jsonPath("$.name", is("Asus TUF RTX 3080")));
  }

  @Test
  void testSaveHardware() throws Exception {
    mockMvc.perform(post("/hardware")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(hardwareDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.code", is("9876543")))
        .andExpect(jsonPath("$.name", is("Test Hardware")));
  }

  @Test
  void testUpdateHardware() throws Exception {
    hardwareService.save(hardwareDTO);

    hardwareDTO.setName("Updated Hardware");

    mockMvc.perform(put("/hardware/{code}", "1234561")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(hardwareDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("Updated Hardware")));
  }

  @Test
  void testDeleteHardware() throws Exception {
    hardwareService.save(hardwareDTO);

    mockMvc.perform(delete("/hardware/{code}", "ABC123")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    assertFalse(hardwareRepository.findByCode("ABC123").isPresent());
  }
}
