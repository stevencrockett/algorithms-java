package algorithms.graph;

import datastructures.graph.Graph;
import datastructures.queue.ArrayQueue;
import datastructures.queue.Queue;
import datastructures.stack.ArrayStack;
import datastructures.stack.Stack;

import java.util.Iterator;
import java.util.Optional;

/**
 * Implementation of depth-first search (DFS), that performs the search from a
 * given vertex and caches the results of the search for fast queries later.
 */
public class DFS {

    /**
     * Index of the vertex that the search begins from.
     */
    private final int rootVertex;


    /**
     * Records which vertices have been visited already.
     */
    private final boolean[] isMarked;


    /**
     * Array of backpointers, recording for each vertex the vertex from which it was first visited from.
     * Allows us to trace a path from any visited vertex back to the root vertex.
     */
    private final int[] edgeTo;


    /**
     * Perform depth-first search on the given graph from the specified vertex in the graph.
     *
     * @param g The graph to search.
     * @param rootVertex Index of the first vertex to search from.
     */
    public DFS(final Graph g, final int rootVertex) {
        this.rootVertex = rootVertex;

        isMarked = new boolean[g.vertexCount()];
        edgeTo = new int[g.vertexCount()];

        graphSearch(g, rootVertex); // begin the recursive search from the starting vertex
    }


    /**
     * Recursive implementation of depth-first search on the graph.
     *
     * @param g The graph to search.
     * @param currentVertex Index of the vertex we are currently examining.
     */
    private void graphSearch(final Graph g, final int currentVertex) {

        isMarked[currentVertex] = true; // indicate the vertex as being visited

        // recursively visit all unvisited neighbours of the current vertex
        g.adj(currentVertex).forEachRemaining(neighbour -> {
            if (!isMarked[neighbour]) {
                // record that we are going to the next vertex for the first time from the current vertex
                edgeTo[neighbour] = currentVertex;
                graphSearch(g, neighbour); // continue search from the unmarked neighbour
            }
        });
    }


    /**
     * Check if there exists a path between the root vertex and the specified vertex (i.e. check if they are connected).
     *
     * @param otherVertex Index of the vertex to check if there is a path to.
     * @return <CODE>true</CODE> if there is a path to the vertex from the root vertex; <CODE>false</CODE> otherwise.
     */
    public boolean hasPathTo(final int otherVertex) {
        return isMarked[otherVertex];
    }


    /**
     * Provides an iterator over the sequence of indices from the root vertex to the specified
     * index, provided that a path exists.
     *
     * @param otherVertex Index of the vertex to find the path to.
     * @return Optional containing an iterator over the vertices in the path if a path exists.
     */
    public Optional<Iterator<Integer>> pathTo(final int otherVertex) {

        // first check that a path exists to begin with
        if (!isMarked[otherVertex]) {
            return Optional.empty();
        }

        // the order is in reverse when following the backpointers, so we push the vertices
        // onto a stack which will reverse the order when iterated over
        final Stack<Integer> path = new ArrayStack<>();
        path.push(otherVertex); // push the destination vertex on to the stack

        // follow backpointers, pushing each vertex along the route on to the stack
        int previousVertex = edgeTo[otherVertex];
        while (previousVertex != rootVertex) {
            path.push(previousVertex);
            previousVertex = edgeTo[previousVertex];
        }
        path.push(rootVertex); // finally, add the root vertex to complete the path

        return Optional.of(path.iterator());
    }


    /**
     * Find all vertices reachable from the specified vertex.
     *
     * @param g The graph to search.
     * @param rootVertex Index of the first vertex to search from.
     * @return Iterator over all vertices reachable from the specified vertex in depth-first order.
     */
    public static Iterator<Integer> search(final Graph g, final int rootVertex) {

        // records which vertices have been visited already.
        final boolean[] isMarked = new boolean[g.vertexCount()];

        // holds all vertices visited in order
        final Queue<Integer> allVertices = new ArrayQueue<>();

        // hold vertices in a stack so we can visit them easily in depth-first order
        final Stack<Integer> nextVertices = new ArrayStack<>();
        nextVertices.push(rootVertex); // begin with the root vertex

        // continue search until we've examined every vertex in the connected component
        while (!nextVertices.isEmpty()) {

            // visit the next vertex in the stack
            final int currentVertex = nextVertices.pop().get();

            // check that we haven't visited this vertex already
            if (!isMarked[currentVertex]) {

                isMarked[currentVertex] = true;

                // add vertex to queue of all vertices visited
                allVertices.enqueue(currentVertex);

                // add all unvisited neighbours of the current vertex to the stack
                g.adj(currentVertex).forEachRemaining(neighbour -> {
                    if (!isMarked[neighbour]) {
                        nextVertices.push(neighbour);
                    }
                });

            }

        }

        return allVertices.iterator();
    }

}
