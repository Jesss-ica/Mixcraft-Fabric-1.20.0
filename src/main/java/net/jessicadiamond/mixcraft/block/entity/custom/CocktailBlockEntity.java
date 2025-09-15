package net.jessicadiamond.mixcraft.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.jessicadiamond.mixcraft.MixCraft;
import net.jessicadiamond.mixcraft.block.entity.ImplementedInventory;
import net.jessicadiamond.mixcraft.block.entity.ModBlockEntities;
import net.jessicadiamond.mixcraft.components.AlcoholComponent;
import net.jessicadiamond.mixcraft.item.ModItems;
import net.jessicadiamond.mixcraft.screen.custom.AlcoholDisplayScreenHandler;
import net.jessicadiamond.mixcraft.screen.custom.CocktailBlockScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CocktailBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    public int MAX_TANK_SIZE = 250;
    public int CURRENT_FILL = 0;

    public ArrayList<TankComponent> tankEntries;


    public CocktailBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COCKTAIL_BLOCK_BE, pos, state);
        this.tankEntries =  new ArrayList<>();
    }

    public class TankComponent{
        public Item alcohol;
        public int amount;

        public void setAlcoholType(Item itm){
            this.alcohol = itm;
        }

        public void setAmount(int amount){
            this.amount = amount;
        }
    }

    public void addToTank(Item item, int amount){
        this.tankEntries.add(getTankEntry(item,amount));
        MixCraft.LOGGER.info("Added to shaker" + tankEntries.getLast().alcohol.toString());
    }

    public TankComponent getTankEntry(Item itm, int amount){
        TankComponent tankEntry = new TankComponent();
        tankEntry.setAlcoholType(itm);
        tankEntry.setAmount(amount);
        return tankEntry;
    }


    public void increaseCurrentFill(int amount){ this.CURRENT_FILL = this.CURRENT_FILL + amount;}

    public int getCurrentFill(){return this.CURRENT_FILL;}

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.mixcraft.cocktail_block");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CocktailBlockScreenHandler(syncId, playerInventory, this.pos);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        super.readNbt(nbt, registryLookup);
    }



    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
