package mcpk;

import mcpk.utils.Arguments;
import mcpk.utils.MathHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Player {
	
	public static byte df = 16;
	
	//momentum calculation
	public int tick = 0;
	public double xOf = 0.0;
	public double zOf = 0.0;
	public double highestZ = 0, lowestZ = 0;
	public double highestX = 0, lowestX = 0;
	public double finalX, finalZ;
	public ArrayList<Double> xCoords = new ArrayList<Double>();
	public ArrayList<Double> zCoords = new ArrayList<Double>();
	
	//coords
	public double x = 0.0;
	public double z = 0.0;
	public double vx = 0.0;
	public double vz = 0.0;
	
	public double slip = 0.0;
	public double last_slip = 0.0;
	public double multiplier = 0.0;
	
	//general move function
	public void move(Arguments args) {
		//defining
		int duration = (int) args.get("duration");
		boolean airborne = (boolean) args.get("airborne");
		boolean sprinting = (boolean) args.get("sprinting");
		boolean sneaking =  (boolean) args.get("sneaking");
		boolean jumping = (boolean) args.get("jumping");
		float facing = (float) args.get("facing");
		
		//modifiers
		boolean blocking = ((double) args.get("blocking") == 1.0) ? true : false;
		boolean soulsand = ((double) args.get("soulsand") == 1.0) ? true : false;
		
		//potions
		int swiftness = (int) (double) args.get("swiftness");
		int slowness = (int) (double) args.get("slowness");
		
		//stuff starts here
		for (int i=1;i<=Math.abs(duration);i++) {
			//check previous tick
			if (jumping) {
				if (!xCoords.contains(this.xOf)) {
					xCoords.add(this.xOf);
					xCoords.add(this.xOf);
				}
				if (!zCoords.contains(this.zOf)) {
					zCoords.add(this.zOf);
					zCoords.add(this.zOf);
				}
			}
			
			//start of tick
			tick++;
			multiplier = 1;
			
			//reset
			float forward = (int) args.get("forward");
			float strafing = (int) args.get("strafing");
			float boost = 0.2F;
			
			//slipperiness
			if (airborne) slip = 1F;
			else slip = (float) ((double) args.get("slip"));
			if (last_slip == 0F) last_slip = slip;
			
			if (forward < 0) {
				//strafing *= -1F;
				boost *= -1F;
			}
			
			//move player
			this.z += this.vz;
			this.x += this.vx;
			this.zOf += this.vz;
			this.xOf += this.vx;
			
			//soulsand
			
			//drag
			this.vx *= 0.91F * last_slip;
			this.vz *= 0.91F * last_slip;
			
			//inertia threshold
			if (Math.abs(this.vz) < 0.003D) this.vz = 0D;
			if (Math.abs(this.vx) < 0.003D) this.vx = 0D;
			
			//movement multipliers
			float accel = 0;
			double drag = (float) (0.91F) * slip;
			if (airborne) {
				accel = 0.02F;
				if (sprinting) accel = (float) (accel + accel * 0.3);
			} else {
				accel = 0.1F;
				accel *= 0.16277136F / (drag * drag * drag);
				if (swiftness > 0) accel = (float) (accel * (1.0D + 0.2F * (float) (swiftness)));
				if (slowness > 0) accel = (float) (accel * Math.max(1.0D + -0.15F * (float) (slowness), 0));
				if (sprinting) accel = (float) (accel * (1.0D + 0.3F));
			}
			
			//sprintjump boost
			if (sprinting && jumping) {
				float angle = (float) (facing * 0.017453292F);
				
				this.vz += (boost * MathHelper.cos(angle));
				this.vx -= (boost * MathHelper.sin(angle));
			}
			
			if (blocking) {
				forward *= 0.2F;
				strafing *= 0.2F;
			}
			
			//sneaking
			if (sneaking) {
				forward = (float) (((float) forward) * 0.3D);
				strafing = (float) (((float) strafing) * 0.3D);
			}
			
			forward *= 0.98F;
			strafing *= 0.98F;
			
			float distance = (float) (strafing * strafing + forward * forward);
			
			if (distance >= 1.0E-4F) {
				distance = (float) Math.sqrt(distance);
				if (distance < 1.0F) distance = 1.0F;
				
				distance = accel / distance;
				strafing = strafing * distance;
				forward = forward * distance;
				
				float angle = (float) (facing * 3.14159265358979323846F / 180F);
				
				this.vx += (float) (strafing * MathHelper.cos(angle) - forward * MathHelper.sin(angle));
				this.vz += (float) (forward * MathHelper.cos(angle) + strafing * MathHelper.sin(angle));
			}
			
			last_slip = slip;
			
			if (!airborne) { //update momentum if airborne
				this.updateMM();
			}
			
			if (true) continue;
			

			
		}
		
	}

	void updateMM() {
		xCoords.add(xOf);
		zCoords.add(zOf);
	}
	
	public void updateBounds() {
		xCoords.sort((c1, c2) -> Double.compare(c1, c2));
		zCoords.sort((c1, c2) -> Double.compare(c1, c2));
		
		xCoords.remove(xCoords.size()-1);
		zCoords.remove(zCoords.size()-1);
		zCoords.remove(0);
		xCoords.remove(0);
		
		if (!xCoords.contains(finalX)) xCoords.add(finalX);
		if (!zCoords.contains(finalZ)) zCoords.add(finalZ);
		
		xCoords.sort((c1, c2) -> Double.compare(c1, c2));
		zCoords.sort((c1, c2) -> Double.compare(c1, c2));
		lowestZ = zCoords.get(0);
		highestZ = zCoords.get(zCoords.size()-1);
		lowestX = xCoords.get(0);
		highestX = xCoords.get(xCoords.size()-1);
		
	}
	
	//non-calculation methods
	public void print() {
		DecimalFormat formatting = new DecimalFormat("#");
		formatting.setMaximumFractionDigits(df);
		this.updateBounds();

		System.out.println("z: " + formatting.format(this.z));
		System.out.println("vz: " + formatting.format(this.vz));
		System.out.println("zmm: " + formatting.format(highestZ - lowestZ));
		

		System.out.println("x: " + formatting.format(this.x));
		System.out.println("vx: " + formatting.format(this.vx));
		System.out.println("xmm: " + formatting.format(highestX - lowestX));
		
		System.out.println("vector: " + formatting.format(Math.hypot(this.vz, this.vx)));
		System.out.println("angle: " + Math.atan2(-this.vx,this.vz) * 180d/Math.PI);
		



	}

}
