package ua.clamor1s.registrationmongofigures.repository;

import ua.clamor1s.registrationmongofigures.dto.NameStatisticDto;

import java.util.List;

public interface FigureRepositoryCustom {

    List<NameStatisticDto> getTopPublicNames(final int limit);

}
