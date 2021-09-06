package de.quantumrange.geometry.communication;

import org.apache.commons.lang.ObjectUtils;

import java.util.function.Consumer;

public interface Communicator {

	void pushMash();
	void pullMash(Consumer<ObjectUtils.Null> consumer);

}
