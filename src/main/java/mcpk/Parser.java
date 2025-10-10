package mcpk;

import mcpk.functions.Function;
import mcpk.functions.nonmovement.SpecialFunction;
import mcpk.utils.ParserException;
import mcpk.utils.ParserFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class Parser {
	
	//globals
	public float default_facing = 0;
	public HashMap<String,Double> default_effects = new HashMap<String,Double>();
	
	@SuppressWarnings("unchecked")
	public void parse(Player player, String text) throws Exception {
		//parser variables
		int state = 0;
		String current_function = "";
		String current_argument = "";
		String current_argument_value = "";
		ArrayList<Character> modifiers = new ArrayList<Character>();
		HashMap<String,Double> effects = new HashMap<String,Double>();
		int argument_num = 1;
		double duration = 1;
		float facing = default_facing;
		String variable_name = "";
		float variable_value = 0;
		Hashtable<String,Float> variables = new Hashtable<String,Float>();
		
		//start zOF
		player.xCoords.add(player.xOf); player.xCoords.add(player.xOf);
		player.zCoords.add(player.zOf); player.zCoords.add(player.zOf);
		
		//start scanning
		for (int i = 0; i < text.length(); i++) {
			if (state == 0) { //searching for function
				if (text.charAt(i) == ' ') { //space, skip
					continue;
					
				} else if (text.charAt(i) == '|') { //reset
					player.x = 0;
					player.z = 0;
					
				} else if (text.charAt(i) == 'b') { //distance
					if (player.z < 0) {
						player.z = player.z - 0.6;
					} else {
						player.z = player.z + 0.6;
					}
					
					if (player.x < 0) {
						player.x = player.x - 0.6;
					} else {
						player.x = player.x + 0.6;
					}
					
				} else { //function found
					current_function = current_function + text.charAt(i);
					state = 1;
					
				}
				
			} else if (state == 1) { //currently reading function, looking for opening parenthesis
				if (text.charAt(i) == '(') { //start arguments
					state = 2;
					
				} else if (text.charAt(i) == ' ') { //execute and end function
					//run function
					run_function(player, current_function, duration, facing, modifiers, effects);
					modifiers = new ArrayList<Character>();
					effects = (HashMap<String, Double>) default_effects.clone();
					current_argument = "";
					current_function = "";
					argument_num = 1;
					duration = 1;
					facing = default_facing;
					state = 0;
					
				} else if (text.charAt(i) == '.') { //special modifiers
					state = 3;
					
				} else {
					current_function = current_function + text.charAt(i);
					
				}
				
			} else if (state == 2) { //currently reading function arguments

				if (text.charAt(i) == '(') { //SYNTAX ERROR
					throw new ParserException("Error: Unexpected (");
				}

				if (current_function.equals("var") || current_function.equals("variable")) { //special case, defining variable
					if (text.charAt(i) == ' ') {
						throw new ParserException("Error: Space in variable name");
						
					} else if (text.charAt(i) == ',') { //next argument
						if (argument_num == 1) { //variable name
							variable_name = current_argument;
						} else if (argument_num == 2) { //variable value
							variable_value = Float.parseFloat(current_argument);
						}
						current_argument = "";
						argument_num++;
						
					} else if (text.charAt(i) == ')') { //execute and end function
						if (text.charAt(i-1) != ',' && text.charAt(i-1) != '(') { //if prev is not ( or , 
							if (argument_num == 1) { //variable name
								variable_name = current_argument;
							} else if (argument_num == 2) { //variable value
								variable_value = Float.parseFloat(current_argument);
							}
						}
						
						try { //make sure variable is not a float
							float variable = Float.parseFloat(variable_name);
							throw new ParserException("Error: invalid variable name");
						} catch (NumberFormatException e) {
							
						}
						
						//run function
						if (variables.containsKey(variable_name)) { //check if value already exists
							variables.replace(variable_name, variable_value);
						} else {
							variables.put(variable_name, variable_value);
						}
						
						current_argument = "";
						current_function = "";
						argument_num = 1;
						state = 0;
						
					} else { //count arguments
						current_argument = current_argument + text.charAt(i);
						
					}		
					continue;
				}
				
				//otherwise proceed normally
				
				if (text.charAt(i) == ' ') {
					continue;
					
				} else if (text.charAt(i) == ',') { //next argument
					if (argument_num == 1) { //set duration
						if (variables.containsKey(current_argument)) {
							duration = variables.get(current_argument);
						} else {
							duration = Double.parseDouble(current_argument);
						}
					} else if (argument_num == 2) { //set facing
						if (variables.containsKey(current_argument)) {
						facing = variables.get(current_argument);
						} else {
						facing = Float.parseFloat(current_argument);
						}
					}
					current_argument = "";
					argument_num++;
					
				} else if (text.charAt(i) == ')') { //execute and end function
					if (text.charAt(i-1) != ',' && text.charAt(i-1) != '(') { //if prev is not ( or , 
						if (argument_num == 1) { //set duration
							if (variables.containsKey(current_argument)) {
								duration = Math.round(variables.get(current_argument));
							} else {
								duration = Double.parseDouble(current_argument);
							}
						} else if (argument_num == 2) { //set facing
							if (variables.containsKey(current_argument)) {
								facing = variables.get(current_argument);
							} else {
								facing = Float.parseFloat(current_argument);
							}
						}
					}
					
					//run function
					run_function(player, current_function, duration, facing, modifiers, effects);
					modifiers = new ArrayList<Character>();
					effects = (HashMap<String, Double>) default_effects.clone();
					current_argument = "";
					current_function = "";
					argument_num = 1;
					duration = 1;
					facing = default_facing;
					state = 0;

				} else if (text.charAt(i) == '=') {
					state = 4;				
					
				} else { //count arguments
					current_argument = current_argument + text.charAt(i);
					
				}
				
			} else if (state == 3) { //adding key modifiers
				if (text.charAt(i) == '(') { //start arguments
					state = 2;
					
				} else if (text.charAt(i) == ' ') { //execute and end function
					//run function
					run_function(player, current_function, duration, facing, modifiers, effects);
					modifiers = new ArrayList<Character>();
					effects = (HashMap<String, Double>) default_effects.clone();
					current_argument = "";
					current_function = "";
					argument_num = 1;
					duration = 1;
					facing = default_facing;
					state = 0;
					
				} else if (text.charAt(i) == '.') { //SYNTAX ERROR
					throw new ParserException("Error: Unexpected .");
					
				} else { //add the modifier
					char x = text.charAt(i);
					if (x == 'a' || x == 'd' || x == 'w' || x == 's') {
					modifiers.add(text.charAt(i));					
					} else { //modifier doesn't exist
						throw new ParserException("Error: Invalid modifier.");
					}
				}
				
				
				
			} else if (state == 4) { //checking for extra modifiers
				if (text.charAt(i) == ')') { //execute and end function
					if (text.charAt(i-1) != ',' && text.charAt(i-1) != '(') { //if prev is not ( or , 
							if (variables.containsKey(current_argument_value)) { //put argument
								effects.put(current_argument.toLowerCase(), (double) variables.get(current_argument_value));
							} else {
								try { //check if the thing is a double
									effects.put(current_argument.toLowerCase(), Double.parseDouble(current_argument_value));	
								} catch (Exception e) { //if it is not a double it is a boolean
									boolean bool = Boolean.parseBoolean(current_argument_value.toLowerCase());
									if (bool) effects.put(current_argument.toLowerCase(), 1.0);
									else effects.put(current_argument.toLowerCase(), 0.0);
								}


						}
						}
					
					//run function
					run_function(player, current_function, duration, facing, modifiers, effects);
					modifiers = new ArrayList<Character>();
					effects = (HashMap<String, Double>) default_effects.clone();
					current_argument = "";
					current_argument_value = "";
					current_function = "";
					argument_num = 1;
					duration = 1;
					facing = default_facing;
					state = 0;

				} else if (text.charAt(i) == ',') {
					if (text.charAt(i-1) != ',' && text.charAt(i-1) != '(') { //if prev is not ( or , 
						if (variables.containsKey(current_argument_value)) {
							effects.put(current_argument.toLowerCase(), (double) variables.get(current_argument_value));
						} else {
							try { //check if the thing is a double
								effects.put(current_argument.toLowerCase(), Double.parseDouble(current_argument_value));	
							} catch (Exception e) { //if it is not a double it is a boolean
								boolean bool = Boolean.parseBoolean(current_argument_value.toLowerCase());
								if (bool) effects.put(current_argument.toLowerCase(), 1.0);
								else effects.put(current_argument.toLowerCase(), 0.0);
							}

						}
						
						current_argument = "";
						current_argument_value = "";
						argument_num++;
						state = 2;
						
					}
					
				} else { //count arguments
					current_argument_value = current_argument_value + text.charAt(i);
					
				}
			}
			
		} //end scanning

		if (state == 1 || state == 3) {
			run_function(player, current_function, duration, facing, modifiers, effects);
			modifiers = new ArrayList<Character>();
			effects = (HashMap<String, Double>) default_effects.clone();
			current_argument = "";
			current_function = "";
			argument_num = 1;
			duration = 1;
			facing = default_facing;
			state = 0;
		}
		
		player.finalX = player.xOf;
		player.finalZ = player.zOf;
		
		return;
	}
	
	//identify and run command
	void run_function(Player player, String function, double arg1, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws Exception {
		for (Function f : functions) { //use arg1 as the duration
			for (String name : f.names()) {
				if (name.equals(function.toLowerCase())) {
					if (arg1 != (int) arg1) throw new ParserException("Duration must be an integer.");
					f.run(player, (int) arg1, facing, modifiers, effects);
					return;
				}
		}}
		
		for (SpecialFunction f : specialFunctions) {
			for (String name : f.names()) {
				if (name.equals(function.toLowerCase())) {
					f.specialRun(player, arg1, this);
					return;
				}
		}}
		
		
		throw new ParserException("Unrecognized function \"" + function + "\"");
	}
	
	//Functions
	public ArrayList<Function> functions = ParserFunctions.functionInit();
	public ArrayList<SpecialFunction> specialFunctions = ParserFunctions.specialFunctionInit();
	

	
}