package com.sdbros.rpgcraft.init;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.world.structures.BrokenTowerPieces;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class ModWorld {

    public static class StructurePieceTypes {
        public static final IStructurePieceType BROKENTOWERPIECE = IStructurePieceType.register(BrokenTowerPieces.Tower::new, String.valueOf(RpgCraft.getId("broken_tower")));
    }
}
