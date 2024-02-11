package org.gecko.application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.gecko.exceptions.ModelException;

/** Represents a manager for the active {@link Gecko}. Additionally, holds a reference to the {@link Stage} of the application.*/
public class GeckoManager {
    @Getter
    private Gecko gecko;
    private final Stage stage;

    public GeckoManager(Stage stage) throws ModelException {
        this.stage = stage;
        setGecko(new Gecko());
    }

    public void setGecko(Gecko gecko) {
        this.gecko = gecko;
        stage.setScene(new Scene(gecko.getView().getMainPane(), 1024, 768));
    }
}
