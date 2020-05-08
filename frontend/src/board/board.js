import React from 'react';
import './board.css'
import Square from './square';

export default class Board extends React.Component {

    render() {
        return (
            <div className="board">
                {
                    [1, 2, 3, 4, 5].map((row) => {
                        return <div key={row} className="board-row">
                            {
                                [1, 2, 3, 4, 5].map((col) => {
                                    return <Square key={col}></Square>
                                })
                            }
                        </div>

                    })
                }
            </div>
        )

    }

}