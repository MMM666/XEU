package net.minecraft.src;

/**
 * SpawnBlockに属性を付けて回収できるようにするクラス。
 * 回収にはシルクタッチが必要。
 */
public class XEU_BlockMobSpawner extends BlockMobSpawner {

	protected XEU_BlockMobSpawner(int par1) {
		super(par1);
	}

	@Override
	public void onBlockHarvested(World par1World, int par2, int par3, int par4,
			int par5, EntityPlayer par6EntityPlayer) {
		super.onBlockHarvested(par1World, par2, par3, par4, par5, par6EntityPlayer);
		
		if (EnchantmentHelper.getSilkTouchModifier(par6EntityPlayer)) {
			// アイテムに属性を記録
			ItemStack litemstack = this.createStackedBlock(par5);
			
			if (litemstack != null) {
				TileEntity ltile = par1World.getBlockTileEntity(par2, par3, par4);
				if (ltile instanceof TileEntityMobSpawner) {
					String ls = ((TileEntityMobSpawner)ltile).func_98049_a().getEntityNameToSpawn();
					NBTTagCompound lnbt = new NBTTagCompound();
					NBTTagList llist = new NBTTagList("Lore");
					llist.appendTag(new NBTTagString("Lore", ls));
					lnbt.setTag("Lore", llist);
					litemstack.setTagInfo("display", lnbt);
				}
				dropBlockAsItem_do(par1World, par2, par3, par4, litemstack);
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLiving par5EntityLiving, ItemStack par6ItemStack) {
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, par6ItemStack);
		TileEntity ltile = par1World.getBlockTileEntity(par2, par3, par4);
		if (ltile instanceof TileEntityMobSpawner) {
			// 属性付与
			if (par6ItemStack.hasTagCompound() &&
				par6ItemStack.getTagCompound().hasKey("display")) {
				NBTTagCompound lnbt = par6ItemStack.getTagCompound().getCompoundTag("display");
				if (lnbt.hasKey("Lore")) {
					NBTTagList llist = lnbt.getTagList("Lore");
					if (llist.tagCount() > 0) {
						String ls = ((NBTTagString)llist.tagAt(0)).data;
						((TileEntityMobSpawner)ltile).func_98049_a().setMobID(ls);
					}
				}
			} else {
//				((TileEntityMobSpawner)ltile).func_98049_a().setMobID("Zombie");
			}
		}
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4,
			EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		ItemStack litemstack = par5EntityPlayer.getCurrentEquippedItem();
		
		if (litemstack != null && litemstack.getItem() instanceof ItemEditableBook) {
			// 書き込み済み本によって属性を変更
			if (litemstack.getDisplayName().contains("ChangeSpawner")) {
				NBTTagList llist = (NBTTagList)litemstack.getTagCompound().getTag("pages");
				String ls = ((NBTTagString)llist.tagAt(0)).data.split("\n")[0];
				if (ls != null && !ls.isEmpty()) {
					TileEntity ltile = par1World.getBlockTileEntity(par2, par3, par4);
					if (ltile instanceof TileEntityMobSpawner) {
						MobSpawnerBaseLogic llogic = ((TileEntityMobSpawner)ltile).func_98049_a();
						llogic.setMobID(ls);
						try {
							// クリアしないと表示が変わらない。
							ModLoader.setPrivateValue(MobSpawnerBaseLogic.class, llogic, 9, null);
						} catch (Exception e) {
						}
					}
				}
				litemstack.stackSize--;
				return true;
			}
		}
		
		return super.onBlockActivated(par1World, par2, par3, par4,
				par5EntityPlayer, par6, par7, par8, par9);
	}

}
