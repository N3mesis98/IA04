package tp7.main;

import sim.display.Console;
import tp7.gui.BeingsWithUI;
import tp7.model.Beings;

public class BeingsMain {
	public static void main(String[] args) {
        runUI();
	}
	public static void runUI() {
		Beings model = new Beings(System.currentTimeMillis());
		BeingsWithUI gui = new BeingsWithUI(model);
		Console console = new Console(gui);
		console.setVisible(true);
	}
}
