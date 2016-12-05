package com.biel.dominatorarena.ui;

import com.biel.dominatorarena.model.entities.Strategy;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalSplitPanel;

/**
 * Created by Biel on 5/12/2016.
 */
public class StrategiesAndBattlesView extends HorizontalSplitPanel implements ComponentContainer {
    private JPAContainer<Strategy> strategyJPAContainer;

    public StrategiesAndBattlesView() {

    }
}
