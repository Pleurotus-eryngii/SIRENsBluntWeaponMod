package pleurotus.eryngii.common;


import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import pleurotus.eryngii.client.ClientSideProxy;
import pleurotus.eryngii.item.EntityBullet;
import pleurotus.eryngii.item.ItemPoker;
import pleurotus.eryngii.item.ItemUrien;

@Mod(modid="SIRENBluntWeapons", version="1.0")
/*
 * このMODのメインクラス。
 * 複雑な処理が行われないアイテムはほぼここで処理されています
*/
public class SIRENWeaponCore
{
	//アイテムの登録名は普通に英訳しましたが、当てはまる語が無い場合創作しました
	//並び順は依頼の通りです＿ぞんび
	public static Item poker;
	public static Item rachetWrench;
	public static Item crowbar;
	public static Item ironPipe;
	public static Item nailHammer;
	public static Item urien;
	public static Item homuranagi;
	public static Item rectangularTimber;
	public static Item monkeyWrench;
	public static Item umbrella;
	public static Item ropeCutter;
	public static Item ropeCutterHook;
	public static Item curlingTongs;
	public static Item truncheon;
	public static Item crosscutSaw;
	public static Item fellingAxe;
	public static Item hammer;
	public static Item ironHammer;
	public static Item pickel;
	public static Item pickaxe;
	public static Item peelingSickle;
	public static Item tennisRacket;
	public static Item shoehorn;
	public static Item handAxe;
	public static Item hatchet;
	public static Item electricIron;
	public static Item kitchenKnife;
	public static Item governmentShovel;
	public static Item shovel;
	public static Item fryingPan;
	public static Item golfClub;
	public static Item deckBrush;
	public static Item rake;
	public static Item trophy;
	public static Item woodenSword;
	public static Item woodenBat;
	public static Item nailBat;
	public static Item sionagi;
	public static Item annaki;
	
	//攻撃力の設定用。
	public static final Item.ToolMaterial BLUE = EnumHelper.addToolMaterial("BLUE", 0, 150, -5.0F, -5.0F, 30  );
	
	//クライアント，サーバー間で異なる処理を行わせるためのプロキシーの設定．
		@SidedProxy(clientSide = "pleurotus.eryngii.client.ClientSideProxy", 
					serverSide = "pleurotus.eryngii.common.CommonSideProxy")
		public static CommonSideProxy proxy;
		public static ClientSideProxy clientproxy;
		
		//エンティティIDの上限を設定．
		public static int entityIdHead = 170;

	public static final CreativeTabs SIRENTabs = new SIRENTabCreateHandler("SIRENWeapons");


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

//面倒なので全部Swordを継承してます。1個ずつ細かい設定するのでそのときに適宜変更してください
		{
			Item poker;
			poker = new ItemPoker(Item.ToolMaterial.EMERALD)
					.setCreativeTab(this.SIRENTabs)
					.setUnlocalizedName("ItemPoker")
					.setTextureName("sirenweaponmod:poker");
		GameRegistry.registerItem(poker, "Poker");

		rachetWrench = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemRachetWrench")
		.setTextureName("sirenweaponmod:rachetwrench");
		GameRegistry.registerItem(rachetWrench, "RachetWrench");

		crowbar = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemCrowbar")
		.setTextureName("sirenweaponmod:crowbar");
		GameRegistry.registerItem(crowbar, "Crowbar");

		ironPipe = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemIronPipe")
		.setTextureName("sirenweaponmod:ironpipe");
		GameRegistry.registerItem(ironPipe, "IronPipe");

		nailHammer = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemNailHammer")
		.setTextureName("sirenweaponmod:nailHammer");
		GameRegistry.registerItem(nailHammer, "NailHammer");

		urien = (new ItemUrien(BLUE))
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemUrien")
		.setTextureName("sirenweaponmod:urien");
		GameRegistry.registerItem(urien, "Urien");
		
		//宇理炎ソードの右クリックで発射されるエンティティを登録．
				EntityRegistry.registerModEntity(EntityBullet.class, "Arrow",entityIdHead, this, 128, 5, true);
				proxy.registerRenderers();

		homuranagi = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemHomuranagi")
		.setTextureName("sirenweaponmod:homuranagi");
		GameRegistry.registerItem(homuranagi, "Homuranagi");

		rectangularTimber = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemRectaungularTimber")
		.setTextureName("sirenweaponmod:rectangulartimber");
		GameRegistry.registerItem(rectangularTimber, "RectangularTimber");

		monkeyWrench = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemMonkeyWrench")
		.setTextureName("sirenweaponmodmonkeywrench:");
		GameRegistry.registerItem(monkeyWrench, "MonkeyWrench");

		umbrella = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemUmbrella")
		.setTextureName("sirenweaponmod:umbrella");
		GameRegistry.registerItem(umbrella, "Umbrella");

		ropeCutter = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemRopeCutter")
		.setTextureName("sirenweaponmod:ropecutter");
		GameRegistry.registerItem(ropeCutter, "RopeCutter");

		ropeCutterHook = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemRopeCutterHook")
		.setTextureName("sirenweaponmod:ropecutterhook");
		GameRegistry.registerItem(ropeCutterHook, "RopeCutterHook");

		curlingTongs = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemCurlingTongs")
		.setTextureName("sirenweaponmod:curlingtongs");
		GameRegistry.registerItem(curlingTongs, "CurlingTongs");

		truncheon = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemTruncheon")
		.setTextureName("sirenweaponmod:truncheon");
		GameRegistry.registerItem(truncheon, "truncheon");

		crosscutSaw = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemCrosscutSaw")
		.setTextureName("sirenweaponmod:crosscutsaw");
		GameRegistry.registerItem(crosscutSaw, "CrosscutSaw");

		fellingAxe = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemFeelingAxe")
		.setTextureName("sirenweaponmod:feelingaxe");
		GameRegistry.registerItem(fellingAxe, "feelingAxe");

		hammer = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemHammer")
		.setTextureName("sirenweaponmod:hammer");
		GameRegistry.registerItem(hammer, "Hammer");

	    ironHammer = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemIronHammer")
		.setTextureName("sirenweaponmod:ironHammer");
		GameRegistry.registerItem(ironHammer, "IronHammer");

		pickel = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemPickel")
		.setTextureName("sirenweaponmod:pickel");
		GameRegistry.registerItem(pickel, "Pickel");

		pickaxe = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemPickaxe")
		.setTextureName("sirenweaponmod:pickaxe");
		GameRegistry.registerItem(pickaxe, "Pickaxe");

		peelingSickle = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemPeelingSickle")
		.setTextureName("sirenweaponmod:peelingsickle");
		GameRegistry.registerItem(peelingSickle, "PeelingSickle");

		tennisRacket = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemTennisRacket")
		.setTextureName("sirenweaponmod:tennisracket");
		GameRegistry.registerItem(tennisRacket, "TennisRacket");

		shoehorn = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemShoehorn")
		.setTextureName("sirenweaponmod:shoehorn");
		GameRegistry.registerItem(shoehorn, "Shoehorn");

		handAxe = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemHandAxe")
		.setTextureName("sirenweaponmod:handaxe");
		GameRegistry.registerItem(handAxe, "handAxe");

		hatchet = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemHatchet")
		.setTextureName("sirenweaponmod:hatchet");
		GameRegistry.registerItem(hatchet, "Hatchet");

		electricIron = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemElectricIron")
		.setTextureName("sirenweaponmod:electriciron");
		GameRegistry.registerItem(electricIron, "ElectricIron");

		kitchenKnife = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemKitchenKnife")
		.setTextureName("sirenweaponmod:kitchenknife");
		GameRegistry.registerItem(kitchenKnife, "KitchenKnife");

		governmentShovel = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemGovernmentShovel")
		.setTextureName("sirenweaponmod:governmentshovel");
		GameRegistry.registerItem(governmentShovel, "GovernmentShovel");

		shovel = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemShovel")
		.setTextureName("sirenweaponmod:shovel");
		GameRegistry.registerItem(shovel, "Shovel");

		fryingPan= new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemFryingPan")
		.setTextureName("sirenweaponmod:fryingpan");
		GameRegistry.registerItem(fryingPan, "FryingPan");

		golfClub = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemGolfClub")
		.setTextureName("sirenweaponmod:golfclub");
		GameRegistry.registerItem(golfClub, "GolfClub");

		deckBrush = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemDeckBrush")
		.setTextureName("sirenweaponmod:deckbrush");
		GameRegistry.registerItem(deckBrush, "DeckBrush");

		rake = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemRake")
		.setTextureName("sirenweaponmod:rake");
		GameRegistry.registerItem(rake, "Rake");

		trophy = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("Itemtrophy")
		.setTextureName("sirenweaponmod:trophy");
		GameRegistry.registerItem(trophy, "Trophy");

		woodenSword = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemWoodenSword")
		.setTextureName("sirenweaponmod:woodensword");
		GameRegistry.registerItem(woodenSword, "WoodenSword");

		woodenBat = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemWoodenBat")
		.setTextureName("sirenweaponmod:woodenbat");
		GameRegistry.registerItem(woodenBat, "WoodenBat");

		nailBat = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemNailBat")
		.setTextureName("sirenweaponmod:nailbat");
		GameRegistry.registerItem(nailBat, "NailBat");

		sionagi = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemSionagi")
		.setTextureName("sirenweaponmod:sionagi");
		GameRegistry.registerItem(sionagi, "Sionagi");

		annaki = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemAnnaki")
		.setTextureName("sirenweaponmod:annaki");
		GameRegistry.registerItem(annaki, "Annnaki");


		}


	}





	@EventHandler
	public void init(FMLInitializationEvent event) throws IOException {
		//レシピの登録はここでどうぞ
	}


}