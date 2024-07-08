package net.g_mungus.vscrumbles.config;

import io.wispforest.owo.config.annotation.*;

import java.util.Arrays;
import java.util.List;


@Modmenu(modId = "vscrumbles")
@Config(name = "vscrumbles-config", wrapperName = "VSCrumblesConfig")
public class ConfigModel {
    public int maxBlocks = 200;
    public int maxBlocksSpecial = 20;
    public List<String> specialBlocks = List.of(
            "vs_eureka:oak_ship_helm",
            "vs_eureka:spruce_ship_helm",
            "vs_eureka:birch_ship_helm",
            "vs_eureka:jungle_ship_helm",
            "vs_eureka:acacia_ship_helm",
            "vs_eureka:dark_oak_ship_helm",
            "vs_eureka:crimson_ship_helm",
            "vs_eureka:warped_ship_helm"
    );

}