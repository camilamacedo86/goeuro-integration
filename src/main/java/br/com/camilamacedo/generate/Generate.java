package br.com.camilamacedo.generate;

import br.com.camilamacedo.Parameters;

public interface Generate {

    public abstract <T> T generate(Parameters parameters) throws Exception;


}
