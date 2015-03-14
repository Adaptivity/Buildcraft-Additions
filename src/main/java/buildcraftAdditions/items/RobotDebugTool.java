package buildcraftAdditions.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.robots.EntityRobotBase;
import buildcraft.api.transport.pluggable.PipePluggable;
import buildcraft.robots.RobotStationPluggable;
import buildcraft.transport.TileGenericPipe;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RobotDebugTool extends ItemBase {
	public RobotDebugTool() {
		super("robotDebugTool");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity instanceof TileGenericPipe) {
			TileGenericPipe pipe = (TileGenericPipe) entity;
			PipePluggable plug = pipe.getPipePluggable(ForgeDirection.getOrientation(side));
			if (plug instanceof RobotStationPluggable) {
				RobotStationPluggable robotplug = (RobotStationPluggable) plug;
				if (robotplug.getStation() != null) {
					EntityRobotBase robot = robotplug.getStation().robotTaking();
					if (robot != null) {
						player.addChatComponentMessage(new ChatComponentText(robot.getBoard().toString()));
						player.addChatComponentMessage(new ChatComponentText("X: " + robot.posX + " Y: " + robot.posY + " Z: " + robot.posZ));
						player.addChatComponentMessage(new ChatComponentText("Energy: " + robot.getBattery().getEnergyStored() + "/" + robot.getBattery().getMaxEnergyStored()));
						return true;
					}
				}
			}
		}
		return false;
	}
}
