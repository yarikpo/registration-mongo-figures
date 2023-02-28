package ua.clamor1s.registrationmongofigures.dao;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import ua.clamor1s.registrationmongofigures.dto.FigureDto;
import ua.clamor1s.registrationmongofigures.dto.NameStatisticDto;
import ua.clamor1s.registrationmongofigures.repository.FigureRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class RegisterDao {

    private final MongoTemplate mongoTemplate;
    @Autowired
    private FigureRepository repository;

    public void clearDatabase() {
        mongoTemplate.remove(new Query(), "figures");
    }

    public void fillDatabaseFromInputStream(InputStream is) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        try {
            String json;

            while ((json = bf.readLine()) != null) {
                builder.append(json);
            }
        }
        finally {
            bf.close();
        }

        List<Document> documentList = convertJsonStringToDocumentList(builder.toString());
        mongoTemplate.insert(documentList, "figures");
    }

    public List<FigureDto> getFiguresByFullName(String fullName) {
        return repository.findByFullNameIncludeFullNameAndFullNameEnAndIsPep(fullName)
                .toList();
    }

    public List<NameStatisticDto> getTopPublicNames(final int limit) {
        return repository.getTopPublicNames(limit);
    }

    private List<Document> convertJsonStringToDocumentList(String json) {
        List<Document> documentList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonDocument = jsonArray.getJSONObject(i);
            documentList.add(Document.parse(jsonDocument.toString()));
        }

        return documentList;
    }

}
