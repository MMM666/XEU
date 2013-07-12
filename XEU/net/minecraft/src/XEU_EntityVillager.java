package net.minecraft.src;

public class XEU_EntityVillager extends EntityVillager {

	public XEU_EntityVillager(World par1World) {
		super(par1World);
	}

	public XEU_EntityVillager(World par1World, int par2) {
		super(par1World, par2);
	}

	public static void respawnVillager(EntityVillager pEntity) {
		// ’u‚«Š·‚¦‚æ‚¤
		if (pEntity.isDead) return;
		XEU_EntityVillager lentity = new XEU_EntityVillager(pEntity.worldObj, pEntity.getProfession());
		lentity.setLocationAndAngles(pEntity.posX, pEntity.posY, pEntity.posZ, pEntity.rotationYaw, pEntity.rotationPitch);
		pEntity.worldObj.spawnEntityInWorld(lentity);
		pEntity.setDead();
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
		// qì‚è
		EntityVillager var2 = new XEU_EntityVillager(this.worldObj);
//		var2.initCreature();
		var2.func_110161_a((EntityLivingData)null);
		return var2;
	}

	@Override
	public boolean func_110164_bC() {
		// ñ‚É“ê‚Â‚¯‚é
		return true;
	}

}
