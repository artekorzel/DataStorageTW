package pl.edu.agh.aorzel.tw.proj.dataStorage;

public class SaveRequest {
	private int fileSize;
	private long step;
	
	public SaveRequest(int fileSize, long step) {
		this.fileSize = fileSize;
		this.step = step;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public long getStep() {
		return step;
	}

	public void setStep(long step) {
		this.step = step;
	}
}