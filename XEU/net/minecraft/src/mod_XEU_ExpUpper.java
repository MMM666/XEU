package net.minecraft.src;

import java.awt.Component;
import java.awt.Frame;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.server.MinecraftServer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

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
	public static int windowPosX = 20;
	@MLProp
	public static int windowPosY = 50;
	
	@MLProp
	public static int ItemIDGezai = 22293;
	public static Item itemGezai;

	@MLProp
	public static int ItemIDVillager = 22294;
	public static Item itemVillagerRaw;
	public static Item itemVillagerCooked;

	@MLProp
	public static boolean isDebugMessage = true;


	public static void Debug(String pText, Object... pVals) {
		// デバッグメッセージ
		if (isDebugMessage) {
			System.out.println(String.format("XEU-" + pText, pVals));
		}
	}

	@Override
	public String getVersion() {
		return "1.6.1-x";
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
			itemRAYXANBER = (new XEU_ItemRAYXANBER(ItemIDRAYXANBER - 256, EnumToolMaterial.IRON)).setUnlocalizedName("RAYXANBER").func_111206_d("RAYXANBER");
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
			blockRegenerate = (new XEU_BlockRegenerate(BlockIDRegenerate)).setHardness(10F).setResistance(2000F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("regenerate");
			ModLoader.addName(blockRegenerate, "Regenerate");
			ModLoader.addName(blockRegenerate, "ja_JP", "自己再生土");
			ModLoader.registerBlock(blockRegenerate);
		}
		
		// UAV
		if (ItemIDUAV > 0 && MMM_Helper.isClient) {
			itemUAV = (new XEU_ItemUAV(ItemIDUAV - 256)).setUnlocalizedName("uav").func_111206_d("uav");
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
			itemCatRemover = (new XEU_ItemCatRemover(ItemIDCatRemover - 256)).setUnlocalizedName("catHand").func_111206_d("catHand");
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
			itemGezai = (new XEU_ItemGezai(ItemIDGezai - 256, -5, 1.0F, false)).setUnlocalizedName("gezai").func_111206_d("gezai");
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
					Debug("window size : %d, %d", Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
					Display.setResizable(false);
					Display.setLocation(windowPosX, windowPosY);
					Display.setResizable(true);
					Debug("Component: Display");
					break;
				} else {
					Debug(oo.getName());
				}
				if (oo instanceof Frame) {
					oo.setLocation(windowPosX, windowPosY);
					Debug("move window location.");
					for (int li = 0; li < ((Frame) oo).getWindows().length; li++) {
						Debug("ComponentCount:" + ((Frame) oo).getWindows()[li]);
					}
					break;
				}
				oo = oo.getParent();
			} while(oo != null);
		}
		
		// コマンドブロックのクリエイトタグへの追加
		Block.blocksList[Block.commandBlock.blockID].setCreativeTab(CreativeTabs.tabRedstone);
		
		
		ModLoader.setInGameHook(this, true, false);
		
		Debug("MoudeButtonCount:%d", Mouse.getButtonCount());
		
		// 村人虐殺どろっぴ
		MMM_Helper.replaceEntityList(EntityVillager.class, XEU_EntityVillager.class);
		if (ItemIDVillager > 0) {
			itemVillagerRaw = (new XEU_ItemVillagerFlesh(ItemIDVillager + 0, 3, 0.3F, true)).setUnlocalizedName("villagerRaw").func_111206_d("villagerRaw");
			itemVillagerCooked = (new XEU_ItemVillagerFlesh(ItemIDVillager + 1, 8, 0.8F, true)).setUnlocalizedName("villagerCooked").func_111206_d("villagerCooked");
			ModLoader.addLocalization("message.xeu.eatVillager", "Delicious! This is your favorite villager flesh! ");
			ModLoader.addLocalization("message.xeu.eatVillager", "ja_JP", "うまい!これはあなたの好きな人肉だ!");
			ModLoader.addName(itemVillagerRaw, "?Raw Meat");
			ModLoader.addName(itemVillagerRaw, "ja_JP", "?生肉");
			ModLoader.addName(itemVillagerCooked, "?Cooked Meat");
			ModLoader.addName(itemVillagerCooked, "ja_JP", "?焼肉");
			ModLoader.addSmelting(itemVillagerRaw.itemID, new ItemStack(itemVillagerCooked), 0.35F);
		}
		
		// SilkMobSpawner
		Block.blocksList[Block.mobSpawner.blockID] = null;
		Block lmob = new XEU_BlockMobSpawner(Block.mobSpawner.blockID).setHardness(5.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("mobSpawner").disableStats().func_111022_d("mob_spawner").setCreativeTab(CreativeTabs.tabDecorations);
		if (MMM_Helper.replaceBlock(Block.mobSpawner, lmob)) {
			Debug("replace mobSpawner");
		} else {
			Debug("faild mobSpawner");
		}
		
		Debug("%d", MMM_Helper.getHexToInt("ffffffff"));
		Debug("%x", MMM_Helper.getHexToInt("ffffffff"));
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
		// 開発環境だとうまく動作しないのでその対策
//		map.put(EntityPlayer.class, new RenderPlayer());
		map.put(XEU_EntityVillager.class, new RenderVillager());
	}

	@Override
	public boolean onTickInGame(float f, Minecraft minecraft) {
		if (entityUAV != null && !entityUAV.isDead) {
//       		entityUAV.onUpdate();
		}
		if (minecraft.isIntegratedServerRunning() && !MMM_Helper.isForge) {
			// 村人置き換え
			for (int lj = 0; lj < MinecraftServer.getServer().worldServers.length; lj++) {
				List llist = MinecraftServer.getServer().worldServers[lj].loadedEntityList;
				for (int li = 0; li < llist.size(); li++) {
					Entity lentity = (Entity)llist.get(li);
					if (lentity != null && lentity.getClass().isAssignableFrom(EntityVillager.class)) {
						XEU_EntityVillager.respawnVillager((EntityVillager)lentity);
					}
				}
			}
		}
		
		return true;
	}

	public boolean onTickInGame(MinecraftServer minecraftServer) {
		// 動作未チェック
		for (int lj = 0; lj < minecraftServer.worldServers.length; lj++) {
			List llist = minecraftServer.worldServers[lj].loadedEntityList;
			for (int li = 0; li < llist.size(); li++) {
				Entity lentity = (Entity)llist.get(li);
				if (lentity.getClass().isAssignableFrom(EntityVillager.class)) {
					XEU_EntityVillager.respawnVillager((EntityVillager)lentity);
				}
			}
		}
		return true;
	}

	private List getEcecutingTasks(EntityAITasks ptasks) {
		List llist = null;
		try {
			llist = (List)ModLoader.getPrivateValue(EntityAITasks.class, ptasks, 1);
		} catch (Exception e) {
		}
		return llist;
	}

	@Override
	public void generateSurface(World world, Random random, int i, int j) {
		super.generateSurface(world, random, i, j);
	}

}
