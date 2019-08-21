# Artificial Intelligence Game: 8-puzzle

```
File description:
  - main.java: Main function with three different menu options
  - Board.java: Board information
  - Solver.java: Solution with using h1(the number of misplace tiles)
  - Solver2.java: Solution with using h2(Manhattan Distance)
```

## Approach

### a. User Selection: 
A user interface provide three menu options for the program.
- (1) Input a specific 8-puzzle configuration
- (2) Generate random 8-puzzle problem
- (3) Read 8-puzzle input from the .txt file

### b. Data Structure: 
In order to improve the performance and speed of the program, This project use ‘character array’ instead of ‘integer array’ and used “1- dimensional” array instead of “2-dimensional” array.

### c. HeuristicFunction:
- (1) h1: the number of misplaced tiles
  - Using “for loop” to check if the number is in the right position or not.
- (2) h2: the sum of the distances of the tiles from their goal position
  - Use equation: |(i / 3) - char[i] / 3| + |(i % 3) - char[i] % 3| to check how many steps should move to the goal position.

### d. Open Set and Close Set:
- (1) Open set: the board which has been created but doesn’t consider yet.
  - Method: add the board into priority queue and use comparator to sort the queue by consider it’s heuristic function and depths.
- (2) Close Set: the board which already been considered as solution.
  - Method: put the board into hash map, then the program will check if the new board is already visited or not. 
  
### e. Search Cost: 
Counter add 1 when new child nodes been created.

### f. Board and Node Information:
- (1) Board information: only contain heuristic value.
- (2) Node information: make every board as a node, the node information contain moves(which is depths), previous node(which is it’s parent).
  - The final depths for the goal steps is the final node’s moves value, and the program can follow the previous node information to print out the each step from the initial state to the final state.

## Analysis
  By comparing the output result, we can find that the average search cost and the average run time from depth 2 to depth 6 for A* search algorithm with using different heuristic function h1 and h2 are almost the same.
  
  However, starting from depth 8, the average search cost and average run time using h1 (the number of misplaced tiles) cost more than the one which using h2 (Manhattan Distance). And after depth 20 the average run time which using h1 increase dramatically which shows in the figure below.

  Therefore, after testing 5000 random puzzles we can conclude that using A* search algorithm with h2 generates less node and cost less time to solve 8-puzzle problem. Thus, A* search algorithm with Manhattan distance heuristic is more efficient.

![](README_IMG/8puzzel.png)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Author: Hank Tsou
* Contact: hank630280888@gmail.com
* Project from California State Polytechnic University, Pomona, Computer Science, CS-4200 Artificial Intelligence
