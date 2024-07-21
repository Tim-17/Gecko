package org.gecko.actions;

import org.gecko.exceptions.GeckoException;
import org.gecko.viewmodel.GeckoViewModel;
import org.gecko.viewmodel.StateViewModel;
import org.gecko.viewmodel.SystemViewModel;

/**
 * A concrete representation of an {@link Action} that sets a {@link StateViewModel} as start state in the current
 * {@link SystemViewModel}. Additionally, holds the previous start-{@link StateViewModel}.
 */
public class AddStartStateViewModelElementAction extends Action {
    private final GeckoViewModel geckoViewModel;
    private final StateViewModel stateViewModel;

    AddStartStateViewModelElementAction(GeckoViewModel geckoViewModel, StateViewModel stateViewModel) {
        this.geckoViewModel = geckoViewModel;
        this.stateViewModel = stateViewModel;
    }

    @Override
    boolean run() throws GeckoException {
        SystemViewModel systemViewModel = geckoViewModel.getCurrentEditor().getCurrentSystem();
        systemViewModel.addStartState(stateViewModel);
        systemViewModel.updateTarget();
        return true;
    }

    @Override
    Action getUndoAction(ActionFactory actionFactory) {
        return actionFactory.createRemoveStartStateViewModelElementAction(stateViewModel);
    }
}
