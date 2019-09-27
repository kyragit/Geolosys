package com.oitsjustjose.geolosys.common.world;

import java.util.ArrayList;
import java.util.Random;

import com.oitsjustjose.geolosys.api.world.DepositStone;
import com.oitsjustjose.geolosys.api.world.IDeposit;
import com.oitsjustjose.geolosys.common.world.feature.PlutonOreFeature;
import com.oitsjustjose.geolosys.common.world.feature.PlutonStoneFeature;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class PlutonRegistry
{
    private ArrayList<IDeposit> ores;
    private ArrayList<IDeposit> oreWeightList;
    private ArrayList<DepositStone> stones;
    private ArrayList<DepositStone> stoneWeightList;

    public PlutonRegistry()
    {
        this.ores = new ArrayList<>();
        this.oreWeightList = new ArrayList<>();
        this.stones = new ArrayList<>();
        this.stoneWeightList = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<IDeposit> getOres()
    {
        return (ArrayList<IDeposit>) this.ores.clone();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<IDeposit> getStones()
    {
        return (ArrayList<IDeposit>) this.stones.clone();
    }

    public boolean addOrePluton(IDeposit ore)
    {
        if (ore instanceof DepositStone)
        {
            return addStonePluton((DepositStone) ore);
        }

        for (int i = 0; i < ore.getChance(); i++)
        {
            oreWeightList.add(ore);
        }

        return this.ores.add(ore);
    }

    public boolean addStonePluton(DepositStone stone)
    {
        for (int i = 0; i < stone.getChance(); i++)
        {
            stoneWeightList.add(stone);
        }
        return this.stones.add(stone);
    }

    public IDeposit pickPluton()
    {
        if (this.oreWeightList.size() > 0)
        {
            Random random = new Random();
            int pick = random.nextInt(this.oreWeightList.size());
            return this.oreWeightList.get(pick);
        }
        return null;
    }

    public DepositStone pickStone()
    {
        if (this.stoneWeightList.size() > 0)
        {
            Random random = new Random();
            int pick = random.nextInt(this.stoneWeightList.size());
            return this.stoneWeightList.get(pick);
        }
        return null;
    }

    public void registerAsOreGenerator()
    {
        for (Biome biome : ForgeRegistries.BIOMES.getValues())
        {
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    Biome.createDecoratedFeature(new PlutonOreFeature(NoFeatureConfig::deserialize),
                            new NoFeatureConfig(), Placement.NOPE, new NoPlacementConfig()));
        }
        for (Biome biome : ForgeRegistries.BIOMES.getValues())
        {
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    Biome.createDecoratedFeature(new PlutonStoneFeature(NoFeatureConfig::deserialize),
                            new NoFeatureConfig(), Placement.NOPE, new NoPlacementConfig()));
        }
    }
}