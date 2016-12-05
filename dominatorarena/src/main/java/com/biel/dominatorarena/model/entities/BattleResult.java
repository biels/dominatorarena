package com.biel.dominatorarena.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by Biel on 29/11/2016.
 */
@Entity
public class BattleResult {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    Battle battle;
}
