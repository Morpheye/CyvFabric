package mcpk.functions.stop;

import mcpk.Player;
import mcpk.functions.Function;
import mcpk.utils.Arguments;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionStopJump extends Function {

	@Override
	public String[] names() {
		return new String[] {"stopjump", "stj", "j", "jump"};
	}

	@Override
	public void run(Player player, int duration, float facing, ArrayList<Character> modifiers, HashMap<String, Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) facing);
		
		checkEffects(effects, args, duration);
		checkNoModifiers(modifiers);
		
		args.replace("jumping", true);
		player.move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("airborne", true);
		player.move(args);
		
	}

	
	
}
