package net.liukrast.block;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.liukrast.registry.TradeworksBlockEntityTypes;
import net.liukrast.registry.TradeworksBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

import java.util.List;
import java.util.function.Predicate;

@NonnullDefault
public class ShelfBlock extends TableClothBlockImpl {
    private static final VoxelShape X_TOP = Shapes.or(
            Block.box(14, 0, 1, 16, 14, 15),
            Block.box(0, 0, 1, 2, 14, 15),
            Block.box(2, 1, 1, 14, 3, 15),
            Block.box(0, 14, 0, 16, 16, 16)
    );
    private static final VoxelShape X_BOTTOM = Shapes.or(
            Block.box(14, 0, 1, 16, 16, 15),
            Block.box(0, 0, 1, 2, 16, 15),
            Block.box(2, 1, 1, 14, 3, 15)
    );

    private static VoxelShape rotateY(VoxelShape shape) {

        VoxelShape[] buffer = {shape, Shapes.empty()};
        int numRotations = 1 % 4;

        for (int i = 0; i < numRotations; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) ->
                    buffer[1] = Shapes.or(buffer[1], Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX))
            );
            buffer[0] = buffer[1];
        }
        return buffer[0];
    }

    private static final VoxelShape Z_TOP = rotateY(X_TOP);
    private static final VoxelShape Z_BOTTOM = rotateY(X_BOTTOM);

    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty TOP = BooleanProperty.create("top");

    private static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());

    public ShelfBlock(Properties pProperties, String type) {
        super(pProperties, type);
        registerDefaultState(defaultBlockState().setValue(HAS_BE, false).setValue(HORIZONTAL_AXIS, Direction.Axis.X));
    }

    public static boolean isShelf(ItemStack stack) {
        if(TradeworksBlocks.SHELVES.stream().anyMatch(bb -> stack.is(bb.get().asItem()))) return true;
        return TradeworksBlocks.METAL_SHELVES.stream().anyMatch(bb -> stack.is(bb.get().asItem()));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (hitResult.getDirection() == Direction.DOWN)
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (level.isClientSide)
            return ItemInteractionResult.SUCCESS;

        ItemStack heldItem = player.getItemInHand(hand);
        boolean shiftKeyDown = player.isShiftKeyDown();
        if (!player.mayBuild())
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        IPlacementHelper placementHelper = PlacementHelpers.get(placementHelperId);
        if (placementHelper.matchesItem(heldItem)) {
            if (shiftKeyDown)
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            placementHelper.getOffset(player, level, state, pos, hitResult)
                    .placeInWorld(level, (BlockItem) heldItem.getItem(), player, hand, hitResult);
            return ItemInteractionResult.SUCCESS;
        }

        if ((shiftKeyDown || heldItem.isEmpty()) && !state.getValue(HAS_BE))
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        if (!level.isClientSide() && !state.getValue(HAS_BE))
            level.setBlockAndUpdate(pos, state.cycle(HAS_BE));

        return onBlockEntityUseItemOn(level, pos, dcbe -> dcbe.use(player, hitResult));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();

        var state = level.getBlockState(pos.above());
        var axis = context.getHorizontalDirection().getOpposite().getAxis();

        boolean isTop = !state.is(this) || !state.getValue(HORIZONTAL_AXIS).equals(axis);
        return defaultBlockState().setValue(HORIZONTAL_AXIS, axis).setValue(TOP, isTop);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HORIZONTAL_AXIS, TOP);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        boolean axis = state.getValue(HORIZONTAL_AXIS) == Direction.Axis.Z;
        boolean top = state.getValue(TOP);
        return axis && top ? X_TOP : axis ? X_BOTTOM : top ? Z_TOP : Z_BOTTOM;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getShape(level, pos);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext pContext) {
        return this.hasCollision ? state.getShape(level, pos) : Shapes.empty();
    }

    @Override
    public BlockEntityType<? extends TableClothBlockEntity> getBlockEntityType() {
        return TradeworksBlockEntityTypes.SHELF.get();
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if(direction.getAxis().isVertical()) {
            var state1 = level.getBlockState(pos.above());
            boolean isTop = !state1.is(this) || state1.getValue(HORIZONTAL_AXIS) != state.getValue(HORIZONTAL_AXIS);
            return state.setValue(TOP, isTop);
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    private static class PlacementHelper implements IPlacementHelper {

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return ShelfBlock::isShelf;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> s.getBlock() instanceof ShelfBlock;
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
                                         BlockHitResult ray) {
            List<Direction> directions = IPlacementHelper.orderedByDistanceOnlyAxis(pos, ray.getLocation(), Direction.Axis.Y,
                    dir -> world.getBlockState(pos.relative(dir))
                            .canBeReplaced());
            var axis = state.getValue(HORIZONTAL_AXIS);

            if (directions.isEmpty())
                return PlacementOffset.fail();
            else {
                var state1 = world.getBlockState(pos.above());
                boolean isTop = !state1.is(state.getBlock()) || state1.getValue(HORIZONTAL_AXIS) != axis;
                return PlacementOffset.success(pos.relative(directions.getFirst()), s -> s.setValue(HORIZONTAL_AXIS, axis).setValue(TOP, isTop));
            }
        }
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(HORIZONTAL_AXIS)) {
                case X -> state.setValue(HORIZONTAL_AXIS, Direction.Axis.Z);
                case Z -> state.setValue(HORIZONTAL_AXIS, Direction.Axis.X);
                default -> state;
            };
            default -> state;
        };
    }
}
