package br.com.camilamacedo.execute;


import br.com.camilamacedo.ParametersGoEuro;
import br.com.camilamacedo.context.GoEuroContext;
import br.com.camilamacedo.context.GoEuroResources;

import java.util.logging.Logger;

public class ExecuteGoEuro {

    private static Logger logger = Logger.getLogger("ExecuteGoEuro");

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++){
            execute(args[i]);
        }
    }

    private static void execute(String arg) {
        logger.info(GoEuroResources.getMessage("goeuro-integration.city.start", arg));
        ParametersGoEuro parametersGoEuro = new ParametersGoEuro();
        parametersGoEuro.setCity(arg);
        try {
            String generatedValue = GoEuroContext.generate.GENERATE_CSV.generate(parametersGoEuro);
            logger.info(GoEuroResources.getMessage("goeuro-integration.city.file", generatedValue));
        } catch (Exception e){
            logger.severe(e.getMessage());
        }
    }
}
