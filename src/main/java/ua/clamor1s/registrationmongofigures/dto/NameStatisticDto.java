package ua.clamor1s.registrationmongofigures.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Jacksonized
@Document("figures")
public class NameStatisticDto {

    @Id
    String first_name;
    Integer count;

}
