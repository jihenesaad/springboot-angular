package tn.spring.pispring.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter

public class AbonnementDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String name;

    private Long price;


    private String decription;


    private byte[] byteImg;

    private Long categoryId;
    private String categoryName;

    private Long quantity;

   // private MultipartFile img;
}
