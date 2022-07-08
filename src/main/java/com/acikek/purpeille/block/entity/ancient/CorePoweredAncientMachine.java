package com.acikek.purpeille.block.entity.ancient;

import com.acikek.purpeille.block.entity.CommonBlockWithEntity;
import com.acikek.purpeille.block.entity.SingleSlotBlockEntity;
import com.acikek.purpeille.item.core.EncasedCore;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.BiFunction;

public abstract class CorePoweredAncientMachine<T extends CorePoweredAncientMachineBlockEntity> extends CommonBlockWithEntity<T> {

    public Class<T> blockEntityClass;
    public boolean playerCheckCore;

    public CorePoweredAncientMachine(Settings settings, BlockEntityTicker<T> ticker, BiFunction<BlockPos, BlockState, T> supplier, Class<T> blockEntityClass, boolean playerCheckCore) {
        super(settings, ticker, supplier);
        this.blockEntityClass = blockEntityClass;
        this.playerCheckCore = playerCheckCore;
    }

    public T getCorePowered(BlockEntity blockEntity) {
        if (blockEntityClass.isInstance(blockEntity)) {
            return blockEntityClass.cast(blockEntity);
        }
        return null;
    }

    public boolean canPlayerUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, SingleSlotBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean extraChecks(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack handStack, SingleSlotBlockEntity blockEntity) {
        return playerCheckCore && blockEntity.playerCheckCore(player, handStack);
    }

    @Override
    public ActionResult removeItem(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack handStack, SingleSlotBlockEntity blockEntity) {
        if (canPlayerUse(state, world, pos, player, hand, blockEntity)) {
            getCorePowered(blockEntity).removeCore(world, player, true, pos, state);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean isValidHandStack(ItemStack stack) {
        return stack.getItem() instanceof EncasedCore;
    }

    @Override
    public ActionResult addItem(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack handStack, SingleSlotBlockEntity blockEntity) {
        getCorePowered(blockEntity).addCore(world, handStack, true, player, pos, state);
        return ActionResult.SUCCESS;
    }
}
