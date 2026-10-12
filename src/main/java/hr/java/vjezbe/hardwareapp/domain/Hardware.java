package hr.java.vjezbe.hardwareapp.domain;

import hr.java.vjezbe.hardwareapp.dto.HardwareDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hardware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "typeId")
    private Type type;
    private String code;
    private long stock;
    private BigDecimal price;

    public Hardware(HardwareDTO hardwareDTO) {
        this.name = hardwareDTO.getName();
        this.type = hardwareDTO.getType();
        this.code = hardwareDTO.getCode();
        this.stock = hardwareDTO.getStock();
        this.price = hardwareDTO.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hardware hardware = (Hardware) o;
        return stock == hardware.stock && Objects.equals(name, hardware.name) && type == hardware.type && Objects.equals(code, hardware.code) && Objects.equals(price, hardware.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, code, stock, price);
    }
}
