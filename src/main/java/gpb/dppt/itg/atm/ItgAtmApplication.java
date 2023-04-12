package gpb.dppt.itg.atm;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
//@Log4j
public class ItgAtmApplication {

	public static void main(String[] args) {

		SpringApplication.run(ItgAtmApplication.class, args);
	}

//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		log.info("Application started with command-line arguments: "+ Arrays.toString(args.getSourceArgs()));
//		log.info("NonOptionArgs: "+ args.getNonOptionArgs());
//		log.info("OptionNames: "+ args.getOptionNames());
//
//		for (String name : args.getOptionNames()){
//			log.info("arg-" + name + "=" + args.getOptionValues(name));
//		}
//
//		boolean containsOption = args.containsOption("debug");
//		log.info("Contains debug: " + containsOption);
//	}

}
