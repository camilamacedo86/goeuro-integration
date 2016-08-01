import br.com.camilamacedo.ParametersGoEuro;
import br.com.camilamacedo.context.GoEuroContext;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;

/**
 * Created by camilamacedo on 8/1/16.
 */
public class GenerateTest {
    @Test
    public void testGenerateValidIbanForGerman()  {
        ParametersGoEuro parametersGoEuro = new ParametersGoEuro();
        parametersGoEuro.setCity("Berlin");
        try {
            String generatedValue = GoEuroContext.generate.GENERATE_CSV.generate(parametersGoEuro);
            assertNotNull(generatedValue);
        } catch (Exception e){
            fail("Exception was occurred :" + e.getMessage());
        }
    }
}
