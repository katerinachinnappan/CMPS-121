//i didn't know javaScript beforehand so I used a lot of help on this assignment, hopefully it works

//global variables
    var board = document.getElementById('board');
	var tileFlag = 1;

	//the initial configuration board tileFlag (1-15)
	refresh();
	
	// if you click on any tile, listen for this click and shift the appropriate tile
	board.addEventListener('click', function(e){
		if(tileFlag == 1){
			moveTile(e.target);
		}
	});
	
	//link appropriate methods to the buttons
	document.getElementById('refresh').addEventListener('click', refresh);
	document.getElementById('scramble').addEventListener('click', scramble);

	/**refresh the puzzle to it's intial configuration state***/
	function refresh(){
	    colorBoard();
	}

	//color the board to it's initial configuration state
	function colorBoard(){
			if(tileFlag == 0){
			return;
		}

		board.innerHTML = ''; //html content of the board

		var n = 1;
		for(var i = 0; i <= 3; i++){
			for(var j = 0; j <= 3; j++){
				var tile = document.createElement('span');//board span
				/*style the number tiles so they fit the puzzle box accordingly*/
				tile.id = 'tile-'+i+'-'+j;
				tile.style.left = (j*99+1*j+1)+'px';//change the 99px to any size to see a difference
				tile.style.top = (i*99+1*i+1)+'px';
				//now column the tiles red and white!
				if(n <= 15){// if n is <= 15
					tile.classList.add('number');//board.span.number css
					//if even and >0 column is white otherwise column red
					tile.classList.add((j%2==0 && i%2>0 || i%2==0 && j%2>0) ? 'white' : 'red');
					//increment the numbers (1-15) and set them on the corresponding tiles
					var tileNumbers = "";
					tileNumbers = (n++).toString();
					tile.innerHTML = tileNumbers;
				}
				//otherwise column tile black
				else {
					tile.className = 'black';
				}

				//now append each tile to the puzzle board...
				board.appendChild(tile);
			}
		}
	}

	function scramble(){

		var previoustile;
		var i = 1;
		var interval = setInterval(function(){
			if(i <= 75){
				var adjacent = getAdjacenttiles(board.querySelector('.black'));
				if(previoustile){
					for(var j = adjacent.length-1; j >= 0; j--){
						if(adjacent[j].innerHTML == previoustile.innerHTML){
							adjacent.splice(j, 1);
						}
					}
				}
				//get a random tile and remember the position for the next scramble
				previoustile = adjacent[Math.floor(Math.random()*(adjacent.length-1 - 0 + 1)) + 0]
				moveTile(previoustile);
				i++;
			}
			else {
				clearInterval(interval);
			}
		}, 1);

	}

	function gettile(row, column){

        var tiles = document.getElementById('tile-'+row+'-'+column);
		return tiles;

	}

     //get the adjacent tiles
	function getAdjacenttiles(tile){

		var id = tile.id.split('-');

		var row = parseInt(id[1]);
		var column = parseInt(id[2]);

		var adjacent = [];

		// return all the adjacent tiles
		if(column < 3){adjacent.push(gettile(row, column+1));}
		if(column > 0){adjacent.push(gettile(row, column-1));}
		if(row < 3){adjacent.push(gettile(row+1, column));}
		if(row > 0){adjacent.push(gettile(row-1, column));}

		return adjacent;

	}

	//tiles that are adjacent to the black tile
	function getblackAdjacenttile(tile){

		// adjacent tiles
		var adjacentTiles = getAdjacenttiles(tile);

		// now search the tiles that are adjacent to black tile specifically
		for(var i = 0; i < adjacentTiles.length; i++){
			if(adjacentTiles[i].className == 'black'){
				return adjacentTiles[i];
			}
		}

		// black adjacent tile was not found
		return false;

	}

    //swap the tiles
	function moveTile(tile){
		
		//if the tile is not black colored, then move it
		if(tile.className != 'black'){
			
			// get the tiles adjacent to black tiles
			var blacktile = getblackAdjacenttile(tile);

			//if i move the tile adjacent to black
			if(blacktile){
			//hold the color and number of tile
				var temp = {id: tile.id, style: tile.style.cssText};
				
				// swap the tiles
				tile.id = blacktile.id;
				tile.style.cssText = blacktile.style.cssText;
				blacktile.style.cssText = temp.style;
				blacktile.id = temp.id;
				
				if(tileFlag == 1){
					//check if the game is solved and for win
					var n = 1;
		        //iterate thru each tile to make sure its in the correct spot
		            for(var i = 0; i <= 3; i++){
			            for(var j = 0; j <= 3; j++){
			                 var temp = n.toString();
			                var temp0 = gettile(i, j).innerHTML;
			            //the tiles aren't in order
				        if(n <= 15 && temp0 != temp){
					        return;
				        }
				        n++;
			    }
		}

            //if won, display popup with win message
            window.alert("you won, congrats, ciao")
				}
			}
		}
		
	}
