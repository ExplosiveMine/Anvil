package me.explosivemine.anvil.version;

import me.explosivemine.anvil.version.abstraction.VersionWrapper;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;

public class VersionMatcher {
    private final String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

    private final List<Class<? extends VersionWrapper>> versions = Arrays.asList(
            Wrapper1_8_R1.class, Wrapper1_8_R2.class, Wrapper1_8_R3.class,
            Wrapper1_9_R1.class, Wrapper1_9_R2.class, Wrapper1_10_R1.class,
            Wrapper1_11_R1.class, Wrapper1_12_R1.class, Wrapper1_13_R1.class,
            Wrapper1_13_R2.class, Wrapper1_14_R1.class, Wrapper1_15_R1.class,
            Wrapper1_16_R1.class, Wrapper1_16_R2.class, Wrapper1_16_R3.class,
            Wrapper1_17_R1.class
        );

    public VersionWrapper match() {
        try {
            return versions.stream().filter(version -> version.getSimpleName().substring(7).equals(serverVersion))
                    .findFirst().orElseThrow(() -> new RuntimeException("Your server version is not supported by this plugin!"))
                    .newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }
}