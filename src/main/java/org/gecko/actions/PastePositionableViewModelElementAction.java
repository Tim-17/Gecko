package org.gecko.actions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.gecko.exceptions.GeckoException;
import org.gecko.model.Element;
import org.gecko.viewmodel.GeckoViewModel;
import org.gecko.viewmodel.PositionableViewModelElement;

public class PastePositionableViewModelElementAction extends Action {
    private final GeckoViewModel geckoViewModel;
    private final Set<PositionableViewModelElement<?>> pastedElements;
    private static final Point2D PASTE_OFFSET = new Point2D(50, 50);
    private CopyPositionableViewModelElementVisitor copyVisitor;

    PastePositionableViewModelElementAction(GeckoViewModel geckoViewModel) {
        this.geckoViewModel = geckoViewModel;
        pastedElements = new HashSet<>();
    }

    @Override
    boolean run() throws GeckoException {
        CopyPositionableViewModelElementVisitor copyVisitor = geckoViewModel.getActionManager().getCopyVisitor();
        if (copyVisitor == null) {
            throw new GeckoException("Invalid Clipboard. Nothing to paste.");
        }
        if (geckoViewModel.getCurrentEditor().isAutomatonEditor() != copyVisitor.isAutomatonCopy()) {
            return false;
        }

        PastePositionableViewModelElementVisitor pasteVisitor = new PastePositionableViewModelElementVisitor(geckoViewModel, copyVisitor);
        List<Element> elementsToPaste =
            new java.util.ArrayList<>(copyVisitor.getOriginalToClipboard().values().stream().toList());
        for (Element element : elementsToPaste) {
            element.accept(pasteVisitor);
        }
        while (!pasteVisitor.getUnsuccessfulPastes().isEmpty()) {
            System.out.println("Unsuccessful pastes: " + pasteVisitor.getUnsuccessfulPastes());
            Set<Element> unsuccessfulPastes = new HashSet<>(pasteVisitor.getUnsuccessfulPastes());
            pasteVisitor.getUnsuccessfulPastes().clear();
            for (Element element : unsuccessfulPastes) {
                element.accept(pasteVisitor);
            }
        }
        pastedElements.addAll(pasteVisitor.getPastedElements());
        return true;
    }

    @Override
    Action getUndoAction(ActionFactory actionFactory) {
        return actionFactory.createDeletePositionableViewModelElementAction(pastedElements);
    }
}
