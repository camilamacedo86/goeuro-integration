package br.com.camilamacedo.context;


import br.com.camilamacedo.Parameters;
import br.com.camilamacedo.ParametersGoEuro;
import br.com.camilamacedo.generate.Generate;
import br.com.camilamacedo.generate.GenerateGoEuro;

public class GoEuroContext {
    public enum generate implements Generate {
        GENERATE_CSV(new GenerateGoEuro());
        private Generate delegate;

        private generate(Generate delegate) {
            this.delegate = delegate;
        }

        @Override
        public <T> T generate(Parameters parameters) throws Exception {
            return this.delegate.generate(parameters);
        }
    }

    public static final ParametersGoEuro getParameters() {
        return new ParametersGoEuro();
    }
}
