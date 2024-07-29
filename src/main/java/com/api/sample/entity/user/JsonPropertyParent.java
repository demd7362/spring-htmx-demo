package com.api.sample.entity.user;

import com.api.sample.entity.common.CommonProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "json_property_parent")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JsonPropertyParent extends CommonProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "jsonPropertyParent")
    List<JsonProperty> jsonProperties;
}
