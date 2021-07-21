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
    
    public ArrayList<ArrayList<Boolean>> adjMatrix;
    
    public int vertices;
    
    public Graph()
    {
        adjMatrix = new ArrayList<ArrayList<Boolean>>();
        vertices = 0;
    }
    
    public void graphInfo()
    {
        System.out.println("Vertices:\t" + vertices);
        System.out.println("AdjIndexes:\t" + adjMatrix.size() + " x " + adjMatrix.get(0).size());
//        printGrid();
    }
    
    public ArrayList<Integer> getConnections(int id)
    {
    	ArrayList<Integer> connections = new ArrayList<Integer>();
    	//Check North
    	for(int i = 0; i < adjMatrix.get(id).size(); i++)
    	{
    		if(adjMatrix.get(id).get(i))
    		{
    			connections.add(i);
    		}
    	}
    	return connections;
    }
    
    public void addEdge(int idA, int idB)
    {
        adjMatrix.get(idA).set(idB, true);
        adjMatrix.get(idB).set(idA, true);
    }
    
    public boolean isConnected(int idA, int idB)
    {
        return adjMatrix.get(idA).get(idB);
    }
    
    public void addVertice()
    {
        ArrayList<Boolean> tempArray = new ArrayList<Boolean>();
        for(int i = 0; i < vertices; i++)
        {
            tempArray.add(false);
        }
        adjMatrix.add(tempArray);
        
        vertices++;
        
        for(ArrayList<Boolean> x : adjMatrix)
        {
            x.add(false);
        }
    }
    
    public void printGrid()
    {
        for(ArrayList<Boolean> x : adjMatrix)
        {
            for(boolean y : x)
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
