package pleurotus.eryngii.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class SIRENTabCreateHandler extends CreativeTabs
{

	public SIRENTabCreateHandler(String lablel) {
		super(lablel);

	}



	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return SIRENWeaponCore.poker;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel()
	{
		return "SIRENBluntWeapons";
	}

}