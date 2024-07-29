package com.api.sample.entity.user;

import com.api.sample.entity.common.CommonProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "json_property")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JsonProperty extends CommonProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String attribute;
    @Column(nullable = false, columnDefinition = "varchar(35)")
    private String type;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private JsonPropertyParent jsonPropertyParent;

}
