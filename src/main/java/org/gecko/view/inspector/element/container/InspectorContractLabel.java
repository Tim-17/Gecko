package org.gecko.view.inspector.element.container;

import org.gecko.actions.ActionManager;
import org.gecko.view.ResourceHandler;
import org.gecko.view.inspector.builder.AbstractInspectorBuilder;
import org.gecko.view.inspector.element.button.InspectorAddContractButton;
import org.gecko.view.inspector.element.label.InspectorLabel;
import org.gecko.viewmodel.StateViewModel;

/**
 * Represents a type of {@link LabeledInspectorElement}. Contains an {@link InspectorLabel} and an
 * {@link InspectorAddContractButton}.
 */
public class InspectorContractLabel extends LabeledInspectorElement {
    private static final String CONTRACTS_KEY = "contract_plural";

    public InspectorContractLabel(ActionManager actionManager, StateViewModel viewModel) {
        super(new InspectorLabel(ResourceHandler.getString(AbstractInspectorBuilder.INSPECTOR, CONTRACTS_KEY)),
            new InspectorAddContractButton(actionManager, viewModel));
    }
}
