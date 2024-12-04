package dev.taintedphoenix.mods.endportalunfixer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.minecraft.block.Blocks;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndPortalUnfixerMod implements ModInitializer {
	public static final String MOD_ID = "end-portal-unfixer";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, from, to) -> {
			if (to.getRegistryKey() == ServerWorld.END) {
				handlePlayerTeleport(player, player.getBlockPos());
			}
		});
	}

	public void handlePlayerTeleport(ServerPlayerEntity player, BlockPos overworldPos) {
			// Get player's last Overworld coordinates from persistent data
			BlockPos platformPos = new BlockPos(overworldPos.getX(), 63, overworldPos.getZ());
			MinecraftServer server = player.getServer();
			assert server != null;
			ServerWorld end = server.getWorld(ServerWorld.END);

			// Teleport the player to the corresponding End location
			player.teleport(end, overworldPos.getX(), 64, overworldPos.getZ(), 0.0f, 0.0f);

			// Create a 5x5 obsidian platform under the player
			createObsidianPlatform(end, platformPos);
		}

	private void createObsidianPlatform(ServerWorld world, BlockPos center) {
		int range = 2;
		for (int x = -range; x <= range; x++) {
			for (int z = -range; z <= range; z++) {
				BlockPos pos = center.add(x, -1, z);
				world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
			}
		}
	}

}