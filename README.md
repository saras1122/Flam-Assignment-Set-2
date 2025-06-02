# Flam Assignment

This repository contains three selected questions:

1. **N-Queens Problem** using Backtracking.
2. **Cycle Detection in Directed Graph** using Kahn's Algorithm (Topological Sort).
3. **WeatherTrack**

## 📘 Question1 – N-Queens Problem

### ❓ Problem

Place `n` queens on an `n x n` chessboard such that no two queens attack each other. A queen can attack another queen horizontally, vertically, or diagonally.

### 🧠 Approach

This is solved using **backtracking**:

- A 2D `char[][]` array represents the chessboard.
- The algorithm tries to place one queen per row, checking for safety using:
  - Column check
  - Left diagonal check
  - Right diagonal check
- If a position is safe, a queen (`'Q'`) is placed and the next row is attempted.
- If a configuration is complete (`row == n`), it is added to the output list.

### 📈 Time Complexity

- Worst-case: `O(N!)` – All permutations of queen placements.

---

## 📗 Question2 – Circular Dependency Detection

### ❓ Problem

Given a directed graph with `n` nodes and a list of edges, determine if there is a **circular dependency** (i.e., a cycle in the graph).

### 🧠 Approach

This is solved using **Kahn’s Algorithm** (a form of topological sorting):

- Build an adjacency list for the graph.
- Maintain an array of **indegree** for each node.
- Enqueue all nodes with indegree `0`.
- Perform BFS: decrement indegree of neighbors.
- If all nodes are processed, there's no cycle. Otherwise, a cycle exists.

### 📈 Time Complexity

- `O(V + E)` where `V` = number of nodes, `E` = number of edges.

