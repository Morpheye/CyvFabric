package mcpk.functions.sprint;

import mcpk.Player;
import mcpk.functions.Function;
import mcpk.utils.Arguments;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionSprintAir extends Function {

	@Override
	public String[] names() {
		// TODO Auto-generated method stub
		return new String[] {"sprintair", "sa"};
	}

	@Override
	public void run(Player player, int duration, float facing, ArrayList<Character> modifiers, HashMap<String, Double> effects)
			throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", facing);
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("sprinting", true);
		args.replace("airborne", true);
		player.move(args);
		
	}

}
