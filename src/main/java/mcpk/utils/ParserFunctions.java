package mcpk.utils;

import mcpk.functions.Function;
import mcpk.functions.nonmovement.SpecialFunction;
import mcpk.functions.nonmovement.globals.FunctionSlip;
import mcpk.functions.nonmovement.globals.FunctionSlowness;
import mcpk.functions.nonmovement.globals.FunctionSwiftness;
import mcpk.functions.nonmovement.position.FunctionFacing;
import mcpk.functions.nonmovement.position.FunctionPosX;
import mcpk.functions.nonmovement.position.FunctionPosZ;
import mcpk.functions.nonmovement.velocity.FunctionVx;
import mcpk.functions.nonmovement.velocity.FunctionVz;
import mcpk.functions.sneak.*;
import mcpk.functions.sneaksprint.*;
import mcpk.functions.sprint.*;
import mcpk.functions.stop.FunctionStop;
import mcpk.functions.stop.FunctionStopAir;
import mcpk.functions.stop.FunctionStopJump;
import mcpk.functions.walk.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ParserFunctions {
	public static ArrayList<Function> functionInit() {
		ArrayList<Function> functions = new ArrayList<Function>();
		functions.addAll(Arrays.asList(new Function[] {
				new FunctionWalk(), new FunctionWalk45(), new FunctionWalkAir(),
				new FunctionWalk45Air(), new FunctionWalkJump(), new FunctionWalkJump45(),
				new FunctionSprint(), new FunctionSprint45(), new FunctionSprintAir(), new FunctionSprint45Air(),
				new FunctionLSprintJump(), new FunctionRSprintJump(), new FunctionLSprintJump45(), new FunctionRSprintJump45(),
				new FunctionSprintJump(), new FunctionSprintJump45(),
				new FunctionSneak(), new FunctionSneak45(), new FunctionSneakAir(),
				new FunctionSneak45Air(), new FunctionSneakJump(), new FunctionSneakJump45(),
				new FunctionSneakSprint(), new FunctionSneakSprint45(), new FunctionSneakSprintAir(), new FunctionSneakSprint45Air(),
				new FunctionLSneakSprintJump(), new FunctionRSneakSprintJump(), new FunctionLSneakSprintJump45(), new FunctionRSneakSprintJump45(),
				new FunctionSneakSprintJump(), new FunctionSneakSprintJump45(),
				new FunctionStop(), new FunctionStopAir(), new FunctionStopJump()
		}));
		return functions;
	}
	
	public static ArrayList<SpecialFunction> specialFunctionInit() {
		ArrayList<SpecialFunction> functions = new ArrayList<SpecialFunction>();
		functions.addAll(Arrays.asList(new SpecialFunction[] {
				new FunctionVx(), new FunctionVz(), new FunctionFacing(), new FunctionPosX(), new FunctionPosZ(),
				new FunctionSwiftness(), new FunctionSlowness(), new FunctionSlip()
		}));
		return functions;
	}
}
