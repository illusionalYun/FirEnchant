package top.catnies.firenchantkt.database.impl;

import com.j256.ormlite.stmt.QueryBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import top.catnies.firenchantkt.api.ServiceContainer;
import top.catnies.firenchantkt.database.AbstractDaoManager;
import top.catnies.firenchantkt.database.PlayerEnchantLogDataManager;
import top.catnies.firenchantkt.entity.PlayerEnchantLogData;

import java.util.List;
import java.util.UUID;

public class FirPlayerEnchantLogDataManager extends AbstractDaoManager<PlayerEnchantLogData, Integer> implements PlayerEnchantLogDataManager {
    private static FirPlayerEnchantLogDataManager instance;

    public static FirPlayerEnchantLogDataManager getInstance() {
        if (instance == null) {
            instance = new FirPlayerEnchantLogDataManager();
            instance.load();
        }

        return instance;
    }

    public void load() {
        ServiceContainer.INSTANCE.register(PlayerEnchantLogDataManager.class, this);
    }

    @Override
    @SneakyThrows
    public List<PlayerEnchantLogData> getList(UUID uuid) {
        QueryBuilder<PlayerEnchantLogData, Integer> queryBuilder = getQueryBuilder();
        queryBuilder.setWhere(queryBuilder.where()
                .eq("player", uuid)
        );

        return queryForList(queryBuilder);
    }

    @Override
    @SneakyThrows
    public List<PlayerEnchantLogData> getList(String enchantment) {
        QueryBuilder<PlayerEnchantLogData, Integer> queryBuilder = getQueryBuilder();
        queryBuilder.setWhere(queryBuilder.where()
                .eq("enchantment", enchantment)
        );

        return queryForList(queryBuilder);
    }

    @Override
    @SneakyThrows
    public List<PlayerEnchantLogData> getList(UUID uuid, String enchantment) {
        QueryBuilder<PlayerEnchantLogData, Integer> queryBuilder = getQueryBuilder();
        queryBuilder.setWhere(queryBuilder.where()
                .eq("player", uuid)
                .and()
                .eq("enchantment", enchantment)
        );

        return queryForList(queryBuilder);
    }
}
