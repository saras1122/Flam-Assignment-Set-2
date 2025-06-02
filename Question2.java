import java.util.*;

public class Question2 {

    public static boolean hasCircularDependency(int n, List<List<Integer>> edges) {
        int[] indegree = new int[n];
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (List<Integer> edge : edges) {
            int a = edge.get(0);
            int b = edge.get(1);
            graph.get(b).add(a);
            indegree[a]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            count++;
            for (int v : graph.get(u)) {
                indegree[v]--;
                if (indegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        return count != n;
    }

    public static void main(String[] args) {
        // Test case 1
        int n1 = 4;
        List<List<Integer>> edges1 = Arrays.asList(
            Arrays.asList(0, 1),
            Arrays.asList(1, 2),
            Arrays.asList(2, 3)
        );
        System.out.println(hasCircularDependency(n1, edges1));

        // Test case 2
        int n2 = 4;
        List<List<Integer>> edges2 = Arrays.asList(
            Arrays.asList(0, 1),
            Arrays.asList(1, 2),
            Arrays.asList(2, 0)
        );
        System.out.println(hasCircularDependency(n2, edges2));  
    }
}
