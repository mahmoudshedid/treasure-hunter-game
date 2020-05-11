import * as actions from '../actions/game.action'

export const initialState = {
    status: true,
    message: 'Start Game',
    hasErrors: false,
    loading: true,
    gameResult: {},
}

export default function gameReducer(state = initialState, action) {
    switch (action.type) {
        case actions.START_GAME:
            // console.log('Loading Start.');
            return { ...state, loading: true }
        case actions.START_GAME_SUCCESS:
            // console.log('Start.');
            return { ...state, gameResult: action.payload.gameResult, status: action.payload.status, message: action.payload.message, loading: false }
        case actions.START_GAME_FAILURE:
            // console.log('Start failed.');
            return { ...state, status: false, hasErrors: true }
        case actions.RESUME_GAME:
            // console.log('Loading Resume.');
            return { ...state, loading: true }
        case actions.RESUME_GAME_SUCCESS:
            // console.log('Resume.');
            return { ...state, gameResult: action.payload.gameResult, status: action.payload.status, message: action.payload.message, loading: false }
        case actions.RESUME_GAME_FAILURE:
            // console.log('Resume failed.');
            return { ...state, status: false, hasErrors: true }
        case actions.PLAY_GAME:
            // console.log('Play Game.');
            return { ...state, gameResult: action.payload.gameResult, status: action.payload.status, message: action.payload.message }
        case actions.PLAY_GAME_FAILURE:
            // console.log('Play Game failed.');
            return { ...state, status: false, hasErrors: true }
        default:
            // console.log('Default.');
            return state
    }
}
