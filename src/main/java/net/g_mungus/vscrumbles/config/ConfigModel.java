package net.g_mungus.vscrumbles.config;

import io.wispforest.owo.config.annotation.*;


@Modmenu(modId = "vscrumbles")
@Config(name = "vscrumbles-config", wrapperName = "VSCrumblesConfig")
public class ConfigModel {
    public int maxBlocks = 5;
}