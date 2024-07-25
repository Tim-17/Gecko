package org.gecko.actions;

import javafx.geometry.Point2D;
import org.gecko.exceptions.GeckoException;
import org.gecko.model.State;
import org.gecko.view.views.viewelement.decorator.ElementScalerBlock;
import org.gecko.viewmodel.*;

/**
 * A concrete representation of an {@link Action} that moves an {link EdgeViewModelElement} with a given
 * {@link Point2D delta value}.
 */
public class MoveEdgeViewModelElementAction extends Action {

    private final GeckoViewModel geckoViewModel;
    private final EditorViewModel editorViewModel;
    private final EdgeViewModel edgeViewModel;
    private final ElementScalerBlock elementScalerBlock;
    private Point2D delta;
    private ModeletViewModel modeletViewModel;
    private ModeletViewModel previousModeletViewModel;
    private ContractViewModel contractViewModel;
    private ContractViewModel previousContractViewModel;

    MoveEdgeViewModelElementAction(
        GeckoViewModel geckoViewModel, EdgeViewModel edgeViewModel, ElementScalerBlock elementScalerBlock,
        Point2D delta) {
        this.geckoViewModel = geckoViewModel;
        this.editorViewModel = geckoViewModel.getCurrentEditor();
        this.edgeViewModel = edgeViewModel;
        this.elementScalerBlock = elementScalerBlock;
        this.delta = delta;
    }

    MoveEdgeViewModelElementAction(
        GeckoViewModel geckoViewModel, EdgeViewModel edgeViewModel, ElementScalerBlock elementScalerBlock,
        ModeletViewModel modeletViewModel, ContractViewModel contractViewModel) {
        this.geckoViewModel = geckoViewModel;
        this.editorViewModel = geckoViewModel.getCurrentEditor();
        this.edgeViewModel = edgeViewModel;
        this.elementScalerBlock = elementScalerBlock;
        this.modeletViewModel = modeletViewModel;
        this.contractViewModel = contractViewModel;
    }


    @Override
    boolean run() throws GeckoException {
        previousModeletViewModel =
            elementScalerBlock.getIndex() == 0 ? edgeViewModel.getSource() : edgeViewModel.getDestination();
        if (modeletViewModel == null) {
            modeletViewModel = attemptRelocation();
            if (modeletViewModel == null || modeletViewModel.equals(previousModeletViewModel)) {
                edgeViewModel.setBindings();
                return false;
            }
        }

        if (elementScalerBlock.getIndex() == 0) {
            edgeViewModel.setSource(modeletViewModel);
            previousContractViewModel = edgeViewModel.getContract();
            edgeViewModel.setContract(contractViewModel);
        } else {
            edgeViewModel.setDestination((StateViewModel) modeletViewModel);
        }

        elementScalerBlock.updatePosition();
        edgeViewModel.updateTarget();
        return true;
    }

    @Override
    Action getUndoAction(ActionFactory actionFactory) {
        return actionFactory.createMoveEdgeViewModelElementAction(edgeViewModel, elementScalerBlock,
                previousModeletViewModel, previousContractViewModel);
    }

    private StateViewModel attemptRelocation() {
        return getStateViewModelAt(elementScalerBlock.getLayoutPosition().add(delta));
    }

    // TODO: maybe has to be adapted for regions as possible edge sources?
    private StateViewModel getStateViewModelAt(Point2D point) {
        for (State state : editorViewModel.getCurrentSystem().getTarget().getAutomaton().getStates()) {
            StateViewModel stateViewModel = (StateViewModel) geckoViewModel.getViewModelElement(state);
            if (point.getX() > stateViewModel.getPosition().getX()
                && point.getX() < stateViewModel.getPosition().getX() + stateViewModel.getSize().getX()
                && point.getY() > stateViewModel.getPosition().getY()
                && point.getY() < stateViewModel.getPosition().getY() + stateViewModel.getSize().getY()) {
                return stateViewModel;
            }
        }
        return null;
    }
}
