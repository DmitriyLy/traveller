package org.dmly.traveller.geography.binding;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

public class ComponentFeature implements Feature {
    @Override
    public boolean configure(FeatureContext featureContext) {
        featureContext.register(new ComponentBinder());
        return true;
    }
}
