package org.gecko.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.gecko.model.Modelet;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class ModeletViewModel extends BlockViewModelElement<Modelet> {

    private final ListProperty<ContractViewModel> contractsProperty;
    private final ObservableList<EdgeViewModel> outgoingEdges;

    ModeletViewModel(int id, @NonNull Modelet target) {
        super(id, target);
        this.contractsProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.outgoingEdges = FXCollections.observableArrayList();
    }

    public abstract void setEdgeOffsets();

    public void addContract(@NonNull ContractViewModel contract) {
        getContractsProperty().add(contract);
    }

    public void removeContract(@NonNull ContractViewModel contract) {
        getContractsProperty().remove(contract);
    }

    public List<ContractViewModel> getContracts() {
        return new ArrayList<>(getContractsProperty());
    }
}
