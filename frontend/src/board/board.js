import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import './board.css';
import Square from './square';
import { fetchStartGame, fetchPlayGame } from '../actions/game.action';

const Board = ({ dispatch, status, message, hasErrors, loading, gameResult }) => {

    useEffect(() => {
        dispatch(fetchStartGame());
    }, [dispatch]);

    const onClickPlayGame = (row, column) => {
        dispatch(fetchPlayGame(row, column));
    }

    let keyContainer = 0;
    let keySquare = 0;

    if (loading) return <p>Loading Game...</p>;
    if (hasErrors) return <p>Unable to display game.</p>;

    return (
        <div>
            <p>{message}</p>
            <div className="board">
                {
                    (gameResult.board ? gameResult.board.map((row, indexRow) => {
                        return <div key={'container_' + keyContainer++} className="board-row">
                            {
                                row.map((column, indexColumn) => {
                                    return <Square key={'square_' + keySquare++} row={indexRow} column={indexColumn} data={column} onClick={onClickPlayGame.bind(this)}></Square>
                                })
                            }
                        </div>

                    }) : '')
                }
            </div>
        </div>
    )
}

const mapStateToProps = state => ({
    status: state.gameData.status,
    message: state.gameData.message,
    hasErrors: state.gameData.hasErrors,
    loading: state.gameData.loading,
    gameResult: state.gameData.gameResult,
});

export default connect(mapStateToProps)(Board);