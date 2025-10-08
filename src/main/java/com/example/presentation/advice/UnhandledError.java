package com.example.presentation.advice;

/**
 * 補足されない例外を検出した場合、上記クラスにエラー情報を設定する
 */
class UnhandledError {
	
	UnhandledError(){}
	
	public String getMessage() {
		// 例外情報の詳細を外部に出力しないため、ここでは汎用的なメッセージの返却とする
		return "予期しない例外が発生しました";
	}
}
