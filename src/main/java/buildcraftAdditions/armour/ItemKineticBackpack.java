package buildcraftAdditions.armour;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.client.models.BackPackModel;
import buildcraftAdditions.items.ItemPoweredBase;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemKineticBackpack extends ItemArmor implements IEnergyContainerItem {

	public ItemKineticBackpack() {
		super(ArmorMaterial.IRON, BuildcraftAdditions.proxy.addArmor("kineticBackpack"), 1);
		setUnlocalizedName("kineticBackpack");
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null && stack.getItem() instanceof IEnergyContainerItem) {
			IEnergyContainerItem eci = (IEnergyContainerItem) stack.getItem();
			int transfer = Math.min(eci.receiveEnergy(player.getCurrentEquippedItem(), getEnergyStored(itemStack), true), 20000);
			eci.receiveEnergy(player.getCurrentEquippedItem(), transfer, false);
			extractEnergy(itemStack, transfer, false);
		}
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		int recieved = maxReceive;
		if (recieved > getMaxEnergyStored(container) - getEnergyStored(container))
			recieved = getMaxEnergyStored(container) - getEnergyStored(container);
		if (!simulate)
			setEnergy(container, getEnergyStored(container) + recieved);
		return recieved;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		int max = Math.min(getMaxEnergyStored(container), maxExtract);
		max = Math.min(max, getEnergyStored(container));
		if (!simulate)
			setEnergy(container, getEnergyStored(container) - max);
		return max;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		tagTest(container);
		return container.stackTagCompound.getInteger("energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		tagTest(container);
		return container.stackTagCompound.getInteger("maxEnergy");
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean sneaking) {
		list.add(Utils.getRFInfoTooltip(getEnergyStored(stack), getMaxEnergyStored(stack)));
	}

	private void tagTest(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("maxEnergy", 0);
			stack.stackTagCompound.setInteger("energy", 0);
			for (int t = 0; t < 4; t++) {
				stack.stackTagCompound.setInteger("capsule" + t, 0);
			}
		}
	}

	private void setEnergy(ItemStack stack, int energy) {
		tagTest(stack);
		stack.stackTagCompound.setInteger("energy", energy);
	}

	private void setMaxEnergy(ItemStack stack, int capacity) {
		tagTest(stack);
		stack.stackTagCompound.setInteger("maxEnergy", capacity);
	}

	public void installCapsule(ItemStack backpack, int slot, ItemStack capsule) {
		tagTest(backpack);
		int capsuleType = 0;
		if (capsule.getItem() == ItemsAndBlocks.powerCapsuleTier1) {
			capsuleType = 1;
		} else if (capsule.getItem() == ItemsAndBlocks.powerCapsuleTier2) {
			capsuleType = 2;
		} else if (capsule.getItem() == ItemsAndBlocks.powerCapsuleTier3) {
			capsuleType = 3;
		}
		if (capsuleType > 0) {
			ItemPoweredBase base = (ItemPoweredBase) capsule.getItem();
			setMaxEnergy(backpack, getMaxEnergyStored(backpack) + base.getMaxEnergyStored(capsule));
			receiveEnergy(backpack, base.getEnergyStored(capsule), false);
			backpack.stackTagCompound.setInteger("capsule" + slot, capsuleType);
		}
	}

	public int getInstalledCapsule(ItemStack stack, int slot) {
		tagTest(stack);
		return stack.stackTagCompound.getInteger("capsule" + slot);
	}

	public void removeCapsule(ItemStack stack, int slot) {
		tagTest(stack);
		stack.stackTagCompound.setInteger("capsule" + slot, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return BackPackModel.INSTANCE2;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "bcadditions:textures/models/armor/kineticBackpack_layer_1.png";
	}

	@Override
	public void registerIcons(IIconRegister register) {
	}
}
