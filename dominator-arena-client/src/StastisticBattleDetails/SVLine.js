import React, { Component } from 'react';
import PropTypes from 'prop-types';
const client = require('../client/client')
import {baseUrl} from '../config'
class SVLine extends Component {
    render() {
        var sb = this.state.sb.entity;
        var vsLine = sb._embedded.strategyVersions.map(sv => sv.strategyName).join('🆚');
        return <li>

        </li>
    }
}
StatisticBattleDetails.propTypes = {
    svLineRes: PropTypes.object
}
export default StatisticBattleDetails;
