package com.beginvegan.domain.tmp.suggestion.domain.child.repository;

import com.beginvegan.domain.tmp.suggestion.domain.child.ModificationSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModificationSuggestionRepository extends JpaRepository<ModificationSuggestion, Long> {
}
