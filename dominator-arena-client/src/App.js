import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import StatisticBattleDetails from "./StastisticBattleDetails/StatisticBattleDetails";
import {NumericInput, Button} from "@blueprintjs/core";
import {Checkbox} from "@blueprintjs/core/dist/components/forms/controls";
const client = require('./client/client')
class App extends Component {
    constructor() {
        super();
        this.state = {
            sbId: 1,
            autoDummy: false
        }
    }
    handleEnabledChange(checked) {
        this.setState(Object.assign({}, this.state, {checked}))
    }
    handleValueChange(valueAsNumber, valueAsString) {
        console.log("Value as number:", valueAsNumber);
        var sbId = 1;
        if (valueAsNumber != null) sbId = valueAsNumber;
        this.setState({sbId})
    }

    render() {
        return (
            <div className="App">
                <div className={"App-header"}>
                    {/*<img src={logo} className="App-logo" alt="logo" />*/}
                    <nobr>
                        <h2>EDA Game Arena</h2>

                    </nobr>
                </div>
                <div className={"App-intro"}>
                    <div className={'center-block'}>
                        <div style={{width: '50%'}} className={'row'}>
                            <NumericInput
                                onValueChange={this.handleValueChange.bind(this)}
                                allowNumericCharactersOnly={true}
                                min={1}

                                value={this.state.sbId}
                            />
                            {/*<div className={'col-md-8'}>*/}
                                {/*<NumericInput*/}
                                    {/*onValueChange={this.handleValueChange.bind(this)}*/}
                                    {/*allowNumericCharactersOnly={true}*/}
                                    {/*min={1}*/}

                                    {/*value={this.state.sbId}*/}
                                {/*/>*/}
                            {/*</div>*/}

                            {/*<div className={'col-md-4'}>*/}
                                {/*<Button iconName={'import'}>Import</Button>*/}
                                {/*<Checkbox checked={this.state.autoDummy} label="Auto dummy" onChange={this.handleEnabledChange.bind(this)} />*/}
                            {/*</div>*/}


                        </div>

                    </div>
                    <br/>
                    <div className={'center-block  '}>
                        <StatisticBattleDetails id={this.state.sbId}/>
                    </div>
                </div>
                <div className={'row'}>

                </div>
            </div>
        );

    }
}

export default App;
