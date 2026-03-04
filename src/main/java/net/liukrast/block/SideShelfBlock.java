package net.liukrast.block;

import com.mojang.math.Axis;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlock;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.liukrast.TableClothPlacement;
import net.liukrast.registry.TradeworksBlockEntityTypes;
import net.liukrast.registry.TradeworksBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

import java.util.List;
import java.util.function.Predicate;

@NonnullDefault
public class SideShelfBlock extends TableClothBlock implements TableClothPlacement {
    private static final VoxelShape N = box(0,0,10, 16, 16, 16);

    private static final VoxelShape E = ShelfBlock.rotateY(N);
    private static final VoxelShape S = ShelfBlock.rotateY(E);
    private static final VoxelShape W = ShelfBlock.rotateY(S);

    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());

    public SideShelfBlock(Properties properties, String type) {
        super(properties, type);
        registerDefaultState(defaultBlockState().setValue(HAS_BE, false).setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    public static boolean isShelf(ItemStack stack) {
        return TradeworksBlocks.SIDE_SHELVES.stream().anyMatch(bb -> stack.is(bb.get().asItem()));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(HORIZONTAL_FACING)) {
            case NORTH -> N;
            case SOUTH -> S;
            case WEST -> W;
            case EAST -> E;
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.hasCollision ? pState.getShape(pLevel, pPos) : Shapes.empty();
    }

    @Override
    public BlockEntityType<? extends TableClothBlockEntity> getBlockEntityType() {
        return TradeworksBlockEntityTypes.SHELF.get();
    }

    @Override
    public int getPlacementId() {
        return placementHelperId;
    }


    private static class PlacementHelper implements IPlacementHelper {

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return SideShelfBlock::isShelf;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> s.getBlock() instanceof SideShelfBlock;
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
                                         BlockHitResult ray) {
            var facing = state.getValue(HORIZONTAL_FACING);
            List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), facing.getAxis(),
                    dir -> world.getBlockState(pos.relative(dir))
                            .canBeReplaced());
            if (directions.isEmpty())
                return PlacementOffset.fail();
            else
                return PlacementOffset.success(pos.relative(directions.getFirst()), s -> s.setValue(HORIZONTAL_FACING, facing));
        }
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return rotate(state, mirror.getRotation(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public Vec3 getPriceOffset(TableClothBlockEntity blockEntity, BlockState blockState) {
        return VecHelper.voxelSpace(0,2,9);
    }

    @Override
    public Axis getRotatingItemsAxis() {
        return Axis.ZP;
    }
}
