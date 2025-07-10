package top.catnies.firenchantkt.engine

import org.bukkit.Bukkit
import top.catnies.firenchantkt.api.ServiceContainer
import top.catnies.firenchantkt.api.event.ActionRegisterEvent
import top.catnies.firenchantkt.engine.actions.SendMessageAction
import top.catnies.firenchantkt.language.MessageConstants.ENGINE_ACTION_NOT_FOUND
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent

class FirActionRegistry: ActionRegistry {

    companion object {
        val instance by lazy { FirActionRegistry().also { it.load() }
        }
    }

    private val registry = mutableMapOf<String, Class<out Action>>()

    fun load() {
        registerAction("send_message", SendMessageAction::class.java)

        ServiceContainer.register(ActionRegistry::class.java, this)
        Bukkit.getPluginManager().callEvent(ActionRegisterEvent(this))
    }

    override fun registerAction(
        name: String,
        action: Class<out Action>
    ) {
        registry.putIfAbsent(name, action)
    }

    override fun unregisterAction(name: String): Boolean {
        return registry.remove(name) != null
    }

    override fun getAction(name: String): Class<out Action>? {
        if (registry[name] == null) {
            Bukkit.getConsoleSender().sendTranslatableComponent(ENGINE_ACTION_NOT_FOUND, name)
        }
        return registry[name]
    }

}