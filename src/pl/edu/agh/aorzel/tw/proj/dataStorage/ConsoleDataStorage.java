package pl.edu.agh.aorzel.tw.proj.dataStorage;

import sim.engine.SimState;
import sim.field.grid.ObjectGrid2D;

public class ConsoleDataStorage extends SimState {

	private static final long serialVersionUID = 1L;

	private int minimalDiskSize = 50;
	private int maximalDiskSize = 100;
	private int minimalFileSize = 2;
	private int maximalFileSize = 10;
	private int width = 5;
	private int height = 5;

	public ObjectGrid2D space;

	public int getMinimalDiskSize() {
		return minimalDiskSize;
	}

	public void setMinimalDiskSize(int minimalDiskSize) {
		if (minimalDiskSize >= 0) {
			this.minimalDiskSize = minimalDiskSize;
		}
	}

	public int getMaximalDiskSize() {
		return maximalDiskSize;
	}

	public void setMaximalDiskSize(int maximalDiskSize) {
		if (maximalDiskSize >= minimalDiskSize) {
			this.maximalDiskSize = maximalDiskSize;
		}
	}

	public int getMinimalFileSize() {
		return minimalFileSize;
	}

	public void setMinimalFileSize(int minimalFileSize) {
		if (minimalFileSize >= 0) {
			this.minimalFileSize = minimalFileSize;
		}
	}

	public int getMaximalFileSize() {
		return maximalFileSize;
	}

	public void setMaximalFileSize(int maximalFileSize) {
		if (maximalFileSize >= minimalFileSize) {
			this.maximalFileSize = maximalFileSize;
		}
	}

	public ConsoleDataStorage(long seed) {
		super(seed);
	}

	public void initGrid() {
		int diff = maximalDiskSize - minimalDiskSize;
		Device d;
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				d = new Device(random.nextInt(diff) + minimalDiskSize, x, y);
				space.set(x, y, d);
			}
		}
	}

	@Override
	public void start() {
		super.start();
		space = new ObjectGrid2D(width, height);
		initGrid();
		schedule.scheduleRepeating(new FileSender());

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				schedule.scheduleRepeating((Device) space.field[x][y]);
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public static void main(String[] args) {
		doLoop(ConsoleDataStorage.class, args);
		System.exit(0);
	}
}
