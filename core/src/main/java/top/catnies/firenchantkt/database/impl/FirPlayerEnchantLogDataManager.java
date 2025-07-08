package top.catnies.firenchantkt.database.impl;

import com.j256.ormlite.stmt.QueryBuilder;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import top.catnies.firenchantkt.api.ServiceContainer;
import top.catnies.firenchantkt.database.AbstractDaoManager;
import top.catnies.firenchantkt.database.PlayerEnchantLogDataManager;
import top.catnies.firenchantkt.database.PlayerEnchantLogData;

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

    @Nullable
    @Override
    public List<PlayerEnchantLogData> getList(int max) {
        QueryBuilder<PlayerEnchantLogData, Integer> queryBuilder = getQueryBuilder();
        queryBuilder.orderBy("id", false);
        if (max >= 0) {
            queryBuilder.limit((long) max);
        }

        return queryForList(queryBuilder);
    }

    @Override
    @SneakyThrows
    public List<PlayerEnchantLogData> getList(UUID uuid, int max) {
        QueryBuilder<PlayerEnchantLogData, Integer> queryBuilder = getQueryBuilder();
        queryBuilder.setWhere(queryBuilder.where()
                .eq("player", uuid)
        );
        queryBuilder.orderBy("id", false);
        if (max >= 0) {
            queryBuilder.limit((long) max);
        }

        return queryForList(queryBuilder);
    }

    @Override
    @SneakyThrows
    public List<PlayerEnchantLogData> getList(String enchantment, int max) {
        QueryBuilder<PlayerEnchantLogData, Integer> queryBuilder = getQueryBuilder();
        queryBuilder.setWhere(queryBuilder.where()
                .eq("enchantment", enchantment)
        );
        queryBuilder.orderBy("id", false);
        if (max >= 0) {
            queryBuilder.limit((long) max);
        }

        return queryForList(queryBuilder);
    }

    @Override
    @SneakyThrows
    public List<PlayerEnchantLogData> getList(UUID uuid, String enchantment, int max) {
        QueryBuilder<PlayerEnchantLogData, Integer> queryBuilder = getQueryBuilder();
        queryBuilder.setWhere(queryBuilder.where()
                .eq("player", uuid)
                .and()
                .eq("enchantment", enchantment)
        );
        queryBuilder.orderBy("id", false);
        if (max >= 0) {
            queryBuilder.limit((long) max);
        }

        return queryForList(queryBuilder);
    }
}
