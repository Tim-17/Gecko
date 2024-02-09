package org.gecko.view.inspector.element.button;

import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import org.gecko.actions.ActionManager;
import org.gecko.view.ResourceHandler;
import org.gecko.view.views.shortcuts.Shortcuts;
import org.gecko.viewmodel.PositionableViewModelElement;

public class InspectorDeleteButton extends AbstractInspectorButton {

    private static final String STYLE = "inspector-delete-button";
    private static final int WIDTH = 300;

    public InspectorDeleteButton(ActionManager actionManager, PositionableViewModelElement<?> elementToRemove) {
        setOnAction(event -> {
            actionManager.run(
                actionManager.getActionFactory().createDeletePositionableViewModelElementAction(elementToRemove));
        });
        setText(ResourceHandler.getString("Buttons", "inspector_delete"));
        setPrefWidth(WIDTH);
        getStyleClass().add(STYLE);
        setTooltip(new Tooltip(Shortcuts.DELETE.get().getDisplayText()));
    }
}
