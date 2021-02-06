package threads;

import java.util.List;

public class Simulation {
	//private List<Robot> robots;
	private ChargingBase base;
	
	public Simulation(List<Robot> robots, ChargingBase base) {
		//this.robots = robots;
		this.setBase(base);
		
		for(Robot r: robots) {
			r.setSim(this);
			new Thread(r).start(); //running the robot
		}
	}

	public ChargingBase getBase() {
		return base;
	}

	public void setBase(ChargingBase base) {
		this.base = base;
	}
	
	

}
