package com.indrajch.investment.mock.api;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.indrajch.investment.mock.data.Currency;

public class CurrencyDeserializer implements JsonDeserializer<Currency> {

	@Override
	public Currency deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		JsonPrimitive value = json.getAsJsonPrimitive();
		return Currency.findOfName(value.getAsString());
	}

}
