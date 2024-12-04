package dev.taintedphoenix.mods.endportalunfixer.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class PositionTrackerMixin {
	@Inject(method = "teleport", at = @At("HEAD"))
	private void onPlayerChangeDimension(ServerWorld destination, CallbackInfo ci) {
		ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
		BlockPos previousPosition = player.getBlockPos();
		ServerWorld previousWorld = (ServerWorld) player.getWorld();

		// Store or use previous position as needed
		System.out.println("Previous Position: " + previousPosition);
		System.out.println("Previous World: " + previousWorld.getRegistryKey());
	}
}