package com.crophider.mixin;

import com.crophider.CropHiderClient;
import net.minecraft.block.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderManager.class)
public class BlockRenderManagerMixin {

    @Inject(method = "renderBlock", at = @At("HEAD"), cancellable = true)
    private void onRenderBlock(BlockState state, BlockPos pos, BlockRenderView world, MatrixStack matrices, VertexConsumer vertexConsumer, boolean cull, Random random, CallbackInfo ci) {
        if (CropHiderClient.hideCrops && isCropOrPlant(state)) {
            ci.cancel();
        }
    }

    private boolean isCropOrPlant(BlockState state) {
        Block block = state.getBlock();
        return block instanceof CropBlock
            || block instanceof PlantBlock
            || block instanceof StemBlock
            || block instanceof AttachedStemBlock
            || block instanceof NetherWartBlock
            || block instanceof CocoaBlock
            || block instanceof SugarCaneBlock
            || block instanceof CactusBlock
            || state.isIn(BlockTags.CROPS)
            || state.isIn(BlockTags.BEE_GROWABLES);
    }
}
