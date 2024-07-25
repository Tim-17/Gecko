package org.gecko.view.inspector.element.container;

import org.gecko.actions.ActionManager;
import org.gecko.view.inspector.element.button.InspectorFocusButton;
import org.gecko.view.inspector.element.label.InspectorLabel;
import org.gecko.viewmodel.ModeletViewModel;
import org.gecko.viewmodel.StateViewModel;

/**
 * Represents a type of {@link LabeledInspectorElement}. Contains an {@link InspectorLabel} and an
 * {@link InspectorFocusButton} for a {@link StateViewModel}.
 */
public class InspectorEdgeStateLabel extends LabeledInspectorElement {
    public InspectorEdgeStateLabel(ActionManager actionManager, ModeletViewModel modeletViewModel, String name) {
        super(new InspectorLabel(name + ": " + modeletViewModel.getName()),
            new InspectorFocusButton(actionManager, modeletViewModel));
    }
}
