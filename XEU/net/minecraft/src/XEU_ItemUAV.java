package net.minecraft.src;


public class XEU_ItemUAV extends ItemSnowball {

	public XEU_ItemUAV(int par1) {
		super(par1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		if (!par3EntityPlayer.capabilities.isCreativeMode) {
			par1ItemStack.stackSize--;
		}
		
		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if (!par2World.isRemote) {
			Minecraft mc = MMM_Helper.mc;
			XEU_EntityUAV uav = new XEU_EntityUAV(mc, par2World, mc.func_110432_I(), par2World.provider.dimensionId);
			uav.setPositionAndRotation(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, par3EntityPlayer.rotationYaw, par3EntityPlayer.rotationPitch);
			Vec3 vec = par3EntityPlayer.getLookVec();
			uav.motionX = vec.xCoord * 3D;
			uav.motionY = vec.yCoord * 3D;
			uav.motionZ = vec.zCoord * 3D;
			par2World.spawnEntityInWorld(uav);
			mod_XEU_ExpUpper.entityUAV = uav;
		}
		
		return par1ItemStack;
	}

}
