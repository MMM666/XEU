package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class XEU_EntityUAV extends EntityPlayerSP {
	

	public XEU_EntityUAV(Minecraft par1Minecraft, World par2World, Session par3Session, int par4) {
		super(par1Minecraft, par2World, par3Session, par4);
		
		mc.renderViewEntity = this;
		setSize(0.0F, 0.0F);
		entityAge = 0;
	}
	
	@Override
	public void setDead() {
		super.setDead();
		if (mc.renderViewEntity == this) {
			mc.renderViewEntity = mc.thePlayer;
		}
		System.out.println("UAV Down.");
	}

	@Override
	public int getMaxHealth() {
		return 0;
	}
	
	@Override
	public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
		posX += motionX;
        posY += motionY;
        posZ += motionZ;
//		onEntityUpdate();
		entityAge++;
		
		if (onGround || entityAge > 100) {
			setDead();
			worldObj.setEntityDead(this);
		}
	}

}
