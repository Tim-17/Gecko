package org.gecko.actions;

import org.gecko.viewmodel.SelectionManager;

public class SelectionHistoryForwardAction extends Action {

    public SelectionHistoryForwardAction(SelectionManager selectionManager) {
    }

    @Override
    void run() {
    }

    @Override
    Action getUndoAction(ActionFactory actionFactory) {
        return null;
    }
}
