package com.baitian.game.xml.manager;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.baitian.game.xml.exception.NoSuchModelException;
import com.baitian.game.xml.model.IdentifierModel;

/**
 * 将XML文件转成Model存储在内存中
 * @author Alias
 * @date 2015年11月24日 上午9:35:27
 *
 * @param <T>
 */
public abstract class XMLModelsManager<T extends IdentifierModel> {
	
	private String path;
	
	/**
	 * Stores all {@link IdentifierModel}s.
	 */
	private volatile Map<Integer, T> models;

	public XMLModelsManager(String path) {
		this.path = path;
	}
	
	public void init() {
		models = new HashMap<>();
	}
	
	/**
	 * Gets all {@link IdentifierModel}s.
	 * @return
	 */
	protected Collection<T> getModels() {
		return Collections.unmodifiableCollection(models.values());
	}
	
	/**
	 * Gets the {@link IdentifierModel} for the specify id.
	 * @param id
	 * @return
	 */
	public T getModel(int id) {
		T result = models.get(id);
		if (result == null) {
			throw new NoSuchModelException("No such Model:" + id);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public void addModel(IdentifierModel model) {
		models.put(model.getId(), (T) model);
	}
	
	public void afterLoad() {
		
	}
	
	public String getPath() {
		return path;
	}
	
	public String getModelAlias() {
		return null;
	}

}
