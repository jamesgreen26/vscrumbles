package net.g_mungus.vscrumbles.config;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class VSCrumblesConfig extends ConfigWrapper<net.g_mungus.vscrumbles.config.ConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.lang.Integer> maxBlocks = this.optionForKey(this.keys.maxBlocks);

    private VSCrumblesConfig() {
        super(net.g_mungus.vscrumbles.config.ConfigModel.class);
    }

    private VSCrumblesConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(net.g_mungus.vscrumbles.config.ConfigModel.class, janksonBuilder);
    }

    public static VSCrumblesConfig createAndLoad() {
        var wrapper = new VSCrumblesConfig();
        wrapper.load();
        return wrapper;
    }

    public static VSCrumblesConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new VSCrumblesConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public int maxBlocks() {
        return maxBlocks.value();
    }

    public void maxBlocks(int value) {
        maxBlocks.set(value);
    }


    public static class Keys {
        public final Option.Key maxBlocks = new Option.Key("maxBlocks");
    }
}

