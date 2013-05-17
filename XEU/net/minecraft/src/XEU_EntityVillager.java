package net.minecraft.src;

public class XEU_EntityVillager extends EntityVillager {

	public XEU_EntityVillager(World par1World) {
		super(par1World);
	}

	public XEU_EntityVillager(World par1World, int par2) {
		super(par1World, par2);
	}

	@Override
	protected int getDropItemId() {
		if (mod_XEU_ExpUpper.itemVillagerRaw == null) {
			return 0;
		} else {
			return isBurning() ? mod_XEU_ExpUpper.itemVillagerCooked.itemID : mod_XEU_ExpUpper.itemVillagerRaw.itemID;
		}
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int li = 1 + rand.nextInt(par2 + 1);
		if (!isChild()) {
			MerchantRecipeList lmrl = getRecipes(null);
			if (lmrl != null) {
				for (Object lo : lmrl) {
					MerchantRecipe lml = (MerchantRecipe)lo;
					entityDropItem(lml.getItemToSell(), 0.0F);
				}
			}
			li += rand.nextInt(3);
		} else {
		}
		int ll = getDropItemId();
		if (ll > 0) {
			dropItem(ll, li);
		}
	}

	@Override
	protected void dropRareDrop(int par1) {
		// TODO Auto-generated method stub
		super.dropRareDrop(par1);
	}

	@Override
	public EntityVillager func_90012_b(EntityAgeable par1EntityAgeable) {
		EntityVillager var2 = new XEU_EntityVillager(this.worldObj);
		var2.initCreature();
		return var2;
	}

}
