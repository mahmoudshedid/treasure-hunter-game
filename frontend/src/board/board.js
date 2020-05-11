import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import './board.css';
import Square from './square';
import { fetchStartGame, fetchPlayGame } from '../actions/game.action';

const Board = ({ dispatch, status, message, hasErrors, loading, gameResult }) => {

    useEffect(() => {
        // dispatch(fetchStartGame());
    }, [dispatch]);

    const onStartGameClick = () => {
        dispatch(fetchStartGame());
    }

    const onClickPlayGame = (row, column) => {
        dispatch(fetchPlayGame(row, column));
    }

    let keyContainer = 0;
    let keySquare = 0;

    if (loading) return (
        <div>
            <Button variant="contained" color="primary" onClick={onStartGameClick}>
                Start New Game
            </Button>
            <p>Loading Game...</p>
        </div>
    )
    if (hasErrors) return <p>Unable to display game.</p>;

    return (
        <div>
            <Button variant="contained" color="primary" onClick={onStartGameClick}>
                Start New Game
            </Button>
            <p><span>Top Ten:</span></p>
            <p>
                {
                    gameResult.topTen.map((score, index) => {
                        return <span key={'score_' + index} className="top-score">{score}</span>
                    })
                }
            </p>
            <p>{message}</p>
            <p>You did {gameResult.moves} move and you are complete {gameResult.turns} turns.</p>
            {/* {console.log(gameResult)} */}
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