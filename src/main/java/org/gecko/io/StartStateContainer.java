package org.gecko.io;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StartStateContainer {
    private int systemId;
    private List<String> startStateNames;
}
