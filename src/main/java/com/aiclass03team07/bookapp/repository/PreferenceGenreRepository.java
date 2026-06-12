package com.aiclass03team07.bookapp.repository;

import com.aiclass03team07.bookapp.entity.PreferenceGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<PreferenceGenreEntity, Long> {
}
