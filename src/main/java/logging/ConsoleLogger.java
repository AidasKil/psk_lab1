package logging;


import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConsoleLogger implements ILogger {

	@Override
	public void log(LogMessage message) {
		System.out.println(message.getMessage());
		for (String key : message.getProperties().keySet())
			System.out.println(key + ": " + message.getProperties().get(key));
		System.out.println("");
	}
}
