package net.jessicadiamond.mixcraft.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jessicadiamond.mixcraft.MixCraft;
import net.jessicadiamond.mixcraft.components.ModComponents;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


@Environment(EnvType.CLIENT)
public class CocktailBlockScreen extends HandledScreen<CocktailBlockScreenHandler> {

    private static final Identifier GUI_TEXTURE =
            Identifier.of(MixCraft.MOD_ID, "textures/gui/cocktail_block/cocktail_block_gui.png");
    private static final Identifier COCKTAIL_TANK =
            Identifier.of(MixCraft.MOD_ID, "textures/gui/cocktail_block/cocktail_block_tank.png");

    private boolean hasRendered;

    public CocktailBlockScreen(CocktailBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        if(!hasRendered){
            renderButtons();
        }

        renderCocktailTank(context, x, y);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private void renderCocktailTank(DrawContext context, int x, int y) {
        int tankX = x + 80;
        int tankY = y + 14;
        int nextTankPos = 0;
        for(int i = 0; i < handler.getTankSize(); i++){
            int scaledTankFill = handler.getScaledTypeFill(i);
            float[] fillColor = handler.getTankEntry(i).color.getRGBColorComponents(null);
            context.setShaderColor(fillColor[0],fillColor[1],fillColor[2], 1.0F);
            context.drawTexture(COCKTAIL_TANK, tankX, tankY + nextTankPos , 0, 0,
                    33, scaledTankFill, 33, 56);
            nextTankPos = nextTankPos + scaledTankFill;
        }

    }

    private int getFillAmount(int i){
        return handler.blockEntity.tankEntries.get(i).amount;
    }

    private void renderButtons(){
        addPourButtons();
        addClearButton();
        addShakeButton();
        hasRendered = true;
    }

    protected void addClearButton(){
        this.addDrawableChild(ButtonWidget.builder(Text.literal("X"),
                        button -> this.sendButtonPressPacket(5))
                .dimensions(x + 116, y + 53, 18, 18).build());
    }

    protected void addShakeButton(){
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Shake"),
                button -> this.sendButtonPressPacket(4))
                .dimensions(x + 116, y + 33, 18, 18).build());
    }


    protected void addPourButtons(){
        int initialButtonPosX = x + 41;
        int initialButtonPosY = y + 13;
        for(int i = 0; i < 3; i++){
            int finalI =  i + 1;
            this.addDrawableChild(ButtonWidget.builder(Text.literal(25 * finalI +"ml"),
                    button -> this.sendButtonPressPacket(finalI))
                    .dimensions(initialButtonPosX, initialButtonPosY + (i * 20), 36, 18).build());
        }
    }

    private void sendButtonPressPacket(int id) {
        this.client.interactionManager.clickButton(this.handler.syncId, id);
    }


}
