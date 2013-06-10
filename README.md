# Clojure Minesweeper 

## 1. Summary

Minesweeper is a well-known game in which the player has to clear bombs, or at least learn the positions of the bombs. The goal of this game is clearing the entire board without clicking on any bombs.

## 2. The game

In minesweeper, the player starts with a board which contains a large amount of unidentified tiles. Each tile can be cleared (left-click). There are two scenario's when clearing a tile. Either there is a bomb underneath the tile, or there is none. In the first case: the player loses instantly. In the other case, the amount of adjacent bombs is shown. If this amount is zero, the adjacent tiles are cleared automatically. 
The player can also flag tiles (right-click). This means that the player thinks underneath that tile is a bomb. This has no further impact on the game, but should make it easier to clear the board.
The player wins when all non-bomb tiles are cleared.

## 3. Justification of choices

- _Flagging_ of tiles, to show where a bomb might be, is handled with javascript and jQuery, because there's no real other option, since the original application makes uses of posting a form on each input click.
- The timer in the application originates from an assignment for the course _Advanced Programming_. That's some real re-use of code.

## 4. What not to overlook?

- _winner?_ and _loser?_ are, in our options, some really nice functions.
- Once the player clicks a tile without adjacent bombs, the adjacent tiles are cleared automatically. It was not easy to implement this functionallity.
- The hyper-fancy and ultra-modern animated GIF explosion once the player clicks on a bomb.

## 5. Install notes

Download and run:
- `git clone https://github.com/BartGoes/clojure-minesweeper.git`
- Go to the directory in your cmd
- `lein ring server`
- `lein ring server` may tell you to install some things first. If so, use `lein self-install`, then do `lein ring server` again.

## 6. Key bindings

- `left-mouse` to clear a tile.
- `right-mouse` to flag a tile.

## 7. Further notes

- If you wish to run on a different port, make sure to change this in both `repl.clj` and `script.js`