package amazoninf.ner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(scanBasePackages = { "amazoninf.ner.repository","amazoninf.ner.service" })
@SpringBootApplication
public class App {
	public static void main(String[] args) {
		System.out.println("Inicio");
		SpringApplication.run(App.class, args);
	}
}
