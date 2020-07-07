package com.lastexceed.lastmod

object LastLog {
	private val left = 0
	private var top = 1
	private val offset = 9
	fun reset() {
		top = 1
	}

	fun log(value: Any?) {
		MC.ingameGUI.drawString(MC.fontRenderer, value.toString(), left, top, 0xFFFF00FF.toInt())
		top += offset
	}
}