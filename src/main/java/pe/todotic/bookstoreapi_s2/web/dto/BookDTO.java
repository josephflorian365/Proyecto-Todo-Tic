package pe.todotic.bookstoreapi_s2.web.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class BookDTO {

    private String title;
    private String slug;
    private String desc;
    private Float price;
}
