package moddedmite.sitmod.api;

import java.util.Optional;

public interface SitModPlugin {
    default boolean canSitOn(SitContext context) {
        return false;
    }

    default boolean canRotateFreely(SitContext context) {
        return true;
    }

    default Optional<Float> getRotation(SitContext context) {
        return Optional.empty();
    }
}
