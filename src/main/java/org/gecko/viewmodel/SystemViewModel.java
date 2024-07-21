package org.gecko.viewmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.gecko.exceptions.ModelException;
import org.gecko.model.State;
import org.gecko.model.System;

/**
 * Represents an abstraction of a {@link System} model element. A {@link SystemViewModel} is described by a code snippet
 * and a set of {@link PortViewModel}s. Contains methods for managing the afferent data and updating the
 * target-{@link System}.
 */
@Getter
@Setter
public class SystemViewModel extends BlockViewModelElement<System> {
    private final StringProperty codeProperty;
    private final ListProperty<PortViewModel> portsProperty;
    private final Set<StateViewModel> startStates;

    private static final Point2D DEFAULT_SYSTEM_SIZE = new Point2D(300, 300);

    public SystemViewModel(int id, @NonNull System target) {
        super(id, target);
        this.codeProperty = new SimpleStringProperty(target.getCode());
        this.portsProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.sizeProperty.setValue(DEFAULT_SYSTEM_SIZE);
        this.startStates = new HashSet<>();
    }

    public List<PortViewModel> getPorts() {
        return new ArrayList<>(portsProperty);
    }

    public String getCode() {
        return codeProperty.getValue();
    }

    public void setCode(String code) {
        codeProperty.setValue(code);
    }

    @Override
    public void updateTarget() throws ModelException {
        super.updateTarget();
        target.setCode(getCode());
        target.getVariables().clear();
        target.addVariables(portsProperty.stream().map(PortViewModel::getTarget).collect(Collectors.toSet()));
        Set<State> startStates = this.startStates
                .stream()
                .map(StateViewModel::getTarget)
                .collect(Collectors.toSet());
        target.getAutomaton().setStartStates(startStates);
    }

    public void addPort(@NonNull PortViewModel port) {
        portsProperty.add(port);
        port.getSystemPositionProperty().bind(positionProperty);
    }

    public void removePort(@NonNull PortViewModel port) {
        portsProperty.remove(port);
        port.getSystemPositionProperty().unbind();
    }

    /**
     * Adds a start state to the automaton of the system.
     *
     * @param startState the added start state
     */
    public void addStartState(StateViewModel startState) {
        if (startState == null) {
            return;
        }
        startState.setStartState(true);
        this.startStates.add(startState);
    }

    public void removeStartState(StateViewModel startState) {
        if (startState == null) {
            return;
        }
        startState.setStartState(false);
        this.startStates.remove(startState);
    }

    @Override
    public <S> S accept(@NonNull PositionableViewModelElementVisitor<S> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemViewModel system)) {
            return false;
        }
        return id == system.id;
    }
}
