package org.gecko.model.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class ArrayType extends ModelType {
    ModelType t;

    @JsonCreator
    public ArrayType(@JsonProperty("t") ModelType t) {
        this.t = t;
    }
}
