package com.lastexceed.lastmod.feature

import com.lastexceed.lastmod.MC
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import kotlin.math.cos
import kotlin.math.sin

object BreakOverlay {
	fun render(width: Int, height: Int) {
		val progress = MC.playerController.curBlockDamageMP
		val segments = 1000
		val radius = 75.0
		val centerX = width/2.0
		val centerY = height/2.0

		var oldX = 0.0
		var oldY = 0.0
		for (i in 0 ..(segments*progress).toInt()) {
			val angle = Math.PI*2/segments.toFloat()*i
			val newX = sin(angle) *radius
			val newY = -cos(angle) *radius

			val tessellator = Tessellator.getInstance()
			val bufferbuilder = tessellator.buffer
			GlStateManager.enableBlend()
			GlStateManager.disableTexture2D()
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO)
			GlStateManager.color(1f, 1f, 1f, 0.3f)
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION)
			bufferbuilder.pos(centerX + newX*0.9, centerY + newY*0.9, 0.0).endVertex()
			bufferbuilder.pos(centerX + newX, centerY + newY, 0.0).endVertex()
			bufferbuilder.pos(centerX + oldX, centerY + oldY, 0.0).endVertex()
			bufferbuilder.pos(centerX + oldX*0.9, centerY + oldY*0.9, 0.0).endVertex()
			tessellator.draw()
			GlStateManager.enableTexture2D()
			GlStateManager.disableBlend()

			oldX = newX
			oldY = newY
		}
	}
}