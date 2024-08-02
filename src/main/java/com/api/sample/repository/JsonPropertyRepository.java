package com.api.sample.repository;

import com.api.sample.entity.user.JsonProperty;
import com.api.sample.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JsonPropertyRepository extends JpaRepository<JsonProperty, Long> {
    List<JsonProperty> findAllByJsonPropertyParentIdAndCreatedBy(Long jsonPropertyParent_id, User createdBy);
}
