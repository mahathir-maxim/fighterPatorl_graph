
/**
 * @author maxim Copyright 2020
 */


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;


public class Graph
{

    public ArrayList<String> vertices; // holds the vertices of the graph
    public HashMap<String,HashMap<String,Double>> neighbors; // holds the adjecency list

    public Graph() // No arg constructor that creates an arraylist of vertices and a hashmap for edges in adjecency list
    {
        this.vertices=new ArrayList<>();
        this.neighbors=new HashMap<String,HashMap<String,Double>>();
    }

    // adds a weighted edge in the hashmap adjecency list
    public void addEdge(String Source,String Destination, Double cost, HashMap<String,Double> val)
    {

        val.put(Destination, cost); // first put the destination and weight in the received hashmap
        this.neighbors.put(Source, val); // put the source and val hashmap in neighbors hasmap
    }

    // this function checks if the vertice is in the vertices list
    public boolean checkVertice(String str)
    {
        int i=this.vertices.indexOf(str);
        if(i==-1)
        {
            //System.out.println("check vertice result for "+str+": "+false);
            return false;
        }
        else
        {
            //System.out.println("check vertice result for "+str+": "+true);
            return true;
        }
    }


    // This function checks if the edge is in the hashmap and if it exists returs its weight else returns null
    public Double checkEdge(String source, String dest)
    {
        if(!(checkVertice(source) && checkVertice(dest)))
        {
            //System.out.println("Yo check edge result for "+source+" to "+dest+": "+0.0);
            return 0.0;
        }
        else
        {
           Double c= neighbors.get(source).get(dest);
           if (c==null )
           {
               //System.out.println("check edge result for "+source+" to "+dest+": "+0.0);
               return c;
           }
           else
           {
               //System.out.println("check edge result for "+source+" to "+dest+": "+c);
               return c;
           }
        }
    }

    // This function displays the existing graph
    public void DisplayGraph()
    {
        for (int i=0;i<vertices.size();i++)
        {
            System.out.println("neighbors of "+vertices.get(i));
            System.out.println(neighbors.get(vertices.get(i)));
        }
    }



    ArrayList <Node> list= new ArrayList<Node>(); // creates a node list for sorting

    public class Node // this node bears value for each pilot name, route weight and validity of the route information
    {
        public String name;
        public String status;
        public Double weight;

        public Node() // default constructor
        {

        }
        public Node(String s1, String s2, Double d) // initializes with the given value in this constructor
        {
            this.name=s1;
            this.status=s2;
            this.weight=d;
        }

        public int compare(Node n1, Node n2)  // this function compares two nodes by their weight
        {
            if(n1.weight<n2.weight)
            {
                return -1;
            }
            else if(n1.weight==n2.weight)
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }

        public String toString() // this function returns the string form of the node with all data
        {
            String w8=fmt(weight);
            String str=name+"\t"+w8+"\t"+status;
            return str;
        }

        public int compareName( Node n2) // this function compares two nodes by their name(string) and returns -1,0,1
        {
            String str1=this.name;
            String str2=n2.name;

            int l1=this.name.length();
            int l2=n2.name.length();
            int lmin= Math.min(l1,l2);

            for (int a=0; a<lmin; a++) // compare letter by letter
            {
                int str1_ch = (int)str1.charAt(a);
                int str2_ch = (int)str2.charAt(a);

                if (str1_ch != str2_ch)
                {
                    return str1_ch - str2_ch;
                }
            }

            if (l1 != l2)
            {
                return l1 - l2;
            }

            else  // when both names are same
            {
                return 0;
            }
        }

    }

    public void addList(String name, String status, Double weight) // adds a node to the node list for sorting
    {
        Node nd=new Node(name,status,weight);
        list.add(nd);
    }


    public void displaySort(PrintWriter write) // this function sorts the nodelist using bubble sort by their weight
    {                                           // for nodes with  same weight it sorts them alphabetically
        int n=list.size();

        // sorting by weight using bubble sort
        for(int i=0; i<n-1; i++)
        {
            for(int j = 0; j < n-i-1; j++)
            {
                System.out.println("//////////////////////// ");
                if(list.get(j).weight>list.get(j+1).weight)
                {
                    Node temp= list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1, temp);
                }

            }
        }

        // sorting by name for nodes with same weight alphabetically using bubble sort
        for(int a=0; a<n-1; a++)
        {
            for(int b = 0; b < n-a-1; b++)
            {
                if((list.get(b).weight-list.get(b+1).weight)<0.0000001  && (list.get(b).weight-list.get(b+1).weight)>=0)
                {
                    System.out.println("********************************* ");

                    if (list.get(b).compareName(list.get(b+1))>0)
                    {
                        System.out.println("****************I am here***************** ");
                        Node temp2= list.get(b);
                        list.set(b,list.get(b+1));
                        list.set(b+1, temp2);
                    }

                }


            }
        }


        // uses a for loop to display sorted node list using node's toString function
        for(int x=0; x<list.size();x++)
        {
            write.println(list.get(x).toString());
        }
    }

    // this function formats double values and gets rid of unnecessary 0's and returns a string
    public String fmt(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}
