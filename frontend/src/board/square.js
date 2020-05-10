import React from 'react';
import './square.css';

export default class Square extends React.Component {
    constructor(props) {
        super(props)
        this.state = { selected: false }
    }

    onClick(e) {
        this.setState({ selected: !this.state.selected });
        this.props.onClick(this.props.row, this.props.column);
    }

    getBackgroundColor() {
        if (this.props.data === -1) return 'board-cell-red';
        else if (this.props.data === 3) return 'board-cell-orange';
        else if (this.props.data === 2) return 'board-cell-light';
        else if (this.props.data === 1) return 'board-cell-more-light';
        else return '';
    }

    getFontColor() {
        if (this.props.data === -1) return 'board-cell-content-white';
        else if (this.props.data === 3 || this.props.data === 2 || this.props.data === 1) return 'board-cell-content-dark';
        else return '';
    }

    getCellContent() {
        if (this.props.data === -1) return 'T';
        else if (this.props.data === 3 || this.props.data === 2 || this.props.data === 1) return this.props.data;
    }

    render() {
        return (

            <div className={this.getBackgroundColor() + ' board-cell'} onClick={this.onClick.bind(this)}>
                <span className={this.getFontColor() + ' board-cell-content'}>{this.getCellContent()}</span>
            </div>
        );
    }
}