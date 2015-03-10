package buildcraftAdditions.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class FluidBlockBase extends BlockFluidClassic {

	public FluidBlockBase(Fluid fluid) {
		super(fluid, Material.water);
		setBlockName(fluid.getUnlocalizedName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (side == 0 || side == 1)
			return stack.getFluid().getStillIcon();
		return stack.getFluid().getFlowingIcon();
	}

	@Override
	public String getLocalizedName() {
		return stack.getLocalizedName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return stack.getFluid().getColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return stack.getFluid().getColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return stack.getFluid().getColor();
	}

	public FluidStack getFluidStack() {
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
	}
}
