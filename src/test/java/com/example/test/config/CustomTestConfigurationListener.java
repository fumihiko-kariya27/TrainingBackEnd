package com.example.test.config;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomTestConfigurationListener implements TestExecutionListener {
	// テスト実行時間計測用
	private long start;
	
	@Override
	public void beforeTestMethod(TestContext testContext) {
		String msg = String.format("[START] %s#%s", testContext.getTestClass().getSimpleName(), testContext.getTestMethod().getName());
		log.debug(msg);
		start = System.currentTimeMillis();
	}
	
	@Override
	public void afterTestMethod(TestContext testContext) {
		if(testContext.getTestException() == null) {
			long elapsed = System.currentTimeMillis() - start;
			String msg = String.format("[END] %s#%s took %d[ms]", testContext.getTestClass().getSimpleName(), testContext.getTestMethod().getName(), elapsed);
			log.debug(msg);
		} else {
			// テストが異常終了した場合は診断ログを出力する
			String msg = String.format("[ERROR] %s#%s : %s", testContext.getTestClass().getSimpleName(), testContext.getTestMethod().getName(), testContext.getTestException().getClass());
			log.error(msg);
		}
	}
}
