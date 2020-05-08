import React from 'react';
import logo from './logo.svg';
import './App.css';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import TypoGraphy from '@material-ui/core/Typography';
import Board from './board/board';

function App() {
  return (
    <div>
      <div>
        <AppBar color="primary" position="static">
          <Toolbar>
            <TypoGraphy color="inherit">
              Treasure hunter Game
         </TypoGraphy>
          </Toolbar>
        </AppBar>
      </div>
      <div className="container">
        <Board />
      </div>
    </div>
  );
}

export default App;
