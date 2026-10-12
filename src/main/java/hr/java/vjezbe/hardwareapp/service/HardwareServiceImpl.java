package hr.java.vjezbe.hardwareapp.service;

import hr.java.vjezbe.hardwareapp.domain.Hardware;
import hr.java.vjezbe.hardwareapp.dto.HardwareDTO;
import hr.java.vjezbe.hardwareapp.repository.SpringDataHardwareRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HardwareServiceImpl implements HardwareService{

    private final SpringDataHardwareRepository hardwareRepository;

    @Override
    public List<HardwareDTO> findAll() {
        return hardwareRepository.findAll().stream().map(HardwareDTO::new).collect(Collectors.toList());
    }

    @Override
    public Optional<HardwareDTO> findByCode(String code) {
        return hardwareRepository.findByCode(code).map(HardwareDTO::new);
    }

    @Override
    public Optional<HardwareDTO> save(HardwareDTO hardwareDTO) {
        return Optional.of(new HardwareDTO(hardwareRepository.save(new Hardware(hardwareDTO))));
    }

    @Override
    public Optional<HardwareDTO> update(String code, HardwareDTO updatedHardwareDTO) {

        Optional<Hardware> hardwareDTOOptional = hardwareRepository.findByCode(code);

        if(hardwareDTOOptional.isPresent()) {
            Hardware hardwareToUpdate = hardwareDTOOptional.get();
            hardwareToUpdate.setName(updatedHardwareDTO.getName());
            hardwareToUpdate.setCode(updatedHardwareDTO.getCode());
            hardwareToUpdate.setType(updatedHardwareDTO.getType());
            hardwareToUpdate.setStock(updatedHardwareDTO.getStock());
            hardwareToUpdate.setPrice(updatedHardwareDTO.getPrice());
            Hardware updatedHardware = hardwareRepository.save(hardwareToUpdate);
            return Optional.of(new HardwareDTO(updatedHardware));
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByCode(String code) {
        Optional<Hardware> hardwareOptional = hardwareRepository.findByCode(code);
        hardwareOptional.ifPresent(hardwareRepository::delete);
    }
}
