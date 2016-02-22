package pleurotus.eryngii.common;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/*
 * プロキシーの設定クラスです． 
 * 
 */


public class CommonSideProxy {
	public void registerRenderers()
	{

	}

	public World getClientWorld() {

		return null;
	}
	
	public void registerClientInfo(){}
	
	public boolean isThePlayer(EntityPlayer player)
	{
		return false;
	}
}
