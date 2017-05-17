import React, {Component} from 'react';
import PropTypes from 'prop-types';
import StrategyVersionTree from "./StrategyVersionTree";
const client = require('../client/client')
import {baseUrl} from '../config'
import './StatisticBattleDetails.css';
class StatisticBattleDetails extends Component {
    updateSB(id) {
        console.log("Hello")
        client({
            method: 'GET',
            path: baseUrl + '/statisticBattles/' + id,
            headers: {'Content-Type': 'application/json'}
        })
            .then(r => {
                console.log("SBr: ", r)
                var SVs;
                client({
                    method: 'GET',
                    path: baseUrl + '/statisticBattles/' + id + '/strategyVersions',
                    headers: {'Content-Type': 'application/json'}
                })
                    .then(r2 => {
                        console.log("SVsr: ", r2)
                        SVs = r2;
                        this.setState({sb: r, SVs})
                    }, e => {
                    });
                console.log("SB", r)
            }, e => {
                this.setState({sb: null})
            });

    }

    componentWillMount() {
        this.updateSB(1);
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.id !== nextProps.id)
            this.updateSB(nextProps.id)
    }

    render() {
        if (this.state == null || this.state.sb == null || this.state.sb.status.code == 404) return <div>Nothing</div>
        var sb = this.state.sb.entity;
        var strategyVersions = this.state.SVs.entity._embedded.strategyVersions;
        var vsLine = strategyVersions.map(sv => sv.strategyName).join('🆚');
        var rows;
        if (sb.report !== null) {
            rows = sb.report.svResults.map((svResult, i) => {
                var intent = 'pt-intent-warning';
                if (svResult.effectiveness > 0.1) intent = 'pt-intent-success';
                if (svResult.effectiveness < -0.1) intent = 'pt-intent-danger';
                return <tr key={i}>
                    <td>{svResult.strategyName + ' (' + svResult.strategyVersionIdentifier + ')'}</td>
                    <td>{(svResult.winRatio * 100).toFixed(2) + '%'}</td>
                    <td><span className={'pt-tag ' + intent}>{(svResult.effectiveness * 100).toFixed(2) + '%'}</span>
                    </td>
                    <td>{(svResult.averagePlace).toFixed(2)}</td>
                    <td>{(svResult.inGamePlayerMultiplicity).toFixed(2)}</td>
                </tr>
            })
        } else {
            rows = 'No report';
        }
        var table = <table className={'pt-table pt-striped '}>
            <thead>
            <th>Strategy</th>
            <th>WinRatio</th>
            <th>Effectiveness</th>
            <th>Avg Place</th>
            <th>Multiplicity</th>
            </thead>
            <tbody>
            {rows}
            </tbody>
        </table>
        console.debug("STATE", this.state);
        return <div>
            <h1>{vsLine}</h1>
            <div style={{textAlign: 'left'}} className={'row'}>
                <div style={{fontSize: '12px'}} className={'col-md-4'}>
                    <StrategyVersionTree SVs={strategyVersions}/>
                </div>
                <div className={'col-md-8'}>
                    {table}
                    <span className={'pt-tag pt-round'}>{sb.report.battleCount + ' battles'}</span> {' '}
                    {sb.allVsFirst && <span className={'pt-tag'}>  {'First VS All'}</span>}
                </div>

            </div>

        </div>
    }
}
StatisticBattleDetails.propTypes = {
    id: PropTypes.number
}
export default StatisticBattleDetails;
