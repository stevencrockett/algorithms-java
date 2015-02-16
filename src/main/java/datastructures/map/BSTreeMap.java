package datastructures.map;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Ordered map implemented using an unbalanced binary search tree. Since balance isn't preserved, depth
 * of the tree may vary by the order in which keys are inserted.
 */
public class BSTreeMap<Key extends Comparable<Key>, Value> implements OrderedMap<Key, Value> {

    /**
     * Root node of the BST.
     */
    private Node root;


    /**
     * Current number of key-value pairs in the hash table.
     */
    private int size = 0;


    public BSTreeMap() { }


    /**
     * {@inheritDoc}
     */
    @Override
    public void put(final Key key, final Value value) {

        Node parent = null;

        Node x = root;

        while (x != null) {
            final int cmp = key.compareTo(x.key);

            if (cmp < 0) {
                parent = x;
                x = x.left;
            } else if (cmp > 0) {
                parent = x;
                x = x.right;
            } else {
                // found a node with the same key, so just update the value
                x.value = value;
                return;
            }
        }

        if (parent == null) {
            root = new Node(key, value, null);
        } else if (key.compareTo(parent.key) < 0) {
            parent.left = new Node(key, value, parent);
        } else {
            parent.right = new Node(key, value, parent);
        }

        size++;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Value> get(final Key key) {

        final Node keyNode = search(key);

        if (keyNode != null) {
            return Optional.of(keyNode.value);
        }

        return Optional.empty();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final Key key) {
        final Node keyNode = search(key);

        // there's nothing to delete
        if (keyNode == null) {
            return;
        }

        deleteNode(keyNode);
        size--;
    }

    /**
     * Delete the given node in the binary tree. Assumes the given node is non-null.
     *
     * @param node Node in the tree to be deleted.
     */
    private void deleteNode(final Node node) {
        // 3 cases to consider

        // 1. node doesn't have children. can trivially be deleted by removing pointer from parent
        if (node.left == null && node.right == null) {
            if (node.parent == null) {
                root = null;
            } else if (node == node.parent.left){
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
            return;
        }

        // 2. node has one child. make node's parent point to its single child
        if (node.left != null && node.right == null) {
            if (node.parent == null) {
                root = node.left;
            } else if (node == node.parent.left){
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }
            return;
        } else if (node.left == null) {
            // node.right is always non-null at this point in control flow
            if (node.parent == null) {
                root = node.right;
            } else if (node == node.parent.right){
                node.parent.right = node.right;
            } else {
                node.parent.left = node.right;
            }
            return;
        }

        // 3. node has both children.

        // predecessor or successor should be moved to where the deleted node is to preserve binary search property.
        // copy it to the deleted node location, then delete the node at its previous location.
        // choose randomly between predecessor and successor to help preserve tree balance.

        Node swappedNode;
        if (ThreadLocalRandom.current().nextBoolean()) {
            swappedNode = predecessorNode(node);
        } else {
            swappedNode = successorNode(node);
        }
        node.key = swappedNode.key;
        node.value = swappedNode.value;

        // recursively, call delete. will simplify to one of the first two cases as
        // the predecessor or successor can't have two children
        deleteNode(swappedNode);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final Key key) {
        return search(key) != null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * Search the tree for the node with the given key.
     *
     * @param key Key to search for in the tree.
     * @return Node containing the key.
     */
    private Node search(final Key key) {

        Node x = root;

        while (x != null) {
            final int cmp = key.compareTo(x.key);

            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                break;
            }
        }

        return x;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Key> min() {
        if (root == null) {
            return Optional.empty();
        }

        final Node min = minNode(root);
        return Optional.of(min.key);
    }

    /**
     * Find the minimum node in the tree rooted at the given node. Assumes the given node is non-null.
     *
     * @param root Node that represents the root of the tree to search.
     * @return Leftmost (i.e. minimum) node in the tree.
     */
    private Node minNode(final Node root) {
        Node currentNode = root;
        while (currentNode.left != null) {
            currentNode = currentNode.left;
        }
        return currentNode;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Key> max() {
        if (root == null) {
            return Optional.empty();
        }

        final Node max = maxNode(root);
        return Optional.of(max.key);
    }

    /**
     * Find the maximum node in the tree rooted at the given node. Assumes the given node is non-null.
     *
     * @param root Node that represents the root of the tree to search.
     * @return Rightmost (i.e. maximum) node in the tree.
     */
    private Node maxNode(final Node root) {
        Node currentNode = root;
        while (currentNode.right != null) {
            currentNode = currentNode.right;
        }
        return currentNode;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Key> predecessor(final Key key) {
        final Node startNode = search(key);

        // no predecessor if key is not in map
        if (startNode == null) {
            return Optional.empty();
        }

        final Node predecessor = predecessorNode(startNode);

        if (predecessor != null) {
            return Optional.of(predecessor.key);
        }

        return Optional.empty();
    }

    /**
     * Find the next smallest node in the tree compared to the given node, as compared by their keys.
     * Assumes the given node is non-null.
     *
     * @param node Node to find the next smallest node of.
     * @return The next smallest node.
     */
    private Node predecessorNode(final Node node) {

        // check if node has left child. predecessor is maximum of tree rooted at left child
        if (node.left != null) {
            return maxNode(node.left);
        }

        // otherwise go up the tree
        Node previousNode = node;
        Node currentNode = previousNode.parent;

        // if no parent, there is no predecessor
        // if node was a right child, predecessor node is the parent
        while (currentNode != null && previousNode == currentNode.left) {
            previousNode = currentNode;
            currentNode = currentNode.parent;
        }

        return currentNode;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Key> successor(final Key key) {
        final Node startNode = search(key);

        // no successor if key is not in map
        if (startNode == null) {
            return Optional.empty();
        }

        final Node successor = successorNode(startNode);

        if (successor != null) {
            return Optional.of(successor.key);
        }

        return Optional.empty();
    }

    /**
     * Find the next largest node in the tree compared to the given node, as compared by their keys.
     * Assumes the given node is non-null.
     *
     * @param node Node to find the next largest node of.
     * @return The next largest node.
     */
    private Node successorNode(final Node node) {

        // check if node has right child. successor is minimum of tree rooted at right child
        if (node.right != null) {
            return minNode(node.right);
        }

        // otherwise go up the tree
        Node previousNode = node;
        Node currentNode = previousNode.parent;

        // if no parent, there is no successor
        // if node was a left child, successor node is the parent
        while (currentNode != null && previousNode == currentNode.right) {
            previousNode = currentNode;
            currentNode = currentNode.parent;
        }

        return currentNode;
    }


    /**
     * A node in the binary tree, storing a key-value pair and pointers to its parent and children.
     */
    private class Node {
        private Key key;
        private Value value;
        private Node parent, left, right;

        public Node(final Key key, final Value value, final Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }

}
