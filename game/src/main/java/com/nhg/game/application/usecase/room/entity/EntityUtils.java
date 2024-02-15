package com.nhg.game.application.usecase.room.entity;

import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.room.entity.component.ComponentType;
import com.nhg.game.domain.room.entity.component.ItemComponent;
import com.nhg.game.domain.room.entity.component.UserComponent;
import com.nhg.game.domain.user.User;
import lombok.NonNull;

public final class EntityUtils {

    public static User getUserFromEntity(@NonNull Entity userEntity) {
        UserComponent userComponent = (UserComponent) userEntity.getComponent(ComponentType.User);
        return userComponent.getUser();
    }

    public static RoomItem getItemFromEntity(@NonNull Entity itemEntity) {
        ItemComponent itemComponent = (ItemComponent) itemEntity.getComponent(ComponentType.Item);
        return itemComponent.getItem();
    }
}