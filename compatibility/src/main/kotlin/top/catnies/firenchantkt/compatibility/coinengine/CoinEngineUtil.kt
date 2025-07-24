package top.catnies.firenchantkt.compatibility.coinengine

import su.nightexpress.coinsengine.api.CoinsEngineAPI

object CoinEngineUtil {

    /**
     * 是否存在某个名称的货币.
     */
    fun hasCurrency(name: String): Boolean {
        return CoinsEngineAPI.hasCurrency(name)
    }

    /**
     * 获取实现了 Vault 接口的货币名称.
     */
    fun getVaultCurrencyName(): String? {
        return CoinsEngineAPI.getCurrencies().find { it.isVaultEconomy }?.name
    }

}