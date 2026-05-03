package com.fm.alSoukBk.specification;

import com.fm.alSoukBk.model.Annonce;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class AnnonceSpecification {

    public static Specification<Annonce> search(
            String keyword,
            String category,
            String regionCode,
            Double minPrice,
            Double maxPrice
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // 🔍 keyword (title + description)
            if (keyword != null && !keyword.isBlank()) {
                String like = "%" + keyword.toLowerCase() + "%";
                predicates.add(
                        cb.or(
                                cb.like(cb.lower(root.get("title")), like),
                                cb.like(cb.lower(root.get("description")), like)
                        )
                );
            }

            // 📂 category
            if (category != null && !category.isBlank()) {
                predicates.add(cb.equal(root.get("category"), category));
            }

            // 🌍 region
            if (regionCode != null && !regionCode.isBlank()) {
                predicates.add(cb.equal(root.get("regionCode"), regionCode));
            }

            // 💰 min price
            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            // 💰 max price
            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}