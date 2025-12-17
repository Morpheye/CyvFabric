package net.cyvfabric.util.parkour;

import net.cyvfabric.CyvFabric;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.ArrayList;
import java.util.List;

public class LandingBlock {

    public BlockPos pos;
    public AABB[] bb; //bounding boxes
    public LandingMode mode;
    public LandingAxis axis;
    public boolean isBox;
    public boolean neoAndNormal;

    public Double pb;
    public Double pbX;
    public Double pbZ;

    public Double lastOffsetX;
    public Double lastOffsetZ;
    public Double lastPb;

    public Double xMaxWall, xMinWall, zMaxWall, zMinWall;

    public double xMinCond, xMaxCond, zMinCond, zMaxCond; //detection boxes (for offsets)

    public LandingBlock(BlockPos pos, LandingMode mode, LandingAxis axis, boolean isBox) {
        this.pos = pos;
        this.mode = mode;
        this.axis = axis;
        this.isBox = isBox;

        calculateBounds(); //calculate the hitbox
        calculateWalls();

        //create detection box
        this.xMinCond = this.smallestX() - 1;
        this.xMaxCond = this.largestX() + 1;
        this.zMinCond = this.smallestZ() - 1;
        this.zMaxCond = this.largestZ() + 1;
    }

    public LandingBlock(AABB bounds) {
        this.pos = BlockPos.containing(bounds.minX, bounds.minY, bounds.minZ);
        this.mode = LandingMode.landing;
        this.axis = LandingAxis.both;
        this.isBox = false;

        this.bb = new AABB[] {bounds};

        this.xMinCond = this.smallestX() - 1;
        this.xMaxCond = this.largestX() + 1;
        this.zMinCond = this.smallestZ() - 1;
        this.zMaxCond = this.largestZ() + 1;
    }

    private void calculateBounds() {
        if (this.pos == null) return;
        Minecraft mc = Minecraft.getInstance();
        Level world = mc.level;

        BlockState blockState = world.getBlockState(pos);
        VoxelShape collisionBox = blockState.getCollisionShape(world, pos);
        Block block = blockState.getBlock();

        //THIS IS TEMPORARY. I will find a better solution in the future
        if (isBox && (block instanceof LadderBlock || block instanceof VineBlock)) {
            double playerX = Minecraft.getInstance().player.getBoundingBox().getXsize();
            double playerZ = Minecraft.getInstance().player.getBoundingBox().getZsize();
            AABB box = new AABB(playerX/2 + pos.getX(), pos.getY(), playerZ/2 + pos.getZ(),
                    1-playerX/2 + pos.getX(), 1 + pos.getY(), 1-playerZ/2 + pos.getZ());
            this.bb = new AABB[] {box};

            return;
        }

        AABB[] tempB = collisionBox.toAabbs().toArray(AABB[]::new);
        this.bb = new AABB[tempB.length];

        for (int i=0; i<tempB.length; i++) {
            bb[i] = new AABB(tempB[i].minX + pos.getX(), tempB[i].minY + pos.getY(), tempB[i].minZ + pos.getZ(),
                    tempB[i].maxX + pos.getX(), tempB[i].maxY + pos.getY(), tempB[i].maxZ + pos.getZ());
        }

    }

    public double smallestX() {
        Double v = null;
        for (AABB bb : this.bb) {
            if (v == null || v > bb.minX) v = bb.minX;
        }
        return v;
    }

    public double smallestY() {
        Double v = null;
        for (AABB bb : this.bb) {
            if (v == null || v > bb.minY) v = bb.minY;
        }
        return v;
    }

    public double smallestZ() {
        Double v = null;
        for (AABB bb : this.bb) {
            if (v == null || v > bb.minZ) v = bb.minZ;
        }
        return v;
    }

    public double largestX() {
        Double v = null;
        for (AABB bb : this.bb) {
            if (v == null || v < bb.maxX) v = bb.maxX;
        }
        return v;
    }

    public double largestY() {
        Double v = null;
        for (AABB bb : this.bb) {
            if (v == null || v < bb.maxY) v = bb.maxY;
        }
        return v;
    }

    public double largestZ() {
        Double v = null;
        for (AABB bb : this.bb) {
            if (v == null || v < bb.maxZ) v = bb.maxZ;
        }
        return v;
    }

    public void adjustCond(double x1, double x2, double z1, double z2) {
        if (x1 > x2) {
            this.xMaxCond = x1;
            this.xMinCond = x2;

        } else {
            this.xMaxCond = x2;
            this.xMinCond = x1;

        }

        if (z1 > z2) {
            this.zMaxCond = z1;
            this.zMinCond = z2;
        } else {
            this.zMaxCond = z2;
            this.zMinCond = z1;
        }

    }

    public void calculateWalls() {
        Level world = Minecraft.getInstance().level;
        AABB playerHitbox = Minecraft.getInstance().player.getBoundingBox();
        BlockPos tempPos = pos; //new variable, because this will be lowered by one if the mode is currently "enter"
        if (this.mode == LandingMode.enter) tempPos = tempPos.below();

        xMinWall = null; xMaxWall = null; zMinWall = null; zMaxWall = null;

        for (AABB box : bb) {
            ArrayList<AABB> wallBoxes = new ArrayList<AABB>();
            BlockPos currentWallPos = null; //current x/z position of checked wall
            double offset, currentWall; //temporary variables

            //z back
            currentWallPos = tempPos.north();
            for (double i = 0; i < playerHitbox.getYsize(); i++) {
                currentWallPos = currentWallPos.above();
                wallBoxes.addAll(world.getBlockState(currentWallPos).getCollisionShape(world, currentWallPos).toAabbs());
            }
            for (AABB wall : wallBoxes) {
                if (wall.getXsize() < box.getXsize()) continue; //skip if not wide enough
                currentWall = wall.maxZ + currentWallPos.getZ();
                offset = box.minZ - currentWall - playerHitbox.getZsize();

                if (offset < 0) {
                    if (zMinWall == null || currentWall > zMinWall) zMinWall = currentWall;
                }
            }

            //z front
            wallBoxes.clear();
            currentWallPos = tempPos.south();
            for (double i = 0; i < playerHitbox.getYsize(); i++) {
                currentWallPos = currentWallPos.above();
                wallBoxes.addAll(world.getBlockState(currentWallPos).getCollisionShape(world, currentWallPos).toAabbs());
            }
            for (AABB wall : wallBoxes) {
                if (wall.getXsize() < box.getXsize()) continue; //skip if not wide enough
                currentWall = wall.minZ + currentWallPos.getZ();
                offset = currentWall - box.maxZ - playerHitbox.getZsize();

                if (offset < 0) {
                    if (zMaxWall == null || currentWall > zMaxWall) zMaxWall = currentWall;
                }
            }

            //x right
            wallBoxes.clear();
            currentWallPos = tempPos.west();
            for (double i = 0; i < playerHitbox.getYsize(); i++) {
                currentWallPos = currentWallPos.above();
                wallBoxes.addAll(world.getBlockState(currentWallPos).getCollisionShape(world, currentWallPos).toAabbs());
            }
            for (AABB wall : wallBoxes) {
                if (wall.getZsize() < box.getZsize()) continue; //skip if not wide enough
                currentWall = wall.maxX + currentWallPos.getX();
                offset = box.minX - currentWall - playerHitbox.getXsize();

                if (offset < 0) {
                    if (xMinWall == null || currentWall > xMinWall) xMinWall = currentWall;
                }
            }

            //x left
            wallBoxes.clear();
            currentWallPos = tempPos.east();
            for (double i = 0; i < playerHitbox.getYsize(); i++) {
                currentWallPos = currentWallPos.above();
                wallBoxes.addAll(world.getBlockState(currentWallPos).getCollisionShape(world, currentWallPos).toAabbs());
            }
            for (AABB wall : wallBoxes) {
                if (wall.getZsize() < box.getZsize()) continue; //skip if not wide enough
                currentWall = wall.minX + currentWallPos.getX();
                offset = currentWall - box.maxX - playerHitbox.getXsize();

                if (offset < 0) {
                    if (xMaxWall == null || currentWall > xMaxWall) xMaxWall = currentWall;
                }
            }

        }
    }

}
