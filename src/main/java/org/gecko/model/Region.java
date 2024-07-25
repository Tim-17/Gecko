package org.gecko.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.gecko.exceptions.MissingViewModelElementException;
import org.gecko.exceptions.ModelException;
import org.gecko.viewmodel.ViewModelFactory;

/**
 * Represents a region in the domain model of a Gecko project. A {@link Region} has a name and is described by a set of
 * {@link State}s, a {@link Contract} and an invariant-{@link Condition}. Contains methods for managing the afferent
 * data.
 */
@Getter
@Setter(onParam_ = {@NonNull})
public class Region extends Modelet implements Renamable {
    private String name;
    private Condition invariant;
    private final Contract preAndPostCondition;
    private final Set<State> states;
    private final Set<Contract> contracts;

    @JsonCreator
    public Region(
        @JsonProperty("id") int id, @JsonProperty("name") String name,
        @JsonProperty("invariant") @NonNull Condition invariant,
        @JsonProperty("preAndPostCondition") @NonNull Contract preAndPostCondition) throws ModelException {
        super(id);
        setName(name);
        this.states = new HashSet<>();
        this.invariant = invariant;
        this.preAndPostCondition = preAndPostCondition;
        this.contracts = new HashSet<>();
    }

    @Override
    public void setName(@NonNull String name) throws ModelException {
        if (name.isEmpty()) {
            throw new ModelException("Region's name is invalid.");
        }
        this.name = name;
    }

    @Override
    public void accept(ElementVisitor visitor) throws ModelException, MissingViewModelElementException {
        visitor.visit(this);
    }

    public void addContract(@NonNull Contract contract) {
        contracts.add(contract);
    }

    public void addContracts(@NonNull Set<Contract> contracts) {
        for (Contract contract : contracts) {
            addContract(contract);
        }
    }

    public void removeContract(@NonNull Contract contract) {
        contracts.remove(contract);
    }

    public void removeContracts(@NonNull Set<Contract> contracts) {
        for (Contract contract : contracts) {
            removeContract(contract);
        }
    }

    public void addState(@NonNull State state) {
        states.add(state);
    }

    public void addStates(@NonNull Set<State> states) {
        for (State state : states) {
            addState(state);
        }
    }

    public void removeState(@NonNull State state) {
        states.remove(state);
    }

    public void removeStates(@NonNull Set<State> states) {
        for (State state : states) {
            removeState(state);
        }
    }

    @Override
    public void createModeletViewModel(ViewModelFactory viewModelFactory) throws MissingViewModelElementException {
        viewModelFactory.createRegionViewModelFrom(this);
    }
}
