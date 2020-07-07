package com.lastexceed.lastmod

import com.lastexceed.lastmod.feature.*
import net.minecraft.client.gui.*
import net.minecraftforge.client.event.MouseEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

import org.lwjgl.input.Keyboard

class LastModEventHandler {
	@SubscribeEvent
	fun renderBars(event: RenderGameOverlayEvent.Pre) {
		when (event.type) {
			ElementType.HEALTH,
			ElementType.FOOD,
			ElementType.AIR,
			ElementType.ARMOR,
			ElementType.EXPERIENCE,
			ElementType.HEALTHMOUNT,
			ElementType.PORTAL,
			ElementType.JUMPBAR -> {
				event.isCanceled = true
			}
			else -> {}
		}
	}

	fun onRenderWorldLastEvent(event: RenderWorldLastEvent) {

	}

	@SubscribeEvent
	fun handleGameUpdate(event: TickEvent.RenderTickEvent) {
		if (MC.ingameGUI == null || !MC.inGameHasFocus) return
		LastLog.reset()

		val res = ScaledResolution(MC)
		val width = res.scaledWidth
		val height = res.scaledHeight

		//MC.mcProfiler.startSection("statBars")
		StatBars.render(width, height)
		BreakOverlay.render(width, height)
		Hitbox.render()
		//MC.mcProfiler.endSection()

		MC.mcProfiler.startSection("actionBar")
		try	{
			FullHotbar.render(width, height)
		}
		catch (ex: Exception) {
			//just to prevent unexpected crashes
		}

		MC.mcProfiler.endSection()
		AntiScaffold.onTick()
	}

	@SubscribeEvent
	fun handleMouseScroll(event: MouseEvent) {
		val wheelRotation = event.dwheel
		if (!Keyboard.isKeyDown(29) || wheelRotation == 0) {
			return
		}
		event.isCanceled = true
		FullHotbar.cycleItems(MC.player.inventory.currentItem, wheelRotation > 0)
	}
}
