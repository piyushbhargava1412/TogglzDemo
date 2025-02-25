package spring.togglz.toggle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.togglz.toggle.repository.CachedFeatureRepository;
import spring.togglz.toggle.repository.FeatureToggles;

@RestController
@RequestMapping("/feature-toggle")
public class FeatureToggleController {

    private final CachedFeatureRepository cachedFeatureRepository;

    public FeatureToggleController(CachedFeatureRepository cachedFeatureRepository) {
        this.cachedFeatureRepository = cachedFeatureRepository;
    }

    @GetMapping("/{featureName}")
    public boolean getFeatureToggle(@PathVariable String featureName) {
        return cachedFeatureRepository.isActive(FeatureToggles.valueOf(featureName));
    }
}

