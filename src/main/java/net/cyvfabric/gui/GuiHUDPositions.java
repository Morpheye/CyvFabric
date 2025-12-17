package net.cyvfabric.gui;

import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.hud.HUDManager;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.IRenderer;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.cyvfabric.util.CyvGui;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.MouseButtonEvent;
import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.Window;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

public class GuiHUDPositions extends CyvGui {
    protected final HashMap<DraggableHUDElement, ScreenPosition> renderers = new HashMap<DraggableHUDElement, ScreenPosition>();
    protected Optional<DraggableHUDElement> selectedRenderer = Optional.empty();
    protected double prevX;
    protected double prevY;
    protected final boolean fromLabels;

    public GuiHUDPositions(boolean fromLabels) {
        super("HUD Position");
        Collection<DraggableHUDElement> registeredRenderers = HUDManager.registeredRenderers;
        this.fromLabels = fromLabels;
        //Keyboard.enableRepeatEvents(true);

        for (DraggableHUDElement renderer : registeredRenderers) {
            if (!renderer.isEnabled) continue;

            ScreenPosition pos = renderer.load();
            if (pos == null) {
                pos = renderer.getDefaultPosition();
            }

            adjustBounds(renderer, pos);
            this.renderers.put(renderer, pos);
        }

    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float partialTicks) {
        this.renderTransparentBackground(context);

        GuiUtils.drawBorder(context, 0, 0, this.width, this.height, ((Long) CyvClientColorHelper.color1.drawColor).intValue()); //GUI Border

        for (DraggableHUDElement renderer : renderers.keySet()) {
            ScreenPosition pos = renderers.get(renderer);
            if (!renderer.isDraggable) pos = renderer.getDefaultPosition();

            renderer.renderDummy(context, pos);

            int color = ((Long) CyvClientColorHelper.color1.drawColor).intValue();
            if (!renderer.isVisible) color = 0xFFAAAAAA;

            GuiUtils.drawBorder(context, pos.getAbsoluteX(), pos.getAbsoluteY(),
                    renderer.getWidth(), renderer.getHeight(), color);
        }

    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        if (input.codepoint() == GLFW.GLFW_KEY_ESCAPE) {
            renderers.entrySet().forEach((entry) -> {
                entry.getKey().save(entry.getValue());
            });

            if (fromLabels) Minecraft.getInstance().setScreen(new GuiMPK());
            else this.onClose();
            return true;
        } else if (input.codepoint() == GLFW.GLFW_KEY_UP) {
            if (selectedRenderer.isPresent()) {
                if (selectedRenderer.get().isDraggable) {
                    moveSelectedRenderBy(0,-1);
                    return true;
                }
            }
        } else if (input.codepoint() == GLFW.GLFW_KEY_LEFT) {
            if (selectedRenderer.isPresent()) {
                if (selectedRenderer.get().isDraggable) {
                    moveSelectedRenderBy(-1,0);
                    return true;
                }
            }
        } else if (input.codepoint() == GLFW.GLFW_KEY_DOWN) {
            if (selectedRenderer.isPresent()) {
                if (selectedRenderer.get().isDraggable) {
                    moveSelectedRenderBy(0,1);
                    return true;
                }
            }
        } else if (input.codepoint() == GLFW.GLFW_KEY_RIGHT) {
            if (selectedRenderer.isPresent()) {
                if (selectedRenderer.get().isDraggable) {
                    moveSelectedRenderBy(1,0);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent click, double offsetX, double offsetY) {
        if (click.button() == 0) { //left-clicked
            if (selectedRenderer.isPresent()) {
                if (selectedRenderer.get().isDraggable) {
                    prevX += offsetX;
                    prevY += offsetY;
                    moveSelectedRenderBy((int) prevX, (int) prevY);
                    prevX -= (int) prevX;
                    prevY -= (int) prevY;
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        this.prevX = 0;
        this.prevY = 0;

        loadMouseOver((int) click.x(), (int) click.y());

        if (click.button() == 1) { //right-clicked
            if (!this.selectedRenderer.isPresent()) return false;
            DraggableHUDElement modRender = this.selectedRenderer.get();
            modRender.isVisible = !modRender.isVisible;

            return true;
        }

        return false;
    }

    private void loadMouseOver(int x, int y) {
        this.selectedRenderer = renderers.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
    }

    private void moveSelectedRenderBy(int offsetX, int offsetY) {
        IRenderer renderer = selectedRenderer.get();
        ScreenPosition pos = renderers.get(renderer);

        pos.setAbsolute(pos.getAbsoluteX() + offsetX, pos.getAbsoluteY() + offsetY);
        adjustBounds(renderer, pos);

    }

    @Override
    public void removed() {
        for (IRenderer renderer : renderers.keySet()) {
            renderer.save(renderers.get(renderer));
        }
    }

    private void adjustBounds(IRenderer renderer, ScreenPosition pos) {
        Window res = Minecraft.getInstance().getWindow();

        int screenWidth = res.getGuiScaledWidth();
        int screenHeight = res.getGuiScaledHeight();

        int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
        int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));

        pos.setAbsolute(absoluteX, absoluteY);
    }

    private class MouseOverFinder implements Predicate<IRenderer> {

        private int mouseX, mouseY;

        public MouseOverFinder(int x, int y) {
            this.mouseX = x; this.mouseY = y;
        }

        @Override
        public boolean test(IRenderer renderer) {
            ScreenPosition pos = renderers.get(renderer);
            int absoluteX = pos.getAbsoluteX();
            int absoluteY = pos.getAbsoluteY();

            if (mouseX >= absoluteX && mouseX <= absoluteX + renderer.getWidth()) {
                if (mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight()) {
                    return true;
                }
            }

            return false;

        }
    }
}
