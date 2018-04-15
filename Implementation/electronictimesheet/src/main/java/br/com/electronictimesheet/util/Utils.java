package br.com.electronictimesheet.util;

import java.util.Optional;

public class Utils {
	
	public static <T> Object returnValueOrDefault(Optional<T> optional)
	{
		return optional.isPresent() ?  optional.get() : null;
	}

}
