package pl.edu.agh.aorzel.tw.proj.dataStorage;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.grid.ObjectGrid2D;

public class FileSender implements Steppable {

	private static final long serialVersionUID = 1L;

	private ObjectGrid2D tempGrid = new ObjectGrid2D(0, 0);

	@Override
	public void step(SimState state) {
		ConsoleDataStorage cds = (ConsoleDataStorage) state;
		tempGrid.setTo(cds.space);

		int diff = cds.getMaximalFileSize() - cds.getMinimalFileSize();
		int currentFileSize = cds.random.nextInt(diff)
				+ cds.getMinimalFileSize();

		int maxX = -1;
		int maxY = -1;
		double plannedPercentage, max = -1.0;
		Device d;
		for (int x = 0, width = cds.getWidth(); x < width; ++x) {
			for (int y = 0, height = cds.getHeight(); y < height; ++y) {
				d = (Device) tempGrid.field[x][y];
				plannedPercentage = d.getPlannedCurrentValue() / (double)d.getInitialValue();
				if (plannedPercentage > max
						&& d.getPlannedCurrentValue() >= currentFileSize) {
					max = plannedPercentage;
					maxX = x;
					maxY = y;
				}
			}
		}

		if (max != -1.0) {
			d = (Device) cds.space.field[maxX][maxY];
			d.setPlannedCurrentValue(d.getPlannedCurrentValue() - currentFileSize);
			d.queue.offer(new SaveRequest(currentFileSize, 
					cds.schedule.getSteps()));
		}
	}
}
