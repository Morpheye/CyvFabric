package mcpk.functions.walk;

import mcpk.Player;
import mcpk.functions.Function;
import mcpk.utils.Arguments;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionWalk45Air extends Function {
	
	@Override
	public String[] names() {
		return new String[] {"w45a", "wa45", "walk45air", "walkair45"};
	}

	@Override
	public void run(Player player, int duration, float facing, ArrayList<Character> modifiers, HashMap<String, Double> effects) throws InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) (facing + 45));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkNoModifiers(modifiers);
		checkEffects(effects, args, duration);
		
		args.replace("strafing", 1);
		args.replace("airborne", true);
		player.move(args);
		
	}

	
	
}
