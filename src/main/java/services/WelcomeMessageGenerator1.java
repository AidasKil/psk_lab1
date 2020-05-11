package services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@Alternative
@ApplicationScoped
public class WelcomeMessageGenerator1 implements IWelcomeMessageGenerator {
	@Override
	public String generateMessage() {
		return "Hi, this is a message from WelcomeMessageGenerator1";
	}
}
