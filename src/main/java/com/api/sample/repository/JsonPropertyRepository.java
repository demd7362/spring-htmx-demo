package com.api.sample.repository;

import com.api.sample.entity.user.JsonProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JsonPropertyRepository extends JpaRepository<JsonProperty, Long> {
    List<JsonProperty> findAllByJsonPropertyParentIdAndJsonPropertyParentUserId(Long jsonPropertyParentId, Long userId);
}
