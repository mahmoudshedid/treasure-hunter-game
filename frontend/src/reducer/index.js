import { combineReducers } from 'redux'

import gameReducer from './game.reducer'

const rootReducer = combineReducers({
    gameData: gameReducer,
})

export default rootReducer