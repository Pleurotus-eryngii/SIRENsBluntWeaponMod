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
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import pleurotus.eryngii.client.ClientSideProxy;
import pleurotus.eryngii.item.ItemPoker;
import pleurotus.eryngii.item.ItemRectangularTimber;
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
	public void preInit(FMLPreInitializationEvent event)
	{

		//Config
		//これも一応。
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try {
			cfg.load();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cfg.save();
		}

		//アイテム・ブロック登録はここで

//面倒なので全部Swordを継承してます。1個ずつ細かい設定するのでそのときに適宜変更してください

		//よく分からない Item pokerr; の処理を削除して括弧をつけたら普通に動きました
		poker = (new ItemPoker(Item.ToolMaterial.EMERALD))
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
		
//よく分からない Item rectangularTimber; の処理を削除して括弧をつけたら普通に動きました
		rectangularTimber = (new ItemRectangularTimber(Item.ToolMaterial.EMERALD))
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemRectaungularTimber")
		.setTextureName("sirenweaponmod:rectangulartimber");
		GameRegistry.registerItem(rectangularTimber, "RectangularTimber");

		monkeyWrench = new ItemSword(Item.ToolMaterial.EMERALD)
		.setCreativeTab(SIRENTabs)
		.setUnlocalizedName("ItemMonkeyWrench")
		.setTextureName("sirenweaponmod:monkeywrench");
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







	@EventHandler
	public void init(FMLInitializationEvent event)  throws IOException{
		//レシピの登録はここでどうぞ
	   //アイテムの表示名の登録も行えます
		
		//表示名の登録。手法が古いけど動作するからいいでしょ
		LanguageRegistry.addName(poker, "Poker");
		LanguageRegistry.instance().addNameForObject(poker, "ja_JP", "Poker");
		
		LanguageRegistry.addName(rachetWrench, "Rachet Wrench");
		LanguageRegistry.instance().addNameForObject(rachetWrench, "ja_JP", "ラチェットレンチ");
		
		LanguageRegistry.addName(crowbar, "Crowbar");
		LanguageRegistry.instance().addNameForObject(crowbar, "ja_JP", "バール");
		
		LanguageRegistry.addName(ironPipe, "Iron Pipe");
		LanguageRegistry.instance().addNameForObject(ironPipe, "ja_JP", "鉄パイプ");
		
		LanguageRegistry.addName(nailHammer, "Nail Hammer");
		LanguageRegistry.instance().addNameForObject(nailHammer, "ja_JP", "ネイルハンマー");
		
		LanguageRegistry.addName(urien, "Urien");
		LanguageRegistry.instance().addNameForObject(urien, "ja_JP", "宇理炎");
		
		LanguageRegistry.addName(homuranagi, "Homuranagi");
		LanguageRegistry.instance().addNameForObject(homuranagi, "ja_JP", "焔薙");
		
		LanguageRegistry.addName(rectangularTimber, "Rectangular Timber");
		LanguageRegistry.instance().addNameForObject(rectangularTimber, "ja_JP", "角材");
		
		LanguageRegistry.addName(monkeyWrench, "Monkey Wrench");
		LanguageRegistry.instance().addNameForObject(monkeyWrench, "ja_JP", "モンキーレンチ");
		
		LanguageRegistry.addName(umbrella, "Umbrella");
		LanguageRegistry.instance().addNameForObject(umbrella, "ja_JP", "傘");
		
		LanguageRegistry.addName(ropeCutter, "Rope Cutter");
		LanguageRegistry.instance().addNameForObject(ropeCutter, "ja_JP", "縄切り");
		
		LanguageRegistry.addName(ropeCutterHook, "Nawakiri Tekagi");
		LanguageRegistry.instance().addNameForObject(ropeCutterHook, "ja_JP", "縄切り手鉤");
		
		LanguageRegistry.addName(curlingTongs, "Curling Tongs");
		LanguageRegistry.instance().addNameForObject(curlingTongs, "ja_JP", "左官用こて");
		
		LanguageRegistry.addName(truncheon, "Truncheon");
		LanguageRegistry.instance().addNameForObject(truncheon, "ja_JP", "警棒");
		
		LanguageRegistry.addName(crosscutSaw, "Menuki Daikiri");
		LanguageRegistry.instance().addNameForObject(crosscutSaw, "ja_JP", "目抜き大切");
		
		LanguageRegistry.addName(fellingAxe, "Felling Axe");
		LanguageRegistry.instance().addNameForObject(fellingAxe, "ja_JP", "鉞");
		
		LanguageRegistry.addName(hammer, "Hammer");
		LanguageRegistry.instance().addNameForObject(hammer, "ja_JP", "ハンマー");
		
		LanguageRegistry.addName(ironHammer, "Iron Hammer");
		LanguageRegistry.instance().addNameForObject(ironHammer, "ja_JP", "金槌");
		
		LanguageRegistry.addName(pickel, "Pickel");
		LanguageRegistry.instance().addNameForObject(pickel, "ja_JP", "ピッケル");
		
		LanguageRegistry.addName(pickaxe, "Pickaxe");
		LanguageRegistry.instance().addNameForObject(pickaxe, "ja_JP", "鶴嘴");
		
		LanguageRegistry.addName(peelingSickle, "Peeling Sickle");
		LanguageRegistry.instance().addNameForObject(peelingSickle, "ja_JP", "皮剥き鎌");
		
		LanguageRegistry.addName(tennisRacket, "Tennis Racket");
		LanguageRegistry.instance().addNameForObject(tennisRacket, "ja_JP", "テニスラケット");
		
		LanguageRegistry.addName(shoehorn, "Shoehorn");
		LanguageRegistry.instance().addNameForObject(shoehorn, "ja_JP", "靴べら");
		
		LanguageRegistry.addName(handAxe, "Hand Axe");
		LanguageRegistry.instance().addNameForObject(handAxe, "ja_JP", "手斧");
		
		LanguageRegistry.addName(hatchet, "Hatchet");
		LanguageRegistry.instance().addNameForObject(hatchet, "ja_JP", "鉈");
		
		LanguageRegistry.addName(electricIron, "Electric Iron");
		LanguageRegistry.instance().addNameForObject(electricIron, "ja_JP", "アイロン");
		
		LanguageRegistry.addName(kitchenKnife, "Broad‐Bladed Kitchen Knife");
		LanguageRegistry.instance().addNameForObject(kitchenKnife, "ja_JP", "出刃包丁");
		
		LanguageRegistry.addName(governmentShovel, "Government Shovel");
		LanguageRegistry.instance().addNameForObject(ropeCutterHook, "ja_JP", "官給スコップ");
		
		LanguageRegistry.addName(shovel, "Shovel");
		LanguageRegistry.instance().addNameForObject(shovel, "ja_JP", "シャベル");
		
		LanguageRegistry.addName(fryingPan, "Frying Pan");
		LanguageRegistry.instance().addNameForObject(fryingPan, "ja_JP", "フライパン");
		
		LanguageRegistry.addName(golfClub, "Golf Club");
		LanguageRegistry.instance().addNameForObject(golfClub, "ja_JP", "ゴルフクラブ");
		
		LanguageRegistry.addName(deckBrush, "Deck Brush");
		LanguageRegistry.instance().addNameForObject(deckBrush, "ja_JP", "デッキブラシ");
		
		LanguageRegistry.addName(rake, "Rake");
		LanguageRegistry.instance().addNameForObject(rake, "ja_JP", "熊手");
		
		LanguageRegistry.addName(trophy, "Trophy");
		LanguageRegistry.instance().addNameForObject(trophy, "ja_JP", "トロフィー");
		
		LanguageRegistry.addName(woodenSword, "Wooden Sword");
		LanguageRegistry.instance().addNameForObject(woodenSword, "ja_JP", "木刀");
		
		LanguageRegistry.addName(nailBat, "Nail Bat");
		LanguageRegistry.instance().addNameForObject(nailBat, "ja_JP", "釘バット");
		
		LanguageRegistry.addName(sionagi, "Sionagi");
		LanguageRegistry.instance().addNameForObject(sionagi, "ja_JP", "潮凪");
		
		LanguageRegistry.addName(annaki, "Annaki");
		LanguageRegistry.instance().addNameForObject(annaki, "ja_JP", "闇那其");
	}


}