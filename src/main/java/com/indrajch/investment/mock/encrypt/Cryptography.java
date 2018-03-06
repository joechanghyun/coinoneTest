package com.indrajch.investment.mock.encrypt;

import java.io.UnsupportedEncodingException;

public interface Cryptography {
	String cryptographyData(String data) throws IllegalStateException, UnsupportedEncodingException;
}
