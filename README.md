# Firenchants杉云附魔 ✨

![](https://img.shields.io/badge/%E6%94%AF%E6%8C%81%E7%89%88%E6%9C%AC-1.21.2--1.21.8-6772616) [![](https://img.shields.io/badge/%E6%8F%92%E4%BB%B6%E5%8F%91%E5%B8%83-MineBBS-6772616)](https://www.minebbs.com/resources/firenchant-eco.8479/) ![](https://img.shields.io/github/languages/code-size/Catnies/FirEnchant?label=代码大小) ![](https://img.shields.io/github/license/Catnies/FirEnchant?label=代码许可) 

## 📌 关于 Firenchants

Firenchants 是一款**附魔系统改革**插件，灵感来源于某国外服务器的创新设计。最初为私人服务器开发，现已对外开放。本插件彻底改造了Minecraft的附魔机制，提供丰富的附魔道具和深度玩法，特别适合想要**延长附魔毕业周期**的生存服。

### 🔥 核心特色

- **覆盖原版但不离开原版** - 仍然使用附魔台作为媒介，玩家可以往附魔台中放入配置文件中指定的可附魔的物品进行附魔，可以高度自定义可出现的魔咒列表和附魔所需的条件。

  <img src="https://s21.ax1x.com/2025/08/05/pVUaMad.png" width = "600" height = "300" alt="" />

- **附魔失败机制** - 引入破损物品和物品修复系统，玩家使用附魔书时如果失败了，装备会破损，玩家可以花时间将破损的装备修复，增加附魔玩法深度。

  <img src="https://s21.ax1x.com/2025/08/05/pVUanqe.png" width = "600" height = "200" alt="" />

  <img src="https://s21.ax1x.com/2025/08/05/pVUaeKO.png" width = "600" height = "330" alt=""  />

- **多种趣味道具** - 配套的多种道具，升级符文、拓展符文、快速修复卡等许多道具，既丰富了玩家体验，服主也可以发放更多附魔相关的道具奖励。

  <img src="https://s21.ax1x.com/2025/08/05/pVUaVxK.png" width = "600" height = "100" alt="" />

- **附魔自动导入** - 无需手动导入附魔，插件在启动时自动检测所有已注册的魔咒，快速上手使用。

  <img src="https://s21.ax1x.com/2025/08/05/pVUai5R.png" width = "600" height = "400" alt="" />

- **兼容主流物品插件** - 支持CraftEngine、Oraxen、ItemsAdder、Nexo等多个自定义物品插件，可以将本插件的道具绑定到这些自定义物品插件，减少物品混乱度。

  <a href="https://modrinth.com/plugin/craftengine" target="_blank">
  <img src="https://cdn.modrinth.com/data/tRX6FMfQ/0cf5b8584176a299543b47937eeeb2e3f6c2b30e.png" width = "100" height = "100" alt=""/>
  </a><a href="https://www.spigotmc.org/resources/%E2%9C%A8itemsadder%E2%AD%90emotes-mobs-items-armors-hud-gui-emojis-blocks-wings-hats-liquids.73355/" target="_blank">
  <img src="https://s21.ax1x.com/2025/08/05/pVUaE26.png" width = "100" height = "100" alt="" />
  </a><a href="https://polymart.org/product/6901/nexo" target="_blank"><img src="https://images.polymart.org/product/6901/thumbnail.png?t=1743583080&v=3" width = "100" height = "100" alt="" />
  </a>

- **只修改附魔玩法，不调整魔咒** - 支持ExcellentEnchant、EcoEnchants、Aiyatsbus等插件注册的真实魔咒，同时也支持使用原版Datapack注册的魔咒！

  <a href="https://www.spigotmc.org/resources/excellentenchants-%E2%AD%90-75-vanilla-like-enchantments.61693/" target="_blank">
  <img src="https://s21.ax1x.com/2025/08/05/pVUaCVJ.png" width = "100" height = "100" alt="" />
  </a><a href="https://www.spigotmc.org/resources/ecoenchants-%E2%AD%95-250-enchantments-%E2%9C%85-create-custom-enchants-%E2%9C%A8-essentials-cmi-support.79573/" target="_blank">
  <img src="https://s21.ax1x.com/2025/08/05/pVUakP1.png" width = "100" height = "100" alt="" />
  </a><a href="https://github.com/PolarAstrum/aiyatsbus" target="_blank">
  <img src="https://s21.ax1x.com/2025/08/05/pVUaA8x.jpg" width = "100" height = "100" alt="" />
  </a>

- **多插件兼容** - 插件挂钩大量其他插件，支持Auraskill，Mcmmo，CustomCrops等许多插件，如果您有希望支持的插件也可以在Issue中提出！
-

-----------------------

## 🤝 如何贡献

### 🌍 翻译

1. 克隆此存储库。

2. 在以下位置创建新的语言文件：

   ```
   /core/src/main/resources/languages
   ```

3. 完成后，提交**拉取请求**以供审核。我们感谢您的贡献！

--------------

## ❌ 已知不兼容

- 不支持AdvancedEnchantments、EnchantmentForge等基于NBT的伪附魔插件

------

## 💖 支持开发者

如果喜欢本插件，请考虑支持开发！

- **购买链接**: [MineBBS](https://www.minebbs.com/resources/firenchant-eco.8479/)
- **联系方式**: QQ 1286071831

------

## 📚 Firenchants API

### 📌 仓库配置

```
repositories {
    maven("https://repo.catnies.top/releases")
}
```

### 📌 依赖项

```
dependencies {
    compileOnly("top.catnies:firenchantkt:3.0.0")
}
```