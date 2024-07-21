package org.gecko.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.gecko.exceptions.MissingViewModelElementException;
import org.gecko.exceptions.ModelException;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a contract in the domain model of a Gecko project. A {@link Contract} has a name and is described by a
 * pre- and a post-{@link Condition}.
 */

@Getter
@Setter(onParam_ = {@NonNull})
public class Contract extends Element implements Renamable {
    private String name;
    private Condition preCondition;
    private Condition postCondition;
    private String modifies;
    private final Set<Variable> returnVariables;

    @JsonCreator
    public Contract(
        @JsonProperty("id") int id, @JsonProperty("name") String name,
        @JsonProperty("preCondition") Condition preCondition, @JsonProperty("postCondition") Condition postCondition,
        @JsonProperty("modifies") String modifies)
        throws ModelException {
        super(id);
        setName(name);
        setPreCondition(preCondition);
        setPostCondition(postCondition);
        setModifies(modifies);
        returnVariables = new HashSet<>();
    }

    @Override
    public void setName(@NonNull String name) throws ModelException {
        if (name.isEmpty()) {
            throw new ModelException("Contract's name is invalid.");
        }
        this.name = name;
    }

    @JsonIgnore
    @Override
    public String toString() {
        return name;
    }

    @Override
    public void accept(ElementVisitor visitor) throws ModelException, MissingViewModelElementException {
        visitor.visit(this);
    }

    public void addReturnVariable(Variable returnVariable) {
        if (returnVariable.getVisibility() != Visibility.OUTPUT) {
            return;
        }
        returnVariables.add(returnVariable);
    }

    public void addReturnVariables(Set<Variable> returnVariables) {
        returnVariables.forEach(this::addReturnVariable);
    }

    public void removeReturnVariable(Variable returnVariable) {
        returnVariables.remove(returnVariable);
    }

    public void removeReturnVariables(Set<Variable> returnVariables) {
        returnVariables.forEach(this::removeReturnVariable);
    }
}
