package org.gecko.actions;

import org.gecko.viewmodel.ContractViewModel;

public class ChangePreconditionViewModelElementAction extends Action {

    private final ContractViewModel contractViewModel;
    private final String newPrecondition;

    private final String oldPrecondition;

    public ChangePreconditionViewModelElementAction(ContractViewModel contractViewModel, String newPrecondition) {
        this.contractViewModel = contractViewModel;
        this.newPrecondition = newPrecondition;
        this.oldPrecondition = contractViewModel.getPrecondition();
    }

    @Override
    void run() {
        contractViewModel.setPrecondition(newPrecondition);
    }

    @Override
    Action getUndoAction(ActionFactory actionFactory) {
        return actionFactory.createChangePreconditionViewModelElementAction(contractViewModel, oldPrecondition);
    }
}
