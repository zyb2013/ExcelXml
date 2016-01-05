package com.baitian.game.xml;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import com.baitian.game.ioc.InstanceFactory;
import com.baitian.game.xml.loader.XMLFileLoader;

/**
 * XML文件重新加载监听器
 * @author Alias
 * @date 2015年11月30日 上午10:18:30
 *
 */
public class XMLFileReloadListener extends FileAlterationListenerAdaptor {

	@Override
	public void onFileChange(File file) {
		XMLFileLoader loader = InstanceFactory.getInstance(XMLFileLoader.class, "xmlFileLoader");
		loader.reload(file);
	}
	
}
