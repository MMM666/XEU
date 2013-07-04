package net.minecraft.src;

public class XEU_ItemVillagerFlesh extends ItemFood {

	public XEU_ItemVillagerFlesh(int par1, int par2, float par3, boolean par4) {
		super(par1, par2, par3, par4);
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		if (par2World.isRemote) {
			MMM_Helper.mc.ingameGUI.getChatGUI().printChatMessage(StatCollector.translateToLocal("message.xeu.eatVillager"));
//			this.mc.ingameGUI.getChatGUI().printChatMessage(ChatMessageComponent.func_111078_c(par1Packet3Chat.message).func_111068_a(true));
//			par3EntityPlayer.sendChatToPlayer(StatCollector.translateToLocal("message.xeu.eatVillager"));
		}
		return super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
	}

}
