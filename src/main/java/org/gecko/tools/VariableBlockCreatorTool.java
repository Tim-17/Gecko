package org.gecko.tools;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import org.gecko.actions.Action;
import org.gecko.actions.ActionManager;

public class VariableBlockCreatorTool extends Tool {

    public VariableBlockCreatorTool(ActionManager actionManager) {
        super(actionManager, ToolType.VARIABLE_BLOCK_CREATOR);
    }

    @Override
    public void visitView(ScrollPane view) {
        super.visitView(view);
        view.setCursor(Cursor.CROSSHAIR);
        view.setOnMouseClicked(event -> {
            Point2D position = new Point2D(event.getX(), event.getY());
            Action createVariableBlockAction = actionManager.getActionFactory().createCreateVariableAction(position);
            actionManager.run(createVariableBlockAction);
        });
    }
}
