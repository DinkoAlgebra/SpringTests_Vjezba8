package hr.java.vjezbe.hardwareapp.controller;

import hr.java.vjezbe.hardwareapp.dto.HardwareDTO;
import hr.java.vjezbe.hardwareapp.service.HardwareService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("hardware")
public class HardwareController {

    private final HardwareService hardwareService;

    public HardwareController(HardwareService hardwareService) {
        this.hardwareService = hardwareService;
    }

    @GetMapping
    public List<HardwareDTO> getAll() {
        return hardwareService.findAll();
    }

    @GetMapping("{code}")
    public HardwareDTO getByCode(@PathVariable final String code) {
        return hardwareService.findByCode(code)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hardware was not found by that code")
                );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public HardwareDTO save(@Valid @RequestBody final HardwareDTO command){
        return hardwareService.save(command)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Hardware with the same code already exists"));
    }

    @PutMapping("{code}")
    public HardwareDTO update(@PathVariable String code, @Valid @RequestBody final HardwareDTO updatedHardwareDTO){
        return hardwareService.update(code, updatedHardwareDTO)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hardware was not found by that code")
                );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{code}")
    public void delete(@PathVariable String code){
        hardwareService.deleteByCode(code);
    }

}
