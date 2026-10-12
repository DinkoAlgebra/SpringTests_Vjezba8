package hr.java.vjezbe.hardwareapp.repository;

import hr.java.vjezbe.hardwareapp.domain.Hardware;
import hr.java.vjezbe.hardwareapp.domain.Type;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface SpringDataTypeRepository extends JpaRepository<Type, Integer> {
  Type findByName(String name);
}
