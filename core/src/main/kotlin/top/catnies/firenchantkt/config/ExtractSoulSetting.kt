package top.catnies.firenchantkt.config

import org.bukkit.Bukkit
import top.catnies.firenchantkt.language.MessageConstants.RESOURCE_MENU_STRUCTURE_ERROR
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent
import xyz.xenondevs.invui.gui.structure.Structure

class ExtractSoulSetting private constructor():
    AbstractConfigFile("modules/extract_soul.yml")
{

    companion object {
        @JvmStatic
        val instance by lazy { ExtractSoulSetting() }
    }

    val fallbackMenuStructure = arrayOf(
        "X.......?",
        "IIIIIIIII",
        "IIIIIIIII",
        "IIIIIIIII",
        ".........",
        "....O...."
    )

    /*菜单设置*/
    var MENU_TITLE: String = "Extract Soul Menu"
    lateinit var MENU_STRUCTURE: Structure
    var MENU_STRUCTURE_ARRAY: Array<String> = fallbackMenuStructure
    var MENU_INPUT_SLOT: Char = 'I'
    var MENU_OUTPUT_SLOT: Char = 'O'


    // 加载数据
    override fun loadConfig() {
        /*菜单设置*/
        MENU_TITLE = config().getString("menu-setting.title", "Extract Soul Menu")!!
        try { config().getStringList("menu-setting.structure").toTypedArray()
                .also { MENU_STRUCTURE = Structure(*it) }
                .also { MENU_STRUCTURE_ARRAY = it }
        } catch (event: IllegalArgumentException) {
            MENU_STRUCTURE = Structure(*MENU_STRUCTURE_ARRAY)
            Bukkit.getConsoleSender().sendTranslatableComponent(RESOURCE_MENU_STRUCTURE_ERROR, fileName) }
        MENU_INPUT_SLOT = config().getString("menu-setting.input-slot", "I")?.first() ?: 'I'
        MENU_OUTPUT_SLOT = config().getString("menu-setting.output-slot", "O")?.first() ?: 'O'
    }


}