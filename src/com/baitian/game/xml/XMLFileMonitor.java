package com.baitian.game.xml;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * A class which monitors the change of the file.
 * 
 * @author Alias
 *
 */
public class XMLFileMonitor {

	private static FileAlterationMonitor monitor = new FileAlterationMonitor();
	
	public static void monitor(File file) {
		FileAlterationObserver observer = new FileAlterationObserver(file);
		monitor.addObserver(observer);
		observer.addListener(new XMLFileReloadListener());
		try {
			monitor.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
