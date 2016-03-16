package com.dianxin.imessage.common.util;

public class IdGenerator {

	/**
	 * UID（生成规则：取系统配置文件的instance号2位+12位currentTimeMillis/10(10毫秒)+2位流水号）
	 * 
	 * @return
	 */
	private static int i = 0;
	private static String instanceId = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH,
			"instanceId");

	public static String getInstanceId() {
		return instanceId;
	}

	public static void setInstanceId(String instanceId) {
		if (instanceId == null || instanceId.length() != 2) {
			throw new IllegalArgumentException("Set instanceId error: the length should be 2 '"
					+ (instanceId == null ? "" : instanceId) + "' is not correct.");
		}
		IdGenerator.instanceId = instanceId;
	}

	/**
	 * 每10毫秒可生成100个序列号
	 * 
	 * @return
	 */
	public synchronized static String genUid() {
		i = i % 100;
		String index = (i < 10) ? ("0" + i) : "" + i;
		String uid = instanceId + Long.toString(System.currentTimeMillis() / 10).substring(6) + index;
		i++;
		return uid;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					System.err.println(genUid());

				}
			});
			t.start();
		}
	}

}
