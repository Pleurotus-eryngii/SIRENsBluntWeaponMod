package pleurotus.eryngii;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class SIRENTabCreateHandler extends CreativeTabs
{

	public SIRENTabCreateHandler(String lablel) {
		super(lablel);
		// TODO 自動生成されたコンストラクター・スタブ
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