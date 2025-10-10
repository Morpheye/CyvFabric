package mcpk.functions.nonmovement;

import mcpk.Parser;
import mcpk.Player;
import mcpk.functions.Function;
import mcpk.utils.ParserException;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class SpecialFunction extends Function {
	
	public abstract void specialRun(Player player, double args, Parser parser) throws ParserException;

	@Override //This method is not used
	public final void run(Player player, int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) 
			throws DurationException, InvalidKeypressException {
		return;
	}
	
}
