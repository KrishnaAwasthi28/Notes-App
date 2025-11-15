package com.project.NotesApp_backend;
import com.project.NotesApp_backend.Entity.Note;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NoteSpecification {

    public static Specification<Note> containsTextInTitleOrContent(String text) {
        return (root, query, cb) -> {
            if (text == null || text.trim().isEmpty()) return cb.conjunction();
            String pattern = "%" + text.trim().toLowerCase() + "%";
            Expression<String> title = cb.lower(root.get("title"));
            Expression<String> content = cb.lower(root.get("content"));
            return cb.or(cb.like(title, pattern), cb.like(content, pattern));
        };
    }
    public static Specification<Note> hasAnyTag(List<String> tagNames) {
        return (root, query, cb) -> {
            if (tagNames == null || tagNames.isEmpty()) return cb.conjunction();
            root.join("tags", JoinType.INNER);
            Expression<String> tagNameExp = cb.lower(root.join("tags").get("name"));
            CriteriaBuilder.In<String> inClause = cb.in(tagNameExp);
            for (String t : tagNames) inClause.value(t.toLowerCase());
            query.distinct(true);
            return inClause;
        };
    }
}
