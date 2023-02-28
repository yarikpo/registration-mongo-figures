package ua.clamor1s.registrationmongofigures.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ua.clamor1s.registrationmongofigures.dto.FigureDto;

import java.util.stream.Stream;

public interface FigureRepository extends MongoRepository<FigureDto, String>, FigureRepositoryCustom {

    @Query(value = "{ 'full_name': ?0 }", fields = "{ 'full_name': 1, 'full_name_en': 1, 'is_pep': 1 }")
    Stream<FigureDto> findByFullNameIncludeFullNameAndFullNameEnAndIsPep(String fullName);

}
