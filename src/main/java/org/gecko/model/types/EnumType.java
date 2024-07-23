package org.gecko.model.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Value
public class EnumType extends UserDefinedType {
    Set<String> consts;

    @JsonCreator
    public EnumType(@JsonProperty("name") String name, @JsonProperty("consts") Set<String> consts) {
        super(name);
        this.consts = Collections.unmodifiableSet(consts);
    }
}
