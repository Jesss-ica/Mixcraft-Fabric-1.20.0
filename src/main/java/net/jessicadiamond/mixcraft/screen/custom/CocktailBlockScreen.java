package net.jessicadiamond.mixcraft.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jessicadiamond.mixcraft.MixCraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CocktailBlockScreen extends HandledScreen<CocktailBlockScreenHandler> {

    private static final Identifier GUI_TEXTURE =
            Identifier.of(MixCraft.MOD_ID, "textures/gui/cocktail_block/cocktail_block_gui.png");
    private static final Identifier COCKTAIL_TANK =
            Identifier.of(MixCraft.MOD_ID, "textures/gui/cocktail_block/cocktail_block_tank.png");

    private boolean hasRendered;
    //private CocktailBlockScreenHandler handler;

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
            addPourButtons();
        }

        renderCocktailTank(context, x, y);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private void renderCocktailTank(DrawContext context, int x, int y) {
            context.drawTexture(COCKTAIL_TANK, x + 80, y + 14, 0, 0,
                    33, handler.getScaledTankFill(), 33, 56);
    }

    protected void addPourButtons(){
        int initialButtonPosX = x + 41;
        int initialButtonPosY = y + 13;
        for(int i = 0; i < 3; i++){
            int finalI =  i + 1;
            this.addDrawableChild(ButtonWidget.builder(Text.literal(25 * finalI +"ml"), button -> handler.addToTank(25 * finalI)).dimensions(initialButtonPosX, initialButtonPosY + (i * 20), 36, 18).build());
        }
        hasRendered = true;
    }


}
