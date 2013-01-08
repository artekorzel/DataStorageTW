package pl.edu.agh.aorzel.tw.proj.dataStorage;

import java.awt.Color;
import javax.swing.JFrame;

import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.grid.FastObjectGridPortrayal2D;
import sim.util.gui.SimpleColorMap;

public class GUIDataStorage extends GUIState {

	private FastObjectGridPortrayal2D portrayal = new FastObjectGridPortrayal2D();

	public Display2D display;
	public JFrame displayFrame;

	public GUIDataStorage() {
		super(new ConsoleDataStorage(System.currentTimeMillis()));
	}

	public GUIDataStorage(SimState state) {
		super(state);
	}

	public static String getName() {
		return "Data Storage";
	}

	public static Object getInfo() {
		return "<h2>Optymalne przechowywanie plików - Data Storage</h2>";
	}

	@Override
	public Object getSimulationInspectedObject() {
		return state;
	}

	public void initPortrayal() {
		ConsoleDataStorage cds = (ConsoleDataStorage) state;
		portrayal.setField(cds.space);
		portrayal.setMap(new SimpleColorMap(0.0, 1.0, Color.RED, Color.GREEN));
	}

	@Override
	public void start() {
		super.start();
		initPortrayal();
		display.reset();
		display.repaint();
	}

	@Override
	public void init(Controller c) {
		super.init(c);
		display = new Display2D(500, 500, this);
		displayFrame = display.createFrame();
		c.registerFrame(displayFrame);
		displayFrame.setVisible(true);
		display.attach(portrayal, "Data Storage");
		display.setBackdrop(Color.BLACK);
	}

	@Override
	public void load(SimState state) {
		super.load(state);
		initPortrayal();
		display.reset();
		display.repaint();
	}

	@Override
	public void quit() {
		super.quit();

		if (displayFrame != null) {
			displayFrame.dispose();
			displayFrame = null;
		}
		display = null;
	}

	public static void main(String[] args) {
		new GUIDataStorage().createController();
	}

}
