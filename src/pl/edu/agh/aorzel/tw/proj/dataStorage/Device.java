package pl.edu.agh.aorzel.tw.proj.dataStorage;

import java.util.concurrent.ConcurrentLinkedQueue;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.MutableDouble2D;
import sim.util.Valuable;

public class Device implements Steppable, Valuable {

	private static final long serialVersionUID = 1L;
	private int initialValue;
	private int currentValue;
	private int plannedCurrentValue;

	public final ConcurrentLinkedQueue<SaveRequest> queue = new ConcurrentLinkedQueue<SaveRequest>();
	public final MutableDouble2D position = new MutableDouble2D();

	public Device(int value, int x, int y) {
		initialValue = value;
		currentValue = value;
		plannedCurrentValue = value;
		position.x = x;
		position.y = y;
	}

	@Override
	public void step(SimState state) {
		SaveRequest req = null;
		if ((req = queue.poll()) != null) {
			currentValue -= req.getFileSize();

			System.out.println(String.format(
					"Step: %d File: %d Disk: [%.0f, %.0f]", req.getStep(),
					req.getFileSize(), position.x, position.y));
		}
	}

	public double doubleValue() {
		return currentValue / (double) initialValue;
	}

	public int getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(int initialValue) {
		this.initialValue = initialValue;
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}

	public int getPlannedCurrentValue() {
		return plannedCurrentValue;
	}

	public void setPlannedCurrentValue(int plannedCurrentValue) {
		this.plannedCurrentValue = plannedCurrentValue;
	}
}
