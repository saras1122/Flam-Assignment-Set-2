# Flam Assignment

This repository contains three selected questions:

1. **N-Queens Problem** using Backtracking.
2. **Cycle Detection in Directed Graph** using Kahn's Algorithm (Topological Sort).
3. **WeatherTrack**

## 📘 Question1 – N-Queens Problem

### 🧠 Approach

This is solved using **backtracking**:

- A 2D `char[][]` array represents the chessboard.
- The algorithm tries to place one queen per row, checking for safety using:
  - Column check
  - Left diagonal check
  - Right diagonal check
- If a position is safe, a queen (`'Q'`) is placed and the next row is attempted.
- If a configuration is complete (`row == n`), it is added to the output list.
- If no safe position is found in the current row, the algorithm backtracks by removing the previously placed queen and trying the next column.
- Each valid board configuration is converted into a list of strings before adding to the final result to match the expected output format.


### 📈 Time Complexity

- Worst-case: `O(N!)` – All permutations of queen placements.

---

## 📗 Question2 – Circular Dependency Detection

### 🧠 Approach

This is solved using **Kahn’s Algorithm** (a form of topological sorting):

- Build an adjacency list for the graph.
- Maintain an array of **indegree** for each node.
- Enqueue all nodes with indegree `0`.
- Perform BFS: decrement indegree of neighbors.
- If all nodes are processed, there's no cycle. Otherwise, a cycle exists.

### 📈 Time Complexity

- `O(V + E)` where `V` = number of nodes, `E` = number of edges.

## 📗 Question4 – WeatherTrack App - Daily Weather Tracking

WeatherTrack is a Java-based Android application that helps users track daily and weekly weather statistics in their city. The app fetches real-time weather data from a mock API, stores it locally every 6 hours, and visualizes trends using line charts. It follows the MVVM architecture, ensuring clean separation of concerns and scalability.The MVVM architecture in WeatherTrack promotes a modular and maintainable codebase by separating concerns across layers. The Persistence Layer uses Room to cache weather data locally, enabling offline access. The Network Layer uses Retrofit and OkHttp to fetch current weather data from a mock API. The Repository Layer acts as a single source of truth, managing data flow between the local database and remote API. The ViewModel Layer holds UI-related data, exposing it through LiveData to ensure reactive updates. The UI Layer observes the ViewModel and renders weather information like current conditions and weekly summaries. The WorkManager Layer handles background tasks, scheduling automatic weather data sync every 6 hours to keep information up to date.

### 📌 Key features
- 🌡 Current Weather Display: Shows live temperature, humidity, and sky conditions.
- 📊 Weekly Summary Charts: Displays temperature trends over the past 7 days using line charts.
- ⏱ Automatic Background Sync: Weather updates every 6 hours using WorkManager.
- 🔁 Manual Refresh: Option to fetch updated weather data on demand.
- 📆 Daily Breakdown: Check historical weather data for any past day.
- 📶 Offline Support: Cached data allows full functionality without internet access.

![image alt]([https://github.com/saras1122/Flam-Assignment-Set-2/blob/master/Screenshot%202025-06-02%20153521.png?raw=true](https://github.com/saras1122/Flam-Assignment-Set-2/blob/4a5e6a153ecb40425532c79422a647788a105274/Screenshot%202025-06-02%20153521.png))



