package mcpk.functions;

import mcpk.Player;
import mcpk.utils.Arguments;
import mcpk.utils.ParserException;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Function {
	
	public Function() {
	}
	
	public abstract String[] names();
	
	public abstract void run(Player player, int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException;
	
	@SuppressWarnings("serial")
	public static class InvalidKeypressException extends ParserException {
		public InvalidKeypressException() {
			super("This function does not allow key modifiers.");
		}
	}
	
	@SuppressWarnings("serial")
	public static class DurationException extends ParserException {
		public DurationException() {
			super("Duration cannot be negative when key modifiers are present.");
		}
	}
	
	//effect checking
	protected static void checkModifiers(ArrayList<Character> modifiers, Arguments args, int duration) throws DurationException {
		if (!modifiers.isEmpty()) { //modifiers
			if (duration < 0) throw new DurationException();

			
			if (modifiers.contains('w')); //w + potential strafing, no change
			else if (modifiers.contains('s')) args.replace("forward", -1); //s + potential strafing
			else args.replace("forward", 0); //only strafing
			
			if (modifiers.contains('w') && modifiers.contains('s')) { //WS
				args.replace("forward", 0);
			}
			
			if (modifiers.contains('a') && modifiers.contains('d')) args.replace("strafing", 0); // WASD user is trolling
			else if (modifiers.contains('a')) args.replace("strafing", 1);
			else if (modifiers.contains('d')) args.replace("strafing", -1);
			
		} //end modifying
		
	}

	protected static void checkNoModifiers(ArrayList<Character> modifiers) throws InvalidKeypressException {
		if (!modifiers.isEmpty()) {
			throw new InvalidKeypressException();
		}
	}

	protected static void checkEffects(HashMap<String,Double> effects, Arguments args, int duration) {
		if (effects.containsKey("slip")) args.replace("slip", effects.get("slip"));
		if (effects.containsKey("blocking")) args.replace("blocking", effects.get("blocking"));
		
		if (effects.containsKey("swiftness")) args.replace("swiftness", effects.get("swiftness"));
		if (effects.containsKey("slowness")) args.replace("slowness", effects.get("slowness"));
		
	}
	
}
