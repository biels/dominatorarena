package com.biel.dominatorarena.ui;

import com.biel.dominatorarena.logic.AutoStrategyVersionImporter;
import com.biel.dominatorarena.model.entities.Executor;
import com.biel.dominatorarena.model.entities.StatisticBattle;
import com.biel.dominatorarena.model.entities.Strategy;
import com.biel.dominatorarena.model.entities.StrategyVersion;
import com.biel.dominatorarena.model.repositories.ExecutorRepository;
import com.biel.dominatorarena.model.repositories.StatisticBattleRepository;
import com.biel.dominatorarena.model.repositories.StrategyRepository;
import com.biel.dominatorarena.model.repositories.StrategyVersionRepository;
import com.vaadin.addon.jpacontainer.fieldfactory.MasterDetailEditor;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import javafx.scene.layout.Pane;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Created by Biel on 1/12/2016.
 */
@SpringUI
@Theme("tests-valo-dark")
public class MainUI extends UI {
    @Autowired
    StrategyRepository strategyRepository;
    @Autowired
    StrategyVersionRepository strategyVersionRepository;
    @Autowired
    ExecutorRepository executorRepository;
    @Autowired
    StatisticBattleRepository statisticBattleRepository;
    @Autowired
    AutoStrategyVersionImporter autoStrategyVersionImporter;

    Label lblTitle;
    private VerticalLayout vlyMain;
    private HorizontalLayout hlyMain;
    private HorizontalLayout hlyHeader;
    private VerticalLayout vlyStrategiesMD;
    private VerticalLayout vlyStatisticBattlesMD;
    private VerticalLayout vlyAdditional;
    private HorizontalLayout hlySBTop;

    private Grid<Strategy> grdStrategies;
    private Grid<StrategyVersion> grdStrategyVersions;

    private Grid<StatisticBattle> grdStatisticBattles;
    private Grid<Executor> grdExecutors;

    private Panel pnlStatisticBattle;
    private Panel pnlNewStatisticBattle;

    @Override
    protected void init(VaadinRequest request) {
        //New Statistic battles panel
        pnlNewStatisticBattle = new Panel(new Label("New Statistic battle"));

        //Statitstic battle panel
        pnlNewStatisticBattle = new Panel(new Label("Selected SB"));

        //Executors
        grdExecutors = new Grid<>(Executor.class);
        updateExecutorGrid();

        //Statistic battles
        grdStatisticBattles = new Grid<>(StatisticBattle.class);
        updateStatisticBattlesGrid();


        //Strategies
        grdStrategies = new Grid<>(Strategy.class);
        updateStrategyGrid();
        grdStrategies.setColumns("id", "name");
        grdStrategies.addStyleName(ValoTheme.LABEL_SMALL);
        grdStrategies.addSelectionListener(event -> event.getFirstSelectedItem()
                .ifPresent(strategy -> updateStrategyVersionGrid()));
        //grdStrategies.setDataProvider((sortOrder, offset, limit) -> strategyRepository.findAll().stream(), () -> (int) strategyRepository.count());

        grdStrategyVersions = new Grid<>(StrategyVersion.class);
        grdStrategyVersions.setColumns("id", "digest", "sourceFile");
        updateStrategyVersionGrid();

        vlyStrategiesMD = new VerticalLayout(new Label("Strategies"), grdStrategies, grdStrategyVersions);

        hlySBTop = new HorizontalLayout(grdStatisticBattles, grdExecutors);
        vlyStatisticBattlesMD = new VerticalLayout(hlySBTop, pnlStatisticBattle);
        vlyAdditional = new VerticalLayout(new Label("Additional"));
        hlyMain = new HorizontalLayout(
                vlyStrategiesMD,
                vlyStatisticBattlesMD
        );
        hlyMain.setSizeFull();

        hlyHeader = headerPanel();
        hlyHeader.setMargin(false);
        vlyMain = new VerticalLayout(
                hlyHeader,
                hlyMain
        );

        setContent(vlyMain);
        //vlyMain.setMargin();
        vlyMain.setMargin(false);
        // Create the selection component
//        ListSelect select = new ListSelect("Strategies");
//
//        // Add some items (here by the item ID as the caption)
////        Collection<Strategy> all = strategyRepository.findAll();
////        select.addItems(all.stream().map(Strategy::getName).collect(Collectors.toList()));
////
////        select.setNullSelectionAllowed(true);
//
//        // Show 5 items and a scrollbar if there are more
//        select.setRows(5);
//        select.setSizeFull();
//        HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel(select, null);
//        horizontalSplitPanel.setSizeFull();
//        hlyMain.addComponent(horizontalSplitPanel);

    }

    private void updateExecutorGrid() {
        grdExecutors.setItems(executorRepository.findAll());
    }

    private void updateStatisticBattlesGrid() {
        grdStatisticBattles.setItems(statisticBattleRepository.findByActiveTrue());
    }

    private void updateStrategyGrid() {
        grdStrategies.setItems(strategyRepository.findAll());
    }

    private void updateStrategyVersionGrid() {
        Strategy strategy = grdStrategies.asSingleSelect().getValue();
        grdStrategyVersions.setItems(strategyVersionRepository.findByStrategy(strategy));
    }

    protected HorizontalLayout headerPanel() {
        hlyHeader = new HorizontalLayout();
        hlyHeader.setHeight(50, Unit.PIXELS);
        //hlyHeader.setSizeFull();
        // parent.addComponent(content);
        hlyHeader.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        HorizontalLayout cLeft = new HorizontalLayout();
        HorizontalLayout cRight = new HorizontalLayout();
        hlyHeader.addComponent(cLeft);
        hlyHeader.setComponentAlignment(cLeft, Alignment.MIDDLE_LEFT);
        hlyHeader.addComponent(cRight);
        hlyHeader.setComponentAlignment(cRight, Alignment.MIDDLE_RIGHT);
        cRight.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);

       // Label label = new Label("<h1>Dominator Arena</h1>", ContentMode.HTML);

        //cLeft.addComponent(label);
        Button b = new Button();

        b.setCaption("Auto Import");
        b.addClickListener(event -> {
            List<StrategyVersion> imported = autoStrategyVersionImporter.autoImport();
            String text = imported.stream().map(v -> v.getStrategy().getName()).reduce((s1, s2) -> s1 + ", " + s2).orElse("");
            Notification.show(imported.size() + " new versions imported", text, Notification.Type.TRAY_NOTIFICATION);
            updateStrategyGrid();
        });
        cRight.addComponent(b);
        cRight.setComponentAlignment(b, Alignment.MIDDLE_RIGHT);
        Button btnGenerateWork = new Button("Generate work");
        cRight.addComponent(btnGenerateWork);
        return hlyHeader;
    }

    protected VerticalLayout strategyExplorer() {
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
