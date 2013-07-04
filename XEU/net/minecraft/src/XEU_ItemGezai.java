package net.minecraft.src;

public class XEU_ItemGezai extends ItemFood {

	public XEU_ItemGezai(int par1, int par2, float par3, boolean par4) {
		super(par1, par2, par3, par4);
		setAlwaysEdible();
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 8;
	}

	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return 0x008fff7f;
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return super.requiresMultipleRenderPasses();
	}

	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
		if (par3EntityPlayer.foodStats.getFoodLevel() < 0) {
			par3EntityPlayer.foodStats.setFoodLevel(0);
		}
		if (par3EntityPlayer.foodStats.getSaturationLevel() < 0) {
			par3EntityPlayer.foodStats.setFoodSaturationLevel(0);
		}
	}
	
	@Override
	protected String func_111208_A() {
		// TODO Auto-generated method stub
		return super.func_111208_A();
	}

}
