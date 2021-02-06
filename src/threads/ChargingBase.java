package threads;

import java.util.ArrayList;
import java.util.List;

public class ChargingBase {
	private int numberOfStations;
	private List<ChargingStation> stations;
	
	public ChargingBase(int size) {
		this.setNumberOfStations(size);
		stations = new ArrayList<ChargingStation>(size + 2*(size-1));
		for(int i = 0; i<size-1; i++)stations.add(i, new ChargingStation(-1));
		for(int i = size-1; i<size*2 - 1; i++)stations.add(i, new ChargingStation(i-(size-1)));
		for(int i = size*2 - 1; i<3*size - 2; i++)stations.add(i, new ChargingStation(-1));
	}

	public int getNumberOfStations() {
		return numberOfStations;
	}

	public void setNumberOfStations(int numberOfStations) {
		this.numberOfStations = numberOfStations;
	}
	
	public List<ChargingStation> getStations() {
		return stations;
	}

	public void take(Robot robot, int firstStationIndex, int lastStationIndex) {
		for(int i = firstStationIndex; i<=lastStationIndex; i++) {
			stations.get(i).setR(robot);
		}
	}

	synchronized public void free(int firstStationIndex, int lastStationIndex) {
		for(int i = firstStationIndex; i<=lastStationIndex; i++) {
			stations.get(i).setR(null);
		}
		notifyAll();
	}

	public int getBiggestSpace() {
		int biggest = 0;
		int current = 0;
		for(ChargingStation s: stations) {
			if(s.getR()==null) {
				current++;
				if(current>biggest)biggest=current;
			}else {
				current = 0;
			}
		}
		return biggest;
	}
	
}
