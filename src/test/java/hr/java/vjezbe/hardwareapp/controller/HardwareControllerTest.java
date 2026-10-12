package hr.java.vjezbe.hardwareapp.controller;

import hr.java.vjezbe.hardwareapp.dto.HardwareDTO;
import hr.java.vjezbe.hardwareapp.service.HardwareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HardwareControllerTest {

  @Mock
  private HardwareService hardwareService;

  @InjectMocks
  private HardwareController hardwareController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAll() {
    List<HardwareDTO> hardwareList = List.of(new HardwareDTO(), new HardwareDTO());
    when(hardwareService.findAll()).thenReturn(hardwareList);

    List<HardwareDTO> result = hardwareController.getAll();

    assertEquals(2, result.size());
    verify(hardwareService, times(1)).findAll();
  }

  @Test
  void testGetByCode_Found() {
    String code = "ABC123";
    HardwareDTO hardwareDTO = new HardwareDTO();
    when(hardwareService.findByCode(code)).thenReturn(Optional.of(hardwareDTO));

    HardwareDTO result = hardwareController.getByCode(code);

    assertEquals(hardwareDTO, result);
    verify(hardwareService, times(1)).findByCode(code);
  }

  @Test
  void testGetByCode_NotFound() {
    String code = "XYZ999";
    when(hardwareService.findByCode(code)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> hardwareController.getByCode(code));

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("Hardware was not found by that code", exception.getReason());
    verify(hardwareService, times(1)).findByCode(code);
  }

  @Test
  void testSave_Success() {
    HardwareDTO hardwareDTO = new HardwareDTO();
    when(hardwareService.save(hardwareDTO)).thenReturn(Optional.of(hardwareDTO));

    HardwareDTO result = hardwareController.save(hardwareDTO);

    assertEquals(hardwareDTO, result);
    verify(hardwareService, times(1)).save(hardwareDTO);
  }

  @Test
  void testSave_Conflict() {
    HardwareDTO hardwareDTO = new HardwareDTO();
    when(hardwareService.save(hardwareDTO)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> hardwareController.save(hardwareDTO));

    assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    assertEquals("Hardware with the same code already exists", exception.getReason());
    verify(hardwareService, times(1)).save(hardwareDTO);
  }

  @Test
  void testUpdate_Success() {
    String code = "ABC123";
    HardwareDTO updatedHardwareDTO = new HardwareDTO();
    when(hardwareService.update(code, updatedHardwareDTO)).thenReturn(Optional.of(updatedHardwareDTO));

    HardwareDTO result = hardwareController.update(code, updatedHardwareDTO);

    assertEquals(updatedHardwareDTO, result);
    verify(hardwareService, times(1)).update(code, updatedHardwareDTO);
  }

  @Test
  void testUpdate_NotFound() {
    String code = "XYZ999";
    HardwareDTO updatedHardwareDTO = new HardwareDTO();
    when(hardwareService.update(code, updatedHardwareDTO)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> hardwareController.update(code, updatedHardwareDTO));

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("Hardware was not found by that code", exception.getReason());
    verify(hardwareService, times(1)).update(code, updatedHardwareDTO);
  }

  @Test
  void testDelete() {
    String code = "ABC123";
    doNothing().when(hardwareService).deleteByCode(code);

    hardwareController.delete(code);

    verify(hardwareService, times(1)).deleteByCode(code);
  }
}

