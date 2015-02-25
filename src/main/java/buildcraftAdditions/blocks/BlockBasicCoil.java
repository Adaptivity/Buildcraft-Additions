package buildcraftAdditions.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileBasicCoil;
import buildcraftAdditions.utils.RenderUtils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockBasicCoil extends BlockCoilBase {

	@SideOnly(Side.CLIENT)
	private IIcon sides, top, bottom;

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileBasicCoil();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		super.onBlockActivated(world, x, y, z, entityplayer, par6, par7, par8, par9);

		// Drop through if the player is sneaking
		if (entityplayer.isSneaking())
			return false;

		if (!world.isRemote)
			entityplayer.openGui(BuildcraftAdditions.instance, Variables.Gui.BASIC_COIL.ordinal(), world, x, y, z);

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		// If no metadata is set, then this is an icon.
		if (j == 0 && i == 3)
			return sides;

		if (i == j && i > 1)
			return sides;

		switch (i) {
			case 0:
				return bottom;
			case 1:
				return top;
			default:
				return sides;
		}
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		sides = RenderUtils.registerIcon(register, "coilBasicSides");
		top = RenderUtils.registerIcon(register, "coilBasicTop");
		bottom = RenderUtils.registerIcon(register, "coilBasicBottom");
	}
}
