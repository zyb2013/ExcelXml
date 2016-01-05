package com.baitian.game.xml.loader;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.baitian.game.xml.manager.XMLModelsManager;
import com.baitian.game.xml.model.IdentifierModel;
import com.baitian.game.xml.model.InitialModel;
import com.thoughtworks.xstream.XStream;

/**
 * Loads data to memory from the xml file.
 * @author Alias
 * @date 2015年11月23日 下午2:49:43
 *
 */
@Component("xmlFileLoader")
public class XMLFileLoader implements ApplicationContextAware {
	
	private static final String MODEL_ALIAS = "Model";

	private static final String LIST_ALIAS = "Models";

	private Logger logger = Logger.getLogger(XMLFileLoader.class);
	
	private Map<File, XMLModelsManager<?>> modelsManagers = new HashMap<>();
	
	private ApplicationContext applicationContext;
	
	/**
	 * Loads all xml file from the specify path.
	 * @throws Exception
	 */
	@PostConstruct
	@SuppressWarnings({ "rawtypes" })
	public void load() throws Exception {
		for (XMLModelsManager each : applicationContext.getBeansOfType(XMLModelsManager.class).values()) {
			URL url = XMLFileLoader.class.getResource(each.getPath());
			File file = new File(url.getFile());
			this.modelsManagers.put(file, each);
			doLoad(each);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void doLoad(XMLModelsManager modelsManager) {
		ParameterizedType type = ((ParameterizedType) modelsManager.getClass().getGenericSuperclass());
		Class clazz = (Class) type.getActualTypeArguments()[0];
		XStream xStream = new XStream();
		alias(modelsManager, clazz, xStream);
		URL url = XMLFileLoader.class.getResource(modelsManager.getPath());
		File file = new File(url.getFile());
		logger.debug("Before loads " + file.getName());
		List<IdentifierModel> models = (List<IdentifierModel>) xStream.fromXML(file);
		modelsManager.init();
		for (IdentifierModel model : models) {
			if (model instanceof InitialModel) {
				((InitialModel) model).initial();
			}
			modelsManager.addModel(model);
		}
		modelsManager.afterLoad();
		logger.debug("After loads " + file.getName());
	}
	
	public void reload(File file) {
		doLoad(modelsManagers.get(file));
	}

	@SuppressWarnings("rawtypes")
	private void alias(XMLModelsManager modelManager, Class clazz, XStream xStream) {
		xStream.alias(LIST_ALIAS, List.class);
		if (modelManager.getModelAlias() != null) {
			xStream.alias(modelManager.getModelAlias(), clazz);
		} else {
			xStream.alias(MODEL_ALIAS, clazz);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
