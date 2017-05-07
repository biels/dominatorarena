package com.biel.dominatorarena.rest;

import com.biel.dominatorarena.model.entities.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * Created by Biel on 7/5/2017.
 */
@RestController
@RequestMapping("/service")
public class ServiceIndex {
    @Autowired
    EntityLinks entityLinks;
    @RequestMapping(method = RequestMethod.GET)
    public List<Link> index() {
        ArrayList<Link> links = new ArrayList<>();
        links.add(linkTo(BattleService.class).withRel("battle"));
        links.add(linkTo(StrategyService.class).withRel("strategy"));
        links.add(linkTo(Executor.class).withRel("executor"));

        //links.add(entityLinks.linkFor(ExecutorWorkService.class).withSelfRel());
        return links;
    }
}
