package org.gecko.model.types;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
public abstract class UserDefinedType extends ModelType {
    private final String name;
}
