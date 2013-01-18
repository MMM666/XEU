﻿package net.minecraft.src;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.client.Minecraft;

public class XEU_ItemRAYXANBER extends ItemSword {
	
    public XEU_ItemRAYXANBER(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, enumtoolmaterial);
    }
    
    /**
     * 恐らくマルチサーバー側の当たり判定処理がおかしい。
     * 視点位置を考慮していない計算なので、標準の視線判定では足元のブロックで判定が終わる。
     */
    public MovingObjectPosition getRayTrace(EntityLiving pEntityLiving, double pRange, float pDelta) {
        Vec3 var4 = pEntityLiving.getPosition(pDelta);
        if (pEntityLiving.yOffset == 0.0F) {
        	var4.yCoord += pEntityLiving.getEyeHeight();
        }
        Vec3 var5 = pEntityLiving.getLook(pDelta);
        Vec3 var6 = var4.addVector(var5.xCoord * pRange, var5.yCoord * pRange, var5.zCoord * pRange);
        return pEntityLiving.worldObj.rayTraceBlocks(var4, var6);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
    	if (!world.isRemote) 
    	{
        	// 視線方向に射程１０
        	float f = 1.0F;
        	double d = 10D;
    		// TODO:littleMaid用コードここから
        	EntityLiving entityliving = entityplayer;
    		try {
    			// 射手の情報をEntityLittleMaidAvatarからEntityLittleMaidへ置き換える
    			Field field = entityliving.getClass().getField("avatar");
    			entityliving = (EntityLiving)field.get(entityliving);
    		}
    		catch (NoSuchFieldException e) {
    		}
    		catch (Exception e) {
    		}
    		// ここまで

//        	MovingObjectPosition moving = entityliving.rayTrace(d, f);
        	MovingObjectPosition moving = getRayTrace(entityliving, d, f);
            Vec3 vec3d = entityliving.getPosition(f);
            if (entityliving.yOffset == 0.0F) {
            	vec3d.yCoord += entityliving.getEyeHeight();
            }
            if (moving != null) {
                d = moving.hitVec.distanceTo(vec3d);
            }
            Vec3 vec3d1 = entityliving.getLook(f);
            Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
            Entity pointedEntity = null;

            float f1 = 1.0F;
            List list = world.getEntitiesWithinAABBExcludingEntity(entityliving, entityliving.boundingBox.addCoord(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d).expand(f1, f1, f1));

            System.out.print(String.format("LightningBolt:%s, Remort:%b, Count:%d, ", entityliving.getClass().toString(), world.isRemote, list.size()));
            System.out.print(String.format("px:%f, py:%f, pz:%f, ", vec3d.xCoord, vec3d.yCoord, vec3d.zCoord));
            System.out.println(String.format("vx:%f, vy:%f, vz:%f, d:%f", vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, d));
        	
            double d2 = 0.0D;
            for (int i = 0; i < list.size(); i++) {
                Entity entity = (Entity)list.get(i);
                if (entity == entityliving) continue;
                if (!entity.canBeCollidedWith()) {
                    continue;
                }
                float f2 = entity.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity.boundingBox.expand(f2, f2, f2);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d2);
                if (axisalignedbb.isVecInside(vec3d)) {
                    if (0.0D < d2 || d2 == 0.0D) {
                        pointedEntity = entity;
                        d2 = 0.0D;
                    }
                    continue;
                }
                if (movingobjectposition == null) {
                    continue;
                }
                double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
                if (d3 < d2 || d2 == 0.0D) {
                    pointedEntity = entity;
                    d2 = d3;
                }
            }
            if (pointedEntity != null) {
                moving = new MovingObjectPosition(pointedEntity);
            }
        	if (moving != null) {
            	if (moving.typeOfHit == EnumMovingObjectType.TILE) {
            		System.out.println("Thunder for TILE.");
            		world.addWeatherEffect(new EntityLightningBolt(world, (double)moving.blockX, (double)moving.blockY + 1.0D, (double)moving.blockZ));
            	} else {
            		System.out.println("Thunder for Entity.");
            		world.addWeatherEffect(new EntityLightningBolt(world, moving.entityHit.posX, moving.entityHit.posY, moving.entityHit.posZ));
            	}
            	itemstack.damageItem(4, entityplayer);
        	}
    	}
    	
    	return super.onItemRightClick(itemstack, world, entityplayer);
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
    	return 0xafcfff;
    }
    
    @Override
    public boolean requiresMultipleRenderPasses() {
    	return false;
    }
    
    @Override
    public boolean canHarvestBlock(Block par1Block) {
    	return true;
    }
    
    @Override
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block) {
    	return 15.0F;
    }
    
    @Override
    public boolean hitEntity(ItemStack par1ItemStack,
    		EntityLiving par2EntityLiving, EntityLiving par3EntityLiving) {
    	// TODO Auto-generated method stub
    	return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
    }
    
    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World,
    		Entity par3Entity, int par4, boolean par5) {
    	super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
    	
    	// 範囲攻撃
		if (par2World.isRemote) {
			// Client
			if (par3Entity instanceof EntityPlayer) {
				EntityPlayer lep = (EntityPlayer)par3Entity;
				if (lep.getCurrentEquippedItem() == par1ItemStack) {
					// 腕の振り始めを検出して判定開始
					if (lep.isSwingInProgress) {
	    				Minecraft lmc = ModLoader.getMinecraftInstance();
	        			if (lep.swingProgressInt == -1) {
	        				// 攻撃判定
	    					Entity lentity = null;
	    					if (lmc != null && lmc.objectMouseOver != null) {
	        					lentity = lmc.objectMouseOver.entityHit;
	    					}
	    					// 自身の周囲のMOBを獲得
	        				List llist = par2World.getEntitiesWithinAABB(EntityLiving.class, par3Entity.boundingBox.expand(5D, 0D, 5D));
	        				for (int lj = 0; lj < llist.size(); lj++) {
	        					// 自分と通常の処理対象は除外
	        					EntityLiving lel = (EntityLiving)llist.get(lj);
	        					if (lel == lentity || lel == lep) continue;
	        					// 射程距離の判定、MOBの大きさを考慮
	        					double lln = 3.0D + (double)lel.width;
	        					lln *= lln;
	        					if (lep.getDistanceSqToEntity(lel) <= lln) {
	           						// 範囲攻撃の対象
	            					double lvx = lel.posX - lep.posX;
	            					double lvz = lep.posZ - lel.posZ;
	                				float lyaw = (float)Math.toDegrees(Math.atan2(lvx, lvz));
	            					float lf = lep.rotationYaw - lyaw;
	            					for (;lf > 360F; lf -= 360);
	            					for (;lf < 0F; lf += 360);
	            					// 左230dig - 正面180deg - 右100dig
	        						if (lf > 100F && lf < 230F) {
	            						// 攻撃判定
	                        			System.out.println(String.format("%s, %d : %d : %f/%f : %f/%f", lel.getClass().getSimpleName(), lep.swingProgressInt, lep.attackTime, lep.getDistanceSqToEntity(lel), lln, lep.rotationYawHead, lf));
	                					ModLoader.clientSendPacket(new Packet7UseEntity(lep.entityId, lel.entityId, 1));
	                			        lep.attackTargetEntityWithCurrentItem(lel);
	        						}
	        					}
	        				}
	        				
	        				// クールタイム
	        				lep.attackTime = 20;
	        			}
	    				try {
	        				// 攻撃間隔を強制的に伸ばす(連打が効かないように)
	        				ModLoader.setPrivateValue(Minecraft.class, lmc, 28, lep.attackTime);
	    				} catch (Exception e) {
	    				}
					}
    			}
    		}
    		
    	}
    }
    
}
