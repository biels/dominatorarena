package commands

import com.biel.dominatorarena.model.entities.Strategy
import com.biel.dominatorarena.model.entities.StrategyVersion
import com.biel.dominatorarena.model.repositories.StrategyRepository
import org.crsh.cli.Command
import org.crsh.cli.Usage
import org.crsh.command.InvocationContext
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Biel on 5/12/2016.
 */
@Usage("sends messages")
class arena {
    @Command
    def msg(InvocationContext context){
        return "Hellow"
    }
    @Command
    def list(InvocationContext context){
        BeanFactory beanFactory = (BeanFactory) context.getAttributes().get("spring.beanfactory");
        StrategyRepository strategyRepository = beanFactory.getBean(StrategyRepository.class);
        return strategyRepository.findAll().size();
    }
}
