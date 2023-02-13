package pe.todotic.bookstoreapi_s2.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

// TODO 4: usar la anotación de lombok para generar los getters, setters, el método equals y hashCode,
//  toString y el constructor con argumentos requeridos.
@Data
// TODO 5: usar la anotación para especificar la clase como una entidad.
@Entity
public class Book {
    // TODO 6: usar la anotación para especificar el campo como llave primaria.
    @Id
    // TODO 7: usar la anotación para generar de forma automática el id usando la estrategia IDENTITY
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String slug;
    @Column(name = "description")
    private String desc;
    private Float price;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void initCreatedAt(){
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void iniUpdatedAt(){
        updatedAt = LocalDateTime.now();
    }

}
