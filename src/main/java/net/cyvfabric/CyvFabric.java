package net.cyvfabric;

import net.cyvfabric.config.ColorTheme;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

//Main class of the mod
public class CyvFabric implements ModInitializer {
	public static final String MOD_ID = "cyvfabric";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static  CyvClientConfig config = new CyvClientConfig();
	public static DecimalFormat df = new DecimalFormat("#");
	public static ColorTheme theme = ColorTheme.CYVISPIRIA;

	@Override
	public void onInitialize() {
		LOGGER.info("CyvFabric initializing...");
	}

	/**Send a client-sided message to the player*/
	public static void sendChatMessage(Object text) {
		try {
			String chatColor2 = CyvClientConfig.getBoolean("whiteChat", false) ? CyvClientColorHelper.colors.get(12).chatColor
					: CyvClientColorHelper.color2.chatColor;
			MinecraftClient.getInstance().player.sendMessage(Text.of(
				CyvClientColorHelper.color1.chatColor + "<Cyv> " + chatColor2 + text.toString()), false);
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}

}