import React, { Component } from 'react';
import PropTypes from 'prop-types';
const client = require('../client/client')
import { Classes, ITreeNode, Tooltip, Tree } from "@blueprintjs/core";
import {baseUrl} from '../config'
//import './StatisticBattleDetails.css';
class StatisticBattleDetails extends Component {
    render() {
        var SVs = this.props.SVs;
        var contents = SVs.map(sv => {
            var features = sv.features.map(f => {
                var params = f.params.map(p => {
                    return <li className={'pt-tree-node'}>
                        <div className={'pt-tree-node-content'}>
                            <span className={'pt-tree-node-caret-none pt-icon-standard'}/>
                            <span className={'pt-tree-node-icon pt-icon-standard'}/>
                            <span className={'pt-tree-node-icon pt-icon-standard pt-icon-property'}/>
                            <span  className={'pt-tree-node-label'}>{p.name + ': ' + p.value}</span>
                        </div>
                        <ul className={'pt-tree-node-list'}>
                            {params}
                        </ul>
                    </li>
                })
                var caret = params.length > 0 ? 'pt-tree-node-caret pt-tree-node-caret-open' : 'pt-tree-node-caret-none';
                return <li className={'pt-tree-node'}>
                    <div className={'pt-tree-node-content'}>
                        <span className={caret + ' pt-icon-standard'}/>
                        <span className={'pt-tree-node-icon pt-icon-standard pt-icon-code-block'}/>
                        <span className={'pt-tree-node-label'}>{f.name}</span>
                    </div>
                    <ul className={'pt-tree-node-list pt-tree-root'}>
                        {params}

                    </ul>
                </li>
            })
            return <li className={'pt-tree-node pt-tree-node-expanded'}>
                <div className={'pt-tree-node-content'}>
                    <span className={'pt-tree-node-icon pt-icon-standard'}/>
                    <span className={'pt-tree-node-icon pt-icon-standard pt-icon-folder-close'}/>
                    <span className={'pt-tree-node-label'}><strong>{sv.strategyName} {' (' + sv.identifier + ')'}
                    </strong></span>
                    {/*<span className={'pt-tree-node-secondary-label'}>{sv.identifier}</span>*/}
                </div>

                <ul className={'pt-tree-node-list'}>
                    {features}
                </ul>
            </li>

        })
        return <div className={'pt-tree pt-elevation-0'}>
            <ul className={'pt-tree-node-list pt-tree-root'}>
                {contents}
            </ul>
        </div>
    }
}
StatisticBattleDetails.propTypes = {
    SVs: PropTypes.number
}
export default StatisticBattleDetails;
