package com.api.sample.repository;

import com.api.sample.entity.user.JsonPropertyParent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JsonPropertyParentRepository extends JpaRepository<JsonPropertyParent, Long> {
    List<JsonPropertyParent> findAllByUserId(long userId);
}
