package buildcraftAdditions.eureka;

import buildcraftAdditions.utils.Utils;
import eureka.client.gui.EurekaChapter;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DusterMechanicalGui extends EurekaChapter {
	@Override
	public String getText(int page) {
		return Utils.localize("engineeringDiary.dusterTier2-1.page" + page);
	}

	@Override
	public void drawCustomStuff(int page) {

	}

	@Override
	public boolean hasNextPage(int page) {
		return !(Utils.localize("engineeringDiary.dusterTier2-1.page" + (page + 1)).equals("engineeringDiary.dusterTier2-1.page" + Integer.toString(page + 1)));
	}

	@Override
	public String getRequiredResearch() {
		return Utils.localize("engineeringDiary.dusterTier2-1.requiredResearch");
	}

	@Override
	public String howToMakeProgress() {
		return Utils.localize("engineeringDiary.dusterTier2-1.howToMakeProgress");
	}
}
