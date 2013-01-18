package net.minecraft.src;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Frame;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

public class mod_XEU_ExpUpper extends BaseMod {
	
	@MLProp
	public static int ItemIDRAYXANBER = 22290;
	public static Item itemRAYXANBER;
	
	@MLProp
	public static int BlockIDRegenerate = 223;
	public static Block blockRegenerate;
	
	@MLProp
	public static int ItemIDUAV = 22291;
	public static Item itemUAV;
	public static XEU_EntityUAV entityUAV = null;

	@MLProp
	public static int ItemIDCatRemover = 22292;
	public static Item itemCatRemover;
	
	@MLProp
	public static boolean moveWindow = true;
	
	@MLProp
	public static int ItemIDGezai = 22293;
	public static Item itemGezai;
	
	public static Entity target;
	public static boolean isHold = false;

	@Override
	public String getVersion() {
		return "1.4.7-x";
	}

	@Override
	public void load() {
		if (MMM_Helper.isClient) {
			// GUI を開くキーの登録と名称変換テーブルの登録
			String s = "key.ExpUpper";
			ModLoader.registerKey(this, new KeyBinding(s, 23), false);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(),
					(new StringBuilder()).append("LevelUp").toString()
					);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(), "ja_JP",
					(new StringBuilder()).append("レッベルアッポ").toString()
					);
			
			s = "key.ToggleRain";
			ModLoader.registerKey(this, new KeyBinding(s, 24), false);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(),
					(new StringBuilder()).append("ToggleRain").toString()
					);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(), "ja_JP",
					(new StringBuilder()).append("雨が降ったり止んだり").toString()
					);
			s = "key.TFV.NextItem";
			ModLoader.registerKey(this, new KeyBinding(s, -97), false);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(),
					(new StringBuilder()).append("NextItem").toString()
					);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(), "ja_JP",
					(new StringBuilder()).append("次のアイテム").toString()
					);
			s = "key.TFV.PrevItem";
			ModLoader.registerKey(this, new KeyBinding(s, -96), false);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(), 
					(new StringBuilder()).append("PrevItem").toString()
					);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(), "ja_JP",
					(new StringBuilder()).append("前のアイテム").toString()
					);
		}
		
		
		// RAYXANBER
		if (ItemIDRAYXANBER > 0) {
			itemRAYXANBER = (new XEU_ItemRAYXANBER(ItemIDRAYXANBER - 256, EnumToolMaterial.IRON)).setIconCoord(2, 4).setItemName("RAYXANBER");
			ModLoader.addName(itemRAYXANBER, "RAYXANBER");
			ModLoader.addName(itemRAYXANBER, "ja_JP", "雷斬刃");
			ModLoader.addRecipe(new ItemStack(itemRAYXANBER), new Object[] {
				"RIR", 
				"RIR", 
				" S ", 
				Character.valueOf('I'), Item.ingotIron,
				Character.valueOf('S'), Item.stick,
				Character.valueOf('R'), Item.redstone
			});
		}
		
		// Regenarate Block
		if (BlockIDRegenerate > 0) {
			blockRegenerate = (new XEU_BlockRegenerate(BlockIDRegenerate)).setHardness(10F).setResistance(2000F).setStepSound(Block.soundStoneFootstep).setBlockName("regenerate");
			ModLoader.addName(blockRegenerate, "Regenerate");
			ModLoader.addName(blockRegenerate, "ja_JP", "自己再生土");
			ModLoader.registerBlock(blockRegenerate);
		}
		
		// UAV
		if (ItemIDUAV > 0 && MMM_Helper.isClient) {
			itemUAV = (new XEU_ItemUAV(ItemIDUAV - 256)).setIconCoord(14, 0).setItemName("uav");
			ModLoader.addName(itemUAV, "UAV");
			ModLoader.addName(itemUAV, "ja_JP", "探査機");
			ModLoader.addRecipe(new ItemStack(itemUAV), new Object[] {
				"W W", 
				"W W", 
				" R ", 
				Character.valueOf('W'), Block.cloth,
				Character.valueOf('R'), Item.redstone
			});
//            ModLoader.setInGameHook(this, true, false);
		}
		
		// 黒猫ハンド
		if (ItemIDCatRemover > 0) {
			itemCatRemover = (new XEU_ItemCatRemover(ItemIDCatRemover - 256)).setIconCoord(2, 8).setItemName("catHand");
			ModLoader.addName(itemCatRemover, "Cat Hand");
			ModLoader.addName(itemCatRemover, "ja_JP", "黒猫ハンド");
			ModLoader.addRecipe(new ItemStack(itemCatRemover), new Object[] {
				"WEW", 
				"WBW", 
				" S ", 
				Character.valueOf('W'), Block.cloth,
				Character.valueOf('B'), Item.writableBook,
				Character.valueOf('E'), Item.enderPearl,
				Character.valueOf('S'), Item.stick
			});
		}
		
		if (ItemIDGezai > 0) {
			// 虫下し
			itemGezai = (new XEU_ItemGezai(ItemIDGezai - 256, -5, 1.0F, false)).setIconCoord(11, 5).setItemName("gezai");
			ModLoader.addName(itemGezai, "GEZAI");
			ModLoader.addName(itemGezai, "ja_JP", "下剤");
			ModLoader.addRecipe(new ItemStack(itemGezai), new Object[] {
				"Z", 
				Character.valueOf('Z'), Item.rottenFlesh
			});
		}
		
		if (moveWindow && MMM_Helper.isClient) {
			// Window Pos
			Component oo = Display.getParent();
			do {
				if (oo == null) {
					System.out.println("owner null.");
				} else {
					System.out.println(oo.getName());
				}
				if (oo instanceof Frame) {
//                    oo.setLocation(-1800, 100);
//                    oo.setLocation(1000, 100);
					oo.setLocation(20, 50);
					System.out.println("move window location.");
					for (int li = 0; li < ((Frame) oo).getWindows().length; li++) {
						System.out.println("ComponentCount:" + ((Frame) oo).getWindows()[li]);
					}
					break;
				}
				oo = oo.getParent();
			} while(oo != null);
		}
		
		
		// 簡易スカウター
		if (MMM_Helper.isClient) {
			// GUI を開くキーの登録と名称変換テーブルの登録
			String s = "key.Lockon";
			ModLoader.registerKey(this, new KeyBinding(s, 157), false);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(),
					(new StringBuilder()).append("Lock ON").toString()
					);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(),
					"ja_JP",
					(new StringBuilder()).append("ロックオン").toString()
					);
			
		}
		
		// コマンドブロックのクリエイトタグへの追加
		Block.blocksList[Block.commandBlock.blockID].setCreativeTab(CreativeTabs.tabRedstone);
		
		ModLoader.setInGameHook(this, true, false);
		
		System.out.println(String.format("MoudeButtonCount:%d", Mouse.getButtonCount()));
	}

	@Override
	public void keyboardEvent(KeyBinding keybinding) {
		// LevelUp
		Minecraft mcGame = MMM_Helper.mc;
		if (mcGame.theWorld != null && mcGame.currentScreen == null) {
			if (keybinding.keyDescription.endsWith(".ExpUpper")) {
				mcGame.thePlayer.sendChatMessage(String.format("/xp 100L %s", mcGame.thePlayer.username));
			}
			if (keybinding.keyDescription.endsWith(".ToggleRain")) {
				if (mcGame.theWorld.getRainStrength(1.0F) > 0F) {
					mcGame.theWorld.worldInfo.setRaining(false);
					mcGame.theWorld.setRainStrength(0.0F);
					mcGame.thePlayer.sendChatMessage("Call Sunny.");
				} else {
					mcGame.theWorld.worldInfo.setRaining(true);
					mcGame.theWorld.setRainStrength(1.0F);
					mcGame.thePlayer.sendChatMessage("Call Rain.");
				}
			}
			if (keybinding.keyDescription.endsWith(".Lockon")) {
				isHold = !isHold;
			}
			if (keybinding.keyDescription.endsWith(".TFV.NextItem")) {
				mcGame.thePlayer.inventory.changeCurrentItem(1);
			}
			if (keybinding.keyDescription.endsWith(".TFV.PrevItem")) {
				mcGame.thePlayer.inventory.changeCurrentItem(-1);
			}
		}
	}

	@Override
	public void addRenderer(Map map) {
		map.put(XEU_EntityUAV.class, new MMM_RenderDummy());
	}

	@Override
	public boolean onTickInGame(float f, Minecraft minecraft) {
		if (entityUAV != null && !entityUAV.isDead) {
//       		entityUAV.onUpdate();
		}
		if (minecraft.currentScreen == null || minecraft.currentScreen instanceof GuiChat) {
			FontRenderer lfont = minecraft.fontRenderer;
			List<String> llines = new ArrayList<String>();
			if (target == null || target.isDead) {
				isHold = false;
				target = null;
			}
			if (!isHold) {
				if (minecraft.objectMouseOver != null 
						&& minecraft.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY
						&& minecraft.objectMouseOver.entityHit != null) {
					Entity lentity = minecraft.objectMouseOver.entityHit;
					target = MinecraftServer.getServer().worldServers[0].getEntityByID(lentity.entityId);
				} else {
					target = null;
				}
			}
			// メッセージの作成
			if (target != null) {
				String ls;
				Class lclass;
				llines.add(String.format("%s (%d)", target.getClass().getSimpleName(), target.entityId));
				llines.add(String.format("%s", target.worldObj.getClass().getSimpleName()));
				llines.add(String.format("Range=%6f(%f)", minecraft.thePlayer.getDistanceToEntity(target), target.width));
				if (target instanceof EntityLiving) {
					llines.add(String.format("Health=%d", ((EntityLiving)target).health));
				}
				if (target instanceof EntityAgeable) {
					llines.add(String.format("Age=%d", ((EntityAgeable)target).getGrowingAge()));
				}
				ls = "EIT_EntityChicken";
				if (target.getClass().getSimpleName().equals(ls)) {
					lclass = MMM_Helper.getNameOfClass(ls);
					try {
						llines.add(String.format("Frontal=%b/%b", (Boolean)lclass.getMethod("isFullFrontal", new Class[] {}).invoke(target, new Object[] {}), (Boolean)lclass.getField("fFullFrontal").get(target)));
						llines.add(String.format("HPMax=%b", (Integer)lclass.getMethod("isHPMax", new Class[] {}).invoke(target, new Object[] {})));
					} catch (Exception e) {
					}
				}
				if (target instanceof EntityChicken) {
					llines.add(String.format("NextEgg=%d", ((EntityChicken)target).timeUntilNextEgg));
				}
				if (target instanceof EntityLiving) {
					Entity lentity;
					EntityLiving lliving = (EntityLiving)target;
					lentity = lliving.getAttackTarget();
					if (lentity != null) {
						llines.add(String.format("AttackTarget=%S(Alive:%b/Dead:%b)", lentity.getClass().getSimpleName(), lentity.isEntityAlive(), lentity.isDead));
					} else {
						llines.add("AttackTarget=NoTarget");
					}
					lentity = lliving.getAITarget();
					if (lentity != null) {
						llines.add(String.format("AITarget=%S(Alive:%b/Dead:%b)", lentity.getClass().getSimpleName(), lentity.isEntityAlive(), lentity.isDead));
					} else {
						llines.add("AITarget=NoTarget");
					}
					llines.add(String.format("Dir YawB=%f/ %f/ %f", lliving.rotationYaw, lliving.prevRotationYaw, lliving.newRotationYaw));
					llines.add(String.format("Dir YawR=%f/ %f", lliving.renderYawOffset, lliving.prevRenderYawOffset));
					llines.add(String.format("Dir YawH=%f/ %f", lliving.rotationYawHead, lliving.prevRotationYawHead));
				}
				if (target instanceof EntityCreature) {
					Entity lentity;
					lentity = ((EntityCreature)target).getEntityToAttack();
					if (lentity != null) {
						llines.add(String.format("entityToAttack=%S(Alive:%b/Dead:%b)", lentity.getClass().getSimpleName(), lentity.isEntityAlive(), lentity.isDead));
					} else {
						llines.add("entityToAttack=NoTarget");
					}
				}
				ls = "LMM_EntityLittleMaid";
				if (target.getClass().getSimpleName().equals(ls)) {
					lclass = MMM_Helper.getNameOfClass(ls);
					try {
						llines.add(String.format("C-Limit=%d(%f)", (Integer)lclass.getDeclaredField("maidContractLimit").get(target), (Float)lclass.getMethod("getContractLimitDays").invoke(target)));
					} catch (Exception e) {
					}
				}
				
				llines.add(String.format("Ride %s(%d : %f) / %s(%d : %f)", 
						target.ridingEntity == null ? "NULL" : target.ridingEntity.getClass().getSimpleName(),
						target.ridingEntity == null ? 0 : target.ridingEntity.entityId,
						target.ridingEntity == null ? 0 : target.ridingEntity.yOffset,
						target.riddenByEntity == null ? "NULL" : target.riddenByEntity.getClass().getSimpleName(),
						target.riddenByEntity == null ? 0 : target.riddenByEntity.entityId,
						target.riddenByEntity == null ? 0 : target.riddenByEntity.yOffset
				));
			} else {
				llines.add("NOTARGET");
			}
			// Draw
			int lj = 5;
			for (String ls : llines) {
				lfont.drawStringWithShadow(ls, 5, lj, isHold ? 0x00ffcccc : 0x00ffffff);
				lj += lfont.FONT_HEIGHT;
			}
			llines.clear();
		}
//    	System.out.println(MinecraftServer.getServer() == null ? "noServer" : "AliveServer");
		if (false && Mouse.getEventButtonState()) {
			System.out.println(String.format("MoudeButtonEvent:%d", Mouse.getEventButton()));
		}

		return true;
	}

	@Override
	public void generateSurface(World world, Random random, int i, int j) {
		super.generateSurface(world, random, i, j);
	}
	
	@Override
	public void clientDisconnect(NetClientHandler var1) {
		target = null;
	}
	
	@Override
	public void clientConnect(NetClientHandler var1) {
		target = null;
	}
	
}
