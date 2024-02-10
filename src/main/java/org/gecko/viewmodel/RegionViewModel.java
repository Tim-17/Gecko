package org.gecko.viewmodel;

import java.util.Random;
import java.util.stream.Collectors;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.gecko.exceptions.ModelException;
import org.gecko.model.Region;

/**
 * Represents an abstraction of a {@link Region} model element. A {@link RegionViewModel} is described by a
 * {@link Color}, a set of {@link StateViewModel}s, a {@link ContractViewModel} and an invariant. Contains methods for
 * managing the afferent data and updating the target-{@link Region}.
 */
@Setter
@Getter
public class RegionViewModel extends BlockViewModelElement<Region> {
    private final Property<Color> colorProperty;
    private final StringProperty invariantProperty;
    private final ObservableList<StateViewModel> statesProperty;
    private final ContractViewModel contract;

    public RegionViewModel(
        int id, @NonNull Region target, @NonNull ContractViewModel contract) {
        super(id, target);
        this.contract = contract;
        this.invariantProperty = new SimpleStringProperty(target.getInvariant().getCondition());
        this.statesProperty = FXCollections.observableArrayList();

        // TODO Alternatives: Fixed default color or random color from given palette.
        Random random = new Random(java.lang.System.currentTimeMillis());
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        this.colorProperty = new SimpleObjectProperty<>(Color.rgb(red, green, blue, 0.5));
    }

    @Override
    public void updateTarget() throws ModelException {
        super.updateTarget();
        target.getInvariant().setCondition(invariantProperty.getValue());
        target.getPreAndPostCondition().getPreCondition().setCondition(contract.getPrecondition());
        target.getPreAndPostCondition().getPostCondition().setCondition(contract.getPostcondition());
        target.getStates().clear();
        target.addStates(statesProperty.stream().map(StateViewModel::getTarget).collect(Collectors.toSet()));
    }

    public void addState(@NonNull StateViewModel state) {
        statesProperty.add(state);
    }

    public void removeState(@NonNull StateViewModel state) {
        statesProperty.remove(state);
    }

    @Override
    public Object accept(@NonNull PositionableViewModelElementVisitor visitor) {
        return visitor.visit(this);
    }

    public void setInvariant(@NonNull String invariant) {
        invariantProperty.setValue(invariant);
    }

    public String getInvariant() {
        return invariantProperty.getValue();
    }

    public void setColor(@NonNull Color color) {
        colorProperty.setValue(color);
    }

    public Color getColor() {
        return colorProperty.getValue();
    }

    public void checkStateInRegion(StateViewModel state) {
        Bounds regionBound =
            new BoundingBox(getPosition().getX(), getPosition().getY(), getSize().getX(), getSize().getY());
        boolean isStateInRegion = regionBound.contains(state.getPosition()) || regionBound.contains(
            state.getPosition().add(new Point2D(0, state.getSize().getY()))) || regionBound.contains(
            state.getPosition().add(new Point2D(state.getSize().getX(), 0))) || regionBound.contains(
            state.getPosition().add(state.getSize()));
        if (isStateInRegion) {
            addState(state);
        } else {
            removeState(state);
        }
    }
}
