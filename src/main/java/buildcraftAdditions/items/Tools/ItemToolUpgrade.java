package buildcraftAdditions.items.Tools;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.Utils;

public class ItemToolUpgrade extends Item {

	private final String type;

	public ItemToolUpgrade(String type) {
		setMaxStackSize(16);
		setCreativeTab(BuildcraftAdditions.bcadditions);
		setUnlocalizedName("toolUpgrade" + type);
		setTextureName(Variables.MOD.ID + ":" + type + "Upgrade");
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedItemTooltips) {
		list.add(Utils.localize("tooltip.forKineticMultiTool"));
	}
}
