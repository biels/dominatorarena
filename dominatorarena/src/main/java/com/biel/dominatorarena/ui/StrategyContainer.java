package com.biel.dominatorarena.ui;

import com.biel.dominatorarena.model.entities.Strategy;
import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;

/**
 * Created by Biel on 5/12/2016.
 */
public class StrategyContainer extends JPAContainer<Strategy> {

    /**
     * Creates a new <code>JPAContainer</code> instance for entities of class
     * <code>entityClass</code>. An entity provider must be provided using the
     * {@link #setEntityProvider(EntityProvider) }
     * before the container can be used.
     */
    public StrategyContainer() {
        super(Strategy.class);

    }
}
