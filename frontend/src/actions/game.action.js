export const START_GAME = 'START_GAME';
export const START_GAME_SUCCESS = 'START_GAME_SUCCESS'
export const START_GAME_FAILURE = 'START_GAME_FAILURE';
export const PLAY_GAME = 'PLAY_GAME';
export const PLAY_GAME_FAILURE = 'PLAY_GAME_FAILURE';

export const startGame = () => ({ type: START_GAME })
export const startGameSuccess = gameResult => ({ type: START_GAME_SUCCESS, payload: gameResult });
export const startGameFailure = () => ({ type: START_GAME_FAILURE });
export const playGame = gameResult => ({ type: PLAY_GAME, payload: gameResult });
export const playGameFailure = () => ({ type: PLAY_GAME_FAILURE });

export function fetchStartGame() {
    return async dispatch => {
        dispatch(startGame())
        try {
            const response = await fetch('http://localhost:4000/start-game', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            const data = await response.json()

            dispatch(startGameSuccess(data))
        } catch (error) {
            dispatch(startGameFailure());
        }
    }
}

export function fetchPlayGame(row, column) {
    return async dispatch => {
        try {
            const response = await fetch('http://localhost:4000/set-positions', {
                method: 'POST',
                body: JSON.stringify({ row: row, column: column }),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                }
            });

            const data = await response.json()
            // console.log('Call Fetch.')
            dispatch(playGame(data))
        } catch (error) {
            dispatch(playGameFailure())
        }
    }
}