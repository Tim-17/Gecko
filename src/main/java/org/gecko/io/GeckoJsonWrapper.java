package org.gecko.io;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO. Wraps two Json Strings: model describes the tree structure of a Gecko Model
 * TODO. and viewModelProperties describes ViewModel-specific attributes of PositionableViewModelElements
 * TODO. like position coordinates, size coordinates and color values for RegionViewModels.
 */
@Getter
@Setter
public class GeckoJsonWrapper {
    private String model;
    private String viewModelProperties;
}
