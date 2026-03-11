package vn.com.eduhub.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import vn.com.eduhub.controller.req.CommonSearchReq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SearchHelper {

    private final MongoTemplate mongoTemplate;

    public SearchHelper(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public <T> SearchResult<T> search(CommonSearchReq req, Class<T> entityClass) {
        return search(req, entityClass, null);
    }

    public <T> SearchResult<T> search(CommonSearchReq req, Class<T> entityClass, CriteriaCustomizer customizer) {
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("created_date")));

        applyPagination(query, req);

        String searchType = req.getSearchType();

        if ("ALL".equals(searchType)) {
            // no extra criteria
        } else if ("FIELD".equals(searchType) && req.getParams() != null) {
            List<Criteria> criteriaList = new ArrayList<>();

            if (customizer != null) {
                customizer.customize(req.getParams(), criteriaList);
            } else {
                buildDefaultCriteria(req.getParams(), criteriaList);
            }

            if (!criteriaList.isEmpty()) {
                query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
            }
        }

        List<T> items = mongoTemplate.find(query, entityClass);

        Query countQuery = Query.of(query).skip(0).limit(0);
        long total = mongoTemplate.count(countQuery, entityClass);

        return new SearchResult<>(total, items);
    }

    private void applyPagination(Query query, CommonSearchReq req) {
        if (req.getPage() != null && req.getPage() > 0 && req.getPageSize() != null && req.getPageSize() > 0) {
            query.skip((long) (req.getPage() - 1) * req.getPageSize());
            query.limit(req.getPageSize());
        }
    }

    private void buildDefaultCriteria(Map<String, Object> params, List<Criteria> criteriaList) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                criteriaList.add(Criteria.where(key).regex(String.valueOf(value), "i"));
            } else {
                criteriaList.add(Criteria.where(key).is(value));
            }
        }
    }

    public record SearchResult<T>(long total, List<T> items) {}

    @FunctionalInterface
    public interface CriteriaCustomizer {
        void customize(Map<String, Object> params, List<Criteria> criteriaList);
    }
}
