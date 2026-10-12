package hr.java.vjezbe.hardwareapp.dto;

import hr.java.vjezbe.hardwareapp.domain.Hardware;
import hr.java.vjezbe.hardwareapp.domain.Type;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HardwareDTO {
    @NotBlank(message = "Name must not be empty")
    @Size(max = 200, message = "Name can't have more than 200 characters")
    private String name;

    @NotNull(message = "Type must be set to CPU, GPU, MBO, RAM, STORAGE or OTHER")
    private Type type;

    @NotBlank(message = "Code must not be empty")
    @Size(min = 7, max = 7, message = "Code must have 7 characters")
    private String code;

    @PositiveOrZero(message = "Stock must be positive or zero")
    @Max(value = 10000, message = "Stock can't be above 10000")
    private long stock;

    @PositiveOrZero(message = "Price must be positive or zero")
    @Digits(integer=5, fraction=2, message = "Price can't be higher than 99999")
    private BigDecimal price;

    public HardwareDTO(Hardware hardware) {
        this.name = hardware.getName();
        this.price = hardware.getPrice();
        this.code = hardware.getCode();
        this.stock = hardware.getStock();
        this.type = hardware.getType();
    }
}
