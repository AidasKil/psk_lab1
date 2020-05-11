package logging;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class LogMessage {
	public LogMessage(String message) {
		this.message = message;
		properties = new HashMap<>();
	}

	public void addProperty(String name, String value) {
		if(properties.containsKey(name))
			properties.replace(name, value);
		else properties.putIfAbsent(name, value);
	}

	private String message;
	private Map<String, String> properties;


}
