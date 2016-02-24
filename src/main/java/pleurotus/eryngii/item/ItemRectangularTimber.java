package pleurotus.eryngii.item;



import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemRectangularTimber extends ItemSword {

	public ItemRectangularTimber(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);

	}
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1item, CreativeTabs par1creativetabs, List itemList){
		ItemStack itemStack = new ItemStack(this, 1, 0);
        itemStack.addEnchantment(Enchantment.knockback, 2);
        itemList.add(itemStack);
	}
	/*
	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		itemStack.addEnchantment(Enchantment.knockback, 2);
	}
	*/



}

