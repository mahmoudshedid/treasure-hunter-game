import React from 'react';
import './square.css';

export default class Square extends React.Component {
    constructor(props) {
        super(props)
        this.state = { selected: false }
    }

    onClick(e) {
        this.setState({ selected: !this.state.selected })
    }

    render() {
        return (
            <div className="board-cell" onClick={this.onClick.bind(this)}>
                <span className="board-cell-content">{this.state.selected ? "X" : null}</span>
            </div>
        );
    }
}