package spring.togglz.toggle.repository;


import org.togglz.core.Feature;

public enum FeatureToggles implements Feature {
    DUMMY_TOGGLE_1("DUMMY_TOGGLE_1"),
    DUMMY_TOGGLE_2("DUMMY_TOGGLE_2"),
    DUMMY_TOGGLE_3("DUMMY_TOGGLE_3");

    private final String name;

    FeatureToggles(String name) {
        this.name = name;
    }

    public String getFeatureName() {
        return name;
    }
}

