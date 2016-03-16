package com.dianxin.imessage.common.key;

import java.util.Hashtable;

public class DxKeyManager {

	private static Hashtable keys = new Hashtable();
	private static DxKeyManager singleton = null;

	protected DxKeyManager() {
		initializeKeys();
	}

	public void initializeKeys() {

	}

	public static DxKeyManager singleton()

	{
		if (singleton == null)
			synchronized (keys) {
				if (singleton == null)
					singleton = new DxKeyManager();
			}
		return singleton;
	}

}
