package org.gecko.actions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.gecko.exceptions.ModelException;
import org.gecko.model.Contract;
import org.gecko.model.Edge;
import org.gecko.model.Element;
import org.gecko.model.State;
import org.gecko.model.System;
import org.gecko.model.SystemConnection;
import org.gecko.model.Variable;
import org.gecko.viewmodel.EdgeViewModel;
import org.gecko.viewmodel.GeckoViewModel;
import org.gecko.viewmodel.PortViewModel;
import org.gecko.viewmodel.PositionableViewModelElement;
import org.gecko.viewmodel.PositionableViewModelElementVisitor;
import org.gecko.viewmodel.RegionViewModel;
import org.gecko.viewmodel.StateViewModel;
import org.gecko.viewmodel.SystemConnectionViewModel;
import org.gecko.viewmodel.SystemViewModel;

@Getter
@Setter
public class CopyPositionableViewModelElementVisitor implements PositionableViewModelElementVisitor {
    @Getter(AccessLevel.NONE)
    private GeckoViewModel geckoViewModel;
    private boolean isAutomatonCopy;
    private HashMap<Element, Element> originalToClipboard;
    private HashMap<Element, Pair<Point2D, Point2D>> elementToPosAndSize;
    @Getter
    private Set<PositionableViewModelElement<?>> failedCopies;

    public CopyPositionableViewModelElementVisitor(GeckoViewModel geckoViewModel) {
        this.geckoViewModel = geckoViewModel;
        isAutomatonCopy = geckoViewModel.getCurrentEditor().isAutomatonEditor();
        originalToClipboard = new HashMap<>();
        elementToPosAndSize = new HashMap<>();
        failedCopies = new HashSet<>();
    }

    @Override
    public Void visit(SystemViewModel systemViewModel) {
        System original = systemViewModel.getTarget();
        Pair<System, Map<Variable, Variable>> copyResult;
        System copy;
        try {
            copyResult = geckoViewModel.getGeckoModel().getModelFactory().copySystem(systemViewModel.getTarget());
        } catch (ModelException e) {
            failedCopies.add(systemViewModel);
            return null;
        }
        copy = copyResult.getKey();
        originalToClipboard.putAll(copyResult.getValue());
        elementToPosAndSize.put(original,
            new Pair<>(systemViewModel.getPosition(), systemViewModel.getSize()));
        elementToPosAndSize.put(copy,
            new Pair<>(systemViewModel.getPosition(), systemViewModel.getSize()));
        originalToClipboard.put(original, copy);
        return null;
    }

    @Override
    public Void visit(RegionViewModel regionViewModel) {
        originalToClipboard.put(regionViewModel.getTarget(),
            geckoViewModel.getGeckoModel().getModelFactory().copyRegion(regionViewModel.getTarget()));
        elementToPosAndSize.put(regionViewModel.getTarget(),
            new Pair<>(regionViewModel.getPosition(), regionViewModel.getSize()));
        return null;
    }

    @Override
    public Void visit(EdgeViewModel edgeViewModel) {
        Set<PositionableViewModelElement<?>> selection = geckoViewModel.getCurrentEditor().getSelectionManager().getCurrentSelection();
        if (selection.contains(edgeViewModel.getSource()) && selection.contains(edgeViewModel.getDestination())) {
            Edge original = edgeViewModel.getTarget();
            Edge copy = geckoViewModel.getGeckoModel().getModelFactory().copyEdge(original);
            State sourceOnClipboard = (State) originalToClipboard.get(original.getSource());
            State destinationOnClipboard = (State) originalToClipboard.get(original.getDestination());
            Contract contractOnClipboard = (Contract) originalToClipboard.get(original.getContract());
            if (sourceOnClipboard == null || destinationOnClipboard == null) {
                failedCopies.add(edgeViewModel);
                return null;
            }
            copy.setSource(sourceOnClipboard);
            copy.setDestination(destinationOnClipboard);
            copy.setContract(contractOnClipboard);
            originalToClipboard.put(original, copy);
        }
        return null;
    }

    @Override
    public Void visit(StateViewModel stateViewModel) {
        State original = stateViewModel.getTarget();
        Pair<State, Map<Contract, Contract>> copyResult = geckoViewModel.getGeckoModel().getModelFactory().copyState(original);
        State copy = copyResult.getKey();
        originalToClipboard.putAll(copyResult.getValue());
        elementToPosAndSize.put(stateViewModel.getTarget(),
            new Pair<>(stateViewModel.getPosition(), stateViewModel.getSize()));
        elementToPosAndSize.put(originalToClipboard.get(stateViewModel.getTarget()),
            new Pair<>(stateViewModel.getPosition(), stateViewModel.getSize()));
        originalToClipboard.put(original, copy);
        return null;
    }

    @Override
    public Void visit(PortViewModel portViewModel) {
        java.lang.System.out.println("actually Copying port");
        /*Variable original = portViewModel.getTarget();
        Variable copy = geckoViewModel.getGeckoModel().getModelFactory().copyVariable(original);
        originalToClipboard.put(original, copy);

        elementToPosAndSize.put(portViewModel.getTarget(),
            new Pair<>(portViewModel.getPosition(), portViewModel.getSize()));*/
        return null;
    }

    @Override
    public Void visit(SystemConnectionViewModel systemConnectionViewModel) {
        Set<PositionableViewModelElement<?>> selection = geckoViewModel.getCurrentEditor().getSelectionManager().getCurrentSelection();
        SystemViewModel sourceSystemViewModel = (SystemViewModel) geckoViewModel.getViewModelElement(geckoViewModel.getCurrentEditor().getCurrentSystem().getTarget().getChildSystemWithVariable(systemConnectionViewModel.getTarget().getSource()));
        SystemViewModel destinationSystemViewModel = (SystemViewModel) geckoViewModel.getViewModelElement(geckoViewModel.getCurrentEditor().getCurrentSystem().getTarget().getChildSystemWithVariable(systemConnectionViewModel.getTarget().getDestination()));
        if (selection.contains(sourceSystemViewModel) && selection.contains(destinationSystemViewModel)) {
            SystemConnection original = systemConnectionViewModel.getTarget();
            SystemConnection copy = geckoViewModel.getGeckoModel().getModelFactory().copySystemConnection(original);
            Variable sourceOnClipboard = (Variable) originalToClipboard.get(original.getSource());
            Variable destinationOnClipboard = (Variable) originalToClipboard.get(original.getDestination());
            if (sourceOnClipboard == null || destinationOnClipboard == null) {
                failedCopies.add(systemConnectionViewModel);
                return null;
            }
            try {
                copy.setSource(sourceOnClipboard);
                copy.setDestination(destinationOnClipboard);
            } catch (ModelException e) {
                throw new RuntimeException(e);
            }
            originalToClipboard.put(original, copy);
        }
        return null;
    }
}
