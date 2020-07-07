package com.lastexceed.lastmod.feature

import com.lastexceed.lastmod.LastLog
import com.lastexceed.lastmod.MC
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d

object AntiScaffold {
	private var previous = ""
	fun onTick() {
		if (MC.player.heldItemOffhand.item != Items.FLINT_AND_STEEL) {
			return
		}
		LastLog.log("Anti-Scaffold is active")
		//MC.player.position is offset by 0.5 so we need to calculate it ourselves
		val ground = BlockPos(MC.player.posX, MC.player.posY, MC.player.posZ).down()
		if (!ground.isTNT()) {
			return
		}

		val ebb = MC.player.entityBoundingBox
		val overhangWest = (ebb.minX+0.1).toInt() < MC.player.posX.toInt()
		val overhangEast = (ebb.maxX-0.1).toInt() > MC.player.posX.toInt()
		val overhangNorth = (ebb.minZ+0.1).toInt() < MC.player.posZ.toInt()
		val overhangSouth = (ebb.maxZ-0.1).toInt() > MC.player.posZ.toInt()

		if ((overhangWest && ground.west().isTNT()) ||
			(overhangEast && ground.east().isTNT()) ||
			(overhangNorth && ground.north().isTNT()) ||
			(overhangSouth && ground.south().isTNT())) {
			MC.playerController.processRightClickBlock(MC.player, MC.world, ground, EnumFacing.DOWN, Vec3d.ZERO, EnumHand.OFF_HAND)
		}
	}

	private fun BlockPos.isTNT(): Boolean {
		return MC.world.getBlockState(this).block == Blocks.TNT
	}
}