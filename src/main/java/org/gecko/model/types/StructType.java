package org.gecko.model.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Value
public class StructType extends UserDefinedType {
    Set<StateVar> stateVars;

    @JsonCreator
    public StructType(@JsonProperty("name") String name, @JsonProperty("stateVars") Set<StateVar> stateVars) {
        super(name);
        this.stateVars = Collections.unmodifiableSet(stateVars);
    }
}