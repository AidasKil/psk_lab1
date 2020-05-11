package services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@Alternative
@ApplicationScoped
public class WelcomeMessageGenerator2 implements IWelcomeMessageGenerator {
	@Override
	public String generateMessage() {
		return "Hi, this is a different message form WelcomeMessageGenerator2";
	}
}
