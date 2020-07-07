package com.lastexceed.lastmod.feature

import com.lastexceed.lastmod.MC
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.inventory.ClickType
import net.minecraft.item.ItemStack

object FullHotbar {
	private const val ITEMSIZE = 20
	private const val MISALIGNMENT = 2
	fun render(width: Int, height: Int) {
		RenderHelper.enableGUIStandardItemLighting()
		val hotbarLeft = width/2 - (4.5* ITEMSIZE).toInt() + MISALIGNMENT

		//inventory
		for (row in 1..3) {
			for (column in 0..8) {
				renderItem(
						MC.player.inventory.mainInventory[row * 9 + column],
						hotbarLeft + column * ITEMSIZE,
						height - (5 - row) * ITEMSIZE
				)
			}
		}

		//armor
//		for (i in 3 downTo 0) {
//			renderItem(
//					MC.player.inventory.armorItemInSlot(i),
//					hotbarLeft + i*ITEMSIZE,
//					height - 8*ITEMSIZE
//			)
//		}

		//offhand
//		renderItem(
//				MC.player.inventory.offHandInventory[0],
//				hotbarLeft + 4*ITEMSIZE,
//				height - 8*ITEMSIZE
//		)

		//extra slots
		for	(i in 0..8) {
			renderItem(
					MC.player.inventoryContainer.inventorySlots[i].stack,
					hotbarLeft + i * ITEMSIZE,
					height - 5 * ITEMSIZE
			)
		}
		renderItem(
				MC.player.inventory.itemStack,
				width - ITEMSIZE,
				0
		)
		RenderHelper.disableStandardItemLighting()
//		for (i in 0..11) {
//			for (j in 0..8) {
//				try {
//					renderItem(
//							MC.player.inventory.getStackInSlot(i*9+j),
//							j*ITEMSIZE,
//							i*ITEMSIZE + 3*ITEMSIZE
//					)
//				}
//				catch (ex: Exception) {
//
//				}
//			}
//		}
	}

	private fun renderItem(itemStack: ItemStack, x: Int, y: Int) {
		MC.renderItem.renderItemAndEffectIntoGUI(itemStack, x, y)
		MC.renderItem.renderItemOverlayIntoGUI(MC.fontRenderer, itemStack, x, y, null)
	}

	fun cycleItems(column: Int, directionUp: Boolean) {
		// 0-8 is crafting stuff (yeah, frigging inventorycontainer ordering...)
		val rowsToCycle = if (directionUp) 1..3 else 3 downTo 1
		for	(row in rowsToCycle) {
			MC.playerController.windowClick(
					MC.player.inventoryContainer.windowId,
					column + row*9,
					column,
					ClickType.SWAP,
					MC.player
			)
		}
	}
}