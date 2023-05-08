package Main;

public class Frame {
	private char name;
	
	private int pageId = 0;
	
	private int frozenFor = 0;
	
	private boolean referenced = false;
	
	public Frame(char name) {
		this.name = name;
	}
	
	public final char getName() { return name; }
	
	public final int getPageId() { return pageId; }
	
	public final void setPage(int p) { this.pageId = p; this.frozenFor = 4; }
	
	public final boolean isFrozen() { return this.frozenFor > 0; }
	
	public final void reference() { this.referenced = true; this.frozenFor = 0; }
	
	public final void unreference() { this.referenced = false; }
	
	public final boolean isReferenced() { return this.referenced; }
	
	public final void step() {
		if(this.frozenFor > 0)
			this.frozenFor--;
	}
}
