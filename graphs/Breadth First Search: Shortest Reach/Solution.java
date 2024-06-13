import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'bfs' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER m
     *  3. 2D_INTEGER_ARRAY edges
     *  4. INTEGER s
     */
     static class Digraph {
         public List<List<Integer>> graph;
         public boolean[] marked;
         public Digraph(List<List<Integer>> adjacency, int V) {
            marked = new boolean[V];
            graph = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                List<Integer> adj = new ArrayList<>();
                graph.add(adj);
                marked[i] = false;
            }
            for (int i = 0; i < adjacency.size(); i++) {
                int from = adjacency.get(i).get(0) - 1;
                int to = adjacency.get(i).get(1) - 1;
                addEdge(from, to);
                addEdge(to, from);
            }
         }
         public void addEdge(int v, int w) {
            List<Integer> adj = graph.get(v);
            adj.add(w);
         }
     }
     
    public static List<Integer> bfs(int n, int m, List<List<Integer>> edges, int s) {
    // Write your code here
        Digraph digraph = new Digraph(edges, n);
        
        List<Integer> distances = new ArrayList<>();
        for (int i = 0; i < n; i++) distances.add(-1);
        distances.set(s - 1, 0);
        
        Queue<Integer> q = new LinkedList<>();
        q.add(s - 1);
        digraph.marked[s - 1] = true;
        
        while (!q.isEmpty()) {
            int v = q.poll();
            int dist = distances.get(v);
            List<Integer> adj = digraph.graph.get(v);
            for (int i = 0; i < adj.size(); i++) {
                int w = adj.get(i);
                if (!digraph.marked[w]) {
                    digraph.marked[w] = true;
                    distances.set(w, dist + 6);
                    q.add(w);
                }
            }
        }
        
        distances.remove(s - 1);
        return distances;
        
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int m = Integer.parseInt(firstMultipleInput[1]);

                List<List<Integer>> edges = new ArrayList<>();

                IntStream.range(0, m).forEach(i -> {
                    try {
                        edges.add(
                            Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                int s = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> result = Result.bfs(n, m, edges, s);

                bufferedWriter.write(
                    result.stream()
                        .map(Object::toString)
                        .collect(joining(" "))
                    + "\n"
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
