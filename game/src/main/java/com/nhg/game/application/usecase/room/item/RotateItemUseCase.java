package com.nhg.game.application.usecase.room.item;

import com.nhg.common.domain.UseCase;
import com.nhg.game.application.repository.ItemRepository;
import com.nhg.game.application.usecase.room.RoomSharedUseCase;
import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.room.entity.component.ComponentType;
import com.nhg.game.domain.room.entity.component.InteractionComponent;
import com.nhg.game.domain.room.entity.component.RotationComponent;
import com.nhg.game.domain.shared.position.Rotation;
import com.nhg.game.domain.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RotateItemUseCase {

    private final RoomSharedUseCase roomSharedUseCase;
    private final ItemRepository itemRepository;


    public void rotateItem(@NonNull Entity userEntity, @NonNull Entity itemEntity, @NonNull Room room) {
        User user = roomSharedUseCase.getUserFromEntity(userEntity);
        RoomItem item = roomSharedUseCase.getItemFromEntity(itemEntity);

        if (user == null || item == null) return;

        RotationComponent rotationComponent = (RotationComponent) itemEntity.getComponent(ComponentType.Rotation);

        if (rotationComponent == null) return;

        Rotation rotation = rotationComponent.rotate();
        item.setRotation(rotation);

        interactionOnRotate(itemEntity, userEntity);

        roomSharedUseCase.updateRoomTile(room, item);

        itemRepository.save(item);
    }

    private void interactionOnRotate(Entity itemEntity, Entity userEntity) {
        InteractionComponent interactionComponent =
                (InteractionComponent) itemEntity.getComponent(ComponentType.Interaction);

        if (interactionComponent == null) return;

        interactionComponent.onRotate(userEntity);
    }


}