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
		//pokerの処理を変更したお陰でバグが起きてたみたいです。
		//どんな変更したのかはっきり分からないので根本的な原因は知りませんが、とりあえずironPipeにしたら収まりました
		return SIRENWeaponCore.ironPipe;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel()
	{
		return "SIRENBluntWeapons";
	}

}