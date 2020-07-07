package com.lastexceed.lastmod

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(
		modid = "lastmod",
		name = "Last Mod",
		version = "1.3.1",
		canBeDeactivated = false,
		clientSideOnly = true,
		acceptedMinecraftVersions = "1.12.2"
)
class LastMod {
	@EventHandler
	fun init(event: FMLInitializationEvent) {
		MinecraftForge.EVENT_BUS.register(LastModEventHandler())
	}
}
