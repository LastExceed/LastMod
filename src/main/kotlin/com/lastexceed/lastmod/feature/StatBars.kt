package com.lastexceed.lastmod.feature

import com.lastexceed.lastmod.MC
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.SharedMonsterAttributes
import kotlin.math.cos
import kotlin.math.sin


object StatBars {
	private var width: Int = 0
	private var height: Int = 0

	fun render(width: Int, height: Int) {
		StatBars.width = width
		StatBars.height = height
		createBar(0, 0xff0000, 6, 0, MC.player.health, MC.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).attributeValue)
		createBar(1, 0xffff00, 18, 0, MC.player.absorptionAmount, 20f)
		createBar(2, 0x884400, 6, 3, MC.player.foodStats.foodLevel, 20)
		createBar(3, 0x0088ff, 2, 2, MC.player.air, 300)
		createBar(4, 0x888888, 5, 1, MC.player.totalArmorValue, 20)
//		val armorToughness = mc.player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).attributeValue - 1
		val xpDisplay = MC.player.experienceLevel + MC.player.experience
		createBar(5, 0xcccc00, 0, 0, MC.player.experience, 1f, "$xpDisplay")//

		val mount = MC.player.ridingEntity
		if (mount != null && mount is EntityLivingBase) {
			createBar(6, 0xff8800, 10, 1, mount.health, mount.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).attributeValue)
			createBar(7, 0x4400cc, 10, 1, MC.player.horseJumpPower, 1f)
		}
		if (MC.player.timeInPortal != 0f) {
			createBar(15, 0x880088, 16, 5, MC.player.timeInPortal, 1f)
			createBar(15, 0xff00ff, 16, 5, MC.player.prevTimeInPortal, 1f)
		}

		//TODO: hurtime
	}



	private const val BARWIDTH = 100
	private const val ICONSIZE = 9
	private fun createBar(offset: Int, color: Int, iconX: Int, iconY: Int, current: Number, max: Number, text: String = "$current") {
		val left= width /2 + 100
		val bottom = height -offset* ICONSIZE
		val fill = current.toFloat()/max.toFloat()
		Gui.drawRect(
				left,
				bottom - ICONSIZE,
				left + (BARWIDTH *fill).toInt(),
				bottom,
				color + 0xff000000.toInt()
		)
		Gui.drawRect(
				left,
				bottom - ICONSIZE,
				left + 1,
				bottom,
				0xff000000.toInt()
		)
		Gui.drawRect(
				left + BARWIDTH + 1,
				bottom - ICONSIZE,
				left + BARWIDTH,
				bottom,
				0xff000000.toInt()
		)
		Gui.drawRect(
				left,
				bottom - ICONSIZE,
				left + BARWIDTH,
				bottom - ICONSIZE + 1,
				0xff000000.toInt()
		)
		GlStateManager.color(1f,1f,1f)
		MC.textureManager.bindTexture(Gui.ICONS)
		MC.ingameGUI.drawTexturedModalRect(left - 9, height - (offset+1)* ICONSIZE, iconX* ICONSIZE - 2, iconY* ICONSIZE, ICONSIZE, ICONSIZE)
		MC.ingameGUI.drawString(MC.fontRenderer, text, left + 1, bottom - ICONSIZE + 1, 0xFFFFFFFF.toInt())
	}
}