import java.util.ArrayList;

public class Graph 
{
    /**
     * 
     */
    private boolean[][] myMatrix;

    /**
     * 
     */
    private int[][] myGrid;

    /**
     * 
     */
    private ArrayList<ArrayList<Boolean>> myAdjMatrix;

    /**
     * 
     */
    private int myVertices;

    public Graph()
    {
        myAdjMatrix = new ArrayList<ArrayList<Boolean>>();
        myVertices = 0;
    }

    public void graphInfo()
    {
        System.out.println("Vertices:\t" + myVertices);
        System.out.println("AdjIndexes:\t" + myAdjMatrix.size() + " x " + myAdjMatrix.get(0).size());
        //        printGrid();
    }

    public ArrayList<Integer> getConnections(final int theId)
    {
        final ArrayList<Integer> connections = new ArrayList<Integer>();
        //Check North
        for (int i = 0; i < myAdjMatrix.get(theId).size(); i++)
        {
            if  (myAdjMatrix.get(theId).get(i))
            {
                connections.add(i);
            }
        }
        return connections;
    }

    public void addEdge(final int theIdA, final int theIdB)
    {
        myAdjMatrix.get(theIdA).set(theIdB, true);
        myAdjMatrix.get(theIdB).set(theIdA, true);
    }

    public boolean isConnected(final int theIdA, final int theIdB)
    {
        return myAdjMatrix.get(theIdA).get(theIdB);
    }

    public void addVertice()
    {
        final ArrayList<Boolean> tempArray = new ArrayList<Boolean>();
        for (int i = 0; i < myVertices; i++)
        {
            tempArray.add(false);
        }
        myAdjMatrix.add(tempArray);

        myVertices++;

        for (ArrayList<Boolean> x : myAdjMatrix)
        {
            x.add(false);
        }
    }

    public void printGrid()
    {
        for (ArrayList<Boolean> x : myAdjMatrix)
        {
            for (boolean y : x)
            {
                System.out.print(y ? "1 " : "0 ");
            }
            System.out.println();
        }
    }

    class Node
    {
        /**
         * 
         */
        private Node myNorth;

        /**
         * 
         */
        private Node mySouth;

        /**
         * 
         */
        private Node myEast;

        /**
         * 
         */
        private Node myWest;

        /**
         * 
         */
        private String mySymbol;

        Node()
        {
            myNorth = null;
            mySouth = null;
            myEast = null;
            myWest = null;
            mySymbol = " ";
        }

        public ArrayList<Node> getNeighbors()
        {
            final ArrayList<Node> nodes = new ArrayList<Node>();
            if (myNorth != null)
            {
                nodes.add(myNorth);
            }
            if (mySouth != null)
            {
                nodes.add(mySouth);
            }
            if (myEast != null)
            {
                nodes.add(myEast);
            }
            if (myWest != null)
            {
                nodes.add(myWest);
            }
            return nodes;
        }

        public String toString()
        {
            return mySymbol;
        }

    }

}
