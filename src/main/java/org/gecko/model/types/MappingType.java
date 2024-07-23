package org.gecko.model.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class MappingType extends ModelType {
    ModelType from;
    ModelType to;

    @JsonCreator
    public MappingType(@JsonProperty("from") ModelType from, @JsonProperty("to") ModelType to) {
        this.from = from;
        this.to = to;
    }
}
