package mcpk.functions.stop;

import mcpk.Player;
import mcpk.functions.Function;
import mcpk.utils.Arguments;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionStop extends Function {

	@Override
	public String[] names() {
		return new String[] {"stop", "st"};
	}

	@Override
	public void run(Player player, int duration, float facing, ArrayList<Character> modifiers, HashMap<String, Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) facing);
		
		checkEffects(effects, args, duration);
		checkNoModifiers(modifiers);
		
		player.move(args);
		
	}

	
	
}
