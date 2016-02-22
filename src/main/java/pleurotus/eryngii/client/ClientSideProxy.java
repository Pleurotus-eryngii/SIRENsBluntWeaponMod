package pleurotus.eryngii.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import pleurotus.eryngii.common.CommonSideProxy;
import pleurotus.eryngii.item.EntityBullet;

/*
 * クライアント側のプロキシーの設定に関するクラスです．
 * コピペ･･
 */

public class ClientSideProxy extends CommonSideProxy{

	//public static final KeyBinding RReloadKey = new KeyBinding("Key.sample", Keyboard.KEY_R, "MCFireArms");
	//public static final KeyBinding ADSKey = new KeyBinding("Key.sample2", Keyboard.KEY_LCONTROL, "MCFireArms2");
	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public void registerRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new EntityRenderer(new BulletModel()));

		//RenderingRegistry.registerEntityRenderingHandler(EntityLaser.class, new EntityRenderer(new LaserModel()));
	}
	@Override
	public boolean isThePlayer(EntityPlayer player)
	{
		return player == FMLClientHandler.instance().getClient().thePlayer;
	}
}
