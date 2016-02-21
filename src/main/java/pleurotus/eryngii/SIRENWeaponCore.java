package pleurotus.eryngii;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "SIRENBluntWeapons", name = "SIRENBluntWeapons",version = "1.7.10_1.0",dependencies = "required*after:Forge@[10.13.2.1558,)")
public class SIRENWeaponCore {

	@Instance("SIRENBluntWeapons")
	public static SIRENWeaponCore instance;

	//Logging
	//念のためです。不要なら消してください。
	public static Logger logger = LogManager.getLogger("SIRENBluntWeapons");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		//Config
		//これも一応。
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try {
			cfg.load();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		//アイテム・ブロック登録はここで

	}

	@EventHandler
	public void init(FMLInitializationEvent event) throws IOException {
		//レシピの登録はここでどうぞ
	}

}
