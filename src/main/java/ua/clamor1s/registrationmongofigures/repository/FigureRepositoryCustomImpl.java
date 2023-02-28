package ua.clamor1s.registrationmongofigures.repository;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import ua.clamor1s.registrationmongofigures.dto.NameStatisticDto;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class FigureRepositoryCustomImpl implements FigureRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<NameStatisticDto> getTopPublicNames(int limit) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("first_name").ne(null)
                    .and("is_pep").is(true)),
            Aggregation.group("first_name").count().as("count"),
            Aggregation.sort(Sort.Direction.DESC, "count"),
            Aggregation.limit(limit)
        );
        AggregationResults<NameStatisticDto> output = mongoTemplate.aggregate(
                aggregation,
                "figures",
                NameStatisticDto.class
        );

        return output.getMappedResults();
    }

}
