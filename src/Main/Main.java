package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	public static ArrayList<Frame> frames = new ArrayList<Frame>();
	
	public static Queue<Frame> queue = new LinkedList<Frame>();
	
	public static void main(String[] args) throws IOException {
		frames.add(new Frame('A'));
		frames.add(new Frame('B'));
		frames.add(new Frame('C'));
		
		BufferedReader reader = new BufferedReader(
	            new InputStreamReader(System.in));
		
		String line = reader.readLine();
		String[] split = line.split(",");
		
		int counter = 0;
		for(String part : split) {
			int num = Integer.parseInt(part);
			if(num < 0)
				num *= -1;
			char result;
			System.out.print(result = process(num));
			if(result != '-')
				counter++;
		}
		System.out.print('\n');
		System.out.println(counter);
	}
	
	public static char process(int pageId) {
		stepAll();
		
		Frame f = getFrameForPage(pageId);
		
		// already in
		if(f != null) {
			f.reference();
			return '-';
		}
		
		// empty frame
		Frame e = getFirstEmptyFrame();
		if(e != null) {
			e.setPage(pageId);
			queue.add(e);
			return e.getName();
		}
		
		// all frozen
		if(allFrozen()) {
			return '*';
		}
		
		while(true) {
			Frame first = null;
			for(Frame q : queue) {
				if(!q.isFrozen()) {
					first = q;
					break;
				}
			}
			
			if(first.isReferenced())
				secondChance(first.getPageId());
			else {
				first.setPage(pageId);
				first.unreference();
				queue.remove(first);
				queue.add(first);
				return first.getName();
			}
		}
	}
	
	public static void secondChance(int pageId) {
		for(Frame q : queue) {
			if(q.getPageId() == pageId) {
				queue.remove(q);
				queue.add(q);
				q.unreference();
				return;
			}
		}
	}
	
	public static Frame getFrameForPage(int pageId) {
		for(Frame q : queue)
			if(q.getPageId() == pageId)
				return q;
		return null;
	}
	
	public static Frame getFirstEmptyFrame() {
		for(Frame f : frames)
			if(f.getPageId() == 0)
				return f;
		return null;
	}
	
	public static boolean allFrozen() {
		boolean result = true;
		for(Frame f : queue)
			result &= f.isFrozen();
		return result;
	}
	
	public static void stepAll() {
		for(Frame f : queue)
			f.step();
	}
}
