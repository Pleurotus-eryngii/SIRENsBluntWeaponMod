package pleurotus.eryngii.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import pleurotus.eryngii.common.EntityBullet;

/*
 * エンティティの描画に関するクラスです．
 * 
 */
@SideOnly(Side.CLIENT)
public class EntityRenderer  extends Render
{
    private static final ResourceLocation bulletTextures = new ResourceLocation("textures/entity/wither/wither.png");

    protected ModelBase modelBullet;

    public EntityRenderer(ModelBase par1ModelBase) {
		super();
		this.modelBullet = par1ModelBase;
		//this.shadowSize = 0.0F;
	}

    private float func_82400_a(float p_82400_1_, float p_82400_2_, float p_82400_3_)
    {
        float f3;

        for (f3 = p_82400_2_ - p_82400_1_; f3 < -180.0F; f3 += 360.0F)
        {
            ;
        }

        while (f3 >= 180.0F)
        {
            f3 -= 360.0F;
        }

        return p_82400_1_ + p_82400_3_ * f3;
    }

    public void doRender(EntityBullet p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        float f2 = this.func_82400_a(p_76986_1_.prevRotationYaw, p_76986_1_.rotationYaw, p_76986_9_);
        float f3 = p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_;
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        float f4 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        this.bindEntityTexture(p_76986_1_);
        this.modelBullet.render(p_76986_1_, 0.0F, 0.0F, 0.0F, f2, f3, f4);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getArrowTextures(EntityBullet par1EntityArrow)
    {
        return bulletTextures;
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getArrowTextures((EntityBullet)par1Entity);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityBullet)par1Entity, par2, par4, par6, par8, par9);
    }
}
