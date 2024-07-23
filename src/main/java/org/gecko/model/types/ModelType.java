package org.gecko.model.types;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IntType.class, name = "intType"),
        @JsonSubTypes.Type(value = UintType.class, name = "uintType"),
        @JsonSubTypes.Type(value = BoolType.class, name = "boolType"),
        @JsonSubTypes.Type(value = StringType.class, name = "stringType"),
        @JsonSubTypes.Type(value = AccountType.class, name = "accountType"),
        @JsonSubTypes.Type(value = ArrayType.class, name = "arrayType"),
        @JsonSubTypes.Type(value = MappingType.class, name = "mappingType"),
        @JsonSubTypes.Type(value = EnumType.class, name = "enumType"),
        @JsonSubTypes.Type(value = StructType.class, name = "structType")
})
public abstract class ModelType {}

