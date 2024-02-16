package com.nhg.game.application.usecase.room.item;

import com.nhg.common.domain.UseCase;
import com.nhg.common.domain.event.DomainEventPublisher;
import com.nhg.game.application.event.room.RemovedRoomEntityEvent;
import com.nhg.game.application.repository.ItemRepository;
import com.nhg.game.application.usecase.room.RoomUtils;
import com.nhg.game.application.usecase.room.entity.EntityUtils;
import com.nhg.game.domain.item.ItemType;
import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.room.entity.component.ComponentType;
import com.nhg.game.domain.room.entity.component.InteractionComponent;
import com.nhg.game.domain.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class PickUpItemUseCase {

    private final ItemRepository itemRepository;
    private final DomainEventPublisher eventPublisher;


    public void pickUpItem(@NonNull Entity userEntity, @NonNull Entity itemEntity, @NonNull Room room) {
        User user = EntityUtils.getUserFromEntity(userEntity);
        RoomItem item = EntityUtils.getItemFromEntity(itemEntity);

        if (user == null || item == null) return;

        interactionOnPickUp(itemEntity, userEntity);

        if (item.getPrototype().getItemType() == ItemType.FLOOR_ITEM) {
            RoomUtils.updateRoomTile(room, item);
        }

        itemRepository.unsetRoomForItem(item);
        room.removeEntity(itemEntity);

        eventPublisher.publish(new RemovedRoomEntityEvent(itemEntity));

    }

    private void interactionOnPickUp(Entity itemEntity, Entity userEntity) {
        InteractionComponent interactionComponent =
                (InteractionComponent) itemEntity.getComponent(ComponentType.Interaction);

        if (interactionComponent == null) return;

        interactionComponent.onPickUp(userEntity);
    }
}
