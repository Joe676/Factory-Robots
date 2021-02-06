package threads;

import javax.swing.JLabel;

public class ChargingStation {
	private int index;
	private boolean empty = false;
	private volatile Robot r;
	private JLabel lbl;
	
	public ChargingStation(int index) {
		this.index = index;
		if(index<0)this.empty=true;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	public JLabel getLbl() {
		return lbl;
	}

	public void setLbl(JLabel lbl) {
		this.lbl = lbl;
	}

	public Robot getR() {
		return r;
	}
	
	public boolean isEmpty() {
		return empty;
	}

	public void setR(Robot r) {
		this.r = r;
		this.lbl.setText((this.empty?"  :":this.index+":")+(r==null?"---":r.getName()));
	}
	
}
