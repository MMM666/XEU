package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class XEU_BlockRegenerate extends Block {

	
    protected XEU_BlockRegenerate(int i) {
		super(i, Material.rock);
        blockIndexInTexture = 0;
		setCreativeTab(CreativeTabs.tabBlock);
        setTickRandomly(true);
	}

    @Override
	public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
        if (i == 1)
        {
            return 0;
        }
        return i != 0 ? 3 : 0x25;
    }

    @Override
    public int getBlockColor()
    {
        double d = 0.5D;
        double d1 = 1.0D;
        return ColorizerGrass.getGrassColor(d, d1);
    }

    @Override
    public int getRenderColor(int i)
    {
        return getBlockColor();
    }

    @Override
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = 0;
        int j = 0;
        int k = 0;

        for (int l = -1; l <= 1; l++)
        {
            for (int i1 = -1; i1 <= 1; i1++)
            {
                int j1 = par1IBlockAccess.getBiomeGenForCoords(par2 + i1, par4 + l).getBiomeGrassColor();
                i += (j1 & 0xff0000) >> 16;
                j += (j1 & 0xff00) >> 8;
                k += j1 & 0xff;
            }
        }

        return (i / 9 & 0xff) << 16 | (j / 9 & 0xff) << 8 | k / 9 & 0xff;
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if (world.isRemote)
        {
            return;
        }
        for (int l = 0; l < 8; l++)
        {
            int i1 = (i + random.nextInt(11)) - 6;
            int j1 = (j + random.nextInt(5)) - 3;
            int k1 = (k + random.nextInt(11)) - 6;
            if (world.getBlockId(i1, j1, k1) == 0)
            {
                world.setBlockWithNotify(i1, j1, k1, Block.grass.blockID);
            }
        }
    }
    

    // Forge
    
    public void addCreativeItems(ArrayList itemList) {
    	itemList.add(new ItemStack(mod_XEU_ExpUpper.blockRegenerate));
    }


}
