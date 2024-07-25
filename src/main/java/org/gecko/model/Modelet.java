package org.gecko.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.gecko.exceptions.MissingViewModelElementException;
import org.gecko.exceptions.ModelException;
import org.gecko.viewmodel.ViewModelFactory;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = State.class, name = "state"),
        @JsonSubTypes.Type(value = Region.class, name = "region")
})
public abstract class Modelet extends Element implements Renamable {
    protected Modelet(int id) throws ModelException {
        super(id);
    }

    public abstract void createModeletViewModel(ViewModelFactory viewModelFactory)
            throws MissingViewModelElementException;
}
