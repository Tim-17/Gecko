package org.gecko.view.views.viewelement.decorator;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import org.gecko.view.views.viewelement.ViewElement;
import org.gecko.view.views.viewelement.ViewElementVisitor;

/** A concrete representation of a {@link ViewElementDecorator} following the decorator pattern for selection purposes. It holds a reference to a {@link Group} containing the drawn target and borderline {@link Path}. */
public class SelectableViewElementDecorator extends ViewElementDecorator {

    public SelectableViewElementDecorator(ViewElement<?> decoratorTarget) {
        super(decoratorTarget);
    }

    @Override
    public Node drawElement() {
        return getDecoratorTarget().drawElement();
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            getDecoratorTarget().drawElement().getStyleClass().add("selected-border");
        } else {
            getDecoratorTarget().drawElement().getStyleClass().remove("selected-border");
        }
        super.setSelected(selected);
        getDecoratorTarget().setSelected(selected);
    }

    @Override
    public Point2D getPosition() {
        return getDecoratorTarget().getPosition();
    }


    @Override
    public void accept(ViewElementVisitor visitor) {
        visitor.visit(this);
        getDecoratorTarget().accept(visitor);
    }
}
