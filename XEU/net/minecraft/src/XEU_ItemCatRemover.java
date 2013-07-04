package net.minecraft.src;

public class XEU_ItemCatRemover extends Item {

	protected XEU_ItemCatRemover(int par1) {
		super(par1);
		setMaxDamage(36);
		setCreativeTab(CreativeTabs.tabTools);
	}
	
	
	
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		TileEntity lte = par3World.getBlockTileEntity(par4, par5, par6);
		
		
		System.out.println("break cat1.");
		return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World,
				par4, par5, par6, par7, par8, par9, par10);
		
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		// TODO Auto-generated method stub
		System.out.println("break cat2.");
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}


	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World,
			int par3, int par4, int par5, int par6,
			EntityLivingBase par7EntityLiving) {
		// onBlockDestroyed
		System.out.println("break cat.");
		TileEntity tileentity = par7EntityLiving.worldObj.getBlockTileEntity(par3, par4, par5);
		if (tileentity != null) {
			System.out.println("have Tile.");
			if (tileentity instanceof TileEntityChest) {
				System.out.println("Tile Chest.");

			}
		}
		
		return super.onBlockDestroyed(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLiving);
//		return super.onBlockDestroyed(par1ItemStack, par2, par3, i, j, entityliving);
	}
}
