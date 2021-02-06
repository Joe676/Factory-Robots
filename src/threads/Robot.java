package threads;

import gui.robotOverview;

public class Robot implements Runnable {
	
	static private int maxSize;
	static final int RUNNING = 0;
	static final int WAITING = 1;
	static final int CHARGING= 2;
	
	private String name;
	private int size;//min - 1, max - numberOfStations
	private int state;// 0 - running, 1 - waiting, 2 - charging
	private int time;
	private robotOverview overview;
	
	private Simulation sim;
	//private List<ChargingStation> stations;
	private int firstStationIndex;
	private int lastStationIndex;
	
	public Robot(String name, int maxSize) {
		Robot.setMaxSize(maxSize);
		this.name = name;
		this.size = (int)(Math.random() * maxSize + 1);
		this.state = RUNNING;
		this.time = (int)(Math.random() * 50 + 11);
	}

	@Override
	public void run() {
		while(true) {
			if(state == RUNNING) {
				try {
					Thread.sleep((time+(int)(Math.random()*6))*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.setState(WAITING);
			}
			if(state == WAITING) {
				findChargingStation();
				this.setState(CHARGING);
			}
			if(state == CHARGING) {
				try {
					Thread.sleep(time*100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Robot "+getName()+" has just finished charging");
				sim.getBase().free(firstStationIndex, lastStationIndex);
				this.setState(RUNNING);
			}
		}
		

	}

	private void findChargingStation() {
		ChargingBase base = sim.getBase();
		boolean found = false;
		
		ChargingStation s;
		while(true) {
			synchronized(base.getStations()) {
				for(int i = 0; i<base.getStations().size()-this.size; i++) {
					firstStationIndex = i;
					lastStationIndex = i+this.size-1;
					for(int j = 0; j<this.size; j++) {
						s = base.getStations().get(i+j);
						if(s.isEmpty())continue;
						else if(s.getR()!=null) {
							i+=j;
							found = false;
							break;
						}
						else {
							found = true;
						}
					}
					if(found)break;
				}
				if(found) {
					base.take(this, firstStationIndex, lastStationIndex);
					System.out.println("Robot "+getName()+" found a charging spot!");
					return;
					
				}		
			}
			try {
				synchronized(base) {
					do{
						System.out.println("Robot "+getName()+" is waiting - not enough space");
						base.wait();
					}while(base.getBiggestSpace()<this.size);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Robot "+getName()+" is no longer waiting");
		}		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getState() {
		return state;
	}
	
	public String getStateString() {
		switch(state) {
			case 0:
				return "running";
			case 1:
				return "waiting";
			case 2:
				return "charging";
			default:
				return null;
		}
	}

	public void setState(int state) {
		this.state = state;
		overview.updateState(this.getStateString());
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public static int getMaxSize() {
		return maxSize;
	}

	public static void setMaxSize(int maxSize) {
		Robot.maxSize = maxSize;
	}

	public robotOverview getOverview() {
		return overview;
	}

	public void setOverview(robotOverview overview) {
		this.overview = overview;
	}

	public Simulation getSim() {
		return sim;
	}

	public void setSim(Simulation sim) {
		this.sim = sim;
	}

}
