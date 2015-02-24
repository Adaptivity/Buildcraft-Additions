package buildcraftAdditions.blocks;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

public class BlockFluidicCompressor extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon textureFront, textureBack, textureTop, textureBottom, textureSide;

	public BlockFluidicCompressor() {
		super(Material.iron);
		setHardness(5F);
		setResistance(10F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileFluidicCompressor();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		super.onBlockActivated(world, x, y, z, entityplayer, par6, par7, par8, par9);

		// Drop through if the player is sneaking
		if (entityplayer.isSneaking())
			return false;


		if (!world.isRemote)
			entityplayer.openGui(BuildcraftAdditions.instance, Variables.Gui.FLUIDIC_COMPRESSOR.ordinal(), world, x, y, z);

		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
		super.onBlockPlacedBy(world, i, j, k, entityliving, stack);

		ForgeDirection orientation = Utils.get2dOrientation(entityliving);
		world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal(), 1);

		TileFluidicCompressor canner = (TileFluidicCompressor) world.getTileEntity(i, j, k);
		canner.fill = true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileFluidicCompressor compressor = (TileFluidicCompressor) world.getTileEntity(x, y, z);
		compressor.openInventory();
		for (int t = 0; t < 2; t++) {
			ItemStack stack = compressor.getStackInSlot(t);
			if (stack != null) {
				compressor.setInventorySlotContents(t, null);
				Utils.dropItemstack(world, x, y, z, stack);
			}
		}
		compressor.closeInventory();
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		// If no metadata is set, then this is an icon.
		if (j == 0 && i == 3)
			return textureFront;

		if (i == j && i > 1)
			return textureFront;

		switch (i) {
			case 0:
				return textureBottom;
			case 1:
				return textureTop;
			default:
				return textureSide;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		textureFront = RenderUtils.registerIcon(register, "fluidicCompressor_front");
		textureSide = RenderUtils.registerIcon(register, "fluidicCompressor_sides");
		textureTop = RenderUtils.registerIcon(register, "fluidicCompressor_top");
		textureBack = RenderUtils.registerIcon(register, "fluidicCompressor_back");
		textureBottom = RenderUtils.registerIcon(register, "fluidicCompressor_bottom");
	}
}
