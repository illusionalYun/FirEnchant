package top.catnies.firenchantkt.engine.actions

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import org.bukkit.entity.Player
import top.catnies.firenchantkt.engine.AbstractAction
import top.catnies.firenchantkt.engine.ArgumentKey

class PlaySoundAction(
    args: Map<String, Any?>
) : AbstractAction(args) {

    @ArgumentKey(["player"], autoInject = true)
    private lateinit var player: Player

    @ArgumentKey(["sound"])
    private lateinit var sound: String

    @ArgumentKey(["source"], required = false)
    private var source = Sound.Source.MASTER

    @ArgumentKey(["volume"], required = false)
    private var volume = 1.0f

    @ArgumentKey(["pitch"], required = false)
    private var pitch = 1.0f

    override fun execute() {
        val sound = Sound.sound(Key.key(sound), source, volume, pitch)
        player.playSound(sound)
    }

}