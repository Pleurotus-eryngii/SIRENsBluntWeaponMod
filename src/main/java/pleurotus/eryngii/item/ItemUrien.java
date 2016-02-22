package pleurotus.eryngii.item;
import java.util.List;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import pleurotus.eryngii.common.SIRENWeaponCore;

/*
 * 宇理炎ソードの実際の挙動を設定するクラスです．
 * FakeUrienModよりコピペ
 */
public class ItemUrien extends ItemSword
{public ItemUrien(ToolMaterial p_i45356_1_) {
	super(p_i45356_1_);
}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
	}
		//武器としての設定．
		public static Item itemWeapon;{
		this.setMaxStackSize(1);
		this.setMaxDamage(1562);
		this.damage = 0;
		}

	private float damage;
	{
	}
	//ウィザーヘッドを流用したエンティティを発射する処理です．
	//右クリックをやめた時に呼ばれるメソッド．
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
   //右クリックの押下上限時間の設定．
        int j = this.getMaxItemUseDuration(par1ItemStack) - par4;
        final boolean   isCreativeMode;
		boolean flag1 =  (par3EntityPlayer.capabilities).isCreativeMode;
		
		//ややこしいので変数の動きをしっかり追ってください．
        //取得した右クリック押下時間を元にため時間を設定．20で割っているのは単位を秒に変換するため．
        float f = (float)j / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;

        //ため時間が一定以下だった場合処理から抜ける(発射しない)．
        if ((double)f < 0.0D)
        {
            return;
        }
        //ため時間の上限値を設定．
        if (f > 1.0F)
        {
            f = 1.0F;
        }
        //爆発するか，燃えた状態で飛ぶか設定する変数．
        boolean ExplosionArrow = true;
        boolean FireArrow = false;
        EntityBullet bullet = new EntityBullet(par2World, par3EntityPlayer, 2F, ExplosionArrow, FireArrow);//���ˏ���
        
        if (!par2World.isRemote)
        {
            par2World.spawnEntityInWorld(bullet);
        }
    }

	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	return par1ItemStack;
    }
	//右クリック押下時間の上限設定．
   public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }
    
 	public void onUpdate(ItemStack p_77663_1_, World world, Entity entity, int p_77663_4_, boolean p_77663_5_){
	   if(entity instanceof EntityPlayer){ //instanceofでentityがEntityPlayerかどうか確認
		   EntityPlayer entP = (EntityPlayer)entity;
		   ItemStack entPItem = entP.getHeldItem();
		   if(entPItem != null){ //�ʂ�ۉ��
			   if(entP.getHeldItem().getItem() == SIRENWeaponCore.urien){ //onUpdate自体はItemがインベントリにあるときにも呼ばれるので持っているアイテムの確認
					
				   //エンティティに当たった際のウィザー効果の設定．
				   int potionID = Potion.wither.id;
				   //効果時間．
				   int duration = 20 * 20;
				   //効果レベル．
				   int amplifier = 5;
				   //PotionEffectの設定．
				   PotionEffect Effect = new PotionEffect(potionID, duration, amplifier);
				  
				   if(!world.isRemote) //マルチプレイサーバーでのみの処理，if文で場合分け．
				   {
				   		//Playerの当たり判定を取得，その大きさを変更してその当たり判定に当たっているentityをすべて取得
						List list = world.getEntitiesWithinAABB(EntityLivingBase.class, entP.boundingBox.expand(10F,10F,10F));
						for(Object obj : list)
						{
							EntityLivingBase entityLB = ((EntityLivingBase)obj);
							//entityが10m以内にあるかどうか
							if(entity.getDistanceToEntity(entP) < 10)
							{
								entityLB.addPotionEffect(Effect);
							}
						}
				   }
			   }
		   }
	   }
   }

 	//以下メソッドは右クリックした際に呼ばれます．
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
				int potionID = Potion.wither.id;
				//Potionの効果時間．
				int duration = 20 * 20;
				//PotionのLv．
				int amplifier = 2;
				//PotionEffectの設定．
				PotionEffect Effect = new PotionEffect(potionID, duration, amplifier);
				//PotionEffect(Effect)がEntityPlayerに付与されているかの判定
                boolean isMoveSpeed = par3EntityPlayer.isPotionActive(Effect.getPotionID());
                
                //PotionEffect(Effect)がEntityPlayerに付与されていない場合
                if( !isMoveSpeed )
                {
                     //振る動作．
                    par3EntityPlayer.swingItem(); 
                    par1ItemStack.damageItem(1, par3EntityPlayer);
                    //PotionEffect(Effect)をEntityPlayerに付与
                    par3EntityPlayer.addPotionEffect(Effect);
                      }
                
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        	return par1ItemStack;
    }
	//右クリック時のアニメーション，バニラ弓のものを使用．
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

}
