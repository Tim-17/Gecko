package org.gecko.tools;

import javafx.geometry.Point2D;
import javafx.scene.control.ScrollPane;
import org.gecko.actions.Action;
import org.gecko.actions.ActionManager;

public class SystemCreatorTool extends Tool {

    private static final String NAME = "System Creator Tool";

    public SystemCreatorTool(ActionManager actionManager) {
        super(actionManager);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getIconPath() {
        //TODO stub
        return null;
    }

    @Override
    public void visitView(ScrollPane view) {
        super.visitView(view);
        view.setOnMouseClicked(event -> {
            if (event.isConsumed())
                return;
            Point2D position = new Point2D(event.getX() - view.getWidth() / 2, event.getY() - view.getHeight() / 2);
            Action createSystemAction = actionManager.getActionFactory().createCreateSystemViewModelElementAction(position);
            actionManager.run(createSystemAction);
        });
    }
}
