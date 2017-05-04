package com.biel.dominatorarena.ui;

import com.biel.dominatorarena.logic.AutoStrategyVersionImporter;
import com.biel.dominatorarena.model.entities.Executor;
import com.biel.dominatorarena.model.entities.Strategy;
import com.biel.dominatorarena.model.entities.StrategyVersion;
import com.biel.dominatorarena.model.repositories.ExecutorRepository;
import com.biel.dominatorarena.model.repositories.StrategyRepository;
import com.biel.dominatorarena.model.repositories.StrategyVersionRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Created by Biel on 1/12/2016.
 */
@SpringUI
@Theme("valo")
public class MainUI extends UI {
    @Autowired
    StrategyRepository strategyRepository;
    @Autowired
    StrategyVersionRepository strategyVersionRepository;
    @Autowired
    ExecutorRepository executorRepository;
    @Autowired
    AutoStrategyVersionImporter autoStrategyVersionImporter;
    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout content = new VerticalLayout();
        content.setHeightUndefined();
        setContent(content);
        content.addComponent(topPanel(content));
        content.addComponent(strategyExplorer());
        HorizontalLayout layoutStrategies = new HorizontalLayout();
        layoutStrategies.setSizeFull();
        content.addComponent(layoutStrategies);
        // Create the selection component
        ListSelect select = new ListSelect("Strategies");

        // Add some items (here by the item ID as the caption)
//        Collection<Strategy> all = strategyRepository.findAll();
//        select.addItems(all.stream().map(Strategy::getName).collect(Collectors.toList()));
//
//        select.setNullSelectionAllowed(true);

        // Show 5 items and a scrollbar if there are more
        select.setRows(5);
        select.setSizeFull();
        HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel(select, null);
        horizontalSplitPanel.setSizeFull();
        layoutStrategies.addComponent(horizontalSplitPanel);

    }
    protected HorizontalLayout topPanel(AbstractOrderedLayout parent){
        HorizontalLayout content = new HorizontalLayout();
        content.setSizeFull();
       // parent.addComponent(content);
        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        HorizontalLayout cLeft = new HorizontalLayout();
        HorizontalLayout cRight = new HorizontalLayout();
        content.addComponent(cLeft);
        content.setComponentAlignment(cLeft, Alignment.MIDDLE_LEFT);
        content.addComponent(cRight);
        content.setComponentAlignment(cRight, Alignment.MIDDLE_RIGHT);
        cRight.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);

        Label label = new Label("<h1>Dominator Arena</h1>", ContentMode.HTML);

        cLeft.addComponent(label);
        Button b = new Button();

        b.setCaption("Auto Import");
        b.addClickListener(event -> {
            List<StrategyVersion> imported = autoStrategyVersionImporter.autoImport();
            String text = imported.stream().map(v -> v.getStrategy().getName()).reduce((s1, s2) -> s1 + ", " + s2).orElse("");
            Notification.show(imported.size() + " new versions imported", text, Notification.Type.TRAY_NOTIFICATION);
        });
        cRight.addComponent(b);
        cRight.setComponentAlignment(b, Alignment.MIDDLE_RIGHT);
        Button btnGenerateWork = new Button("Generate work");
        cRight.addComponent(btnGenerateWork);
        return content;
    }
    protected VerticalLayout strategyExplorer(){
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth(20, Unit.PERCENTAGE);

        Grid<Executor> executorGrid = new Grid<>(Executor.class);
        executorGrid.setDataProvider((sortOrder, offset, limit) -> executorRepository.findAll().stream(), () -> (int) executorRepository.count());

        layout.addComponent(executorGrid);

        Grid<StrategyVersion> strategyVersionGrid = new Grid<>(StrategyVersion.class);
        strategyVersionGrid.setDataProvider((sortOrder, offset, limit) -> strategyVersionRepository.findAll().stream(), () -> (int) strategyVersionRepository.count());

        layout.addComponent(strategyVersionGrid);
        //Panels
        Panel strategyPanel = new Panel("Strategies");

        layout.addComponent(strategyPanel);
        strategyPanel.setSizeFull();
        Panel strategyVersionPanel = new Panel("Strategy versions");
        layout.addComponent(strategyVersionPanel);
        strategyVersionPanel.setSizeFull();
        //Tables
//        IndexedContainer strategyIndexedContainer = new IndexedContainer();
//        //JPAContainer<Strategy> strategyJPAContainer = new JPAContainer<>(Strategy.class);
//        //strategyJPAContainer.setEntityProvider();
//
//        strategyIndexedContainer.addContainerProperty("Name", String.class, "[Unnamed]");
//        strategyIndexedContainer.addContainerProperty("Volume", Double.class, -1.0D);

//        Table tblStrategies = new Table("Strategy table", strategyIndexedContainer);
//        Collection<Strategy> all = strategyRepository.findAll();
//        BeanItemContainer newDataSource = new BeanItemContainer(Strategy.class);
//        newDataSource.addAll(all);
//        tblStrategies.setContainerDataSource(newDataSource);
//        layout.addComponent(tblStrategies);
        return layout;
    }
}
