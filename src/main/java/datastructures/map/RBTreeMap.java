package datastructures.map;

import java.util.Optional;

/**
 * Ordered map implemented using a red-black tree to achieve a balanced BST.
 *
 * @param <Key> Type of the keys stored in the map which can be compared and ordered.
 * @param <Value> Type of the values stored in the map.
 */
public class RBTreeMap<Key extends Comparable<Key>, Value> implements OrderedMap<Key, Value>  {

    /**
     * Possible colours of the nodes in the red-black tree.
     */
    enum Colour {
        RED,
        BLACK
    }


    /**
     * A node in the red-black tree, storing a key-value pair, pointers to
     * its parent and children, and an associated colour.
     *
     * @param <K> Type of the key stored in the node which can be compared and ordered.
     * @param <V> Type of the value stored in the node.
     */
    private static class Node<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private Node<K, V> parent, left, right;
        private Colour colour = Colour.RED;

        public Node(final K key, final V value, final Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }


    /**
     * Root node of the BST.
     */
    private Node<Key, Value> root;


    /**
     * Current number of key-value pairs in the tree.
     */
    private int size = 0;


    public RBTreeMap() { }


    /**
     * {@inheritDoc}
     */
    @Override
    public void put(final Key key, final Value value) {
        Node<Key, Value> parent = null;

        Node<Key, Value> x = root;

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

        // create the new node now that we've found the insertion position.
        // since nodes are coloured red by default, we need to potentially adjust the
        // tree if any properties of the RB tree are violated
        if (parent == null) {
            root = new Node<>(key, value, null);
            fixRBTreeInsert(root);
        } else if (key.compareTo(parent.key) < 0) {
            parent.left = new Node<>(key, value, parent);
            fixRBTreeInsert(parent.left);
        } else {
            parent.right = new Node<>(key, value, parent);
            fixRBTreeInsert(parent.right);
        }

        size++;
    }

    /**
     * Fix the red-black tree after an insertion of the given node, going up the tree making
     * sure that the red-black properties are preserved and ensuring balance of the tree.
     *
     * @param x Red-coloured node that was just inserted.
     */
    private void fixRBTreeInsert(Node<Key, Value> x) {

        while (x != root && x.colour == Colour.RED) {

            final Node<Key, Value> grandParent = x.parent.parent;

            // if grandparent is null then the parent must be root.
            // since the root is black and x is red, there is no violation.
            if (grandParent == null) {
                break;
            }

            // first check if the parent is a left or right child
            // the three main cases are symmetrical
            if (x.parent == grandParent.left) {

                final Node<Key, Value> uncle = grandParent.right; // the uncle/aunt of node x

                if (getColour(uncle) == Colour.RED) {
                    // case 1: the parent and uncle are red
                    // we can do a simple recolouring and move on
                    setColourRed(grandParent);
                    x = grandParent;
                    continue;
                } else if (x == x.parent.right) {
                    // case 2: the node is a right child
                    // there is a zig-zag pattern. i.e. no straight path from x to grandparent.
                    // fix by rotations. rotate left around parent to end up in case 3
                    x = x.parent;
                    rotateLeft(x);
                }

                // case 3: the node is a left child.
                // parent is also a left child, so there is a straight path to the grandparent
                // rotate right around grandparent so the parent becomes the root of the subtree
                rotateRight(grandParent);

                // recolour. grandparent becomes red and parent is black
                setColourBlack(x.parent);
            } else {
                // parent is a right child.

                final Node<Key, Value> uncle = grandParent.left; // the uncle/aunt of node x

                if (getColour(uncle) == Colour.RED) {
                    // case 1: the parent and uncle are red
                    // we can do a simple recolouring and move on
                    setColourRed(grandParent);
                    x = grandParent;
                    continue;
                } else if (x == x.parent.left) {
                    // case 2: the node is a left child
                    // there is a zig-zag pattern. i.e. no straight path from x to grandparent.
                    // fix by rotations. rotate right around parent to end up in case 3
                    x = x.parent;
                    rotateRight(x);
                }

                // case 3: the node is a right child.
                // parent is also a right child, so there is a straight path to the grandparent
                // rotate left around grandparent so the parent becomes the root of the subtree
                rotateLeft(grandParent);

                // recolour. grandparent becomes red and parent is black
                setColourBlack(x.parent);
            }
        }

        root.colour = Colour.BLACK; // ensure that the root is always black
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Value> get(final Key key) {

        Node<Key, Value> x = root;

        while (x != null) {
            final int cmp = key.compareTo(x.key);

            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                return Optional.of(x.value);
            }
        }

        return Optional.empty();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final Key key) {
        final Node<Key, Value> keyNode = search(key);

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
    private void deleteNode(final Node<Key, Value> node) {

        // 3 cases to consider

        // case 1: node doesn't have children. can trivially be deleted by removing pointer from parent
        if (node.left == null && node.right == null) {

            // if the leaf node is red it can trivially be deleted. if black then pretend
            // the node is its own replacement to adjust tree first then delete

            if (node.colour == Colour.BLACK) {
                fixRBTreeDelete(node);
            }

            // delete node
            if (node == root) {
                root = null;
            } else if (node == node.parent.left){
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }

        } else if (node.left != null && node.right == null) {
            // case 2: node has one child. make node's parent point to its single child
            // has single left child
            final Node<Key, Value> leftChild = node.left;

            if (node == root) {
                root = node.left;
                root.parent = null;
            } else if (node == node.parent.left){
                node.parent.left = leftChild;
                leftChild.parent = node.parent; // make node point to its grandparent
            } else {
                node.parent.right = leftChild;
                leftChild.parent = node.parent; // make node point to its grandparent
            }

            // fix tree if the left child (which has been moved) is black
            if (leftChild.colour == Colour.BLACK) {
                fixRBTreeDelete(leftChild);
            }

        } else if (node.left == null) {
            // case 2 continued: node has single right child
            final Node<Key, Value> rightChild = node.right;

            if (node == root) {
                root = node.right;
                root.parent = null;
            } else if (node == node.parent.right){
                node.parent.right = rightChild;
                rightChild.parent = node.parent; // make node point to its grandparent
            } else {
                node.parent.left = rightChild;
                rightChild.parent = node.parent; // make node point to its grandparent
            }

            // fix tree if the right child (which has been moved) is black
            if (rightChild.colour == Colour.BLACK) {
                fixRBTreeDelete(rightChild);
            }

        } else {
            // case 3: node has both children.

            // move successor to where the deleted node is, by copying the key-value pair.
            // this will not violate the red-black properties as the node retains its original colour.
            // then delete the successor's original node. this may cause a violation so we may need to fix the tree

            final Node<Key, Value> successor = minNode(node.right);

            node.key = successor.key;
            node.value = successor.value;

            // recursively delete the successor's original node
            // will simplify to one of the first two cases, so the tree will get fixed after deletion
            deleteNode(successor);
        }
    }

    /**
     * Fix the red-black tree after deletion of a node, going up the tree making
     * sure that the red-black properties are preserved and ensuring balance of the tree.
     *
     * @param x Black-coloured node to begin the fixing procedure from.
     */
    private void fixRBTreeDelete(Node<Key, Value> x) {

        while (x != root && x.colour == Colour.BLACK) {

            // 4 cases if x is a left child. other 4 cases are symmetrical.
            if (x == x.parent.left) {

                Node<Key, Value> sibling = x.parent.right;

                // case 1: sibling is red
                if (sibling.colour == Colour.RED) {
                    // recolour and rotate to create one of the other cases
                    sibling.colour = Colour.BLACK;
                    x.parent.colour = Colour.RED;
                    rotateLeft(x.parent);
                    sibling = x.parent.right; // new sibling after rotation
                }

                // case 2: black sibling, which has black children
                if (getColour(sibling.left) == Colour.BLACK && getColour(sibling.right) == Colour.BLACK) {
                    sibling.colour = Colour.RED;
                    x = x.parent;
                } else {

                    // case 3: black sibling, with red left child and black right child
                    if (getColour(sibling.right) == Colour.BLACK) {
                        setColour(sibling.left, Colour.BLACK);
                        sibling.colour = Colour.RED;
                        rotateRight(sibling);
                        sibling = x.parent.right;
                    }

                    // case 4: black sibling, with red right child
                    sibling.colour = x.parent.colour;
                    x.parent.colour = Colour.BLACK;
                    setColour(sibling.right, Colour.BLACK);
                    rotateLeft(x.parent);
                    x = root; // terminates the loop
                }

            } else {
                // the same 4 cases, but with notions of left and right reversed.

                Node<Key, Value> sibling = x.parent.left;

                // case 1: sibling is red
                if (sibling.colour == Colour.RED) {
                    // recolour and rotate to create one of the other cases
                    sibling.colour = Colour.BLACK;
                    x.parent.colour = Colour.RED;
                    rotateRight(x.parent);
                    sibling = x.parent.left; // new sibling after rotation
                }

                // case 2: black sibling, which has black children
                if (getColour(sibling.left) == Colour.BLACK && getColour(sibling.right) == Colour.BLACK) {
                    sibling.colour = Colour.RED;
                    x = x.parent;
                } else {

                    // case 3: black sibling, with black left child and red right child
                    if (getColour(sibling.left) == Colour.BLACK) {
                        setColour(sibling.right, Colour.BLACK);
                        sibling.colour = Colour.RED;
                        rotateLeft(sibling);
                        sibling = x.parent.left;
                    }

                    // case 4: black sibling, with red left child
                    sibling.colour = x.parent.colour;
                    x.parent.colour = Colour.BLACK;
                    setColour(sibling.left, Colour.BLACK);
                    rotateRight(x.parent);
                    x = root; // terminates the loop
                }
            }
        }

        x.colour = Colour.BLACK; // ensure that the root is always black
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
    private Node<Key, Value> search(final Key key) {

        Node<Key, Value> x = root;

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

        final Node<Key, Value> min = minNode(root);
        return Optional.of(min.key);
    }

    /**
     * Find the minimum node in the tree rooted at the given node. Assumes the given node is non-null.
     *
     * @param root Node that represents the root of the tree to search.
     * @return Leftmost (i.e. minimum) node in the tree.
     */
    private Node<Key, Value> minNode(final Node<Key, Value> root) {
        Node<Key, Value> currentNode = root;
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

        final Node<Key, Value> max = maxNode(root);
        return Optional.of(max.key);
    }

    /**
     * Find the maximum node in the tree rooted at the given node. Assumes the given node is non-null.
     *
     * @param root Node that represents the root of the tree to search.
     * @return Rightmost (i.e. maximum) node in the tree.
     */
    private Node<Key, Value> maxNode(final Node<Key, Value> root) {
        Node<Key, Value> currentNode = root;
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
        final Node<Key, Value> startNode = search(key);

        // no predecessor if key is not in map
        if (startNode == null) {
            return Optional.empty();
        }

        final Node<Key, Value> predecessor = predecessorNode(startNode);

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
    private Node<Key, Value> predecessorNode(final Node<Key, Value> node) {

        // check if node has left child. predecessor is maximum of tree rooted at left child
        if (node.left != null) {
            return maxNode(node.left);
        }

        // otherwise go up the tree
        Node<Key, Value> previousNode = node;
        Node<Key, Value> currentNode = previousNode.parent;

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
        final Node<Key, Value> startNode = search(key);

        // no successor if key is not in map
        if (startNode == null) {
            return Optional.empty();
        }

        final Node<Key, Value> successor = successorNode(startNode);

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
    private Node<Key, Value> successorNode(final Node<Key, Value> node) {

        // check if node has right child. successor is minimum of tree rooted at right child
        if (node.right != null) {
            return minNode(node.right);
        }

        // otherwise go up the tree
        Node<Key, Value> previousNode = node;
        Node<Key, Value> currentNode = previousNode.parent;

        // if no parent, there is no successor
        // if node was a left child, successor node is the parent
        while (currentNode != null && previousNode == currentNode.right) {
            previousNode = currentNode;
            currentNode = currentNode.parent;
        }

        return currentNode;
    }


    /**
     * Rotate the subtree rooted at the given node to the left.
     *
     * @param pivotNode Node to rotate the subtree around.
     */
    private void rotateLeft(final Node<Key, Value> pivotNode) {
        final Node<Key, Value> newParent = pivotNode.right; // record the right node (which will become the parent node)
        pivotNode.right = newParent.left; // as x is lowered it gains y's left child as its right child

        // link the node node correctly to its new parent
        if (pivotNode.right != null) {
            pivotNode.right.parent = pivotNode;
        }

        newParent.parent = pivotNode.parent; // y takes the parent of x since x is now a child of y

        // need to link the parent correctly to the new child too
        if (pivotNode.parent == null) {
            root = newParent;
        } else if (pivotNode == pivotNode.parent.left) {
            // if x was a left child, then y will need to be the left child
            pivotNode.parent.left = newParent;
        } else {
            // x was the right child, so y needs to be the right child
            pivotNode.parent.right = newParent;
        }

        // finally, connect x and y properly
        newParent.left = pivotNode; // x becomes the left child
        pivotNode.parent = newParent;
    }

    /**
     * Rotate the subtree rooted at the given node to the right.
     *
     * @param pivotNode Node to rotate the subtree around.
     */
    private void rotateRight(final Node<Key, Value> pivotNode) {
        final Node<Key, Value> newParent = pivotNode.left; // record the left node (which will become the parent node)
        pivotNode.left = newParent.right; // as x is lowered it gains y's right child as its left child

        // link the node node correctly to its new parent
        if (pivotNode.left != null) {
            pivotNode.left.parent = pivotNode;
        }

        newParent.parent = pivotNode.parent; // y takes the parent of x since x is now a child of y

        // need to link the parent correctly to the new child too
        if (pivotNode.parent == null) {
            root = newParent;
        } else if (pivotNode == pivotNode.parent.left) {
            // if x was a left child, then y will need to be the left child
            pivotNode.parent.left = newParent;
        } else {
            // x was the right child, so y needs to be the right child
            pivotNode.parent.right = newParent;
        }

        // finally, connect x and y properly
        newParent.right = pivotNode; // x becomes the right child
        pivotNode.parent = newParent;
    }


    /**
     * Sets the colour of the given node to red and the colour of its children to black.
     *
     * @param node Node to recolour.
     */
    private void setColourRed(final Node<Key, Value> node) {

        node.colour = Colour.RED;

        setColour(node.left, Colour.BLACK);
        setColour(node.right, Colour.BLACK);
    }


    /**
     * Sets the colour of the given node to black and the colour of its children to red.
     *
     * @param node Node to recolour.
     */
    private void setColourBlack(final Node<Key, Value> node) {

        node.colour = Colour.BLACK;

        setColour(node.left, Colour.RED);
        setColour(node.right, Colour.RED);
    }


    /**
     * Gets the colour of the given node. Null nodes are treated as black.
     *
     * @param node Node to get the colour of.
     * @return Colour of te given node.
     */
    private static Colour getColour(final Node node) {
        return (node == null) ? Colour.BLACK : node.colour;
    }


    /**
     * Set the colour of the given node.
     *
     * @param node Node to change the colour of.
     * @param colour New colour for the given node.
     */
    private static void setColour(final Node node, final Colour colour) {
        if (node != null) {
            node.colour = colour;
        }
    }

}
