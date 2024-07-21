package org.gecko.actions;

import org.gecko.exceptions.GeckoException;
import org.gecko.viewmodel.GeckoViewModel;
import org.gecko.viewmodel.StateViewModel;
import org.gecko.viewmodel.SystemViewModel;

public class RemoveStartStateViewModelElementAction extends Action {
    private final GeckoViewModel geckoViewModel;
    private final StateViewModel stateViewModel;

    RemoveStartStateViewModelElementAction(GeckoViewModel geckoViewModel, StateViewModel stateViewModel) {
        this.geckoViewModel = geckoViewModel;
        this.stateViewModel = stateViewModel;
    }

    @Override
    boolean run() throws GeckoException {
        SystemViewModel systemViewModel = geckoViewModel.getCurrentEditor().getCurrentSystem();
        systemViewModel.removeStartState(stateViewModel);
        systemViewModel.updateTarget();
        return true;
    }

    @Override
    Action getUndoAction(ActionFactory actionFactory) {
        return actionFactory.createAddStartStateViewModelElementAction(stateViewModel);
    }
}
