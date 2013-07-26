package net.minecraft.src;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;

public class XEU_RenderWorldItem {

	protected static boolean occlusionEnabled;
	protected static int glRenderListBase;
	protected static WorldRenderer worldItem;
	protected static List tileEntities = new ArrayList();
	protected static IntBuffer occlusionResult = GLAllocation.createDirectIntBuffer(64);
	protected static IntBuffer glOcclusionQueryBase;


	public static boolean renderItem(Entity pEntity,
			ItemStack pItemStack, int pIndex) {
		if (worldItem == null) {
			occlusionEnabled = OpenGlCapsChecker.checkARBOcclusion();
			
			if (occlusionEnabled) {
				occlusionResult.clear();
				glOcclusionQueryBase = GLAllocation.createDirectIntBuffer(1);
				glOcclusionQueryBase.clear();
				glOcclusionQueryBase.position(0);
				glOcclusionQueryBase.limit(1);
				ARBOcclusionQuery.glGenQueriesARB(glOcclusionQueryBase);
			}
			
			glRenderListBase = GLAllocation.generateDisplayLists(1 * 3);
			worldItem = new WorldRenderer(MMM_Helper.mc.theWorld, tileEntities, 0, 0, 0, glRenderListBase);
//			worldItem = WrUpdates.makeWorldRenderer(MMM_Helper.mc.theWorld,
//					tileEntities, 0, 0, 0, glRenderListBase);
			if (occlusionEnabled) {
				worldItem.glOcclusionQuery = glOcclusionQueryBase.get(0);
			}
			worldItem.isWaitingOnOcclusionQuery = false;
			worldItem.isVisible = true;
			worldItem.isInFrustum = false;
			worldItem.chunkIndex = 0;
		}
		
//		worldItem.setPosition(0, 0, 0);
		RenderHelper.disableStandardItemLighting();
		worldItem.updateRenderer();

		if (occlusionEnabled && MMM_Helper.mc.gameSettings.advancedOpengl && !MMM_Helper.mc.gameSettings.anaglyph) {
//			worldItem.isVisible = true;
//			worldItem.isVisibleFromPosition = true;
//			worldItem.isUpdating = false;
			worldItem.isInFrustum = true;
//			worldItem.isWaitingOnOcclusionQuery = false;
			
			
//			GL11.glFinish();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
//			GL11.glDisable(GL11.GL_FOG);
			GL11.glColorMask(false, false, false, false);
			GL11.glDepthMask(false);
			
			checkOcclusionQueryResult();
			render(0, 0D);
/*			
			GL11.glPushMatrix();
			if (worldItem.skipAllRenderPasses()) {
				worldItem.isInFrustum = false;
			} else if (worldItem.isUpdating) {
				worldItem.isVisible = true;
			} else if (worldItem.isInFrustum) {
				if (Config.isOcclusionFancy() && !worldItem.isInFrustrumFully) {
					worldItem.isVisible = true;
				} else if (worldItem.isInFrustum && !worldItem.isWaitingOnOcclusionQuery) {
					ARBOcclusionQuery.glBeginQueryARB(ARBOcclusionQuery.GL_SAMPLES_PASSED_ARB, worldItem.glOcclusionQuery);
					worldItem.callOcclusionQueryList();
					ARBOcclusionQuery.glEndQueryARB(ARBOcclusionQuery.GL_SAMPLES_PASSED_ARB);
					worldItem.isWaitingOnOcclusionQuery = true;
				}
			}
			GL11.glPopMatrix();
*/
//			GL11.glFinish();
			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
//			GL11.glEnable(GL11.GL_FOG);
		} else {
			render(0, 0D);
		}


		return true;
	}

	private static void checkOcclusionQueryResult() {
		if (worldItem.isWaitingOnOcclusionQuery) {
			occlusionResult.clear();
			ARBOcclusionQuery.glGetQueryObjectuARB(worldItem.glOcclusionQuery, ARBOcclusionQuery.GL_QUERY_RESULT_AVAILABLE_ARB, occlusionResult);
			
			if (occlusionResult.get(0) != 0) {
				worldItem.isWaitingOnOcclusionQuery = false;
				occlusionResult.clear();
				ARBOcclusionQuery.glGetQueryObjectuARB(worldItem.glOcclusionQuery, ARBOcclusionQuery.GL_QUERY_RESULT_ARB, occlusionResult);
				worldItem.isVisible = occlusionResult.get(0) != 0;
			}
		}
	}

	protected static int render(int par3, double par4) {
		int ll = worldItem.getGLCallListForPass(par3);
		if (ll < 0) {
			worldItem.markDirty();
			return 0;
		}
		GL11.glDisable(GL11.GL_FOG);

		occlusionResult.clear();
		occlusionResult.put(ll);
		MMM_Helper.mc.entityRenderer.enableLightmap(par4);
//		GL11.glTranslatef((float)(-var15), (float)(-var10), (float)(-var12));
		GL11.glCallLists(occlusionResult);
//		GL11.glTranslatef((float)var15, (float)var10, (float)var12);
		MMM_Helper.mc.entityRenderer.disableLightmap(par4);
		return 1;
	}

}
