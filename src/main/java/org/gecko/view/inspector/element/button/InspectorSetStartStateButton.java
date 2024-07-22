package org.gecko.view.inspector.element.button;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import org.gecko.actions.Action;
import org.gecko.actions.ActionManager;
import org.gecko.view.ResourceHandler;
import org.gecko.view.inspector.element.InspectorElement;
import org.gecko.viewmodel.StateViewModel;

/**
 * Represents a type of {@link AbstractInspectorButton} used for setting a {@link StateViewModel} as start-state.
 */
public class InspectorSetStartStateButton extends ToggleButton implements InspectorElement<ToggleButton> {
    private static final String START_STATE_STYLE = "inspector-start-state-button";

    public InspectorSetStartStateButton(ActionManager actionManager, StateViewModel stateViewModel) {
        setMaxWidth(Double.MAX_VALUE);
        setText(ResourceHandler.getString("Buttons", "set_start_state"));
        setTooltip(new Tooltip(ResourceHandler.getString("Tooltips", "set_start_state")));
        update(stateViewModel.getIsStartState());
        stateViewModel.getIsStartStateProperty().addListener((observable, oldValue, newValue) -> {
            update(newValue);
        });

        setOnAction(event -> {
            Action action = stateViewModel.getIsStartState() ?
                    actionManager.getActionFactory().createRemoveStartStateViewModelElementAction(stateViewModel)
                    : actionManager.getActionFactory().createAddStartStateViewModelElementAction(stateViewModel);
            actionManager.run(action);
        });
    }

    private void update(boolean newValue) {
        setSelected(newValue);
        if (newValue) {
            getStyleClass().add(START_STATE_STYLE);
        } else {
            getStyleClass().remove(START_STATE_STYLE);
        }
    }

    @Override
    public ToggleButton getControl() {
        return this;
    }
}
