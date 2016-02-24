package pleurotus.eryngii.common;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
/*
 * エンティティの実際の挙動を設定するクラスです．
 * これもコピペ
 */
public class EntityBullet extends Entity implements IProjectile{

	//地中判定に使うもの 
    protected int xTile = -1;
    protected int yTile = -1;
    protected int zTile = -1;
    protected int inTile;
    protected int inData;
    protected boolean inGround;

    private Block g;

    public int arrowShake;

    public static boolean hitCrossHair;

    //この弾を撃ったエンティティ
    public Entity shootingEntity;

    //地中・空中にいる時間
    protected int ticksInGround;
    protected int ticksInAir;

    //ダメージの大きさ
    protected double damage = 3.0D;

    //ノックバックの大きさ
    protected int knockbackStrength = 1;

    int tickcnt;

    protected boolean isExplosion = false;
    protected boolean isFire = false;

    public EntityBullet(World p_i1753_1_)
    {
        super(p_i1753_1_);
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
    }

    public EntityBullet(World p_i1754_1_, double p_i1754_2_, double p_i1754_4_, double p_i1754_6_)
    {
        super(p_i1754_1_);
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
        this.setPosition(p_i1754_2_, p_i1754_4_, p_i1754_6_);
        this.yOffset = 0.0F;
    }

    public EntityBullet(World p_i1755_1_, EntityLivingBase p_i1755_2_, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_)
    {
        super(p_i1755_1_);
        this.renderDistanceWeight = 10.0D;
        this.shootingEntity = p_i1755_2_;

        if (p_i1755_2_ instanceof EntityPlayer)
        {
            //this.canBePickedUp = 1;
        }

        this.posY = p_i1755_2_.posY + (double)p_i1755_2_.getEyeHeight() - 0.10000000149011612D;
        double d0 = p_i1755_3_.posX - p_i1755_2_.posX;
        double d1 = p_i1755_3_.boundingBox.minY + (double)(p_i1755_3_.height / 3.0F) - this.posY;
        double d2 = p_i1755_3_.posZ - p_i1755_2_.posZ;
        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);

        if (d3 >= 1.0E-7D)
        {
            float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
            double d4 = d0 / d3;
            double d5 = d2 / d3;
            this.setLocationAndAngles(p_i1755_2_.posX + d4, this.posY, p_i1755_2_.posZ + d5, f2, f3);
            this.yOffset = 0.0F;
            float f4 = (float)d3 * 0.2F;
            this.setThrowableHeading(d0, d1 + (double)f4, d2, p_i1755_4_, p_i1755_5_);
        }
    }

    public EntityBullet(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_, boolean ExplosionArrow, boolean isfire)
    {
        super(p_i1756_1_);
        this.renderDistanceWeight = 10.0D;
        this.shootingEntity = p_i1756_2_;

        this.isExplosion = ExplosionArrow;
        this.isFire = isfire;

        if (p_i1756_2_ instanceof EntityPlayer)
        {
            //this.canBePickedUp = 1;
        }

        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(p_i1756_2_.posX, p_i1756_2_.posY + (double)p_i1756_2_.getEyeHeight(), p_i1756_2_.posZ, p_i1756_2_.rotationYaw, p_i1756_2_.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, p_i1756_3_ * 1.5F, 1.0F);
    }
   
    //dataWatcherを利用したサーバ・クライアント間の同期処理だと思う
    protected void entityInit()
    {
    	this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    /*
     * IProjectileで実装が必要なメソッド．
     * ディスペンサーによる発射メソッドなどで使用されている
     */
    public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
    {
        float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= (double)f2;
        par3 /= (double)f2;
        par5 /= (double)f2;
        par1 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par8;
        par3 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par8;
        par5 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par8;
        par1 *= (double)par7;
        par3 *= (double)par7;
        par5 *= (double)par7;
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
        float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f3) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
    {
        this.setPosition(par1, par3, par5);
        this.setRotation(par7, par8);
    }

    @SideOnly(Side.CLIENT)
    /*
     * Sets the velocity to the args. Args: x, y, z
     * 速度の処理。クライアント・サーバ間での同期処理にて利用されている。
     */
    public void setVelocity(double par1, double par3, double par5)
    {
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }

    /*
     * Tick毎に呼ばれる更新処理．
     * 速度の更新、衝突判定などをここで行う．
     */
    public void onUpdate()
    {
        super.onUpdate();

        //直前のパラメータと新パラメータを一致させているところ．
        //また，速度に応じてエンティティの向きを調整し、常に進行方向に前面が向くようにしている．
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
        }

        Block block = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);

        if (block.getMaterial() != Material.air)
        {
            block.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

            if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ)))
            {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0)
        {
            --this.arrowShake;
        }
        
        if (this.inGround)
        {
        	Block j = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
            int k = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            /*
             * 前のTickに確認した埋まりブロックのIDとメタを照合している。違ったら埋まり状態を解除、一致したら埋まり状態を継続。
             * 埋まり状態2tick継続でこのエンティティを消す
             */
            if (j == g && k == this.inData)
            {
            	++this.ticksInGround;
            	//�u���b�N�ђʂ̏ꍇ�A20tick�i1�b�ԁj�̓u���b�N���ɂ����Ă������Ȃ��悤�ɂȂ�B
            	int limit = this.isPenetrateBlock() ? 20 : 2;
            	//int limit = 0;

                if (this.ticksInGround > limit)
                {
                    this.setDead();
                    if(this.isExplosion){
                    	if (!this.worldObj.isRemote)
                    	{
                    		this.worldObj.createExplosion(this, this.xTile, this.yTile, this.zTile, 2.0F, true); //����
                    	}
                    }
                }
            }
            else//埋まり状態の解除処理
            {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.1F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.1F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.1F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else//埋まってない時．速度の更新．
        {
            ++this.ticksInAir;
            //ブロックとの衝突判定
            Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
            vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);


          //ブロック貫通がONの場合，ブロック衝突判定をスキップ．
            if (this.isPenetrateBlock())
            {
            	movingobjectposition = null;
            }

            //ブロックに当たった
            if (movingobjectposition != null)
            {
            	vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }

            //Entityとの衝突判定
            Entity entity = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
                            this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            int l;
            float f1;
            boolean isVillager = false;

            //
            for (l = 0; l < list.size(); ++l)
            {
                Entity entity1 = (Entity)list.get(l);

                //���˕����gor���ˌ�5tick�ȊO���Ƃ���ʂ���
                if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5))
                {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double)f1, (double)f1, (double)f1);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3, vec31);

                    if (movingobjectposition1 != null)
                    {
                        double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            //�G���e�B�e�B�ɓ�������
            if (entity != null)
            {
                movingobjectposition = new MovingObjectPosition(entity);

            }

            /* ���������G���e�B�e�B���ꂻ��ɂ��Ă̔��蕔���B
             * ������movingobjectposition = null�ɂ��邱�Ƃœ���̎�ނ̃G���e�B�e�B�ɓ�����Ȃ��悤�ɂł���B*/
            if (movingobjectposition != null && movingobjectposition.entityHit != null)
            {
                if (movingobjectposition.entityHit instanceof EntityPlayer)
                {
                	//�v���C���[�ɓ���������
                	EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;

                    if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer &&
                           !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
                    {
                    	//PvP��������Ă��Ȃ��Ɠ�����Ȃ�
                        movingobjectposition = null;
                    }
                    else if (entityplayer == this.shootingEntity)
                    {
                    	//�Ώۂ��������{�l�̏ꍇ��������Ȃ�
                    	movingobjectposition = null;
                    }
                }
                else if (movingobjectposition.entityHit instanceof EntityTameable ||
                             movingobjectposition.entityHit instanceof EntityHorse)
                {
                	//���̖h�~�ׁ̈AEntityTameable�i����L�Ȃǂ̃y�b�g�j�A�n�ɂ�������Ȃ��悤�ɂ���
                	//movingobjectposition = null;
                }
                else if (movingobjectposition.entityHit instanceof EntityVillager)
                {
                	//���l�ɓ��������ꍇ�Ƀt���O��true�ɂȂ�
                	isVillager = true;
                }

                /*if(worldObj.isRemote){
                    if(this.shootingEntity instanceof EntityPlayer){
                     if(ProjectileTutorialCore.clientproxy.isThePlayer((EntityPlayer)this.shootingEntity)){
                    	 System.out.println(hitCrossHair);
                          hitCrossHair = true;
                     }
                    }
                   }*/
            }

            float f2;
            float f3;

            //�����������Ƃ̏���
            if (movingobjectposition != null)
            {
            	//�G���e�B�e�B�ɓ�������
                if (movingobjectposition.entityHit != null)
                {
                	//�Փˎ��̒e�̑��x���v�Z
                    f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY +
                            this.motionZ * this.motionZ);
                    //���x���傫���قǁA�_���[�W���傫���Ȃ�
                    int i1 = MathHelper.ceiling_double_int((double)f2 * this.damage);
                    //0~2���x�̗����l����悹
                    i1 += this.rand.nextInt(3);

                    DamageSource damagesource = null;


                    //�ʃ��\�b�h�Ń_���[�W�\�[�X���m�F
                    damagesource = this.thisDamageSource(this.shootingEntity);

                    if(this.isFire){
                    	this.setFire(5000);
                    }

                    //�o�j����Ɠ��l�A���̃G���e�B�e�B���R���Ă���Ȃ�Ώۂɒ��΂��邱�Ƃ��o����
                    if (this.isBurning() /*&& !(movingobjectposition.entityHit instanceof EntityEnderman)*/)
                    {
                    	//this.worldObj.spawnParticle("largesmoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
                        movingobjectposition.entityHit.setFire(3);
                    }

                    if(false){}

                    else
                    {
                    	//���l�ȊO�Ȃ�A�_���[�W��^���鏈�����Ă�
                    	if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float)i1))
                        {
                    		//�_���[�W��^���邱�Ƃɐ���������ȉ��̏������s��
                            if (movingobjectposition.entityHit instanceof EntityLivingBase)
                            {

                                EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;

                                //�m�b�N�o�b�N
                                if (this.knockbackStrength > 0)
                                {
                                    f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

                                    if (f3 > 0.0F)
                                    {
                                        movingobjectposition.entityHit.addVelocity(this.motionX *
                                            (double)this.knockbackStrength * 0.6000000238418579D / (double)f3, 0.1D, this.motionZ *
                                            (double)this.knockbackStrength * 0.6000000238418579D / (double)f3);
                                    }
                                }
                                else
                                {
                                	movingobjectposition.entityHit.hurtResistantTime = 0;
                                }

                                
                                if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity &&
                                        movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                                {
                                	((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                                }
                            }

                            //�����Ńq�b�g���̌�ʉ����Ȃ�
                            this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                            if(this.isExplosion){
                            	if (!this.worldObj.isRemote)
                            	{
                            		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.0F, false); //����
                            	}
                            }

                            //�����������ƁA�e����������B�G���e�B�e�B�ђʂ�ON�̒e��͂��̂܂܎c���B
                            if (!(movingobjectposition.entityHit instanceof EntityEnderman) && !this.isPenetrateEntity())
                            {
                                this.setDead();
                                //this.worldObj.createExplosion(shootingEntity, this.xTile, this.yTile, this.zTile, 4.0F, true); //����
                            }
                        }
                    }

                }
                else if (!this.isPenetrateBlock())  //�G���e�B�e�B�ɂ͓������ĂȂ��B�u���b�N�ɓ��������B
                {
                    this.xTile = movingobjectposition.blockX;
                    this.yTile = movingobjectposition.blockY;
                    this.zTile = movingobjectposition.blockZ;
                    //this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
                    this.g = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
                    this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    this.motionX = (double)((float)(movingobjectposition.hitVec.xCoord - this.posX));
                    this.motionY = (double)((float)(movingobjectposition.hitVec.yCoord - this.posY));
                    this.motionZ = (double)((float)(movingobjectposition.hitVec.zCoord - this.posZ));
                    f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY +
                             this.motionZ * this.motionZ);
                    this.arrowShake = 7;
                    this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
                    this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
                    this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
                    this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    this.inGround = true;

                    if (this.inTile != 0)
                    {
                        //Block.blocksList[this.inTile].onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
                    	this.g.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
                    }
                }
            }

            //��߂ă|�W�V�����ɑ��x�����Z�B����X�V�B
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            //this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI);
            for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
            {
                ;
            }

            while ( this.rotationPitch - this.prevRotationPitch < -180.0F)
            {
            	this.prevRotationPitch -= 360.0F;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
            {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
            {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
            {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;

            //���X�Ɍ�������
            float f4 = 0.99F;

            //�d�͗���
            //�������x�͕ʃ��\�b�h�Őݒ肵�Ă���B�f�t�H���g�ł�0.0F�B
            f1 = this.fallSpeed();

            //�����ɗL��
            if (this.isInWater())
            {
            	//�A�p�[�e�B�N�����o��
                for (int j1 = 0; j1 < 4; ++j1)
                {
                    f3 = 0.25F;
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f3, this.posY - this.motionY *
                         (double)f3, this.posZ - this.motionZ * (double)f3, this.motionX, this.motionY, this.motionZ);
                }

                //�������傫���Ȃ�
                f4 = 0.8F;
            }

            this.motionX *= (double)f4;
            this.motionY *= (double)f4;
            this.motionZ *= (double)f4;
            this.motionY -= (double)f1;

            //���ȏ�x���Ȃ����������
            if (this.worldObj.isRemote && this.motionX * this.motionX + this.motionZ * this.motionZ < 0.001D)
            {
            	this.setDead();
            }


            this.setPosition(this.posX, this.posY, this.posZ);
            //this.doBlockCollisions();
            this.func_145775_I();
        }
    }

    /*
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("xTile", (short)this.xTile);
        par1NBTTagCompound.setShort("yTile", (short)this.yTile);
        par1NBTTagCompound.setShort("zTile", (short)this.zTile);
        par1NBTTagCompound.setByte("inTile", (byte)this.inTile);
        par1NBTTagCompound.setByte("inData", (byte)this.inData);
        par1NBTTagCompound.setByte("shake", (byte)this.arrowShake);
        par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        par1NBTTagCompound.setDouble("damage", this.damage);
    }

    /*
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.xTile = par1NBTTagCompound.getShort("xTile");
        this.yTile = par1NBTTagCompound.getShort("yTile");
        this.zTile = par1NBTTagCompound.getShort("zTile");
        this.inTile = par1NBTTagCompound.getByte("inTile") & 255;
        this.arrowShake = par1NBTTagCompound.getByte("shake") & 255;
        this.inData = par1NBTTagCompound.getByte("inData") & 255;
        this.inGround = par1NBTTagCompound.getByte("inGround") == 1;

        if (par1NBTTagCompound.hasKey("damage"))
        {
            this.damage = par1NBTTagCompound.getDouble("damage");
        }
    }

    /*
     * �v���C���[�ƏՓ˂������̃��\�b�h�B����͉������Ȃ�
     */
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {

    }

    /*
     * �u���b�N�ɑ΂��A�����������Ƃ�������̑ΏۂɂȂ邩�A�Ƃ���Entity�N���X�̃��\�b�h�B
     * �k�n���r�炵���肷��̂Ɏg���B
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    public void setDamage(double par1)
    {
        this.damage = par1;
    }

    public double getDamage()
    {
        return this.damage;
    }

    public void setKnockbackStrength(int par1)
    {
        this.knockbackStrength = par1;
    }

    public boolean canAttackWithItem()
    {
        return false;
    }

    public boolean getIsFire(){
    	return isFire;
    }

    /** �ȉ��A��MOD�p�̃p�����[�^��`����*/

    /* �������x */
    public float fallSpeed()
    {
    	return 0.02F;
    }

    /* �_���[�W�\�[�X�̃^�C�v */
    public DamageSource thisDamageSource(Entity entity)
    {
        //���ˌ���Entity��null�������ꍇ�̑΍���܂ށB
    	//return entity != null ? EntityDamageSource.causeIndirectMagicDamage(entity, this) : DamageSource.magic;
    	//return DamageSource.fall;
    	return DamageSource.generic;
    }

    /* �u���b�N�ђ� */
    public boolean isPenetrateBlock()
    {
    	return false;
    }

    /* �G���e�B�e�B�ђ� */
    public boolean isPenetrateEntity()
    {
    	return false;
    }

}
